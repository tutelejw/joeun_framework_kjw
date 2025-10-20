<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="java.security.SecureRandom" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	
	<title>로그인 화면</title>
	
	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
    
    <script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.6/kakao.min.js" integrity="sha384-WAtVcQYcmTO/N+C1N+1m6Gp8qxh+3NlnP7X1U7qP6P5dQY/MsRBNTh+e1ahJrkEm" crossorigin="anonymous"></script>
    <script type="text/javascript">
        // 페이지 로드 시 카카오 SDK 초기화
        $(function() {
            // 본인의 JavaScript 키를 입력하세요.
            Kakao.init('8cff1ee0835498e88c05badb308f0dbd'); 
            console.log('Kakao SDK Initalized:', Kakao.isInitialized());
        });

		$( function() {
			
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			$("#userId").focus();
			
			//==>"Login"  Event 연결
			$("img[src='/images/btn_login.gif']").on("click" , function() {

				var id=$("input:text").val();
				var pw=$("input:password").val();
				
				if(id == null || id.length <1) {
					alert('ID 를 입력하지 않으셨습니다.');
					$("input:text").focus();
					return;
				}
				
				if(pw == null || pw.length <1) {
					alert('패스워드를 입력하지 않으셨습니다.');
					$("input:password").focus();
					return;
				}
				
				$.ajax( 
						{
							url : "/user/json/login",
							method : "POST" ,
							dataType : "json" ,
							headers : {
								"Accept" : "application/json",
								"Content-Type" : "application/json"
							},
							data : JSON.stringify({
								userId : id,
								password : pw
							}),
							success : function(JSONData , status) {
								if( JSONData != null ){
									// 부모창(index.jsp)의 프레임들을 새로고침
									$(window.parent.frames["topFrame"].document.location).attr("href","/layout/top.jsp");
									$(window.parent.frames["leftFrame"].document.location).attr("href","/layout/left.jsp");
									$(window.parent.frames["rightFrame"].document.location).attr("href","/user/getUser?userId="+JSONData.userId);
								}else{
									alert("아이디 , 패스워드를 확인하시고 다시 로그인...");
								}
							}
					}); 
			});

            // ============= 카카오 로그인 버튼 Event 연결 =============
			$("#kakaoLoginBtn").on("click", function() {
			    kakaoLoginPopup(); // 함수 이름 변경
			});
            
            // ============= [신규] 구글 로그인 버튼 Event 연결 =============
            $("#googleLoginBtn").on("click", function() {
                googleLoginPopup();
            });
            // ============= [신규] 네이버 로그인 버튼 Event 연결 =============
            $("#naverLoginBtn").on("click", function() {
                naverLoginPopup();
            });
            
		});
		
		
		//============= 회원원가입화면이동 =============
		$( function() {
			//==> 추가된부분 : "addUser"  Event 연결
			$("img[src='/images/btn_add.gif']").on("click" , function() {
				self.location = "/user/addUser"
			});
		});


        // ============= 카카오 로그인 처리 함수 =============

        function kakaoLogin() {
            // Kakao.isInitialized()를 통해 SDK 초기화 여부를 한번 더 확인합니다.
            if (!Kakao.isInitialized()) {
                alert('카카오 SDK가 초기화되지 않았습니다.');
                return;
            }

            // ★★★ 디버깅 코드 추가 ★★★  // Kakao.Auth 객체를 콘솔에 출력해봅니다.
            console.log('Inspecting Kakao.Auth object:', Kakao.Auth); 
            
            // 에러가 나는 라인
            Kakao.Auth.login({
                success: function (authObj) {
                },
                fail: function (err) {
                },
            });
        }
        
        // ============= [신규] 구글 로그인 팝업 처리 함수 =============
        function googleLoginPopup() {
            // 1. Google Cloud Console에서 발급받은 클라이언트 ID
            const GOOGLE_CLIENT_ID = "1095911084251-9vedhnalqe4lhkmpakr4t1h7vqe5ld5e.apps.googleusercontent.com"; // ◀◀◀ 여기에 발급받은 클라이언트 ID를 입력하세요.

            // 2. 서버에 설정된 Redirect URI (절대 경로)
            const GOOGLE_REDIRECT_URI = "http://localhost:8080/user/googleLogin";

            // 3. 사용자 정보 범위를 지정 (기본: email, profile)
            const GOOGLE_SCOPE = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";

            // 4. OAuth 2.0 인증 URL 생성
            const authUrl = 'https://accounts.google.com/o/oauth2/v2/auth'
                        + '?client_id=' + GOOGLE_CLIENT_ID
                        + '&redirect_uri=' + GOOGLE_REDIRECT_URI
                        + '&response_type=code'
                        + '&scope=' + encodeURIComponent(GOOGLE_SCOPE);

            // [디버깅] 생성된 인증 URL을 콘솔에 출력
            console.log('Generated Google Auth URL:', authUrl);

            // 5. 팝업 창 열기
            window.open(authUrl, 'googleLoginPopup', 'width=600,height=700');
        }
        
        // ============= [신규] 네이버 로그인 팝업 처리 함수 =============
