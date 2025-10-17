package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RestController  // 💡 JSON 반환을 위한 REST Controller 선언
@RequestMapping("/product/*")  // 💡 URL prefix 설정
public class ProductRestController {

    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;

    public ProductRestController() {
        System.out.println("==> ProductRestController 생성됨");
    }

    /**
     * ✅ 1. 상품 등록
     * @param product : 등록할 상품 정보
     * @throws Exception
     * @return 등록된 상품 정보(JSON)
     * 📌 예제 URL: POST /product/json/addProduct
     * 📌 Content-Type: application/json
     * {
     *     "prodName": "iPhone 15",
     *     "prodDetail": "Apple 신형 스마트폰",
     *     "manuDate": "2025-09-01",
     *     "price": 1500000,
     *     "fileName": "iphone15.jpg"
     * }
     */
    @PostMapping("json/addProduct")
    public Product addProduct(@RequestBody Product product) throws Exception {
        System.out.println("▶ REST:: addProduct() 호출됨");
        productService.addProduct(product);
        return product;
    }

    /**
     * ✅ 2. 상품 상세 조회
     * @param prodNo : 상품 번호 (경로변수)
     * @return 해당 상품 정보(JSON)
     * 📌 예제 URL: GET /product/json/getProduct/10001
     */
    @GetMapping("json/getProduct/{prodNo}")
    public Product getProduct(@PathVariable int prodNo) throws Exception {
        System.out.println("▶ REST:: getProduct() 호출됨 - prodNo: " + prodNo);
        return productService.getProduct(prodNo);
    }

    /**
     * ✅ 3. 상품 리스트 조회
     * @param search : 검색 조건 및 페이지 정보
     * @return 상품 리스트 + 전체 개수(JSON)
     * 📌 예제 URL: GET /product/json/getProductList?currentPage=1&pageSize=10
     */
    @GetMapping("json/getProductList")
    public Map<String, Object> getProductList(@ModelAttribute Search search) throws Exception {
        System.out.println("▶ REST:: getProductList() 호출됨 - page: " + search.getCurrentPage());
        
        if (search.getCurrentPage() == 0) {
            search.setCurrentPage(1);
        }
        if (search.getPageSize() == 0) {
            search.setPageSize(10);  // 기본 페이지 사이즈
        }

        return productService.getProductList(search);
    }

    /**
     * ✅ 4. 상품 정보 수정
     * @param product : 수정할 상품 정보 (JSON)
     * @return 수정 완료된 상품 정보(JSON)
     * 📌 예제 URL: PUT /product/json/updateProduct
     * 📌 Content-Type: application/json
     * {
     *     "prodNo": 10001,
     *     "prodName": "iPhone 15 Pro",
     *     ...
     * }
     */
    @PutMapping("json/updateProduct")
    public Product updateProduct(@RequestBody Product product) throws Exception {
        System.out.println("▶ REST:: updateProduct() 호출됨 - prodNo: " + product.getProdNo());

        productService.updateProduct(product);
        return product;
    }
    
    /**
     * ✅ 5. 상품 리스트 조회 무한스크롤
     * @param search : 검색 조건 및 페이지 정보
     * @return 상품 리스트 + 전체 개수(JSON)
     * 📌 예제 URL: GET /product/json/getProductList?currentPage=1&pageSize=10
     */
    @GetMapping("json/getProductListScroll")
    public Map<String, Object> getProductListScroll(@ModelAttribute Search search) throws Exception {
        System.out.println("▶ REST:: getProductListScroll() 호출됨 - page: " + search.getCurrentPage());
        
        if (search.getCurrentPage() == 0) {
            search.setCurrentPage(1);
        }
        if (search.getPageSize() == 0) {
            search.setPageSize(10);  // 기본 페이지 사이즈
        }

        return productService.getProductList(search);
    }
	// [추가] Autocomplete 요청을 처리하고 JSON 데이터를 반환하는 메서드
    /**
     * 상품명 자동완성 목록을 JSON으로 반환합니다.
     * @param term jQuery UI Autocomplete가 보내는 검색어 파라미터
     * @return 상품명 List
     * @throws Exception
     */
 // @RequestMapping(value="getProductNameList", method=RequestMethod.GET) 대신 @GetMapping 사용 가능
    @GetMapping("json/getProductNameList") 
    public List<String> getProductNameList(@RequestParam("term") String term) throws Exception {
        
        System.out.println("/product/json/getProductNameList : GET");
        System.out.println("자동완성 검색어: " + term);
        
        // @RestController 클래스에 있으므로 @ResponseBody 어노테이션이 없어도
        // List<String>이 자동으로 JSON 배열로 변환되어 반환됩니다.
        return productService.getProductNameList(term);
    }
    
}
