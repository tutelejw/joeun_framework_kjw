<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>카카오 로그인 성공</title>
<script>
    window.onload = function() {
        if (window.opener) {
            window.opener.location.reload();
            window.close();
        } else {
            alert("팝업이 아닌 창에서 접근했습니다.");
        }
    };
</script>
</head>
<body>
    카카오 로그인 성공! 창이 자동으로 닫힙니다.
</body>
</html>
