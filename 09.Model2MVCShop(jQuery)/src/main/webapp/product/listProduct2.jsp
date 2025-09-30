<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ✅ 디버깅용 메시지 -->
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
  <link rel="stylesheet" href="/css/admin.css" type="text/css" />
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
  // 페이지가 완전히 로드된 후 실행되는 jQuery 함수
  $(document).ready(function () {

    // 🔍 검색 버튼 클릭 시 1페이지로 이동하고 폼 제출
    $('#btnSearch').on('click', function () {
      $('#currentPage').val(1);
      $('form[name="detailForm"]').submit();
    });

    // 📦 상품명 클릭 시 항상 상세보기 페이지로 이동
    $('.product-link').on('click', function () {
      const prodNo = $(this).data('prodno');

      // prodNo가 없으면 동작하지 않음 (예외 처리)
      if (!prodNo) return;

      // 항상 상세 페이지로 이동
      const url = `/product/getProduct?prodNo=${prodNo}`;
      location.href = url;
    });
  });
</script>
</head>

<body bgcolor="#ffffff" text="#000000">
<div style="width:98%; margin-left:10px;">

  <form name="detailForm" action="${pageContext.request.contextPath}/product/listProduct" method="post">
    <!-- 타이틀 -->
    <table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="15"><img src="/images/ct_ttl_img01.gif" width="15" height="37" /></td>
        <td background="/images/ct_ttl_img02.gif" style="padding-left:10px;">
          <span class="ct_ttl01">${title}</span>
        </td>
        <td width="12"><img src="/images/ct_ttl_img03.gif" width="12" height="37" /></td>
      </tr>
    </table>

    <!-- 검색 영역 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
      <tr>
        <td align="right">
          <select name="searchCondition" class="ct_input_g" style="width:80px;">
            <option value="0" ${search.searchCondition == 0 ? 'selected' : ''}>상품NO</option>
            <option value="1" ${search.searchCondition == 1 ? 'selected' : ''}>상품명</option>
          </select>
          <input type="text" name="searchKeyword"
                 value="${!empty search.searchKeyword ? search.searchKeyword : ''}"
                 class="ct_input_g" style="width:200px; height:20px;" />
        </td>
        <td align="right" width="70">
          <table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="/images/ct_btnbg01.gif" width="17" height="23" /></td>
              <td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
                <a href="javascript:void(0);" id="btnSearch">검색</a>
              </td>
              <td><img src="/images/ct_btnbg03.gif" width="14" height="23" /></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>

    <!-- 리스트 출력 -->
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
        <c:set var="action" value="" />
        <c:choose>
          <c:when test="${menuParam eq 'manage'}">
            <c:set var="action" value="update" />
          </c:when>
          <c:when test="${product.proTranCode eq '재고없음'}">
            <c:set var="action" value="" />
          </c:when>
          <c:otherwise>
            <c:set var="action" value="view" />
          </c:otherwise>
        </c:choose>

        <tr class="ct_list_pop">
          <td align="center">${i}</td>
          <td></td>
          <td align="left">
            <c:choose>
              <c:when test="${empty action}">
                ${product.prodName}
              </c:when>
              <c:otherwise>
                <a href="javascript:void(0);" class="product-link"
                   data-prodno="${product.prodNo}" data-action="${action}">
                  ${product.prodName}
                </a>
              </c:otherwise>
            </c:choose>
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

    <!-- 페이지 네비게이션 -->
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
