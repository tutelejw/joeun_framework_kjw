package com.model2.mvc.web.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
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
	public String login(HttpSession session) throws Exception{
		
		System.out.println("/user/logon : GET");
	       // 1. state 값 생성을 컨트롤러로 이동
        String state = new BigInteger(130, new SecureRandom()).toString();
        
        // 2. 생성된 state 값을 세션에 저장
        session.setAttribute("state", state);
        System.out.println("  - [CSRF] 세션에 state 값 저장: " + state);

        // 3. 기존과 동일하게 loginView.jsp로 리다이렉트
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
//	public String kakaoLogin(@RequestParam("code") String code, HttpSession session) throws Exception {
		public String kakaoLogin(@RequestParam("code") String code, HttpSession session, HttpServletRequest request) throws Exception { 
	    System.out.println("============== KAKAO LOGIN START ==============");
        // ▼▼▼ [추가] 요청받은 URL을 기반으로 동적 Redirect URI 생성 ▼▼▼
        String requestURL = request.getRequestURL().toString(); // 예: "http://127.0.0.1:8080/user/kakaoLogin"
        System.out.println("요청받은 전체 URL: " + requestURL);
        // ▲▲▲
	    System.out.println("1. /user/kakaoLogin GET 요청 받음");

	    if (code == null || code.trim().isEmpty()) {
	        System.out.println("[ERROR] 인가 코드가 비어있습니다.");
	        // [수정된 부분] ViewResolver를 거치지 않고 JSP 파일로 직접 포워딩
	        return "forward:/user/kakaoCallback.jsp";
	    }
	    System.out.println("2. 인가 코드 확인 완료: " + code);

	    // 1. 인가 코드로 Access Token 받기
	    String accessToken = getKakaoAccessToken(code, requestURL); 
	    	    
	    if (accessToken == null) {
	        System.out.println("[ERROR] Access Token 받기 실패");
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
	    //String kakaoId = String.valueOf(kakaoUserInfo.get("id"));
	    String kakaoId = "k_" + String.valueOf(kakaoUserInfo.get("id"));
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
	        user.setPassword(kakaoId);
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

	    // [수정] 5. 성공 상태를 전달하며 kakaoCallback.jsp로 포워딩
	    System.out.println("return forward:/user/kakaoCallback.jsp?login=success");	    
	    // ▼▼▼ [핵심 수정] forward를 redirect로 변경 ▼▼▼
	    return "redirect:/user/kakaoCallback.jsp?login=success";

	}

    // [신규 추가] 인가 코드로 Access Token을 요청하는 메소드
    	private String getKakaoAccessToken(String code, String redirectURI) throws Exception {
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
        // ▼▼▼ [수정] 하드코딩된 주소 대신 파라미터로 받은 redirectURI 사용 ▼▼▼
        sb.append("&redirect_uri=" + redirectURI);
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
    
    
    
    
    
    
    // ============= [신규] 구글 로그인 콜백 처리 메소드 추가 =============
    @RequestMapping(value="googleLogin", method=RequestMethod.GET)
    public String googleLogin(@RequestParam("code") String code, HttpSession session, Model model) throws Exception {

        System.out.println("============== GOOGLE LOGIN START ==============");
        System.out.println("1. /user/googleLogin GET 요청 받음 (Callback)");
        System.out.println("2. 인가 코드(Authorization Code) 확인: " + code);

        // 3. 인가 코드를 사용하여 Access Token 요청
        String accessToken = getGoogleAccessToken(code);
        if (accessToken == null) {
            System.out.println("[ERROR] Access Token 받기 실패");
            model.addAttribute("loginSuccess", false);
            return "forward:/user/googleCallback.jsp";
        }
        System.out.println("3. Access Token 받기 성공: " + accessToken);

        // 4. Access Token으로 구글 사용자 정보 요청
        Map<String, Object> googleUserInfo = getGoogleUserInfo(accessToken);
        if (googleUserInfo == null) {
            System.out.println("[ERROR] 구글 사용자 정보 받기 실패");
            model.addAttribute("loginSuccess", false);
            return "forward:/user/googleCallback.jsp";
        }
        System.out.println("4. 구글 사용자 정보 받기 성공: " + googleUserInfo);

        // 5. 구글 사용자 정보 기반으로 회원 정보 확인 및 처리
        String googleId = (String) googleUserInfo.get("id");
        String email = (String) googleUserInfo.get("email");
        String userName = (String) googleUserInfo.get("name");
        String userId = "g_" + googleId;

        System.out.println("[DEBUG] Google User Info (sub): " + googleId);
        System.out.println("[DEBUG] Google User Info (email): " + email);
        System.out.println("[DEBUG] Google User Info (name): " + userName);
        System.out.println("[DEBUG] Generated System userId: " + userId);

        User user = userService.getUser(userId);

        // 비회원일 경우 자동 회원가입
        if (user == null) {
            System.out.println("5. 비회원 확인. 자동 회원가입을 시작합니다.");
            user = new User();
            user.setUserId(userId);
            user.setUserName(userName);
            user.setEmail(email);
            user.setPassword(googleId); // googleId를 임시 비밀번호로 저장
            user.setRole("user"); // 기본 역할 부여

            System.out.println("[DEBUG] New User object to be saved: " + user);

            userService.addUser(user);
            System.out.println("6. 신규 회원가입 완료.");
        } else {
            System.out.println("5. 기존 회원 확인: " + user.getUserId());
        }

        // 7. 세션에 로그인 정보 저장
        session.setAttribute("user", user);
        model.addAttribute("loginSuccess", true);
        model.addAttribute("userId", user.getUserId());

        System.out.println("8. 세션 저장 완료. User ID: " + user.getUserId());
        System.out.println("============== GOOGLE LOGIN END ==============");

        // 9. 팝업을 제어할 JSP로 포워딩
        return "forward:/user/googleCallback.jsp";
    }

    /**
     * [신규] 인가 코드로 Access Token을 요청하는 헬퍼 메소드
     */
	private String getGoogleAccessToken(String code) throws Exception {
	    // 1. Google Cloud Console에서 발급받은 정보
	    String clientId = "1095911084251-9vedhnalqe4lhkmpakr4t1h7vqe5ld5e.apps.googleusercontent.com";
	    String clientSecret = "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"; 
	    String redirectUri = "http://localhost:8080/user/googleLogin";
	    String tokenUrl = "https://oauth2.googleapis.com/token";

	    URL url = new URL(tokenUrl);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setDoOutput(true);

        // 2. POST 요청 본문 작성
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        StringBuilder sb = new StringBuilder();
        sb.append("code=").append(code);
        sb.append("&client_id=").append(clientId);
        sb.append("&client_secret=").append(clientSecret);
        sb.append("&redirect_uri=").append(redirectUri);
        sb.append("&grant_type=authorization_code");
        bw.write(sb.toString());
        bw.flush();

        // [디버깅] 요청 본문 및 응답 코드 출력
        System.out.println("[getGoogleAccessToken] Request Body: " + sb.toString());
        int responseCode = conn.getResponseCode();
        System.out.println("[getGoogleAccessToken] Response Code: " + responseCode);

	    if (responseCode == 200) {
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = "";
	        StringBuilder result = new StringBuilder();
	        while ((line = br.readLine()) != null) {
	            result.append(line);
	        }
	        br.close();
            bw.close();

            // [디버깅] 응답 결과 출력
            System.out.println("[getGoogleAccessToken] Response Body: " + result.toString());

	        ObjectMapper objectMapper = new ObjectMapper();
	        Map<String, Object> jsonMap = objectMapper.readValue(result.toString(), new TypeReference<Map<String, Object>>() {});
	        return (String) jsonMap.get("access_token");
	    }
	    return null;
	}

    /**
     * [신규] Access Token으로 사용자 정보를 요청하는 헬퍼 메소드
     */
	private Map<String, Object> getGoogleUserInfo(String accessToken) throws Exception {
	    String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
	    URL url = new URL(userInfoUrl);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = conn.getResponseCode();
        System.out.println("[getGoogleUserInfo] Response Code: " + responseCode);

	    if (responseCode == 200) {
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = "";
	        StringBuilder result = new StringBuilder();
	        while ((line = br.readLine()) != null) {
	            result.append(line);
	        }
	        br.close();

            // [디버깅] 사용자 정보 응답 출력
            System.out.println("[getGoogleUserInfo] Response Body: " + result.toString());

	        ObjectMapper objectMapper = new ObjectMapper();
	        return objectMapper.readValue(result.toString(), new TypeReference<Map<String, Object>>() {});
	    }
	    return null;
	}
	
	
    // ============= [신규] 네이버 로그인 콜백 처리 메소드 추가 =============
    @RequestMapping(value="naverLogin", method=RequestMethod.GET)
    public String naverLogin(@RequestParam("code") String code, 
                             @RequestParam("state") String state, 
                             HttpSession session,
                             HttpServletRequest request,
                             Model model) throws Exception {

        System.out.println("\n============== NAVER LOGIN START ==============");
        System.out.println("1. /user/naverLogin GET 요청 받음 (Callback)");

        // [디버깅] 전달받은 code, state 값 확인
        System.out.println("   - Authorization Code: " + code);
        System.out.println("   - State: " + state);

        // [보안] 세션에 저장된 state와 콜백으로 전달받은 state가 일치하는지 확인
        String sessionState = (String) session.getAttribute("state");
        System.out.println("   - Session State: " + sessionState);
        if (sessionState == null || !sessionState.equals(state)) {
            System.out.println("[ERROR] 세션이 만료되었거나 유효하지 않은 state 값입니다.");
            model.addAttribute("loginSuccess", false);
            model.addAttribute("errorMessage", "세션 불일치 오류");
            return "forward:/user/naverCallback.jsp";
        }
        session.removeAttribute("state"); // 사용한 state 값은 즉시 제거

        // 2. 인가 코드를 사용하여 Access Token 요청
        String accessToken = getNaverAccessToken(code, state, request);
        if (accessToken == null) {
            System.out.println("[ERROR] Access Token 받기 실패");
            model.addAttribute("loginSuccess", false);
            model.addAttribute("errorMessage", "토큰 발급 실패");
            return "forward:/user/naverCallback.jsp";
        }
        System.out.println("2. Access Token 받기 성공: " + accessToken);

        // 3. Access Token으로 네이버 사용자 정보 요청
        Map<String, Object> naverUserInfo = getNaverUserInfo(accessToken);
        if (naverUserInfo == null) {
            System.out.println("[ERROR] 네이버 사용자 정보 받기 실패");
            model.addAttribute("loginSuccess", false);
            model.addAttribute("errorMessage", "사용자 정보 조회 실패");
            return "forward:/user/naverCallback.jsp";
        }
        System.out.println("3. 네이버 사용자 정보 받기 성공: " + naverUserInfo);
        
        // 4. 네이버 사용자 정보 기반으로 회원 정보 확인 및 처리
        Map<String, String> response = (Map<String, String>) naverUserInfo.get("response");
        String naverId = response.get("id");
        String email = response.get("email");
        String userName = response.get("name");
        String userId = "n_" + naverId; // 기존 회원 ID와 충돌 방지를 위해 'n_' 접두사 추가

        // [디버깅] 네이버에서 받은 사용자 정보 로그
        System.out.println("   - Naver Unique ID: " + naverId);
        System.out.println("   - Email: " + email);
        System.out.println("   - Name: " + userName);
        System.out.println("   - Generated System userId: " + userId);

        User user = userService.getUser(userId);

        // 5. 비회원일 경우 자동 회원가입
        if (user == null) {
            System.out.println("4. 비회원 확인. 자동 회원가입을 시작합니다.");
            user = new User();
            user.setUserId(userId);
            user.setUserName(userName);
            user.setEmail(email);
            user.setPassword(naverId); // naverId를 임시 비밀번호로 저장
            user.setRole("user"); 

            System.out.println("   - DB 저장 직전 User 객체: " + user);
            userService.addUser(user);
            System.out.println("5. 신규 회원가입 완료.");
        } else {
            System.out.println("4. 기존 회원 확인: " + user.getUserId());
        }

        // 6. 세션에 로그인 정보 저장
        session.setAttribute("user", user);
        model.addAttribute("loginSuccess", true);
        model.addAttribute("userId", user.getUserId());

        System.out.println("6. 세션 저장 완료. User ID: " + user.getUserId());
        System.out.println("============== NAVER LOGIN END ==============\n");

        // 7. 팝업을 제어할 JSP로 포워딩
        return "forward:/user/naverCallback.jsp";
    }

    /**
     * [신규] 인가 코드로 Access Token을 요청하는 헬퍼 메소드
     */
    private String getNaverAccessToken(String code, String state, HttpServletRequest request) throws Exception {
        // 1. 네이버 개발자 센터에서 발급받은 정보
        String clientId = "YhvYDqSntCxLVR1hLWdt"; // ◀◀◀ 여기에 클라이언트 ID 입력
        String clientSecret = "UfidBKEeEm"; // ◀◀◀ 여기에 클라이언트 시크릿 입력

        // 2. 동적 Redirect URI 생성
        // 요청받은 URL("http://localhost:8080/user/naverLogin")을 그대로 사용
        String redirectURI = request.getRequestURL().toString();
        
        // 3. 토큰 발급 요청 URL
        String tokenUrl = "https://nid.naver.com/oauth2.0/token";
        URL url = new URL(tokenUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        // 4. POST 요청 본문 작성
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        StringBuilder sb = new StringBuilder();
        sb.append("grant_type=authorization_code");
        sb.append("&client_id=").append(clientId);
        sb.append("&client_secret=").append(clientSecret);
        sb.append("&redirect_uri=").append(URLEncoder.encode(redirectURI, "UTF-8"));
        sb.append("&code=").append(code);
        sb.append("&state=").append(state);
        bw.write(sb.toString());
        bw.flush();

        // [디버깅] 토큰 요청 정보 및 응답 코드 출력
        System.out.println("\n   [getNaverAccessToken] >> Request URL: " + tokenUrl);
        System.out.println("   [getNaverAccessToken] >> Request Body: " + sb.toString());
        int responseCode = conn.getResponseCode();
        System.out.println("   [getNaverAccessToken] >> Response Code: " + responseCode);

        if (responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();
            bw.close();

            // [디버깅] 토큰 응답 결과 출력
            System.out.println("   [getNaverAccessToken] >> Response Body: " + result.toString() + "\n");

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(result.toString(), new TypeReference<Map<String, Object>>() {});
            return (String) jsonMap.get("access_token");
        }
        return null;
    }

    /**
     * [신규] Access Token으로 사용자 정보를 요청하는 헬퍼 메소드
     */
    private Map<String, Object> getNaverUserInfo(String accessToken) throws Exception {
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
        URL url = new URL(userInfoUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = conn.getResponseCode();
        System.out.println("\n   [getNaverUserInfo] >> Request URL: " + userInfoUrl);
        System.out.println("   [getNaverUserInfo] >> Response Code: " + responseCode);

        if (responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();

            // [디버깅] 사용자 정보 응답 출력
            System.out.println("   [getNaverUserInfo] >> Response Body: " + result.toString() + "\n");

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(result.toString(), new TypeReference<Map<String, Object>>() {});
        }
        return null;
    }
}