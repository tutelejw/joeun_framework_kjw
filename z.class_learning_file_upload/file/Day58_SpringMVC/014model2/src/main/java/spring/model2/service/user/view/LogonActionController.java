package spring.model2.service.user.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spring.model2.control.Controller;
import spring.model2.control.ModelAndView;
import spring.model2.service.user.dao.UserDao;
import spring.model2.service.user.vo.UserVO;

/*
 * FileName : LogonActionController.java
 *	:: Client Request (logonAction.do) 처리 Controller
*/	
public class  LogonActionController implements Controller{

	///Method
	public ModelAndView execute(HttpServletRequest req,HttpServletResponse res)
		                                                           throws ServletException,IOException{
		
		System.out.println("[ LogonActionController.execute() start........]");
		
		HttpSession session = req.getSession(true);

		//==> CONTROLLER :: 권한 / 인증처리
		//==> session ObjcetScope 저장된 UserVO 객체 이용 인증
		//==> 경우 1 : session ObjectScope 에 userVO인스턴스 생성 및 저장
		if(session.isNew() || session.getAttribute("userVO") == null){
			session.setAttribute("userVO",new UserVO());
		}		
		//==> 경우 2 : session ObjectScope  userVO 추출
		UserVO userVO =(UserVO)session.getAttribute("userVO");

		//==> CONTROLLER :: Navigation (forward/sendRedirect view page 결정)
		// Navigation 디폴트 페이지 지정
		String requestPage = "/user/logon.jsp";
		
		//==>UserVO.active  이용 로그인 유무판단 
		if( userVO.isActive() ){
			requestPage = "/user/home.jsp";
		}
		//==> 로그인한 회원이 아니라면..........
		else{
			//==> Client Form Data 처리
			String userId = req.getParameter("userId");
			String userPasswd = req.getParameter("userPasswd");
			
			//==> CONTROLLER :: Model 과 View 의 연결 : Binding
			userVO.setUserId(userId);
			userVO.setUserPasswd(userPasswd);
			
			//==> CONTROLLER :: Business logic 처리
			UserDao userDao = new UserDao();
			userDao.getUser(userVO);
			
			//==> CONTROLLER ::  Navigation (forward/sendRedirect view page 결정)  
			if( userVO.isActive() ){
				requestPage = "/user/home.jsp";
			}
		}
		//====================================================
		//==> 변경된 부분
		//==> 이동할 View( jsp page) 정보를 갖는 ModelAndView 객체 return
		//====================================================
		//==> CONTROLLER :: Navigation (최종 결정된 page forward)
		//RequestDispatcher rd = req.getRequestDispatcher(requestPage);
		//rd.forward(req,res);
		
		System.out.println("[ LogonActionController.execute() end........]");
		
		return new ModelAndView(requestPage,"info",
																"[LogonActionController Message] ::  Welcome");
	}
}//end of class