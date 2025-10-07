package com.model2.mvc.web.user;

// Import 문 추가
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


@RestController
@RequestMapping("/user/*")
public class UserRestController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    public UserRestController() {
        System.out.println("==> UserRestController 실행됨 : " + this.getClass());
    }

    // [ 신규 추가 ] 카카오 로그인 처리
    @PostMapping("json/kakaoLogin")
    public User kakaoLogin(@RequestBody Map<String, String> requestBody, HttpSession session) throws Exception {
        System.out.println("/user/json/kakaoLogin : POST 호출됨");
        
        String accessToken = requestBody.get("accessToken");
        
        // 1. access_token을 사용하여 카카오 사용자 정보 조회
        Map<String, Object> kakaoUserInfo = getKakaoUserInfo(accessToken);
        
        if (kakaoUserInfo == null) {
            // 사용자 정보 조회 실패
            return null;
        }

        // 2. 카카오 ID (고유번호)를 사용하여 우리 서비스의 회원인지 확인
        String kakaoId = String.valueOf(kakaoUserInfo.get("id"));
        User user = userService.getUser(kakaoId);
        
        // 3. 비회원일 경우, 카카오 정보를 바탕으로 자동 회원가입
        if (user == null) {
            user = new User();
            user.setUserId(kakaoId); // 카카오 고유 ID를 우리 서비스의 userID로 사용

            Map<String, Object> kakaoAccount = (Map<String, Object>) kakaoUserInfo.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            user.setUserName((String) profile.get("nickname"));
            
            // 이메일은 선택 항목이므로, null 체크
            if (kakaoAccount.get("email") != null) {
                user.setEmail((String) kakaoAccount.get("email"));
            }
            
            // 패스워드는 카카오 인증을 사용하므로 임의의 값 또는 null 처리 가능
            user.setPassword(kakaoId); // 또는 다른 임의의 값
            
            // 기타 필수값들 기본값으로 설정 (예: role)
            user.setRole("user");

            userService.addUser(user);
            System.out.println("신규 카카오 회원 자동 가입 완료: " + user);
        }

        // 4. 세션에 사용자 정보 저장 (로그인 처리)
        session.setAttribute("user", user);
        
        return user;
    }

    // [ 신규 추가 ] Access Token으로 카카오 사용자 정보 가져오는 메소드
    private Map<String, Object> getKakaoUserInfo(String accessToken) throws Exception {
        String requestURL = "https://kapi.kakao.com/v2/user/me";
        
        URL url = new URL(requestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setDoOutput(true); // 요청을 통해 파라미터를 전달할 수 있도록 설정
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = conn.getResponseCode();
        System.out.println("카카오 사용자 정보 요청 결과 코드 : " + responseCode);
        
        if (responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();
            
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();
            
            // Jackson ObjectMapper를 사용하여 JSON 문자열을 Map으로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(result.toString(), new TypeReference<Map<String, Object>>() {});
        }
        
        return null; // 실패 시 null 반환
    }


    // ================= 기존 코드 =================
    @PostMapping("json/addUser")
    public boolean addUser(@RequestBody User user) throws Exception {
        System.out.println("/user/json/addUser : POST 호출됨");
        userService.addUser(user);
        return true;
    }

    @PostMapping("json/login")
    public User login(@RequestBody User user, HttpSession session) throws Exception {
        System.out.println("/user/json/login : POST 호출됨");
        User dbUser = userService.getUser(user.getUserId());
        if (dbUser != null && user.getPassword().equals(dbUser.getPassword())) {
            session.setAttribute("user", dbUser);
            return dbUser;
        }
        return null; // 로그인 실패 시 null 반환하도록 수정
    }

    @GetMapping("json/getUser/{userId}")
    public User getUser(@PathVariable String userId) throws Exception {
        System.out.println("/user/json/getUser : GET 호출됨");
        return userService.getUser(userId);
    }

    @PostMapping("json/getUserList")
    public Map<String, Object> getUserList(@RequestBody Search search) throws Exception {
        System.out.println("/user/json/getUserList : POST 호출됨");
        return userService.getUserList(search);
    }

    @PostMapping("json/updateUser")
    public boolean updateUser(@RequestBody User user) throws Exception {
        System.out.println("/user/json/updateUser : POST 호출됨");
        userService.updateUser(user);
        return true;
    }

    @GetMapping("json/checkDuplication/{userId}")
    public boolean checkDuplication(@PathVariable String userId) throws Exception {
        System.out.println("/user/json/checkDuplication : GET 호출됨");
        return userService.checkDuplication(userId);
    }
}