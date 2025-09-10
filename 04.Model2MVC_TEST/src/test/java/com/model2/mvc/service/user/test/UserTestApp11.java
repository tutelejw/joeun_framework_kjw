package com.model2.mvc.service.user.test;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.model2.mvc.service.domain.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.impl.UserDaoImpl11;

public class UserTestApp11 {

    public static void main(String[] args) throws Exception {
        System.out.println("---- UserTestApp11 START ----");
        
        // 1. SqlSession 생성
        SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
        
        // 2. UserDaoImpl11 생성 및 SqlSession DI
        UserDaoImpl11 userDao = new UserDaoImpl11();
        userDao.setSqlSession(sqlSession);
        
        // 3. 테스트용 User 생성
        User user = new User();
        user.setUserId("testuser01");
        user.setUserName("테스트 사용자");
        user.setPassword("pass123");
        user.setRole("user");
        user.setSsn("9001011234567");
        user.setPhone("010-1234-5678");
        user.setAddr("서울시 강남구");
        user.setEmail("testuser01@example.com");
        user.setRegDate(new Date(System.currentTimeMillis()));
        
        // 4. insertUser 테스트
        System.out.println(">>> insertUser 테스트 시작");
        userDao.insertUser(user);
        
        // 5. findUser 테스트
        System.out.println(">>> findUser 테스트 시작");
        User foundUser = userDao.findUser("testuser01");
        System.out.println("조회된 User: " + (foundUser != null ? foundUser : "없음"));
        
        // 6. updateUser 테스트 (이름 및 이메일 변경)
        System.out.println(">>> updateUser 테스트 시작");
        if (foundUser != null) {
            foundUser.setUserName("변경된 사용자");
            foundUser.setEmail("changed_email@example.com");
            userDao.updateUser(foundUser);
            User updatedUser = userDao.findUser(foundUser.getUserId());
            System.out.println("수정 후 User: " + updatedUser);
        }
        
        // 7. checkDuplication 테스트
        System.out.println(">>> checkDuplication 테스트 시작");
        boolean isDup = userDao.checkDuplication("testuser01");
        System.out.println("아이디 중복 여부: " + isDup);
        
        // 8. getUserList 테스트 (검색 조건 없이 전체 조회)
        System.out.println(">>> getUserList 테스트 시작");
        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(10);
        Map<String, Object> userListMap = userDao.getUserList(search);
        
        @SuppressWarnings("unchecked")
        List<User> userList = (List<User>) userListMap.get("list");
        
        System.out.println("총 User 수: " + userListMap.get("totalCount"));
        for (User u : userList) {
            System.out.println(u);
        }
        
        System.out.println("---- UserTestApp11 END ----");
        
        // sqlSession 닫기 (필요시)
        sqlSession.close();
    }
}
