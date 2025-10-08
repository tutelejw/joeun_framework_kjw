<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>네이버 로그인 처리</title>
<script type.text/javascript">
window.onload = function() {
    // Controller에서 model에 담아준 로그인 성공 여부와 사용자 ID를 확인
    const loginSuccess = "${loginSuccess}";
    const userId = "${userId}";

    console.log("naverCallback.jsp 로드됨");
    console.log("로그인 성공 여부:", loginSuccess);
    console.log("사용자 ID:", userId);

    if (loginSuccess === "true" && userId) {
        // 부모 창(loginView.jsp)의 콜백 함수(naverLoginCallback)를 호출
        // opener는 팝업을 띄운 부모 창을 의미합니다.
        if (window.opener && window.opener.naverLoginCallback) {
            console.log("부모 창의 naverLoginCallback 함수 호출 시도");
            window.opener.naverLoginCallback(userId);
        } else {
            console.error("부모 창 또는 콜백 함수를 찾을 수 없습니다.");
            alert("로그인 처리 중 오류가 발생했습니다. (부모 창 없음)");
        }
    } else {
        // 로그인 실패 시
        const errorMessage = "${errorMessage}";
        console.error("네이버 로그인 실패:", errorMessage);
        alert("네이버 로그인에 실패했습니다. " + errorMessage);
    }

    // 모든 처리가 끝나면 팝업 창을 닫음
    console.log("팝업 창을 닫습니다.");
    window.close();
};
</script>
</head>
<body>
    로그인 처리 중...
</body>
</html>