package spring.model2.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * FileName : DispatcherServlet.java
 * ㅇ 단일인입점(Single Point of Entry)
 * ㅇ Client 요구사항 판단(?)
 * ㅇ 선처리 / 공통처리
 * 		- Work Flow Control :: 권한,인증 등..
 * 		- Client Form Data 한글 처리  
 * ㅇ Business logic 수행 ( Bean Method Call)
 * ㅇ Model 과 View 연결 
 * 		- Business Logic 처리 결과  JSP 전달 (Object Scope / VO 사용 )  
 * 	ㅇ 처리된 결과에 따라, JSP 로 forward /sendRedirect  : Navigation
 *	
 * ////////////////////////////////////// 변경된 부분 /////////////////////////////////////////
 * ㅇ DispatcherServlet은 단순히 단일 인입점  역할 만
 * ㅇ 단일 인입점 이외의 모든 역활은 각각의 다른 Bean 으로 위임 
 *  ////////////////////////////////////////////////////////////////////////////////////////////////////
 */
public class DispatcherServlet extends HttpServlet{
	
	///service() Method
	public void service(HttpServletRequest req,HttpServletResponse res)
                                                               throws ServletException,IOException{
		
		System.out.println("\n[ DispatcherServlet.service() start........]");
		
		//==> CONTROLLER :: client 요구사항 판단 ::  URL/URI 이용
		String actionPage = this.getURI( req.getRequestURI() );
		System.out.println("::URI ?  =>: "+req.getRequestURI());
		System.out.println("::client의 요구사항은 ?  =>: "+actionPage);

		//==> CONTROLLER :: 선처리 / 공통처리 사항이 있다면  
		//==> 본 예제는..  :: 한글처리 
		req.setCharacterEncoding("UTF-8");

		//==> CONTROLLER :: Client 요구사항 처리 및 Business logic 처리
		//==> Business Logic 을 처리하는  Controller 선언
		Controller controller = null;

		//==> client 의 request 에 대응하는 Controller instance 생성
		//==> ControllerMapping 를 통해 client Request 를 처리할 Controller instance 생성
		ControllerMapping cf = ControllerMapping.getInstance();
		controller = cf.getController(actionPage);
		
		//====================================================
		//==> 변경된 부분
		//==> 각각의 Controller 는 ModelAndView(Model/View 추상화한) return  
		//==> ViewResolver 객체를 이용Forward 
		//====================================================
		//controller.execute(req,res);
		ModelAndView modelAndView = controller.execute(req,res);
		new ViewResolver().forward(req, res, modelAndView);
		
		System.out.println("[ DispatcherServlet.service() end........]");

	}//end of service
	
	//client 의 요구사항 판단.  ==>requestURI = "/~~.do" 형식이므로....
	private String getURI(String requestURI){
		int start = requestURI.lastIndexOf('/')+1;
		int end = requestURI.lastIndexOf(".do");
		String actionPage = requestURI.substring(start,end);
		return 	actionPage;
	}

}//end of class