package com.model2.mvc.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.model2.mvc.service.domain.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserDao;

public class UserDaoImpl11 implements UserDao {

    /// Field
    private SqlSession sqlSession;

    /// Constructor
    public UserDaoImpl11() {
        System.out.println("[" + getClass().getSimpleName() + "] 생성자 호출됨");
    }

    /// Setter for SqlSession (DI 주입용)
    public void setSqlSession(SqlSession sqlSession) {
        System.out.println("[" + getClass().getSimpleName() + "] setSqlSession() 호출됨");
        this.sqlSession = sqlSession;
    }

    /// Method - insertUser
    @Override
    public void insertUser(User user) throws Exception {
        System.out.println("[" + getClass().getSimpleName() + "] insertUser() 호출됨");
        int affectedRows = sqlSession.insert("UserMapper10.addUser", user);
        System.out.println("insertUser - 영향받은 행 수: " + affectedRows);
    }

    /// Method - findUser
    @Override
    public User findUser(String userId) throws Exception {
        System.out.println("[" + getClass().getSimpleName() + "] findUser() 호출됨");
        User user = sqlSession.selectOne("UserMapper10.getUser", userId);
        System.out.println("findUser - 조회된 User: " + (user != null ? user.getUserId() : "없음"));
        return user;
    }

    /// Method - getUserList
    @Override
    public Map<String, Object> getUserList(Search search) throws Exception {
        System.out.println("[" + getClass().getSimpleName() + "] getUserList() 호출됨");
        List<User> list = sqlSession.selectList("UserMapper10.getUserList", search);

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("totalCount", list.size());  // 단순히 list 사이즈로 대체 (실제 totalCount 쿼리 필요할 수 있음)

        System.out.println("getUserList - 반환된 리스트 크기: " + list.size());

        return map;
    }

    /// Method - updateUser
    @Override
    public void updateUser(User user) throws Exception {
        System.out.println("[" + getClass().getSimpleName() + "] updateUser() 호출됨");
        int affectedRows = sqlSession.update("UserMapper10.updateUser", user);
        System.out.println("updateUser - 영향받은 행 수: " + affectedRows);
    }

    /// Method - checkDuplication
    @Override
    public boolean checkDuplication(String userId) throws Exception {
        System.out.println("[" + getClass().getSimpleName() + "] checkDuplication() 호출됨");
        User user = sqlSession.selectOne("UserMapper10.getUser", userId);
        boolean isDuplicated = (user != null);
        System.out.println("checkDuplication - 중복 여부: " + isDuplicated);
        return isDuplicated;
    }
}
