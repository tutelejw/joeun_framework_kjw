package com.model2.mvc.web.user;

import java.net.URI;
//import java.net.http.*;
//import java.net.http.HttpRequest.BodyPublishers;
import java.time.Duration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;

//import com.kakao.auth.KakaoSDK;
//import com.kakao.auth.ISessionCallback;
//import com.kakao.auth.Session;
//import com.kakao.usermgmt.UserManagement;
//import com.kakao.usermgmt.callback.UserProfileCallback;
//import com.kakao.usermgmt.response.model.UserProfile;

//==> 회원관리 Controller
@Controller
@RequestMapping("/user/*")
public class UserController {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
		
	public UserController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	
	@RequestMapping( value="addUser", method=RequestMethod.GET )
	public String addUser() throws Exception{
	
		System.out.println("/user/addUser : GET");
		
		return "redirect:/user/addUserView.jsp";
	}
	
	@RequestMapping( value="addUser", method=RequestMethod.POST )
	public String addUser( @ModelAttribute("user") User user ) throws Exception {

		System.out.println("/user/addUser : POST");
		//Business Logic
		userService.addUser(user);
		
		return "redirect:/user/loginView.jsp";
	}
	

	@RequestMapping( value="getUser", method=RequestMethod.GET )
	public String getUser( @RequestParam("userId") String userId , Model model ) throws Exception {
		
		System.out.println("/user/getUser : GET");
		//Business Logic
		User user = userService.getUser(userId);
		// Model 과 View 연결
		model.addAttribute("user", user);
		
		return "forward:/user/getUser.jsp";
	}
	

	@RequestMapping( value="updateUser", method=RequestMethod.GET )
	public String updateUser( @RequestParam("userId") String userId , Model model ) throws Exception{

		System.out.println("/user/updateUser : GET");
		//Business Logic
		User user = userService.getUser(userId);
		// Model 과 View 연결
		model.addAttribute("user", user);
		
		return "forward:/user/updateUser.jsp";
	}

	@RequestMapping( value="updateUser", method=RequestMethod.POST )
	public String updateUser( @ModelAttribute("user") User user , Model model , HttpSession session) throws Exception{

		System.out.println("/user/updateUser : POST");
		//Business Logic
		userService.updateUser(user);
		
		String sessionId=((User)session.getAttribute("user")).getUserId();
		if(sessionId.equals(user.getUserId())){
			session.setAttribute("user", user);
		}
		
		return "redirect:/user/getUser?userId="+user.getUserId();
	}
	
	
	@RequestMapping( value="login", method=RequestMethod.GET )
	public String login() throws Exception{
		
		System.out.println("/user/logon : GET");

		return "redirect:/user/loginView.jsp";
	}
	
	@RequestMapping( value="login", method=RequestMethod.POST )
	public String login(@ModelAttribute("user") User user , HttpSession session ) throws Exception{
		
		System.out.println("/user/login : POST");
		//Business Logic
		User dbUser=userService.getUser(user.getUserId());
		
		if( user.getPassword().equals(dbUser.getPassword())){
			session.setAttribute("user", dbUser);
		}
		
		return "redirect:/index.jsp";
	}
		
	
	@RequestMapping( value="logout", method=RequestMethod.GET )
	public String logout(HttpSession session ) throws Exception{
		
		System.out.println("/user/logout : POST");
		
		session.invalidate();
		
		return "redirect:/index.jsp";
	}
	
	
	@RequestMapping( value="checkDuplication", method=RequestMethod.POST )
	public String checkDuplication( @RequestParam("userId") String userId , Model model ) throws Exception{
		
		System.out.println("/user/checkDuplication : POST");
		//Business Logic
		boolean result=userService.checkDuplication(userId);
		// Model 과 View 연결
		model.addAttribute("result", new Boolean(result));
		model.addAttribute("userId", userId);

		return "forward:/user/checkDuplication.jsp";
	}

	
	@RequestMapping( value="listUser" )
	public String listUser( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/user/listUser : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=userService.getUserList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/user/listUser.jsp";
	}
	
//	@GetMapping("/kakaoLogin")
//	public String kakaoLogin(@RequestParam("code") String code, HttpSession session) throws Exception {
//	    // 1. Access Token 요청
//	    String accessToken = getKakaoAccessToken(code);
//
//	    // 2. 사용자 정보 요청
//	    Map<String, Object> kakaoUserInfo = getKakaoUserInfo(accessToken);
//
//	    String kakaoId = String.valueOf(kakaoUserInfo.get("id"));
//	    String nickname = ((Map)((Map)kakaoUserInfo.get("kakao_account")).get("profile")).get("nickname").toString();
//
//	    // 3. DB 사용자 확인 (없으면 자동 가입 처리)
//	    User user = userService.getUser(kakaoId);
//	    if (user == null) {
//	        user = new User();
//	        user.setUserId(kakaoId);
//	        user.setUserName(nickname);
//	        user.setPassword("kakao"); // 더미 비밀번호
//	        userService.addUser(user);
//	    }
//
//	    // 4. 로그인 처리
//	    session.setAttribute("user", user);
//
//	    return "redirect:/index.jsp";
//	}

//	private String getKakaoAccessToken(String code) throws Exception {
//	    String requestUrl = "https://kauth.kakao.com/oauth/token";
//
//	    // 요청 파라미터 설정
//	    String form = "grant_type=authorization_code"
//	                + "&client_id=f38379dc4a1fd8db1c81e44d5bf62547"
//	                + "&redirect_uri=http://localhost:8080/user/kakaoLogin"
//	                + "&code=" + code;
//
////	    // HttpClient 생성
////	    HttpClient client = HttpClient.newHttpClient();
////
////	    // HttpRequest 생성
////	    HttpRequest request = HttpRequest.newBuilder()
////	            .uri(URI.create(requestUrl))
////	            .timeout(Duration.ofSeconds(10))
////	            .header("Content-Type", "application/x-www-form-urlencoded")
////	            .POST(BodyPublishers.ofString(form))
////	            .build();
////
////	    // 응답 처리
////	    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//	    // JSON 파싱
//	    ObjectMapper objectMapper = new ObjectMapper();
//	    Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);
//
//	    return responseMap.get("access_token").toString();
//	}



	

}