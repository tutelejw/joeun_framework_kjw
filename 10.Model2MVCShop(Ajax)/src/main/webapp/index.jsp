<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	
	<title>Model2 MVC Shop</title>
	
	<script type="text/javascript">
	   (function() {
	        // 1초마다 localStorage에 로그인 성공 신호가 있는지 확인
	        const loginCheckInterval = setInterval(function() {
	            
	            // 'kakaoLoginStatus' 항목을 확인
 	            if (localStorage.getItem('kakaoLoginStatus') === 'success') {
	                
	                // ▼▼▼ [핵심 디버깅 코드] 신호를 감지했는지 알림창으로 확인 ▼▼▼
	                alert('index.jsp: 로그인 성공 신호를 감지했습니다! 이제 페이지를 새로고침합니다.');
	              
	            	console.log('index.jsp: localStorage에서 로그인 성공 신호를 발견했습니다.');

	                // 확인이 끝났으므로 신호를 삭제
	                localStorage.removeItem('kakaoLoginStatus');

	                // 더 이상 확인할 필요가 없으므로 인터벌 종료
	                clearInterval(loginCheckInterval);

	                // 페이지를 새로고침하여 로그인 상태 반영
	                console.log('index.jsp: 페이지를 새로고침합니다.');
	                window.location.reload(true);
	            }

	        }, 1000); // 1초 간격으로 확인

	        console.log('index.jsp: localStorage 로그인 상태 감시를 시작합니다.');
	    })();
		
		
		
		
	</script>
	</head>

<frameset rows="80,*" cols="*" frameborder="NO" border="0" framespacing="0">
  
  <frame src="/layout/top.jsp" name="topFrame" scrolling="NO" noresize >
  
  <frameset rows="*" cols="160,*" framespacing="0" frameborder="NO" border="0">
    <frame src="/layout/left.jsp" name="leftFrame" scrolling="NO" noresize>
    <frame src="/product/listProduct?menu=search" name="rightFrame"  scrolling="auto">
  </frameset>

</frameset>

<noframes>
<body>
</body>
</noframes>

</html>