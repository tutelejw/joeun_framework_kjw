<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.model2.mvc.service.domain.Product" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	Product product=(Product)request.getAttribute("product");
%>
<%-- /////////////////////// EL / JSTL 적용으로 주석 처리 ////////////////////////
<%@ page import="com.model2.mvc.service.domain.Product" %>
<%
	Product product=(Product)request.getAttribute("product");
%>/////////////////////// EL / JSTL 적용으로 주석 처리 //////////////////////// --%>

<html>
<head>
<title>상품 정보 수정</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		// 수정 버튼 클릭 시
		$('#btnUpdate').on('click', function() {
			document.detailForm.action = '/product/updateProduct';
			document.detailForm.submit();
		});

		// 취소 버튼 클릭 시
		$('#btnCancel').on('click', function() {
			history.back();  // 또는 history.go(-1);
		});

	});
</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm"  method="post" >

<%--<input type="hidden" name="prodNo" value="<%=product.getProdNo() %>"> --%>
<input type="hidden" name="prodNo" value="${product.prodNo }">
<% System.out.println("updateProduct.jsb - product.getProdNo : " + product.getProdNo());%>
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">상품정보수정</td>
					<td width="20%" align="right">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37" />
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:13px;">
	<tr>		<td height="1" colspan="3" bgcolor="D6D6D6"></td>	</tr>
	
	<tr>
		<td width="104" class="ct_write">상품명 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle" />
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<%--<td class="ct_write01"><%=product.getProdNo() %>	</td> --%>
		<td class="ct_write01">
			<input 	type="text" name="prodName" value="${product.prodName}" class="ct_input_g" 
							style="width:100px; height:19px"  maxLength="50" >	</td>
	</tr>
	
	<tr>		<td height="1" colspan="3" bgcolor="D6D6D6"></td>	</tr>
	
	<tr>
		<td width="104" class="ct_write">	상품상세정보 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle" />
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<%-- <input type="text" name="prodName" value="<%=product.getProdName() %>" class="ct_input_g" style="width:100px; height:19px"  maxLength="50" >--%>
			<input 	type="text" name="prodDetail" value="${product.prodDetail}" class="ct_input_g" 
							style="width:100px; height:19px"  maxLength="50" >
		</td>
	</tr>
	
	<tr>		<td height="1" colspan="3" bgcolor="D6D6D6"></td>	</tr>
	
	<tr>
		<td width="104" class="ct_write">제조일자</td>		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<%--<input type="text" name="addr" value="<%=product.getAddr() %>" class="ct_input_g" style="width:370px; height:19px"  maxLength="100">--%>
			<input 	type="text" name="manuDate" value="${product.manuDate}" class="ct_input_g" 
							style="width:370px; height:19px"  maxLength="100">
		</td>
	</tr>
	
	<tr>		<td height="1" colspan="3" bgcolor="D6D6D6"></td>	</tr>
	
	<tr>
		<td width="104" class="ct_write">가격 </td>		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<%--<input type="text" name="email" value="<%=product.getEmail() %>" class="ct_input_g" style="width:100px; height:19px" onChange="check_email(this.form);">--%>
			<input 	type="text" name="price" value="${product.price}" class="ct_input_g" 
							style="width:100px; height:19px"   maxLength="100">
		</td>
	</tr>
	
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
		<tr>
		<td width="104" class="ct_write">상품이미지 </td>		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<%--<input type="text" name="email" value="<%=product.getEmail() %>" class="ct_input_g" style="width:100px; height:19px" onChange="check_email(this.form);">--%>
			<input 	type="text" name="fileName" value="${product.fileName}" class="ct_input_g" 
							style="width:100px; height:19px"   maxLength="100">
		</td>
	</tr>
	
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td width="53%">	</td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23" />
					</td>
<!-- 수정 버튼 -->
<td background="/images/ct_btnbg02.gif" id="btnUpdate" class="ct_btn01" style="padding-top:3px; cursor:default;">
	수정
</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23" />
					</td>
					<td width="30"></td>					
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23" />
					</td>
<!-- 취소 버튼 -->
<td background="/images/ct_btnbg02.gif" id="btnCancel" class="ct_btn01" style="padding-top:3px; cursor:default;">
	취소
</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</form>

</body>
</html>
