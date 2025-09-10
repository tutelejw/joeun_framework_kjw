package com.model2.mvc.service.user.test;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.model2.mvc.service.domain.User;

public class UserTestApp10 {

    public static void main(String[] args) throws Exception {

        // 1. SqlSession 생성
        SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();

        System.out.println("==> SqlSession 생성 완료 : " + sqlSession.getClass());

        // 2. Mapper 호출
        String namespace = "UserMapper10";

        // 3. 테스트용 User 생성
        User testUser = new User();
        testUser.setUserId("testuser10");
        testUser.setUserName("테스트유저10");
        testUser.setPassword("testpass");
        testUser.setRole("user");
        testUser.setSsn("9001011234567");
        testUser.setPhone("010-1234-5678");
        testUser.setAddr("서울시 마포구");
        testUser.setEmail("testuser10@example.com");
        testUser.setRegDate(new Date(System.currentTimeMillis()));

        System.out.println("\n==> 1. 사용자 추가");
        int insertCount = sqlSession.insert(namespace + ".addUser", testUser);
        System.out.println("insert count: " + insertCount);

        System.out.println("\n==> 2. 사용자 조회");
        User selectedUser = sqlSession.selectOne(namespace + ".getUser", testUser.getUserId());
        System.out.println("조회된 사용자: " + selectedUser);

        System.out.println("\n==> 3. 사용자 수정");
        selectedUser.setUserName("업데이트유저10");
        selectedUser.setEmail("updated10@example.com");
        int updateCount = sqlSession.update(namespace + ".updateUser", selectedUser);
        System.out.println("update count: " + updateCount);

        System.out.println("\n==> 4. 사용자 리스트 조회 (전체)");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("searchCondition", "");
        paramMap.put("value", "");
        List<User> userList = sqlSession.selectList(namespace + ".getUserList", paramMap);
        SqlSessionFactoryBean.printList((List<Object>) (List<?>) userList);

        System.out.println("\n==> 5. 사용자 삭제");
        int deleteCount = sqlSession.delete(namespace + ".removeUser", testUser.getUserId());
        System.out.println("delete count: " + deleteCount);

        System.out.println("\n==> 테스트 종료");

        // 세션 닫기
        sqlSession.close();
    }
}
