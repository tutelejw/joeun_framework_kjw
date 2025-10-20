<%-- webapp/user/kakaoCallback.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Processing...</title>
<script type="text/javascript">
    window.onload = function() {
        console.log("kakaoCallback.jsp: 스크립트 실행.");

        // URL 파라미터에서 login=success 값을 확인
        const urlParams = new URLSearchParams(window.location.search);
        
        // ▼▼▼ [디버깅 로그 추가] ▼▼▼
        console.log("kakaoCallback.jsp: 전체 URL 파라미터 문자열:", window.location.search);
        console.log("kakaoCallback.jsp: 'login' 파라미터의 값:", urlParams.get('login'));
        console.log("kakaoCallback.jsp: 파라미터 값이 'success'와 일치하는가?", urlParams.get('login') === 'success');
        // ▲▲▲ [디버깅 로그 추가] ▲▲▲
        
        if (urlParams.get('login') === 'success') {
            // 브라우저 공용 저장소(localStorage)에 로그인 성공 상태를 기록
            console.log("kakaoCallback.jsp: localStorage에 로그인 성공 상태를 기록합니다.");
            localStorage.setItem('kakaoLoginStatus', 'success');
            // ▼▼▼ [추가] 브라우저의 디버거를 여기서 잠시 멈추게 합니다. ▼▼▼
            //debugger;
        }else{
        	console.log("kakaoCallback.jsp: urlParams.get('login') fail??." + urlParams.get('login'));
        }

        console.log("kakaoCallback.jsp: 팝업을 닫습니다.");
        window.close();
    };
</script>
</head>
<body>
</body>
</html>