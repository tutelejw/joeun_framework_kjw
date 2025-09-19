package json.test;

import java.util.ArrayList;
import java.util.Arrays;

import org.codehaus.jackson.map.ObjectMapper;

import spring.domain.Search;
import spring.domain.UserHasASearch;

//public class JSONObjectMapperHasARelationTestApp {
public class UserHasASearchObjectMapperTestApp{

    public static void main(String[] args) throws Exception {

        // Jackson의 ObjectMapper 생성
        ObjectMapper mapper = new ObjectMapper();

        // ================================================
        // 1. UserHasASearch + Search 객체 생성
        // ================================================
        UserHasASearch user = new UserHasASearch("user01", "홍길동", "1111", null, 10);

        // Search 객체 구성
        Search search = new Search();
        search.setUserName(new String[] {"홍길동", "이순신"});
        ArrayList<String> userIds = new ArrayList<String>(Arrays.asList("user01", "user02"));
        search.setUserId(userIds);
        search.setSearchCondition("name");

        // Has-A 관계 설정
        user.setSearch(search);

        // ================================================
        // 2. 객체 → JSON 문자열 변환
        // ================================================
        String json = mapper.writeValueAsString(user);
        System.out.println("1) UserHasASearch → JSON:\n" + json);

        // ================================================
        // 3. JSON 문자열 → 객체로 역직렬화
        // ================================================
        UserHasASearch deserializedUser = mapper.readValue(json, UserHasASearch.class);
        System.out.println("\n2) JSON → UserHasASearch 객체:\n" + deserializedUser);

        // ================================================
        // 4. 역직렬화된 객체의 값 접근
        // ================================================
        System.out.println("\n3) 역직렬화 후 개별 데이터 확인:");
        System.out.println("userId: " + deserializedUser.getUserId());
        System.out.println("userName: " + deserializedUser.getUserName());
        System.out.println("searchCondition: " + deserializedUser.getSearch().getSearchCondition());
        System.out.println("search.userId list: " + deserializedUser.getSearch().getUserId());
        System.out.println("search.userName array: " + Arrays.toString(deserializedUser.getSearch().getUserName()));
    }
}

