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

  <script type="text/javascript">
    // JSP 변수 menuParam을 JS 변수로 전달
    var menuParam = '${menuParam}';

    $(document).ready(function () {
    	  
    	  // 상품명 빨간색으로 스타일 적용
    	  $(".product-link").css("color", "red");
    	  
    	  // 상품명 클릭 이벤트
    	  $(document).on('click', '.product-link', function () {
    	    const prodNo = $(this).data('prodno');
    	    const proTranCode = $(this).data('protrancode');

    	    if (!prodNo) {
    	      alert("상품번호 정보가 없습니다!");
    	      return;
    	    }

    	    if (proTranCode === '재고없음') {
    	      alert("재고가 없습니다.");
    	      return;
    	    }

    	    let url = '';
    	    if (menuParam === 'manage') {
    	      url = '/product/updateProduct?prodNo=' + prodNo;
    	    } else {
    	      url = '/product/getProduct?prodNo=' + prodNo;
    	    }

    	    if (url) {
    	      window.location.href = url;
    	    }
    	  });

    	  // 검색 버튼 클릭
    	  $('#btnSearch').on('click', function () {
    	    $('input[name="currentPage"]').val('1');
    	    $('form[name="detailForm"]').submit();
    	  });
    	});

  </script>
</head>

<body bgcolor="#ffffff" text="#000000">
<div style="width:98%; margin-left:10px;">

  <form name="detailForm" action="${pageContext.request.contextPath}/product/listProduct" method="post">

    <!-- 검색 영역 생략 가능 -->

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
      data-protrancode="${product.proTranCode}"
      style="cursor:default;">
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
      </c:forEach>
    </table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
      <tr>
        <td align="center">
          <input type="hidden" id="currentPage" name="currentPage" value="1" />
          <jsp:include page="../common/pageNavigator.jsp" />
        </td>
      </tr>
    </table>

  </form>

</div>
</body>
</html>
