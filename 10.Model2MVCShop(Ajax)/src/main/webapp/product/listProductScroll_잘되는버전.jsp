<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${!empty list}">
    <div style="color:red;">[디버깅] list 에 데이터가 정상 있습니다.</div>
</c:if>
<c:if test="${empty list}">
    <div style="color:red;">[디버깅] list가 비어 있습니다.</div>
</c:if>
<c:if test="${empty resultPage}">
    <div style="color:red;">[디버깅] resultPage가 비어 있습니다.</div>
</c:if>
<c:if test="${empty search}">
    <div style="color:red;">[디버깅] search가 비어 있습니다.</div>
</c:if>

<html>
<head>
  <c:set var="menuParam" value="${param.menu}" />
  <c:set var="title" value="상품 목록 조회" />
  <c:if test="${menuParam eq 'manage'}">
    <c:set var="title" value="상품관리" />
  </c:if>

  <title>${title}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css" type="text/css" />
  
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

  <style type="text/css">
  .detail-content {
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
  }
  </style>

<script type="text/javascript">
  var currentPage = ${resultPage.currentPage};
  var totalCount = ${resultPage.totalCount};
  var pageSize = ${search.pageSize};
  var loading = false;
  var isEnd = false;
  var maxInitialLoadAttempts = 3;
  var initialLoadCount = 0;
  var menuParam = '${menuParam}';

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
          pageSize: pageSize,
          searchCondition: $('select[name="searchCondition"]').val(),
          searchKeyword: $('input[name="searchKeyword"]').val()
        },
        success: function (html) {
          // =================================================================== //
          // ▼▼▼ [핵심 수정] 정확한 테이블을 타겟하도록 클래스 선택자 사용 ▼▼▼ //
          var newRows = $(html).find("table.main-list-table").find("tr.ct_list_pop, tr.product-detail, tr[bgcolor='D6D7D6']");
          // ▲▲▲ [핵심 수정] 정확한 테이블을 타겟하도록 클래스 선택자 사용 ▲▲▲ //
          // =================================================================== //

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
            $("table.main-list-table").first().append(newRows);
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
    $(".product-link").css("color", "red");
    $(document).on('click', '.product-link', function () {
      const prodNo = $(this).data('prodno');
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
      checkAndLoadMore();
    });
    checkAndLoadMore();
  });

  let throttleTimer = null;
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

  function fncGetUserList(currentPage) {
    $("#currentPage").val(currentPage);
    $("form[name='detailForm']").submit();
  }
</script>

</head>

<body bgcolor="#ffffff" text="#000000">
<div style="width:98%; margin-left:10px;">
  <form name="detailForm" action="${pageContext.request.contextPath}/product/listProductScroll" method="post">
    <table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="15" height="37"><img src="/images/ct_ttl_img01.gif" width="15" height="37" /></td>
            <td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr><td width="93%" class="ct_ttl01">${title}</td></tr>
                </table>
            </td>
            <td width="12" height="37"><img src="/images/ct_ttl_img03.gif" width="12" height="37"/></td>
        </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
        <tr>
            <td align="right">
                <select name="searchCondition" class="ct_input_g" style="width:80px">
                    <option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품NO</option>
                    <option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
                </select>
                <input type="text" name="searchKeyword" value="${! empty search.searchKeyword ? search.searchKeyword : ""}" class="ct_input_g" style="width:200px; height:20px" > 
            </td>
            <td align="right" width="70">
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="17" height="23"><img src="/images/ct_btnbg01.gif" width="17" height="23"></td>
                        <td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
                            <a href="javascript:fncGetUserList('1');">검색</a>
                        </td>
                        <td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    
    <table class="main-list-table" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
      <tr>
        <td colspan="11">전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
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
        <c:set var="i" value="${ i + 1 }" />
        <tr class="ct_list_pop">
          <td align="center">${i}</td>
          <td></td>
          <td align="left">
            <span class="product-link" data-prodno="${product.prodNo}" style="cursor:pointer;" id="${product.prodNo}">${product.prodName}</span>
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
          <td colspan="11" width="100%"></td>
        </tr>
      </c:forEach> 
    </table>
    <input type="hidden" id="currentPage" name="currentPage" value="${resultPage.currentPage}" />
  </form>
</div>
<div id="endMessage" style="text-align:center; padding:15px; display:none;">
  <strong>📌 마지막 데이터입니다.</strong>
</div>
</body>
</html>