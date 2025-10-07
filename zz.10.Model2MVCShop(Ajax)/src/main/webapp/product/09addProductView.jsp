<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
<title>상품등록</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
function fncAddProduct() {
	var id = document.detailForm.prodNo.value;
	var name = document.detailForm.prodName.value;
	
	if(id == null || id.length < 1){
		alert("아이디는 반드시 입력하셔야 합니다.");
		return;
	}
	if(name == null || name.length < 1){
		alert("이름은 반드시 입력하셔야 합니다.");
		return;
	}

	document.detailForm.action = '/product/addProduct';
	document.detailForm.submit();
}

function resetData() {
	document.detailForm.reset();
}

/* 리스트 페이지에서 상품등록 클릭 시 JavaScript 함수 호출 jQuery 기반으로 버튼 클릭 이벤트를 바인딩 */
$(document).ready(function() { 
	// 첫 번째 버튼: 상품등록
/* 	$('.ct_btn01').eq(0).on('click', function() { */
	$('#btnAddProduct').on('click', function() {
		fncAddProduct();
	});

	// 두 번째 버튼: 취소
/* 	$('.ct_btn01').eq(1).on('click', function() { */
	$('#btnCancel').on('click', function() {
		resetData();
	});
});
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm" method="post"> 

<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif" width="15" height="37"/></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">상품등록</td>
					<td width="20%" align="right">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif" width="12" height="37"/></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px; min-height:200px;">
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
	<tr>
		<td width="104" class="ct_write">상품번호 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><input type="text" name="prodNo" class="ct_input_g" style="width:100px; height:19px" maxlength="20" /></td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
	<tr>
		<td class="ct_write">상품명 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><input type="text" name="prodName" class="ct_input_g" style="width:100px; height:19px" maxlength="50" /></td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
	<tr>
		<td class="ct_write">상품상세정보 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><input type="text" name="prodDetail" class="ct_input_g" style="width:100px; height:19px" maxLength="50" /></td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
	<tr>
		<td class="ct_write">제조일자 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/></td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><input type="date" name="manuDate" class="ct_input_g" style="width:100px; height:19px" maxLength="50" /></td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
	<tr>
		<td class="ct_write">가격</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><input type="text" name="price" class="ct_input_g" style="width:150px; height:19px" maxLength="100"/></td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
	<tr>
		<td class="ct_write">상품이미지</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01" style="height: 25px; vertical-align: middle;">
			<input type="text" name="fileName" class="ct_input_g" style="width:200px; height:25px;" maxLength="100"/>
		</td>
	</tr>
	<tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td width="53%"></td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><img src="/images/ct_btnbg01.gif" width="17" height="23"/></td>
<!-- 					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">상품등록</td> -->
					<td id="btnAddProduct" background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">상품등록</td>
					<td><img src="/images/ct_btnbg03.gif" width="14" height="23"/></td>
					<td width="30"></td>
					<td><img src="/images/ct_btnbg01.gif" width="17" height="23"/></td>
<!-- 					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">취소</td> -->
					<td id="btnCancel" background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">취소</td>
					<td><img src="/images/ct_btnbg03.gif" width="14" height="23"/></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</form>

</body>
</html>
