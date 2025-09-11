package com.model2.mvc.service.product.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/commonservice.xml"})
public class ProductServiceTest {

    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;

    private Product testProduct;

    @Before
    public void setUp() {
        testProduct = new Product();
        testProduct.setProdNo(10042);
        testProduct.setProdName("테스트상품");
        testProduct.setProdDetail("테스트 상세정보");
        testProduct.setManuDay("20250101");
        testProduct.setPrice(10000);
        testProduct.setImageFile("test.png");
    }

    @After
    public void tearDown() {
        System.out.println("==> 테스트 종료");
    }

    //@Test
    public void testAddAndGetProduct() throws Exception {
        productService.addProduct(testProduct);
        //int prodNo = 10042; // addProduct 후 prodNo가 세팅되어 있어야 함
        int prodNo = testProduct.getProdNo(); // addProduct 후 prodNo가 세팅되어 있어야 함

        Product result = productService.getProduct(prodNo);

        assertNotNull(result);
        assertEquals("테스트상품", result.getProdName());
        System.out.println(result);
        System.out.println("==> testAddAndGetProduct 완료");
    }

    @Test
    public void testUpdateProduct() throws Exception {
        productService.addProduct(testProduct);
        int prodNo = testProduct.getProdNo();
        System.out.println("testUpdateProduct : prodNo : " + prodNo);
        Product updated = productService.getProduct(prodNo);
        updated.setPrice(20000);
        updated.setProdDetail("수정된 상세정보");

        productService.updateProduct(updated);

        Product result = productService.getProduct(prodNo);
        assertEquals(20000, result.getPrice());
        assertEquals("수정된 상세정보", result.getProdDetail());
        System.out.println(result);
        System.out.println("==> testUpdateProduct 완료");
    }

    @Test
    public void testGetProductList() throws Exception {
        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(10);
        search.setSearchCondition("1");
        search.setSearchKeyword("상품"); // 상품명 일부

        Map<String, Object> map = productService.getProductList(search);
        assertNotNull(map.get("list"));
        assertTrue(((java.util.List<?>) map.get("list")).size() >= 0);
        System.out.println("map get : " + map.get("list"));
        System.out.println("==> testGetProductList 결과 : " + map.get("list"));
    }

    //@Test
    public void testGetTotalCount() throws Exception {
        Search search = new Search();
        search.setSearchCondition("1");
        search.setSearchKeyword("상품");

        int count = productService.getTotalCount(search);
        assertTrue(count >= 0);
        System.out.println("==> testGetTotalCount 결과 : " + count);
    }
}
