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