function naverLoginPopup() {
    console.log("[loginView.jsp] naverLoginPopup() 함수 시작");

    // 1. 인증 URL 생성
    const NAVER_CLIENT_ID = "YhvYDqSntCxLVR1hLWdt";
    const NAVER_REDIRECT_URI = window.location.origin + '/user/naverLogin';
    const state = "${state}";
    const authUrl = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
                + "&client_id=" + NAVER_CLIENT_ID
                + "&redirect_uri=" + encodeURIComponent(NAVER_REDIRECT_URI)
                + "&state=" + state;
    
    console.log("  - 생성된 인증 URL:", authUrl);

    // 2. 팝업 창 열기
    const naverPopup = window.open(authUrl, 'naverLoginPopup', 'width=600,height=700');
    if (naverPopup) {
        console.log("  - 팝업 창 객체 생성 성공");
    } else {
        console.error("  - 팝업 창 생성 실패! (브라우저의 팝업 차단 기능 확인)");
        alert("팝업이 차단되었습니다. 브라우저 설정을 확인해주세요.");
        return;
    }

    // 3. 팝업창이 닫혔는지 0.5초마다 확인하는 타이머 설정
    console.log("  - 팝업창 감시 타이머 시작 (0.5초 간격)");
    const timer = setInterval(() => {
        // naverPopup 객체가 (어떤 이유로든) 사라졌거나, 창이 정상적으로 닫혔는지 확인
        if (naverPopup == null || naverPopup.closed) {
            
            // 타이머가 중복 실행되지 않도록 즉시 중지
            clearInterval(timer); 
            
            console.log("  - [타이머] 팝업 닫힘 감지! 타이머를 중지하고 페이지 새로고침을 시작합니다.");
            
            // 4. 부모의 프레임들을 새로고침하여 로그인 상태를 반영
            try {
                console.log("    -> topFrame 새로고침 시도...");
                window.parent.frames["topFrame"].location.reload(true);
                console.log("    -> topFrame 새로고침 완료.");

                console.log("    -> leftFrame 새로고침 시도...");
                window.parent.frames["leftFrame"].location.reload(true);
                console.log("    -> leftFrame 새로고침 완료.");
                
                // rightFrame은 초기화면(/)으로 이동
                console.log("    -> rightFrame을 '/'으로 이동 시도...");
                window.parent.frames["rightFrame"].location.href = "/";
                console.log("    -> rightFrame 이동 완료.");

                console.log("  - 모든 프레임 새로고침 작업이 정상적으로 요청되었습니다.");

            } catch (e) {
                console.error("  - 프레임 새로고침 중 오류 발생:", e);
                // 프레임 구조가 아닐 경우를 대비한 대체 새로고침
                console.log("  - 프레임 접근 실패. 전체 페이지를 새로고침합니다.");
                window.location.reload();
            }

        } else {
            // 이 로그는 0.5초마다 계속 찍히므로, 팝업이 안 닫히고 있는지 확인할 수 있습니다.
            console.log("  - [타이머] 팝업창 감시 중... (상태: 열려있음)");
        }
    }, 500);
}

        // ============= [신규] 네이버 로그인 팝업 콜백 함수 =============
        // 이 함수는 팝업(naverCallback.jsp)에서 로그인이 성공했을 때 호출합니다.
        function naverLoginCallback(userId) {
            console.log("naverLoginCallback 호출됨! userId:", userId);
            // 부모창(index.jsp)의 프레임들을 새로고침하여 로그인 상태를 반영
            $(window.parent.frames["topFrame"].document.location).attr("href","/layout/top.jsp");
            $(window.parent.frames["leftFrame"].document.location).attr("href","/layout/left.jsp");
            $(window.parent.frames["rightFrame"].document.location).attr("href","/user/getUser?userId="+userId);
        }
        
        // ============= [신규] 팝업 창에서 호출할 콜백 함수 =============
        // 이 함수는 팝업(googleCallback.jsp)에서 로그인이 성공했을 때 호출합니다.
        function googleLoginCallback(userId) {
            console.log("googleLoginCallback called with userId:", userId);
            // 부모창(index.jsp)의 프레임들을 새로고침
            $(window.parent.frames["topFrame"].document.location).attr("href","/layout/top.jsp");
            $(window.parent.frames["leftFrame"].document.location).attr("href","/layout/left.jsp");
            $(window.parent.frames["rightFrame"].document.location).attr("href","/user/getUser?userId="+userId);
        }


     // [변경] 카카오 로그인 팝업을 수동으로 띄우는 함수
