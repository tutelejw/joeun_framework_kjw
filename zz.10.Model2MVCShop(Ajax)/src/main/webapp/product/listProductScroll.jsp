<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <c:set var="menuParam" value="${param.menu}" />
  <c:set var="title" value="상품 목록 조회" />
  <c:if test="${menuParam eq 'manage'}">
    <c:set var="title" value="상품관리" />
  </c:if>

  <title>${title}</title>
  <link rel="stylesheet" href="/css/admin.css" type="text/css" />
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

  <style type="text/css">
  /* AJAX로 동적으로 삽입되는 상세 정보의 줄 바꿈을 방지 */
  .detail-content {
      white-space: nowrap; /* 줄 바꿈 방지 */
      overflow: hidden;    /* 내용이 셀을 넘어갈 경우 숨김 */
      text-overflow: ellipsis; /* 숨길 경우 ...으로 표시 */
  }
  </style>

<script type="text/javascript">
  // 변수 선언
  var currentPage = ${resultPage.currentPage}; // 현재 페이지
  var totalCount = ${resultPage.totalCount};   // 전체 상품 수
  var pageSize = ${search.pageSize};           // 페이지당 상품 수
  var loading = false;                          // 중복 요청 방지
  var isEnd = false;                            // 마지막 페이지 여부

  // 초기 로딩 시 최대 재귀 호출 횟수
  var maxInitialLoadAttempts = 3;
  var initialLoadCount = 0;

  // JSP 변수 menuParam을 JS 변수로 전달
  var menuParam = '${menuParam}';

  // 초기 자동 로딩 함수 (재귀 호출 제한)
  function checkAndLoadMore() {
    if (isEnd || loading) return;

    if ($(document).height() <= $(window).height()) {
      if (initialLoadCount < maxInitialLoadAttempts) {
        initialLoadCount++;
        loadNextPage().then(() => {
          checkAndLoadMore();
        });
      }
    }
  }

  // 다음 페이지 데이터 불러오기 (Promise 반환)
  function loadNextPage() {
    return new Promise(function(resolve, reject) {
      loading = true;
      currentPage++;

      var maxPage = Math.ceil(totalCount / pageSize);
      if (currentPage > maxPage) {
        isEnd = true;
        $("#endMessage").show();
        loading = false;
        resolve();
        return;
      }

      $.ajax({
        url: "/product/listProductScroll",
        method: "GET",
        data: {
          currentPage: currentPage,
          pageSize: pageSize
        },
        success: function (html) {
          var newRows = $(html).find("table").first().find("tr.ct_list_pop, tr.product-detail, tr[bgcolor='D6D7D6']");

          if (newRows.length === 0) {
            isEnd = true;
            $("#endMessage").show();
          } else {
            var currentCount = $("tr.ct_list_pop").length;
            var i = 1;
            newRows.each(function () {
              if ($(this).hasClass("ct_list_pop")) {
                $(this).find("td").first().text(currentCount + i);
                i++;
              }
            });

            $("table").first().append(newRows);
          }
        },
        error: function () {
          alert("데이터를 불러오는 중 오류가 발생했습니다.");
        },
        complete: function () {
          loading = false;
          resolve();
        }
      });
    });
  }

  $(document).ready(function () {
    // 1. 상품명 빨간색 스타일
    $(".product-link").css("color", "red");

    // 2. 상품명 클릭 이벤트
    $(document).on('click', '.product-link', function () {
      const prodNo = $(this).data('prodno');  // 상품번호
      const prodName = $(this).text().trim(); // 상품명

      if (!prodNo) {
        alert("상품번호 정보가 없습니다!");
        return;
      }

      var $currentDetailRow = $("#" + prodNo + "-detail");

      if ($currentDetailRow.is(":visible")) {
        $currentDetailRow.hide();
      } else {
        $.ajax({
          url: "/product/json/getProduct/" + prodNo,
          method: "GET",
          dataType: "json",
          headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
          },
          success: function (JSONData) {
            if (JSONData) {
              var detailText = "상품명: " + JSONData.prodName + "<br/>" 
                + "  가격 : " + JSONData.price  + "<br/>"
                + "  상태 : " + JSONData.proTranCode  + "<br/>"
                + "  등록일 : " + JSONData.regDateString + "<br/>";

              var displayValue = '<div class="detail-content">' + detailText + '</div>';

              $(".product-detail").hide();

              var $detailRowToOpen = $("#" + prodNo +"-detail");
              $detailRowToOpen.find("td").html(displayValue);
              $detailRowToOpen.show();

            } else {
              alert("상품 정보를 불러오는 데 실패했습니다.");
            }
          },
          error: function () {
            alert("서버에서 상품 정보를 불러오는 데 오류가 발생했습니다.");
          }
        });
      }

      // 클릭 시점에도 혹시 부족하면 자동 로딩 시도 (안정성용)
      checkAndLoadMore();
    });

    // 8. 검색 버튼 클릭 이벤트
    $('#btnSearch').on('click', function () {
      $('input[name="currentPage"]').val('1');
      $('form[name="detailForm"]').submit();
    });

    // 초기 로딩 시 스크롤 부족하면 최대 3번만 자동 로딩
    checkAndLoadMore();
  });

  // Throttle 처리용 타이머 변수
  let throttleTimer = null;

  // 스크롤 이벤트 감지 (Throttle 적용)
  $(window).on('scroll', function () {
    if (throttleTimer) return;

    throttleTimer = setTimeout(function () {
      throttleTimer = null;

      if (loading || isEnd) return;

      if ($(window).scrollTop() + $(window).height() >= $(document).height() - 150) {
        loadNextPage();
      }
    }, 300);
  });

  // 검색 / 페이지 전환용 함수
  function fncGetUserList(currentPage) {
    $("#currentPage").val(currentPage)
    $("form").attr("method", "POST").attr("action", "/product/listProductScroll").submit();
  }
