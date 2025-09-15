package spring.model2.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* FileName : Controller.java
*	- Controller 객체의 일반적,공통적 행위(Method)을 정의,규정하는 interface
*	- Controller 객체는 Control 에서 수행하는 실질적,핵심적 열할을 수행
*	- Servlet API  핵심,주요한 객체  HttpServletRequest/HttpServletResponse 인자로 전달받음
*/
public interface Controller{
	
	//====================================================
	//==> 추가된 부분
	//==> Model / View  추상화, 켑슐화한 ModelAndView 객체 return
	//====================================================
	public ModelAndView execute(HttpServletRequest req,HttpServletResponse res)
		                                                           					throws ServletException,IOException;
	
}//end of class