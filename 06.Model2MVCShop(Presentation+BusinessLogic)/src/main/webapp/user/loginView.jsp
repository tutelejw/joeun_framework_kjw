<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
<title>로그인 화면</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

	function fncLogin() {
		var id = document.loginForm.userId.value;
		var pw = document.loginForm.password.value;

		if (id == null || id.trim() === "") {
			alert('ID 를 입력하지 않으셨습니다.');
			document.loginForm.userId.focus();
			return false;
		}

		if (pw == null || pw.trim() === "") {
			alert('패스워드를 입력하지 않으셨습니다.');
			document.loginForm.password.focus();
			return false;
		}

		// 숨겨진 submit 버튼 클릭 → Enter 키 대응
		document.getElementById("realSubmit").click();
	}

	// 페이지 로드 시 ID 입력란에 포커스
	window.onload = function() {
		document.getElementById("userId").focus();
	}

</script>

</head>

<body bgcolor="#ffffff" text="#000000" >

<form name="loginForm"  method="post" action="/login.do" target="_parent">

<div align="center">

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
          <td width="345" align="left" valign="top" background="/images/login02.gif">
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
                       
<input type="text" name="userId" id="userId" class="ct_input_g"
       style="width:180px; height:19px" maxLength='50'/>
  
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
                         	<!-- ★★★ 로그인 버튼 클릭시 fncLogin() 호출 ★★★ -->
                         	<a href="javascript:fncLogin();">
                         		<img src="/images/btn_login.gif" width="56" height="20" border="0"/>
                         	</a>
                         	
                         	<!-- ★★★ Enter 키 입력 시 폼 submit 처리 위한 숨겨진 submit 버튼 ★★★ -->
                         	<input type="submit" id="realSubmit" style="display:none;" />
                         </td>
                         <td width="10">&nbsp;</td>
                         <td width="70">
                         	<a href="addUserView.jsp">
                         		<img src="/images/btn_add.gif" width="70" height="20" border="0">
                         	</a>
                         </td>
                       </tr>
                     </table>
                 </td>
                 <td width="20" height="20">&nbsp;</td>
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