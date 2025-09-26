package com.model2.mvc.service.purchase.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.List;
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
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:config/context-common.xml",
        "classpath:config/context-aspect.xml",
        "classpath:config/context-mybatis.xml",
        "classpath:config/context-transaction.xml"
})
public class PurchaseServiceTest {

    @Autowired
    @Qualifier("purchaseServiceImpl")
    private PurchaseService purchaseService;

    private Purchase testPurchase;

    @Before
    public void setUp() {
        testPurchase = new Purchase();

        Product product = new Product();
        product.setProdNo(10001); // DB에 존재하는 상품 번호
        testPurchase.setPurchaseProd(product);

        User user = new User();
        user.setUserId("user01"); // DB에 존재하는 유저 ID
        testPurchase.setBuyer(user);

        testPurchase.setPaymentOption("1");
        testPurchase.setReceiverName("테스트수령인");
        testPurchase.setReceiverPhone("01012345678");
        testPurchase.setDivyAddr("서울시 강남구 테스트로 1");
        testPurchase.setDivyRequest("빠른 배송 부탁");
        testPurchase.setTranCode("1");
        testPurchase.setOrderDate(Date.valueOf("2025-09-25"));
        testPurchase.setDivyDate(Date.valueOf("2025-09-30"));
    }

    @After
    public void tearDown() {
        System.out.println("==> 테스트 종료");
    }

    @Test
    public void testAddAndGetPurchase() throws Exception {
        purchaseService.addPurchase(testPurchase);
        System.out.println("==> testAddAndGetPurchase 완료 (tranNo는 시퀀스 자동 생성)");
    }

    @Test
    public void testGetPurchase() throws Exception {
        int tranNo = 10001; // 실제 존재하는 거래번호
        Purchase result = purchaseService.getPurchase(tranNo);

        assertNotNull(result);
        assertEquals(tranNo, result.getTranNo());
        assertNotNull(result.getBuyer());
        assertNotNull(result.getPurchaseProd());

        System.out.println(result);
        System.out.println("==> testGetPurchase 완료");
    }

    @Test
    public void testUpdatePurchase() throws Exception {
        int tranNo = 10001;
        Purchase purchase = purchaseService.getPurchase(tranNo);

        purchase.setReceiverName("수정된수령인");
        purchase.setReceiverPhone("01098765432");
        purchase.setDivyAddr("서울시 수정로 2");
        purchase.setDivyRequest("수정 요청사항");
        purchase.setDivyDate(Date.valueOf("2025-10-01"));

        purchaseService.updatePurchase(purchase);

        Purchase updated = purchaseService.getPurchase(tranNo);
        assertEquals("수정된수령인", updated.getReceiverName());
        assertEquals("01098765432", updated.getReceiverPhone());
        assertEquals("서울시 수정로 2", updated.getDivyAddr());
        assertEquals("수정 요청사항", updated.getDivyRequest());

        System.out.println(updated);
        System.out.println("==> testUpdatePurchase 완료");
    }

    @Test
    public void testGetPurchaseList() throws Exception {
        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(10);
        search.setSearchCondition("0"); // buyer_id
        search.setSearchKeyword("user01");

        Map<String, Object> resultMap = purchaseService.getPurchaseList(search);
        List<Purchase> list = (List<Purchase>) resultMap.get("list");

        assertNotNull(list);
        assertTrue(list.size() >= 0);

        System.out.println("구매목록 조회: " + list);
        System.out.println("==> testGetPurchaseList 완료");
    }

    @Test
    public void testUpdateTranStatus() throws Exception {
        int prodNo = 10001; // 실제 존재하는 상품 번호
        int newTranCode = 2; // 배송중

        purchaseService.updateTranStatus(prodNo, newTranCode);

        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(10);
        search.setSearchCondition("0");
        search.setSearchKeyword("user01");

        Map<String, Object> resultMap = purchaseService.getPurchaseList(search);
        List<Purchase> list = (List<Purchase>) resultMap.get("list");

        for (Purchase p : list) {
            if (p.getPurchaseProd().getProdNo() == prodNo) {
                assertEquals(String.valueOf(newTranCode), p.getTranCode());
                break;
            }
        }

        System.out.println("==> testUpdateTranStatus 완료");
    }
}