</script>



</head>

<body bgcolor="#ffffff" text="#000000">
<div style="width:98%; margin-left:10px;">

  <form name="detailForm" action="${pageContext.request.contextPath}/product/listProductScroll" method="post">

    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
      <tr>
        <td colspan="11">
          전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지
        </td>
      </tr>
      <tr>
        <td class="ct_list_b" width="100">No</td>
        <td class="ct_line02"></td>
        <td class="ct_list_b" width="150">상품명</td>
        <td class="ct_line02"></td>
        <td class="ct_list_b" width="150">가격</td>
        <td class="ct_line02"></td>
        <td class="ct_list_b" width="700">등록일</td>
        <td class="ct_line02"></td>
        <td class="ct_list_b">상태</td>
      </tr>
      <tr><td colspan="11" bgcolor="808285" height="1"></td></tr>

      <c:set var="i" value="0" />
      <c:forEach var="product" items="${list}">
        <c:set var="i" value="${i + 1}" />

        <tr class="ct_list_pop">
          <td align="center">${i}</td>
          <td></td>
          <td align="left">
            <span class="product-link"
                  data-prodno="${product.prodNo}"
                  style="cursor:pointer;" id="${product.prodNo}">
              ${product.prodName}
            </span>
          </td>
          <td></td>
          <td align="left">${product.price}</td>
          <td></td>
          <td align="left">${product.regDate}</td>
          <td></td>
          <td align="left">${product.proTranCode}</td>
        </tr>
        <tr><td colspan="11" bgcolor="D6D7D6" height="1"></td></tr>
        <tr id="${product.prodNo}-detail" class="product-detail" style="display:none;">
          <td colspan="11" width="100%">
            </td>
        </tr>
      </c:forEach> </table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
      <tr>
        <td align="center">
          <input type="hidden" id="currentPage" name="currentPage" value="1" />
          <%-- <jsp:include page="../common/pageNavigator.jsp" /> --%>
        </td>
      </tr>
    </table>

  </form>

</div>
<div id="endMessage" style="text-align:center; padding:15px; display:none;">
  <strong>📌 마지막 데이터입니다.</strong>
</div>
</body>
</html>