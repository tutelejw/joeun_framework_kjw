package com.model2.mvc.web.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


//==> 회원관리 Controller
//@Controller
//@RequestMapping("/user/*")
public class UserController_2로그인되는버전 {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
		
	public UserController_2로그인되는버전(){
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
	
	// [디버깅 로그 추가] 카카오 로그인 콜백 처리
	@RequestMapping(value="kakaoLogin", method=RequestMethod.GET)
	public String kakaoLogin(@RequestParam("code") String code, HttpSession session) throws Exception {
	    System.out.println("============== KAKAO LOGIN START ==============");
	    System.out.println("1. /user/kakaoLogin GET 요청 받음");

	    if (code == null || code.trim().isEmpty()) {
	        System.out.println("[ERROR] 인가 코드가 비어있습니다.");
	        // [수정된 부분] ViewResolver를 거치지 않고 JSP 파일로 직접 포워딩
	        return "forward:/user/kakaoCallback.jsp";
	    }
	    System.out.println("2. 인가 코드 확인 완료: " + code);

	    // 1. 인가 코드로 Access Token 받기
	    String accessToken = getKakaoAccessToken(code);
	    if (accessToken == null) {
	        System.out.println("[ERROR] Access Token 받기 실패");
	        // [수정된 부분] ViewResolver를 거치지 않고 JSP 파일로 직접 포워딩
	        return "forward:/user/kakaoCallback.jsp";
	    }
	    System.out.println("3. Access Token 받기 성공: " + accessToken);

	    // 2. Access Token으로 카카오 사용자 정보 받기
	    Map<String, Object> kakaoUserInfo = getKakaoUserInfo(accessToken);

	    if (kakaoUserInfo == null) {
	        System.out.println("[ERROR] 카카오 사용자 정보 받기 실패");
	        // [수정된 부분] ViewResolver를 거치지 않고 JSP 파일로 직접 포워딩
	        return "forward:/user/kakaoCallback.jsp";
	    }
	    System.out.println("4. 카카오 사용자 정보 받기 성공: " + kakaoUserInfo);

	    // 3. 카카오 ID로 회원 정보 확인 및 처리
	    String kakaoId = String.valueOf(kakaoUserInfo.get("id"));
	    User user = userService.getUser(kakaoId);

	    // 비회원일 경우 자동 회원가입
	    if (user == null) {
	        System.out.println("5. 비회원 확인. 자동 회원가입을 시작합니다.");
	        user = new User();
	        user.setUserId(kakaoId);
	        Map<String, Object> kakaoAccount = (Map<String, Object>) kakaoUserInfo.get("kakao_account");
	        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
	        user.setUserName((String) profile.get("nickname"));
	        if (kakaoAccount.get("email") != null) {
	            user.setEmail((String) kakaoAccount.get("email"));
	        }
	        user.setPassword(kakaoId); // 비밀번호는 고유 ID로 임시 저장
	        user.setRole("user");
	        userService.addUser(user);
	        System.out.println("6. 신규 회원가입 완료: " + user);
	    } else {
	        System.out.println("5. 기존 회원 확인: " + user.getUserId());
	    }

	    // 4. 세션에 로그인 정보 저장
	    session.setAttribute("user", user);
	    User sessionUser = (User) session.getAttribute("user");
	    if (sessionUser != null && sessionUser.getUserId().equals(kakaoId)) {
	        System.out.println("7. 세션 저장 성공! User ID: " + sessionUser.getUserId());
	    } else {
	        System.out.println("[ERROR] 세션 저장 실패!");
	    }
	    System.out.println("============== KAKAO LOGIN END ==============");


	    // [핵심 수정 부분] ViewResolver를 거치지 않고 JSP 파일로 직접 포워딩
	    return "forward:/user/kakaoCallback.jsp";
	}

    // [신규 추가] 인가 코드로 Access Token을 요청하는 메소드
    private String getKakaoAccessToken(String code) throws Exception {
        String requestURL = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(requestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        // POST 요청 본문 작성
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        StringBuilder sb = new StringBuilder();
        sb.append("grant_type=authorization_code");
        sb.append("&client_id=f38379dc4a1fd8db1c81e44d5bf62547"); // REST API 키
        sb.append("&redirect_uri=http://localhost:8080/user/kakaoLogin"); // 리다이렉트 URI
        sb.append("&code=" + code);
        bw.write(sb.toString());
        bw.flush();

        if (conn.getResponseCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();
            bw.close();

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(result.toString(), new TypeReference<Map<String, Object>>() {});
            
            return (String) jsonMap.get("access_token");
        }
        return null;
    }
    
    // [신규 추가] Access Token으로 사용자 정보를 요청하는 메소드 (UserRestController의 것과 동일)
    private Map<String, Object> getKakaoUserInfo(String accessToken) throws Exception {
        String requestURL = "https://kapi.kakao.com/v2/user/me";
        URL url = new URL(requestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        if (conn.getResponseCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();
            
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(result.toString(), new TypeReference<Map<String, Object>>() {});
        }
        return null;
    }
}