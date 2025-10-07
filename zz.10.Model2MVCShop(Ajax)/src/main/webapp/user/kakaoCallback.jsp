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
        console.log("kakaoCallback.jsp: 스크립트가 실행되었습니다.");

        if (window.opener && !window.opener.closed) {
            
            // [핵심 수정] 
            // opener(rightFrame)의 최상위 부모(top)인 index.jsp에게 메시지를 보냅니다.
            console.log("kakaoCallback.jsp: 최상위 부모창(index.jsp)에 'kakao-login-success' 메시지를 전송합니다.");
            window.opener.top.postMessage('kakao-login-success', 'http://localhost:8080');

        } else {
            console.error("kakaoCallback.jsp: 부모창(opener)을 찾을 수 없거나 이미 닫혀있습니다.");
        }
        
        console.log("kakaoCallback.jsp: 메시지 전송 후 팝업을 닫습니다.");
        window.close();
    };
</script>
</head>
<body>
</body>
</html>