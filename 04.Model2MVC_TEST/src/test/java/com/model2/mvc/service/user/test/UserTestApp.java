package com.model2.mvc.service.user.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.model2.mvc.service.domain.Search;
import com.model2.mvc.service.domain.User;

public class UserTestApp {

    public static void main(String[] args) throws Exception {
        
        // SqlSession 생성
        SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
        
        // 테스트용 User 객체 생성
        User user = new User();
        user.setUserId("user04");
        user.setUserName("주몽");
        user.setPassword("user04");
        user.setRole("user");
        user.setSsn("9001011234567");
        user.setPhone("010-1234-5678");
        user.setAddr("Seoul");
        user.setEmail("jumong@example.com");
        user.setRegDate(new java.sql.Date(System.currentTimeMillis()));

        // 1. INSERT
        System.out.println("1. addUser :: INSERT");
        int insertCount = sqlSession.insert("UserMapper10.addUser", user);
        System.out.println("Inserted: " + insertCount);
        System.out.println();

        // 2. SELECT (getUser)
        System.out.println("2. getUser :: SELECT");
        User foundUser = sqlSession.selectOne("UserMapper10.getUser", "user04");
        System.out.println("Found User: " + foundUser);
        System.out.println();

        // 3. UPDATE (updateUser)
        System.out.println("3. updateUser :: UPDATE");
        user.setUserName("온달");
        user.setRole("admin");
        int updateCount = sqlSession.update("UserMapper10.updateUser", user);
        System.out.println("Updated: " + updateCount);
        System.out.println("Updated User: " + sqlSession.selectOne("UserMapper10.getUser", "user04"));
        System.out.println();

        // 4. getUserList :: 전체 조회
        System.out.println("4. getUserList :: 전체 사용자 목록 조회");
        Search searchAll = new Search();  // 검색 조건 없음
        List<User> allUsers = sqlSession.selectList("UserMapper10.getUserList", searchAll);
        allUsers.forEach(u -> System.out.println(u));
        System.out.println();

        // 5. getUserList :: 조건 검색 (userId)
        System.out.println("5. getUserList :: userId='user04'");
        Search searchById = new Search();
        searchById.setSearchCondition("userId");
        searchById.setValue("user04");
        List<User> usersById = sqlSession.selectList("UserMapper10.getUserList", searchById);
        usersById.forEach(u -> System.out.println(u));
        System.out.println();

        // 6. getUserList :: 조건 검색 (userName)
        System.out.println("6. getUserList :: userName='온달'");
        Search searchByName = new Search();
        searchByName.setSearchCondition("userName");
        searchByName.setValue("온달");
        List<User> usersByName = sqlSession.selectList("UserMapper10.getUserList", searchByName);
        usersByName.forEach(u -> System.out.println(u));
        System.out.println();

        // 7. DELETE
        System.out.println("7. removeUser :: DELETE");
        int deleteCount = sqlSession.delete("UserMapper10.removeUser", "user04");
        System.out.println("Deleted: " + deleteCount);

        // SqlSession 종료
        sqlSession.close();
    }
}
