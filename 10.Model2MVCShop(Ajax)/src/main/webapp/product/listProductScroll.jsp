<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
  <c:set var="cPath" value="${pageContext.request.contextPath}" />
  <c:set var="menuParam" value="${param.menu}" />
  <c:set var="title" value="상품 목록 조회" />
  <c:if test="${menuParam eq 'manage'}">
    <c:set var="title" value="상품관리" />
  </c:if>

  <title>${title}</title>
  <link rel="stylesheet" href="${cPath}/css/admin.css" type="text/css" />
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

  <style type="text/css">
    /* AJAX로 동적으로 삽입되는 상세 정보 */
    .detail-content {
      padding: 8px 12px;
      background-color: #f8f8f8;
      text-align: left;
      /* CSS 주석을 존중하여 한 줄로 표시되도록 유지 */
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
    .product-link {
      cursor: pointer;
    }
  </style>

  <script type="text/javascript">
    $(document).ready(function () {

      // 1. 상품명 빨간색으로 스타일 적용
      $(".product-link").css("color", "red");

      // 2. 상품명 클릭 이벤트 (이벤트 위임 방식 사용)
      $(document).on('click', '.product-link', function () {
        const prodNo = $(this).data('prodno');
        if (!prodNo) {
          alert("상품번호 정보가 없습니다!");
          return;
        }

        var $detailRow = $("#" + prodNo + "-detail");

        // 이미 열려 있으면 닫기만 하고 종료
        if ($detailRow.is(":visible")) {
          $detailRow.hide();
          return;
        }

        // 4. AJAX 요청으로 상품 상세 정보 받아오기
        $.ajax({
          url: "${cPath}/product/json/getProduct/" + prodNo,
          method: "GET",
          dataType: "json",
          success: function (JSONData) {
            if (JSONData) {
              
              var detailText = "상품명: " + JSONData.prodName 
                  + " | 가격 : " + JSONData.price
                  + " | 상태 : " + JSONData.proTranCode
                  + " | 등록일 : " + JSONData.regDate;

              var displayValue = '<div class="detail-content">' + detailText + '</div>';

              // 다른 상세 정보는 닫고, 현재 것만 보여주기
              $(".product-detail").not($detailRow).hide();

              $detailRow.find("td").html(displayValue);
              $detailRow.show();
              
            } else {
              alert("상품 정보를 불러오는 데 실패했습니다.");
            }
          },
          error: function () {
            alert("서버에서 상품 정보를 불러오는 데 오류가 발생했습니다.");
          }
        });
      });

      // 8. 검색 버튼 클릭 이벤트
      $('#btnSearch').on('click', function (e) {
        e.preventDefault(); 
        $('input[name="currentPage"]').val('1');
        $('form[name="detailForm"]').submit();
      });

    });
    
	// 페이지네이션 링크 클릭 시 사용되는 함수
	function fncGetUserList(currentPage) {
		$("#currentPage").val(currentPage);
		$("form[name='detailForm']").submit();
	}
  </script>
</head>

<body bgcolor="#ffffff" text="#000000">
<div style="width:98%; margin-left:10px;">

  <form name="detailForm" action="${pageContext.request.contextPath}/product/listProductScroll" method="post">
    
    <div style="margin: 10px 0; text-align: right;">
        <input type="text" name="searchKeyword" value="${param.searchKeyword}" placeholder="검색어 입력">
        <button type="button" id="btnSearch">검색</button>
    </div>

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

      <c:forEach var="product" items="${list}" varStatus="status">
        <tr class="ct_list_pop">
          <td align="center">
            ${resultPage.totalCount - ((resultPage.currentPage - 1) * resultPage.pageSize + status.index)}
          </td>
          <td></td>
          <td align="left">
            <span class="product-link" data-prodno="${product.prodNo}">
              ${product.prodName}
            </span>
          </td>
          <td></td>
          <td align="left"><fmt:formatNumber value="${product.price}" type="number" /></td>
          <td></td>
          <td align="left"><fmt:formatDate value="${product.regDate}" pattern="yyyy-MM-dd" /></td>
          <td></td>
          <td align="left">${product.proTranCode}</td>
        </tr>
        <tr><td colspan="11" bgcolor="D6D7D6" height="1"></td></tr>
        <tr id="${product.prodNo}-detail" class="product-detail" style="display:none;">
          <td colspan="11">
            <%-- 이 안의 내용은 AJAX를 통해 동적으로 채워집니다. --%>
          </td>
        </tr>
      </c:forEach>
    </table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
      <tr>
        <td align="center">
          <input type="hidden" id="currentPage" name="currentPage" value="${resultPage.currentPage}" />
          <jsp:include page="../common/pageNavigator.jsp" />
        </td>
      </tr>
    </table>
  </form>
</div>
</body>
</html>