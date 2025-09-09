package mybatis.service.user;

import java.util.List;
import mybatis.service.domain.User;
import mybatis.service.domain.Search;

public interface UserService {

    public int addUser(User user) throws Exception;

    public User getUser(String userId) throws Exception;

    public int updateUser(User user) throws Exception;

    public int removeUser(String userId) throws Exception;

    public List<User> getUserList(Search search) throws Exception;

}