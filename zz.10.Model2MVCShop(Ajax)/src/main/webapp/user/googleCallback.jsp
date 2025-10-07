<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Google Login Callback</title>
<script>
window.onload = function() {
    try {
        // Controller에서 Model에 담아준 로그인 성공 여부와 userId를 확인
        var loginSuccess = '<c:out value="${loginSuccess}" />';
        var userId = '<c:out value="${userId}" />';

        console.log("Callback page loaded. Login success:", loginSuccess);
        
        if (loginSuccess === 'true' && window.opener && !window.opener.closed) {
            console.log("Login successful. Calling opener's callback function.");
            // 부모창(loginView.jsp)의 googleLoginCallback 함수 호출
            window.opener.googleLoginCallback(userId);
        } else {
            console.error("Login failed or opener window is not accessible.");
            // 실패 시 사용자에게 알림
            alert("구글 로그인에 실패했습니다. 다시 시도해 주세요.");
        }
    } catch (e) {
        console.error("An error occurred in the callback page:", e);
    } finally {
        // 성공하든 실패하든 팝업창을 닫음
        console.log("Closing popup window.");
        window.close();
    }
};
</script>
</head>
<body>
    Processing login...
</body>
</html>