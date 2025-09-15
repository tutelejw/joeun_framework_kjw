<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="spring.model2.service.user.vo.UserVO" %>

<!-- ////////////////////////////////////////   변 경 된  부 분  //////////////////////////////////////////////////////////
	ㅇ Model2 Web Arch. 적용 시 ::  JSP는 View 역할  
 	ㅇ Work Flow Control 은 Controller 담당                                                  
 	ㅇ 아래의 주석 : Controller 담당하는 ControlServlet 에서 처리   
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->

<%	UserVO userVO = (UserVO)session.getAttribute("userVO");	%>

<%	//if ( userVO == null  ||  ! userVO.isActive() )  { %>
		<%-- <jsp:forward page="logon.jsp" /> --%>
<% //} %>

<html>
	<head></head>
	<body>
		<p>Simple Model2 Examples</p>
		<p> 환영합니다.  : <%= userVO.getUserId()%>님</p>
		
		<br/><br/>
		<hr/>
		:: info <%= request.getAttribute("info") %>
		<hr/>
	</body>
</html>