<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 로그 찍기: JSTL core 태그를 이용한 디버깅 --%>
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
<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////

<%@ page import="java.util.List"  %>

<%@ page import="com.model2.mvc.service.domain.Product" %>
<%@ page import="com.model2.mvc.common.Search" %>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>

<%
	List<Product> list= (List<Product>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");
	
	Search search = (Search)request.getAttribute("search");
	//==> null 을 ""(nullString)으로 변경
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
%> 	/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
<%
String uri = request.getRequestURI();
String url = request.getRequestURL().toString();
String query = request.getQueryString();
String method = request.getMethod();

System.out.println("[로그] 요청 URI: " + uri);
System.out.println("[로그] 전체 URL: " + url);
System.out.println("[로그] 쿼리스트링: " + query);
System.out.println("[로그] 요청 방식: " + method);
%>

<html>
<head>
<c:set var="menuParam" value="${param.menu}" />
<c:set var="title" value="상품 목록 조회" />

<c:if test="${menuParam eq 'manage'}">
    <c:set var="title" value="상품관리" />
</c:if>


<title>${title}</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	// 검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
	//function fncGetProductList(currentPage) {
	function fncGetUserList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
	   	document.detailForm.submit();		
	}
</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="${pageContext.request.contextPath}/product/listProduct" method="post">


<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37" />
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">${title}</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
			<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
				<option value="0" <%= (searchCondition.equals("0") ? "selected" : "")%>>회원ID</option>
				<option value="1" <%= (searchCondition.equals("1") ? "selected" : "")%>>회원명</option>
				/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
				<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품NO</option>
				<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
			</select>
			<%--<input type="text" name="searchKeyword" value="<%= searchKeyword %>"  class="ct_input_g" style="width:200px; height:14px" >--%>
			<input type="text" name="searchKeyword" 
						value="${! empty search.searchKeyword ? search.searchKeyword : ""}"  
						class="ct_input_g" style="width:200px; height:20px" > 
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

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<%--
		<td colspan="11" >
			전체  <%= resultPage.getTotalCount() %> 건수, 현재 <%= resultPage.getCurrentPage() %>  페이 지
		</td>
		 --%>
		<td colspan="11" >
			전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b"width="700">등록일</td>		
		<td class="ct_line02"></td>
		<td class="ct_list_b">상태</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
	<%
		for(int i=0; i<list.size(); i++) {
			Product vo = list.get(i);
	%>
	<tr class="ct_list_pop">
		<td align="center"><%= i + 1 %></td>
		<td></td>
		<td align="left"><a href="/getProduct.do?prodNo=<%=vo.getProdNo() %>"><%= vo.getProdNo() %></a></td>
		<td></td>
		<td align="left"><%= vo.getProdName() %></td>
		<td></td>
		<td align="left"><%= vo.getEmail() %>
		</td>		
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<% } %>/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
	
	<!-- ➊ 루프 인덱스를 수동 증가시키기 위한 초기값 설정 -->
	<c:set var="i" value="0" />
	<!-- ➋ forEach 루프를 통해 list 반복 -->
	<c:forEach var="product" items="${list}">
		<!-- ➌ 인덱스 증가 (스크립틀릿에서 i++) 대체) -->
		<c:set var="i" value="${ i+1 }" />
		<!-- 🔁 아래는 link 변수를 조건에 따라 설정하는 부분 (기존 if-else 분기문 대체) -->
		<!-- ✅ 수정된 부분 시작 -->
			<c:choose>
				<c:when test="${menuParam eq 'manage'}">
					<c:set var="link" value="/product/updateProduct?prodNo=${product.prodNo}" />
				</c:when>
				<c:when test="${product.proTranCode eq '재고없음'}">
					<c:set var="link" value="" />
				</c:when>
				<c:otherwise>
					<c:set var="link" value="/product/getProduct?prodNo=${product.prodNo}" />
				</c:otherwise>
			</c:choose>
			<!-- ✅ 수정된 부분 끝 -->
		
		<tr class="ct_list_pop">
			<td align="center">${ i }</td>
			<td></td>
			<!-- <td align="left"><a href="/product/getProduct?prodNo=${product.prodNo}">${product.prodNo}</a></td> -->
			<td align="left">
            <c:choose>
                <c:when test="${empty link}"> ${product.prodName}
                </c:when>
                <c:otherwise>  <a href="${link}">${product.prodName}</a>
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
		<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>


<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value="1"/>
		   		<jsp:include page="../common/pageNavigator.jsp"/>	
	<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// 		   
	<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
			◀ 이전
	<% }else{ %>
			<a href="javascript:fncGetProductList('<%=resultPage.getCurrentPage()-1%>')">◀ 이전</a>
	<% } %>

	<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
			<a href="javascript:fncGetProductList('<%=i %>');"><%=i %></a>
	<% 	}  %>
	
	<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
			이후 ▶
	<% }else{ %>
			<a href="javascript:fncGetProductList('<%=resultPage.getEndUnitPage()+1%>')">이후 ▶</a>
	<% } %>
	 /////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>
	

			
    	</td>
	</tr>
</table>
<!-- PageNavigation End... -->

</form>
</div>

</body>
</html>
