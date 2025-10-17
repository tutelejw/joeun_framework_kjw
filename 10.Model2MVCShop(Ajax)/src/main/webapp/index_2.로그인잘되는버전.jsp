<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	
	<title>Model2 MVC Shop</title>
	
	<script type="text/javascript">
		// 'message' 이벤트를 감지하는 리스너를 추가합니다.
		window.addEventListener('message', function(event) {
			
			// 1. 메시지를 보낸 곳(origin)이 내 도메인과 일치하는지 확인합니다. (보안)
			if (event.origin !== 'http://localhost:8080') {
				return; // 일치하지 않으면 무시
			}
	
			// 2. 수신한 메시지(data)가 우리가 예상한 메시지인지 확인합니다.
			if (event.data === 'kakao-login-success') {
				console.log('index.jsp: 카카오 로그인 성공 메시지를 수신했습니다. 페이지를 새로고침합니다.');
				// 페이지 전체를 새로고침하여 로그인 상태를 반영합니다.
				window.location.reload(true);
			}
	
		}, false);
		
		
		
		
	</script>
	</head>

<frameset rows="80,*" cols="*" frameborder="NO" border="0" framespacing="0">
  
  <frame src="/layout/top.jsp" name="topFrame" scrolling="NO" noresize >
  
  <frameset rows="*" cols="160,*" framespacing="0" frameborder="NO" border="0">
    <frame src="/layout/left.jsp" name="leftFrame" scrolling="NO" noresize>
    <frame src="/product/listProduct?menu=search" name="rightFrame"  scrolling="auto">   </frameset>

</frameset>

<noframes>
<body>
</body>
</noframes>

</html>