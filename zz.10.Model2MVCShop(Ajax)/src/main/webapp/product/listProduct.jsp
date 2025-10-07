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
    // JSP 변수 menuParam을 JS 변수로 전달
    var menuParam = '${menuParam}';

    $(document).ready(function () {

      // 1. 상품명 빨간색으로 스타일 적용
      $(".product-link").css("color", "red");

      // 2. 상품명 클릭 이벤트
      $(document).on('click', '.product-link', function () {
        const prodNo = $(this).data('prodno');  // 상품번호
        const prodName = $(this).text().trim(); // 상품명

        if (!prodNo) {
          alert("상품번호 정보가 없습니다!");
          return;
        }

        // 3. 클릭된 상품 상세 정보가 이미 열려 있는지 확인
        var $currentDetailRow = $("#" + prodNo + "-detail");  // 해당 상품번호에 대한 상세 정보 영역 (<tr>)
        
        // **수정된 부분:** currentDetail.is(":visible") 대신 $currentDetailRow.is(":visible") 사용
        if ($currentDetailRow.is(":visible")) {
          // 이미 열려 있으면, 상세 정보 숨기기
          $currentDetailRow.hide();
        } else {
          // 4. AJAX 요청으로 상품 상세 정보 받아오기
          $.ajax({
            url: "/product/json/getProduct/" + prodNo,  // 상품 번호로 상세 정보 요청
            method: "GET",
            dataType: "json",
            headers: {
              "Accept": "application/json",
              "Content-Type": "application/json"
            },
            success: function (JSONData) {
              // 5. 받은 데이터를 HTML로 동적으로 삽입
              if (JSONData) {
                
                // 줄 바꿈 <br/> 제거하고 | 구분자를 사용하여 한 줄로 표시
                var detailText = "상품명: " + JSONData.prodName + "<br/>" 
                    + "  가격 : " + JSONData.price  + "<br/>"
                    + "  상태 : " + JSONData.proTranCode  + "<br/>"
                    + "  등록일 : " + JSONData.regDateString + "<br/>";

                // 줄 바꿈 방지를 위해 detail-content 클래스를 가진 <div>로 감쌉니다.
                var displayValue = '<div class="detail-content">' + detailText + '</div>';


                // 6. 모든 다른 상세 정보를 숨김 (열려있는 다른 상세 정보들을 닫음)
                $(".product-detail").hide();

                // 7. 해당 상품에 대한 상세 정보 표시
                // **수정된 부분:** <tr>과 <td>의 처리를 명확히 분리
                var $detailRowToOpen = $("#" + prodNo +"-detail");
                
                // <tr> 내부의 <td>에 HTML 삽입
                $detailRowToOpen.find("td").html(displayValue);
                
                // <tr> 요소 자체를 보이게 처리
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
      });

      // 8. 검색 버튼 클릭 이벤트
      $('#btnSearch').on('click', function () {
        $('input[name="currentPage"]').val('1');
        $('form[name="detailForm"]').submit();
      });

    });
    
	// 검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
	function fncGetUserList(currentPage) {
		$("#currentPage").val(currentPage)
		$("form").attr("method" , "POST").attr("action" , "/product/listProduct").submit();
	}

  </script>
</head>

<body bgcolor="#ffffff" text="#000000">
<div style="width:98%; margin-left:10px;">

  <form name="detailForm" action="${pageContext.request.contextPath}/product/listProduct" method="post">

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
          <jsp:include page="../common/pageNavigator.jsp" />
        </td>
      </tr>
    </table>

  </form>

</div>
</body>
</html>