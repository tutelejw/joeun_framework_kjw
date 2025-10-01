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
    .detail-content { white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
    #loadingMessage { text-align:center; display:none; }
  </style>

  <script type="text/javascript">
  var menuParam = '${menuParam}';
  var currentPage = 1;
  var loading = false;
  var noMoreData = false;

  const loadMoreObserver = new IntersectionObserver((entries, observer) => {
      entries.forEach(entry => {
          if (entry.isIntersecting && !loading && !noMoreData) {
              loading = true;
              currentPage++;
              loadMoreProducts(currentPage);
          }
      });
  }, { rootMargin: '100px', threshold: 1.0 });

  // 무한스크롤: 마지막 row 변경 감시
  function observeLastRow() {
      loadMoreObserver.disconnect();
      var lastRow = $(".ct_list_pop:last");
      if (lastRow.length > 0) {
          loadMoreObserver.observe(lastRow[0]);
      }
  }

  // 상품 목록 로드 (AJAX)
  function loadMoreProducts(page) {
      $("#loadingMessage").show();
      $.ajax({
          url: "/product/listProductScroll",
          method: "GET",
          data: {
              currentPage: page,
              menu: menuParam
          },
          dataType: "html",
          success: function (data) {
              var $rows = $(data).find("table tr.ct_list_pop, table tr.product-detail, table tr:has(td[bgcolor])");
              if ($rows.length > 0) {
                  $("table").append($rows);
                  loading = false;
                  observeLastRow(); // 새로 추가 후, 마지막 row를 감시
              } else {
                  noMoreData = true;
              }
              $("#loadingMessage").hide();
          },
          error: function () {
              alert("상품 목록을 불러오는 데 실패했습니다.");
              loading = false;
              $("#loadingMessage").hide();
          }
      });
  }

  $(document).ready(function () {
      observeLastRow(); // 최초 로딩 시 마지막 row 감시
      // 첫 페이지 확인 및 선로딩
      // loadMoreProducts(currentPage); // 필요시 주석 해제
  });
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
          <td colspan="11" width="100%"></td>
        </tr>
      </c:forEach> 
    </table>
    <div id="loadingMessage">
      <p>로딩 중...</p>
    </div>
  </form>

</div>
</body>
</html>
