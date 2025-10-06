<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<%
    // 여기에 카카오에서 받은 code 파라미터로 토큰 요청 및 사용자 정보 조회 후
    // 로그인 처리를 하고 세션 등에 저장하세요.
    // 예제는 로그인 성공 후 userId 변수 세팅 가정

    String userId = "kakaoUser123"; // 실제 로그인된 사용자 ID로 변경하세요
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>카카오 로그인 처리</title>
</head>
<body>
    <script>
        if (window.opener) {
            // 부모창에 로그인 성공 메시지 전달 + userId 포함
            window.opener.postMessage({ type: "KAKAO_LOGIN_SUCCESS", userId: "<%= userId %>" }, "*");
            window.close();
        } else {
            alert("부모창이 존재하지 않습니다.");
        }
    </script>
</body>
</html>
