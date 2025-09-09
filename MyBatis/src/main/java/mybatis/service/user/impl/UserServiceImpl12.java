package mybatis.service.user.impl;

import mybatis.service.user.UserService;
import mybatis.service.user.UserDao;
import mybatis.service.domain.User;
import mybatis.service.domain.Search;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userServiceImpl12")
public class UserServiceImpl12 implements UserService {

    private UserDao userDao;

    public UserServiceImpl12() {
        System.out.println(":: "+getClass().getName()+" default 생성자 호출");
    }

    public void setUserDao(UserDao userDao) {
        System.out.println(":: "+getClass().getName()+".setUserDao() 호출");
        this.userDao = userDao;
    }

    @Override
    public int addUser(User user) throws Exception {
        System.out.println(":: "+getClass().getName()+".addUser() 호출");
        return userDao.addUser(user);
    }

    @Override
    public User getUser(String userId) throws Exception {
        System.out.println(":: "+getClass().getName()+".getUser() 호출");
        return userDao.getUser(userId);
    }

    @Override
    public int updateUser(User user) throws Exception {
        System.out.println(":: "+getClass().getName()+".updateUser() 호출");
        return userDao.updateUser(user);
    }

    @Override
    public int removeUser(String userId) throws Exception {
        System.out.println(":: "+getClass().getName()+".removeUser() 호출");
        return userDao.removeUser(userId);
    }

    @Override
    public List<User> getUserList(Search search) throws Exception {
        System.out.println(":: "+getClass().getName()+".getUserList() 호출");
        return userDao.getUserList(search);
    }
}