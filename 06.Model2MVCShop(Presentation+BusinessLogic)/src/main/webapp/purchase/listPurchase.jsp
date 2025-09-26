<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
  <title>${empty menuParam or menuParam ne 'manage' ? '구매 목록 조회' : '구매목록'}</title>
  <link rel="stylesheet" href="/css/admin.css" type="text/css">
  <script type="text/javascript">
    function fncGetPurchaseList(page) {
      document.getElementById("currentPage").value = page;
      document.detailForm.submit();
    }
  </script>
</head>

<body bgcolor="#ffffff" text="#000000">
<div style="width: 98%; margin-left: 10px;">
  <form name="detailForm" action="/listPurchase.do" method="post">
    <input type="hidden" id="currentPage" name="currentPage" value="1"/>

    <!-- 타이틀 -->
    <table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="15"><img src="/images/ct_ttl_img01.gif" width="15" height="37"/></td>
        <td background="/images/ct_ttl_img02.gif" style="padding-left:10px;">
          <span class="ct_ttl01">${empty menuParam or menuParam ne 'manage' ? '구매 목록 조회' : '구매목록'}</span>
        </td>
        <td width="12"><img src="/images/ct_ttl_img03.gif" width="12" height="37"/></td>
      </tr>
    </table>

    <!-- 리스트 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
      <tr>
        <td colspan="11">전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
      </tr>
      <tr>
        <td class="ct_list_b" width="100">No</td>
        <td class="ct_line02"></td>
        <td class="ct_list_b" width="150">회원ID</td>
        <td class="ct_line02"></td>
        <td class="ct_list_b" width="150">회원명</td>
        <td class="ct_line02"></td>
        <td class="ct_list_b">전화번호</td>
        <td class="ct_line02"></td>
        <td class="ct_list_b">배송현황</td>
        <td class="ct_line02"></td>
        <td class="ct_list_b">정보수정</td>
      </tr>
      <tr>
        <td colspan="11" bgcolor="808285" height="1"></td>
      </tr>

      <c:forEach var="purchase" items="${list}" varStatus="status">
        <tr class="ct_list_pop">
          <td align="center">
            <a href="/getPurchase.do?tranNo=${purchase.tranNo}">${status.index + 1}</a>
          </td>
          <td></td>
          <td align="left">
            <a href="/getUser.do?userId=${purchase.buyer.userId}">${purchase.buyer.userId}</a>
          </td>
          <td></td>
          <td align="left">${purchase.buyer.userName}</td>
          <td></td>
          <td align="left">${purchase.receiverPhone}</td>
          <td></td>
          <td align="left">${purchase.tranCode}</td>
          <td></td>
          <td align="left">
            <c:if test="${purchase.tranCode eq '배송중'}">
              <a href="/updatePurchaseDelivery.do?prodNo=${purchase.purchaseProd.prodNo}&tranCode=3">[물건도착]</a>
            </c:if>
          </td>
        </tr>
        <tr>
          <td colspan="11" bgcolor="D6D7D6" height="1"></td>
        </tr>
      </c:forEach>
    </table>

    <!-- 페이지 네비게이션 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
      <tr>
        <td align="center">
          <c:choose>
            <c:when test="${resultPage.currentPage <= resultPage.pageUnit}">
              ◀ 이전
            </c:when>
            <c:otherwise>
              <a href="javascript:fncGetPurchaseList('${resultPage.currentPage - 1}')">◀ 이전</a>
            </c:otherwise>
          </c:choose>

          <c:forEach var="i" begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}">
            <a href="javascript:fncGetPurchaseList('${i}')">${i}</a>
          </c:forEach>

          <c:choose>
            <c:when test="${resultPage.endUnitPage >= resultPage.maxPage}">
              이후 ▶
            </c:when>
            <c:otherwise>
              <a href="javascript:fncGetPurchaseList('${resultPage.endUnitPage + 1}')">이후 ▶</a>
            </c:otherwise>
          </c:choose>
        </td>
      </tr>
    </table>
  </form>
</div>
</body>
</html>
