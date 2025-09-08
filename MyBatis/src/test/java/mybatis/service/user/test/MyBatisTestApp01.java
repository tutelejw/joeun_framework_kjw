package mybatis.service.user.test;

import java.io.InputStream;
import java.util.List;

import mybatis.service.domain.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisTestApp01 {

    public static void main(String[] args) throws Exception {

        // MyBatis 설정 파일 로드
        String resource = "sql/mybatis-config01.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        
        // SqlSession 열기
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            
            System.out.println("=== 전체 유저 목록 조회 ==1111=");
            List<User> userList = sqlSession.selectList("UserMapper01.getUserList");
            for (User user : userList) {
                System.out.println(user);
            }

            System.out.println("\n=== userId로 유저 조회 ===");
            User user = sqlSession.selectOne("UserMapper01.getUser", "user01");
            System.out.println(user);

            System.out.println("\n=== userId + password로 유저 이름 조회 ==22222222=");
            User userParam = new User();
            userParam.setUserId("user01");
            userParam.setPassword("user01");
            String userName = sqlSession.selectOne("UserMapper01.findUserId", userParam);
            System.out.println("유저 이름: " + userName);

            System.out.println("\n=== age로 유저 이름 목록 조회 =2323==");
            User ageParam = new User();
//            ageParam.setAge(25);
            ageParam.setAge(20);
            List<String> names = sqlSession.selectList("UserMapper01.getUserListAge", ageParam);
            for (String name : names) {
                System.out.println("유저 이름: " + name);
            }
            
            String targetUserId = "t04";
            
            System.out.println("\n=== 신규 유저 추가 ===");
            User newUser = new User(targetUserId, "테스트", "pass123", 30, 1);
            int inserted = sqlSession.insert("UserMapper01.addUser", newUser);
            System.out.println("추가된 레코드 수: " + inserted);

            System.out.println("\n=== 방금 추가한 유저 조회 ===");
            User addedUser = sqlSession.selectOne("UserMapper01.getUser", targetUserId);
            System.out.println(addedUser);

            System.out.println("\n=== 유저 정보 수정 ===");
            addedUser.setUserName("update유저");
            addedUser.setPassword("newpass");
            addedUser.setAge(31);
            addedUser.setGrade(2);
            int updated = sqlSession.update("UserMapper01.updateUser", addedUser);
            System.out.println("수정된 레코드 수: " + updated);
//
            System.out.println("\n=== 수정된 유저 조회 ===");
            User updatedUser = sqlSession.selectOne("UserMapper01.getUser", targetUserId);
            System.out.println(updatedUser);

            System.out.println("\n=== 유저 삭제 ===");
            int deleted = sqlSession.delete("UserMapper01.deleteUser", targetUserId);
            System.out.println("삭제된 레코드 수: " + deleted);

            System.out.println("\n=== 삭제 확인 ===");
            User deletedUser = sqlSession.selectOne("UserMapper01.getUser", targetUserId);
            System.out.println(deletedUser == null ? "유저 없음" : deletedUser);

            
        }
    }
}
