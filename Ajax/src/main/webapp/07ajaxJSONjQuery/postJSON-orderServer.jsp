<%@page contentType="text/html; charset=UTF-8" %>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="org.json.simple.JSONArray" %>
<% 
	//==>한글처리(POST)	 ::  UTF-8로 encoding
	request.setCharacterEncoding("UTF-8");
	String name =  request.getParameter("name");
	String phone = request.getParameter("phone");
	
	//==> 주소를 임의로 발생시키기...
	String address ="";
	int random = new java.util.Random().nextInt(3);
	if( random ==0 ){
		address = "서울";
	}else if( random ==1 ){
		address = "부산";
	}else{
		address = "제주도";
	}
%>

<%
	 System.out.println("post-orderServer.jsp  ::   이름="+name+" 주소="+address+" 전화="+phone);

JSONObject obj = new JSONObject();
obj.put("name",name);
obj.put("phone",phone);
obj.put("address",address);
System.out.println(obj);

%>

<%=obj %>


<%-- 이름 : <%= name %> 고객님
전화 : <%= phone %>
주소 : <%= address %> --%>