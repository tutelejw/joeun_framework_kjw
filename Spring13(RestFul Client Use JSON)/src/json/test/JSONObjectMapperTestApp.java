package json.test;

import java.util.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import spring.domain.User;

public class JSONObjectMapperTestApp {

    public static void main(String[] args) throws Exception {

        // Jackson ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // -----------------------------------------------
        // 1. User 객체 → JSON 문자열
        // -----------------------------------------------
        User user = new User("user01", "홍길동", "1111", null, 10);
        String jsonOneValue = objectMapper.writeValueAsString(user);
        System.out.println("1. User → JSON : " + jsonOneValue);

        // -----------------------------------------------
        // 2. JSON 문자열 → User 객체
        // -----------------------------------------------
        User userFromJson = objectMapper.readValue(jsonOneValue, User.class);
        System.out.println("2. JSON → User : " + userFromJson);

        // -----------------------------------------------
        // 3. List<User> → JSON 문자열
        // -----------------------------------------------
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        userList.add(new User("user02", "이순신", "2222", 30, 20));
        String listJson = objectMapper.writeValueAsString(userList);
        System.out.println("3. List<User> → JSON : " + listJson);

        // -----------------------------------------------
        // 4. JSON 문자열 → List<User>
        // -----------------------------------------------
        List<User> listFromJson = objectMapper.readValue(listJson, new TypeReference<List<User>>() {});
        System.out.println("4. JSON → List<User> : " + listFromJson);

        // -----------------------------------------------
        // 5. Map<String, User> → JSON 문자열
        // -----------------------------------------------
        Map<String, User> userMap = new LinkedHashMap<String, User>();
        userMap.put("first", user);
        userMap.put("second", new User("user03", "강감찬", "3333", 40, 30));
        String mapJson = objectMapper.writeValueAsString(userMap);
        System.out.println("5. Map<String, User> → JSON : " + mapJson);

        // -----------------------------------------------
        // 6. JSON 문자열 → Map<String, User>
        // -----------------------------------------------
        Map<String, User> mapFromJson = objectMapper.readValue(mapJson, new TypeReference<Map<String, User>>() {});
        System.out.println("6. JSON → Map<String, User> : " + mapFromJson);
    }
}

