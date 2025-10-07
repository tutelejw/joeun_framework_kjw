<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>로그인 화면</title>
  <link rel="stylesheet" href="/css/admin.css" type="text/css">

  <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
  <script type="text/javascript">
    $(function() {
      // 기존 로그인 처리 부분 생략...

      // 카카오 로그인 버튼 클릭 시 팝업 열기
      $("#kakao-login-btn").on("click", function() {
        const KAKAO_CLIENT_ID = "f38379dc4a1fd8db1c81e44d5bf62547";
        const REDIRECT_URI = "http://localhost:8080/user/kakaoLogin";  // 카카오 로그인 후 돌아올 URI

        const kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize" +
          "?response_type=code" +
          "&client_id=" + KAKAO_CLIENT_ID +
          "&redirect_uri=" + encodeURIComponent(REDIRECT_URI);

        // 팝업 크기 및 위치 설정
        const width = 500;
        const height = 600;
        const left = (window.screen.width / 2) - (width / 2);
        const top = (window.screen.height / 2) - (height / 2);

        window.open(
          kakaoAuthUrl,
          "kakaoLoginPopup",
          `width=${width},height=${height},top=${top},left=${left},resizable=no,scrollbars=no,status=no`
        );
      });
    });
  </script>

</head>
<body bgcolor="#ffffff" text="#000000">

<form>
<div align="center">
  <table width="650" height="390" border="5" cellpadding="0" cellspacing="0" bordercolor="#D6CDB7">
    <tr>
      <td width="305">
        <img src="/images/logo-spring.png" width="305" height="390"/>
      </td>
      <td width="345" align="left" valign="top" background="/images/login02.gif">
        <!-- 로그인 폼 영역 -->
        <table width="100%" height="220" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="/images/text_login.gif" width="91" height="32"/></td>
          </tr>
          <tr>
            <td>
              <input type="text" name="userId" id="userId" class="ct_input_g" style="width:180px; height:19px" maxlength="50" placeholder="아이디" />
            </td>
          </tr>
          <tr>
            <td>
              <input type="password" name="password" class="ct_input_g" style="width:180px; height:19px" maxlength="50" placeholder="비밀번호" />
            </td>
          </tr>
          <tr>
            <td>
              <img src="/images/btn_login.gif" width="56" height="20" border="0" style="cursor:pointer;" id="btn-login" />
              <img src="/images/btn_add.gif" width="70" height="20" border="0" style="cursor:pointer;" id="btn-add" />
            </td>
          </tr>
          <tr>
            <td align="center" style="padding-top: 20px;">
              <!-- 카카오 로그인 버튼 -->
              <a href="#" id="kakao-login-btn">
                <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" alt="카카오 로그인" />
              </a>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
</form>

</body>
</html>
