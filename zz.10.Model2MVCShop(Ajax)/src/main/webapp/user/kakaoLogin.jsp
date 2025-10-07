<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String code = request.getParameter("code");
    if (code == null || code.isEmpty()) {
%>
    <script>
        alert("카카오 인증 코드가 없습니다. 로그인 실패 또는 취소됨.");
        window.close();
    </script>
<%
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>카카오 로그인 처리 중...</title>
<script>
    window.onload = function() {
        var code = "<%= code %>";
        window.location.href = "/user/kakaoLogin?code=" + code;
    };
</script>
</head>
<body>
    카카오 로그인 처리 중입니다...
</body>
</html>
