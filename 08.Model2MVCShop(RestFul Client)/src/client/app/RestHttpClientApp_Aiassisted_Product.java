package client.app;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.common.Search;

/**
 * REST API 테스트용 Java 클라이언트
 * - HttpURLConnection + Jackson(codehaus) 기반
 * - ProductRestController의 메서드 테스트용
 * 
 * 사용 라이브러리:
 * - jackson-core-asl-1.9.13.jar
 * - jackson-mapper-asl-1.9.13.jar
 */
public class RestHttpClientApp_Aiassisted_Product {

    private static final String BASE_URL = "http://localhost:8080/product/json";
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 📌 상품 등록 테스트
     * POST /product/json/addProduct
     * Content-Type: application/json
     */
    public static void addProduct_test() throws Exception {
        String apiUrl = BASE_URL + "/addProduct";

        Product product = new Product();
        product.setProdName("Galaxy S25");
        product.setProdDetail("Samsung 최신 스마트폰");
        product.setManuDate("2025-09-01");
        product.setPrice(1300000);
        product.setFileName("galaxy25.jpg");

        String jsonInput = mapper.writeValueAsString(product);
        String response = sendHttpRequest(apiUrl, "POST", jsonInput);

        Product result = mapper.readValue(response, Product.class);
        System.out.println("▶ 상품 등록 결과: " + result);
    }

    /**
     * 📌 상품 상세 조회 테스트
     * GET /product/json/getProduct/{prodNo}
     */
    public static void getProduct_test(int prodNo) throws Exception {
        String apiUrl = BASE_URL + "/getProduct/" + prodNo;

        String response = sendHttpRequest(apiUrl, "GET", null);

        Product result = mapper.readValue(response, Product.class);
        System.out.println("▶ 상품 상세 조회 결과: " + result);
    }

    /**
     * 📌 상품 리스트 조회 테스트
     * POST /product/json/getProductList (Search 객체를 JSON으로 전송)
     */
    public static void getProductList_test() throws Exception {
        String apiUrl = BASE_URL + "/getProductList";

        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(5);
        // 검색 조건 설정 가능
        // search.setSearchCondition("prodName");
        // search.setSearchKeyword("Galaxy");

        String jsonInput = mapper.writeValueAsString(search);
        String response = sendHttpRequest(apiUrl, "POST", jsonInput);

        Map<String, Object> resultMap = mapper.readValue(response, new TypeReference<Map<String, Object>>() {});
        System.out.println("▶ 상품 리스트 조회 결과:");
        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            System.out.println(" - " + entry.getKey() + " : " + entry.getValue());
        }
    }

    /**
     * 📌 상품 수정 테스트
     * PUT /product/json/updateProduct
     */
    public static void updateProduct_test() throws Exception {
        String apiUrl = BASE_URL + "/updateProduct";

        Product product = new Product();
        product.setProdNo(10001); // 기존에 등록된 상품 번호 사용
        product.setProdName("Galaxy S25 Ultra");
        product.setProdDetail("Samsung 최고 사양 모델");
        product.setManuDate("2025-09-01");
        product.setPrice(1600000);
        product.setFileName("galaxy25_ultra.jpg");

        String jsonInput = mapper.writeValueAsString(product);
        String response = sendHttpRequest(apiUrl, "PUT", jsonInput);

        Product updatedProduct = mapper.readValue(response, Product.class);
        System.out.println("▶ 상품 수정 결과: " + updatedProduct);
    }

    /**
     * ✅ 공통 HTTP 요청 메서드 (GET, POST, PUT)
     * 
     * @param apiUrl  요청 URL
     * @param method  HTTP 메서드
     * @param jsonInput 요청 JSON 문자열 (GET일 경우 null)
     * @return 응답 JSON 문자열
     */
    private static String sendHttpRequest(String apiUrl, String method, String jsonInput) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("Accept", "application/json");

        if ("POST".equals(method) || "PUT".equals(method)) {
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(jsonInput.getBytes("UTF-8"));
            os.flush();
            os.close();
        }

        int responseCode = conn.getResponseCode();
        InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            sb.append(line);
        br.close();

        if (responseCode >= 200 && responseCode < 300) {
            return sb.toString();
        } else {
            throw new RuntimeException("HTTP 요청 실패 - 코드: " + responseCode + ", 메시지: " + sb);
        }
    }

    /**
     * ▶ 테스트 메인 메서드
     */
    public static void main(String[] args) throws Exception {
        System.out.println("==== REST API 테스트 시작 ====");

        // 테스트 시 아래 메서드 중 선택 실행

        // 1. 상품 등록
       // addProduct_test();

        // 2. 상품 상세 조회 (예: prodNo=10001)
//        getProduct_test(10001);

        // 3. 상품 리스트 조회
        getProductList_test();

        // 4. 상품 정보 수정
//        updateProduct_test();

        System.out.println("==== 테스트 종료 ====");
    }
}
