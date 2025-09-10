package com.model2.mvc.service.user;

import java.util.Map;

import com.model2.mvc.service.domain.Search;
import com.model2.mvc.service.domain.User;

public interface UserDao {

    public void insertUser(User user) throws Exception;

    public User findUser(String userId) throws Exception;

    public Map<String, Object> getUserList(Search search) throws Exception;

    public void updateUser(User user) throws Exception;

    public boolean checkDuplication(String userId) throws Exception;
}
