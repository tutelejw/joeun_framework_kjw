<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	
	<title>로그인 화면</title>
	
	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
    
    <script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.6/kakao.min.js" integrity="sha384-WAtVcQYcmTO/N+C1N+1m6Gp8qxh+3NlnP7X1U7qP6P5dQY/MsRBNTh+e1ahJrkEm" crossorigin="anonymous"></script>
<!--     <script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.1/kakao.min.js"
        integrity="sha384-kDljxUXHaJ9xAb2AzRd59KxjrFjzHa5TAoFQ6GbYTCAG0PqcUX7QXPFqm4i3EuUN" crossorigin="anonymous"></script> -->
	
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
/*             $("#kakaoLoginBtn").on("click", function() {
                kakaoLogin();
            }); */
			$("#kakaoLoginBtn").on("click", function() {
			    kakaoLoginPopup(); // 함수 이름 변경
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

            // ★★★ 디버깅 코드 추가 ★★★
            // Kakao.Auth 객체를 콘솔에 출력해봅니다.
            console.log('Inspecting Kakao.Auth object:', Kakao.Auth); 
            
            // 에러가 나는 라인
            Kakao.Auth.login({
                success: function (authObj) {
                    // ... (기존 success 로직)
                },
                fail: function (err) {
                    // ... (기존 fail 로직)
                },
            });
        }
        
     // [변경] 카카오 로그인 팝업을 수동으로 띄우는 함수
function kakaoLoginPopup() {
    const KAKAO_REST_API_KEY = 'f38379dc4a1fd8db1c81e44d5bf62547'; // 본인의 REST API 키
    const KAKAO_REDIRECT_URI = 'http://localhost:8080/user/kakaoLogin'; // 콜백 URL

    // [수정] 템플릿 리터럴(``) 대신 일반 문자열 합치기(+) 방식으로 변경
    const authUrl = 'https://kauth.kakao.com/oauth/authorize?response_type=code'
                  + '&client_id=' + KAKAO_REST_API_KEY 
                  + '&redirect_uri=' + KAKAO_REDIRECT_URI;

    // 디버깅을 위해 완성된 URL을 콘솔에 출력해봅니다.
    console.log('Generated Kakao Auth URL:', authUrl);
    
    // 팝업창을 띄웁니다.
    window.open(authUrl, 'kakaoLoginPopup', 'width=600,height=700');
}
	</script>		
	
</head>

<body bgcolor="#ffffff" text="#000000" >

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