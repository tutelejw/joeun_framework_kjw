package mybatis.service.user.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import mybatis.service.domain.Search;
import mybatis.service.domain.User;
import mybatis.service.user.UserDao;

public class UserDaoImpl11 implements UserDao {

    private SqlSession sqlSession;

    public UserDaoImpl11() {
        System.out.println("[UserDaoImpl11] 기본 생성자 호출 - 클래스: " + this.getClass().getName());
    }

    public void setSqlSession(SqlSession sqlSession) {
        System.out.println("[UserDaoImpl11] setSqlSession 호출 - sqlSession 클래스: " + sqlSession.getClass().getName());
        this.sqlSession = sqlSession;
    }

    @Override
    public int addUser(User user) throws Exception {
        System.out.println("[UserDaoImpl11] addUser 호출 - user: " + user);
        return sqlSession.insert("UserMapper10.addUser", user);
    }

    @Override
    public User getUser(String userId) throws Exception {
        System.out.println("[UserDaoImpl11] getUser 호출 - userId: " + userId);
        return (User) sqlSession.selectOne("UserMapper10.getUser", userId);
    }

    @Override
    public int updateUser(User user) throws Exception {
        System.out.println("[UserDaoImpl11] updateUser 호출 - user: " + user);
        return sqlSession.update("UserMapper10.updateUser", user);
    }

    @Override
    public int removeUser(String userId) throws Exception {
        System.out.println("[UserDaoImpl11] removeUser 호출 - userId: " + userId);
        return sqlSession.delete("UserMapper10.removeUser", userId);
    }

    @Override
    public List<User> getUserList(Search search) throws Exception {
        System.out.println("[UserDaoImpl11] getUserList 호출 - search: " + search);
        return sqlSession.selectList("UserMapper10.getUserList", search);
    }
}
