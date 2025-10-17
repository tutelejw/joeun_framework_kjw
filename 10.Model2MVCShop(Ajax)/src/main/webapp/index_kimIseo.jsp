<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
<style>
    /* --- 기본 및 레이아웃 --- */
    @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;700&display=swap');
    * { box-sizing: border-box; }
    body, html { margin: 0; padding: 0; height: 100%; font-family: 'Noto Sans KR', sans-serif; color: #333; }
    a { text-decoration: none; }
    body { display: flex; justify-content: center; align-items: center; background-color: #f9f9f9; }
    .login-container { width: 100%; max-width: 400px; padding: 50px 40px; background-color: #fff; border-radius: 16px; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05); text-align: center; }

    /* --- 헤더 --- */
    .login-header { margin-bottom: 40px; }
    .login-header h1 { font-size: 28px; font-weight: 700; margin: 0 0 10px; }
    .login-header p { font-size: 14px; color: #888; margin: 0; }

    /* --- 로그인 폼 --- */
    #loginForm { display: flex; flex-direction: column; gap: 12px; }
    #loginForm input { height: 50px; padding: 0 18px; border: 1px solid #ddd; border-radius: 10px; font-size: 15px; transition: all 0.2s ease; }
    #loginForm input:focus { outline: none; border-color: #F8B4C4; box-shadow: 0 0 0 3px rgba(248, 180, 196, 0.3); }
    #loginForm button { height: 52px; border: none; background-color: #F8B4C4; color: white; font-size: 16px; font-weight: 700; border-radius: 10px; cursor: pointer; transition: background-color 0.2s; margin-top: 10px; }
    #loginForm button:hover { background-color: #F6A5B9; }

    /* --- 하단 링크 (아이디/비밀번호 찾기) --- */
    .login-links { margin-top: 20px; font-size: 13px; color: #777; }
    .login-links a { margin: 0 8px; color: #777; text-decoration: none; }
    .login-links a:hover { text-decoration: underline; }

    /* --- 구분선 (OR) --- */
    .social-divider { display: flex; align-items: center; text-align: center; color: #aaa; margin: 30px 0; font-size: 12px; }
    .social-divider::before, .social-divider::after { content: ''; flex: 1; border-bottom: 1px solid #eee; }
    .social-divider:not(:empty)::before { margin-right: .25em; }
    .social-divider:not(:empty):after { margin-left: .25em; }
    
    /* --- 소셜 로그인 버튼 공통 스타일 --- */
    .social-login { display: flex; flex-direction: column; gap: 12px; }
    .social-btn { position: relative; height: 48px; border-radius: 10px; font-size: 14px; font-weight: 500; cursor: pointer; transition: all 0.2s ease; display: flex; align-items: center; justify-content: center; gap: 8px; background-color: #fff; border: 1px solid #ddd; color: #333; text-decoration: none; }
    .social-btn::before { content: ''; position: absolute; left: 24px; top: 50%; transform: translateY(-50%); width: 20px; height: 20px; background-repeat: no-repeat; background-position: center; background-size: contain; }

    /* --- 카카오 로그인 버튼 --- */
    .social-btn.kakao { border-color: #FEE500; color: #191919; }
    .social-btn.kakao:hover { background-color: #FEE500; }
    .social-btn.kakao::before { background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 28 28'%3e%3cpath fill-rule='evenodd' clip-rule='evenodd' d='M14 2.8C7.8 2.8 2.8 6.97 2.8 12.11c0 3.16 1.89 5.96 4.83 7.64-.22 1.42-.89 3.39-1.04 3.82-.12.35.1.55.44.49 2.24-.47 4.25-1.66 5.66-2.78.47.05.95.08 1.43.08 6.2 0 11.2-4.17 11.2-9.28C25.2 6.97 20.2 2.8 14 2.8z' fill='%23191919'/%3e%3c/svg%3e"); }

    /* --- 구글 로그인 버튼 --- */
    .social-btn.google { border-color: #ddd; color: #555; }
    .social-btn.google:hover { background-color: #f5f5f5; }
    .social-btn.google::before { background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 18 18'%3e%3cpath fill='%234285F4' d='M17.64 9.2045c0-.6382-.0573-1.2518-.1636-1.8409H9v3.4818h4.8436c-.2086 1.125-.8427 2.0782-1.7963 2.7218v2.2582h2.9087c1.7018-1.5668 2.6836-3.8741 2.6836-6.621z'/%3e%3cpath fill='%2334A853' d='M9 18c2.43 0 4.47-.805 5.96-2.18l-2.91-2.26c-.81.54-1.85.86-3.05.86-2.34 0-4.32-1.58-5.03-3.71H.96v2.33C2.43 16.15 5.48 18 9 18z'/%3e%3cpath fill='%23FBBC05' d='M3.97 10.71c-.18-.54-.28-1.12-.28-1.71s.1-1.17.28-1.71V4.96H.96C.35 6.18 0 7.55 0 9s.35 2.82.96 4.04l3.01-2.33z'/%3e%3cpath fill='%23EA4335' d='M9 3.58c1.32 0 2.5.45 3.44 1.34l2.58-2.58C13.46.96 11.43 0 9 0 5.48 0 2.43 1.85.96 4.96l3.01 2.33C4.68 5.16 6.66 3.58 9 3.58z'/%3e%3c/svg%3e"); }

    /* --- 네이버 로그인 버튼 --- */
    .social-btn.naver { border-color: #03C75A; color: #03C75A; }
    .social-btn.naver:hover { background-color: #03C75A; color: #fff; }
    .social-btn.naver::before { background-image: url("data:image/svg+xml,%3csvg viewBox='0 0 24 24' xmlns='http://www.w3.org/2000/svg'%3e%3cpolygon fill='%2303c75a' points='4,4 9,4 15,12.5 15,4 20,4 20,20 15,20 9,11.5 9,20 4,20'/%3e%3c/svg%3e"); }
    .social-btn.naver:hover svg polygon { fill: #fff; }
</style>
</head>
<body>
    <div class="login-container">
        <!-- 헤더 -->
        <div class="login-header">
            <h1>로그인</h1>
            <p>서비스를 이용하려면 로그인하세요.</p>
        </div>

        <!-- 로그인 폼 -->
        <form id="loginForm" method="post" action="<c:url value='/login' />">
            <input type="text" name="username" placeholder="아이디 또는 이메일" required />
            <input type="password" name="password" placeholder="비밀번호" required />
            <button type="submit">로그인</button>
        </form>

        <!-- 하단 링크 -->
        <div class="login-links">
            <a href="<c:url value='/findId' />">아이디 찾기</a>
            |
            <a href="<c:url value='/findPassword' />">비밀번호 찾기</a>
            |
            <a href="<c:url value='/signup' />">회원가입</a>
        </div>

        <!-- 구분선 -->
        <div class="social-divider">또는</div>

        <!-- 소셜 로그인 버튼 -->
        <div class="social-login">
            <a href="<c:url value='/auth/kakao' />" class="social-btn kakao">카카오로 로그인</a>
            <a href="<c:url value='/auth/google' />" class="social-btn google">구글로 로그인</a>
            <a href="<c:url value='/auth/naver' />" class="social-btn naver">네이버로 로그인</a>
        </div>
    </div>
</body>
</html>