function kakaoLoginPopup() {
    const KAKAO_REST_API_KEY = 'f38379dc4a1fd8db1c81e44d5bf62547'; // 본인의 REST API 키
    
    // 현재 접속한 주소(origin)를 기반으로 Redirect URI를 동적으로 생성합니다.
    const KAKAO_REDIRECT_URI = window.location.origin + '/user/kakaoLogin';

    const authUrl = 'https://kauth.kakao.com/oauth/authorize?response_type=code'
                  + '&client_id=' + KAKAO_REST_API_KEY 
                  + '&redirect_uri=' + KAKAO_REDIRECT_URI;
    // 3. CSRF 방지를 위한 state 값 생성 (세션에서 값을 가져오도록 수정)
    const state = "${sessionScope.state}"; // session에 저장된 값을 JSTL로 가져옵니다.

    console.log('Generated Kakao Auth URL:', authUrl); // 생성된 URL 확인
    
    window.open(authUrl, 'kakaoLoginPopup', 'width=600,height=700');
}
	</script>		
	
</head>

<body bgcolor="#ffffff" text="#000000" >
<%-- <%
    // CSRF 공격 방지를 위한 state 토큰 생성 및 세션에 저장
    String state = new BigInteger(130, new SecureRandom()).toString();
    session.setAttribute("state", state);
%> --%>
<form onsubmit="return false;">

<div align="center" >

<TABLE WITH="100%" HEIGHT="100%" BORDER="0" CELLPADDING="0" CELLSPACING="0">
<TR>
<TD ALIGN="CENTER" VALIGN="MIDDLE">

<table width="650" height="390" border="5" cellpadding="0" cellspacing="0" bordercolor="#D6CDB7">
  <tr> 
    <td width="10" height="5" align="left" valign="top" bordercolor="#D6CDB7">
    	<table width="650" height="390" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="305">
            <img src="/images/logo-spring.png" width="305" height="390"/>
          </td>
          <td width="345" align="left" valign="top">
          	<table width="100%" height="220" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="30" height="100">&nbsp;</td>
                <td width="100" height="100">&nbsp;</td>
                <td height="100">&nbsp;</td>
                <td width="20" height="100">&nbsp;</td>
              </tr>
              <tr> 
                <td width="30" height="50">&nbsp;</td>
                <td width="100" height="50">
                	<img src="/images/text_login.gif" width="91" height="32"/>
                </td>
                <td height="50">&nbsp;</td>
                <td width="20" height="50">&nbsp;</td>
              </tr>
              <tr> 
                <td width="200" height="50" colspan="4"></td>
              </tr>              
              <tr> 
                <td width="30" height="30">&nbsp;</td>
                <td width="100" height="30">
                	<img src="/images/text_id.gif" width="100" height="30"/>
                </td>
                <td height="30">
                  <input 	type="text" name="userId"  id="userId"  class="ct_input_g" 
                  				style="width:180px; height:19px"  maxLength='50'/>          
          		</td>
                <td width="20" height="30">&nbsp;</td>
              </tr>
              <tr> 
                <td width="30" height="30">&nbsp;</td>
                <td width="100" height="30">
                	<img src="/images/text_pas.gif" width="100" height="30"/>
                </td>
                <td height="30">                    
                    <input 	type="password" name="password" class="ct_input_g" 
                    				style="width:180px; height:19px"  maxLength="50" />
                </td>
                <td width="20" height="30">&nbsp;</td>
              </tr>
              <tr> 
                <td width="30" height="20">&nbsp;</td>
                <td width="100" height="20">&nbsp;</td>
                <td height="20" align="center">
   				    <table width="136" height="20" border="0" cellpadding="0" cellspacing="0">
                       <tr> 
                         <td width="56">
                         		<img src="/images/btn_login.gif" width="56" height="20" border="0" style="cursor:pointer;"/>
                         </td>
                         <td width="10">&nbsp;</td>
                         <td width="70">
                       			<img src="/images/btn_add.gif" width="70" height="20" border="0" style="cursor:pointer;">
                         </td>
                       </tr>
                     </table>
                 </td>
                 <td width="20" height="20">&nbsp;</td>
              </tr>
              <tr>
                <td colspan="4" align="center" style="padding-top:10px;">
                    <img id="kakaoLoginBtn" src="/images/kakao_login_medium_narrow.jpg" style="cursor:pointer;"/>
                </td>
              </tr>
              <tr>
                <td colspan="4" align="center" style="padding-top:5px;">
                    <img id="googleLoginBtn" src="/images/btn_google_signin_light_normal_web.png" style="cursor:pointer; height: 45px;"/>
                    <!-- https://developers.google.com/identity/branding-guidelines -->
                </td>
              </tr>
             <tr>
             <td colspan="4" align="center" style="padding-top:5px;">
                 <img id="naverLoginBtn" src="/images/btnG_naver.png" style="cursor:pointer; height: 45px;"/>
                 </td>
             </tr>
            </table>
            </td>
      	</tr>                            
      </table>
      </td>
  </tr>
</table>
</TD>
</TR>
</TABLE>

</div>

</form>

</body>
</html>