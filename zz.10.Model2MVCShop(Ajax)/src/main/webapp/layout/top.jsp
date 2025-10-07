<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Model2 MVC Shop</title>
	<link href="/css/left.css" rel="stylesheet" type="text/css">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	
    <script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.6/kakao.min.js" integrity="sha384-WAtVcQYcmTO/N+C1N+1m6Gp8qxh+3NlnP7X1U7qP6P5dQY/MsRBNTh+e1ahJrkEm" crossorigin="anonymous"></script>
<!--     <script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.1/kakao.min.js"
        integrity="sha384-kDljxUXHaJ9xAb2AzRd59KxjrFjzHa5TAoFQ6GbYTCAG0PqcUX7QXPFqm4i3EuUN" crossorigin="anonymous"></script> -->

	<script type="text/javascript">
		// 페이지 로드 시 카카오 SDK 초기화
		$(function() {
			// 본인의 JavaScript 키를 입력하세요.
			Kakao.init('8cff1ee0835498e88c05badb308f0dbd'); 
		});
	
		$(function() {
			//==> login Event 연결처리부분
		 	$("td[width='115']:contains('login')").on("click" , function() {
				$(window.parent.frames["rightFrame"].document.location).attr("href","/user/login");
			});
			
			// ▼▼▼ 2. 로그아웃 Event 핸들러 수정 ▼▼▼
			$("td[width='56']:contains('logout')").on("click" , function() {
				// 카카오 SDK가 초기화되었는지 확인
				if (Kakao.isInitialized()) {
					Kakao.Auth.logout()
						.then(function () {
							// 카카오 로그아웃 성공 시 우리 서버 로그아웃 실행
							console.log('카카오계정 로그아웃 성공');
							window.parent.location.href = '/user/logout';
						})
						.catch(function () {
							// 카카오 로그아웃 실패하더라도 우리 서버 로그아웃은 실행
							console.log('카카오계정 로그아웃 실패');
							window.parent.location.href = '/user/logout';
						});
				} else {
					// SDK 초기화 전이면 그냥 우리 서버 로그아웃만 실행
					window.parent.location.href = '/user/logout';
				}
			}); 
		});	
	</script>		
</head>
<body topmargin="0" leftmargin="0">
	<table width="100%" height="50" border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td height="10"></td>
		<td height="10" >&nbsp;</td>
	  </tr>
	  <tr>
	    <td width="800" height="30"><h2>Model2 MVC Shop</h2></td>
	  </tr>
	  <tr>
	    <td height="20" align="right" background="/images/img_bg.gif">
		    <table width="200" border="0" cellspacing="0" cellpadding="0">
		        <tr> 
		          <td width="115" style="cursor:pointer;">
			          <c:if test="${ empty user }">login</c:if>   
		          </td>
		          <td width="14">&nbsp;</td>
		          <td width="56" style="cursor:pointer;">
			          <c:if test="${ ! empty user }">logout </c:if>
		          </td>
		        </tr>
		      </table>
	      </td>
	    <td height="20" background="/images/img_bg.gif">&nbsp;</td>
	  </tr>
	</table>
</body>
</html>