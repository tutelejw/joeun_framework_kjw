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
        console.log("kakaoCallback.jsp: 스크립트 실행 시작");

        // opener (부모창)가 있는지 확인
        if (window.opener && !window.opener.closed) {
            console.log("kakaoCallback.jsp: 부모창(opener)이 존재합니다.");
            try {
                // 부모창의 프레임들을 새로고침 시도
                // location.reload()는 캐시 문제를 일으킬 수 있어, href를 재할당하는 것이 더 확실합니다.
                window.opener.parent.frames["topFrame"].location.href = "/layout/top.jsp";
                window.opener.parent.frames["leftFrame"].location.href = "/layout/left.jsp";
                // rightFrame은 사용자가 로그인 후 봐야 할 첫 페이지(예: 메인)로 보냅니다.
                window.opener.parent.frames["rightFrame"].location.href = "/";
                console.log("kakaoCallback.jsp: 프레임 새로고침 성공");
            } catch (e) {
                // 프레임 구조가 아니거나, 다른 도메인 문제 등으로 실패할 경우
                // 부모창 전체를 새로고침하는 예비 로직
                console.error("kakaoCallback.jsp: 프레임 제어 실패. 부모창 전체를 새로고침합니다.", e);
                window.opener.location.reload();
            }
        } else {
            console.error("kakaoCallback.jsp: 부모창(opener)을 찾을 수 없습니다.");
        }

        // 모든 작업이 끝난 후 팝업창을 닫습니다.
        console.log("kakaoCallback.jsp: 팝업을 닫습니다.");
        window.close();
    };
</script>
</head>
<body>
</body>
</html>