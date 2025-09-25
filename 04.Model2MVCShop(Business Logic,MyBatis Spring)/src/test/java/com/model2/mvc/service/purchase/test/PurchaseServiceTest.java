package com.model2.mvc.service.purchase.test;

//import java.util.Date;
import java.util.List;
import java.util.Map;
import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/commonservice.xml"})
public class PurchaseServiceTest {

    @Autowired
    private PurchaseService purchaseService;

    // 1. addPurchase 테스트
    @Test
    public void testAddAndGetPurchase() throws Exception {
        Purchase purchase = new Purchase();

        Product product = new Product();
        product.setProdNo(10001); // DB에 실제 존재하는 상품 번호로 설정해야 함
        purchase.setPurchaseProd(product);

        User user = new User();
        user.setUserId("user01"); // DB에 존재하는 사용자 ID로 설정해야 함
        purchase.setBuyer(user);

        purchase.setPaymentOption("1");
        purchase.setReceiverName("테스트수령인");
        purchase.setReceiverPhone("01012345678");
        purchase.setDivyAddr("서울시 강남구 테스트로 1");
        purchase.setDivyRequest("빠른 배송 부탁");
        purchase.setTranCode("1");
//        purchase.setOrderDate(new Date());
//        purchase.setDivyDate(new Date());
        purchase.setOrderDate(Date.valueOf("2025-09-25"));
        purchase.setDivyDate(Date.valueOf("2025-09-30"));

        purchaseService.addPurchase(purchase);
        // 콘솔 출력으로만 확인 가능 - tranNo는 시퀀스로 자동 생성됨

        // 실제 tranNo를 알 수 없으므로 getPurchase 테스트에서 따로 확인
    }

    // 2. getPurchase 테스트
    @Test
    public void testGetPurchase() throws Exception {
        int tranNo = 10001; // 실제 DB에 존재하는 거래 번호 사용
        Purchase purchase = purchaseService.getPurchase(tranNo);

        Assert.assertNotNull(purchase);
        Assert.assertEquals(tranNo, purchase.getTranNo());
        Assert.assertNotNull(purchase.getBuyer());
        Assert.assertNotNull(purchase.getPurchaseProd());

        // System.out.println(purchase);  // 선택적 콘솔 출력
    }

    // 3. updatePurchase 테스트
    @Test
    public void testUpdatePurchase() throws Exception {
        int tranNo = 10001;
        Purchase purchase = purchaseService.getPurchase(tranNo);

        purchase.setReceiverName("수정된수령인");
        purchase.setReceiverPhone("01098765432");
        purchase.setDivyAddr("서울시 수정로 2");
        purchase.setDivyRequest("수정 요청사항");
        purchase.setDivyDate(Date.valueOf("2025-09-30"));
        purchaseService.updatePurchase(purchase);

        Purchase updated = purchaseService.getPurchase(tranNo);

        Assert.assertEquals("수정된수령인", updated.getReceiverName());
        Assert.assertEquals("01098765432", updated.getReceiverPhone());
        Assert.assertEquals("서울시 수정로 2", updated.getDivyAddr());
        Assert.assertEquals("수정 요청사항", updated.getDivyRequest());
    }

    // 4. getPurchaseList 테스트
    @Test
    public void testGetPurchaseList() throws Exception {
        Search search = new Search();
        search.setCurrentPage(1);
        search.setPageSize(10);
        search.setSearchCondition("0"); // buyer_id
        search.setSearchKeyword("user01");

        Map<String, Object> resultMap = purchaseService.getPurchaseList(search);
        List<Purchase> list = (List<Purchase>) resultMap.get("list");
        int totalCount = (int) resultMap.get("totalCount");

        Assert.assertTrue(list.size() >= 0);
        Assert.assertTrue(totalCount >= list.size());

        // System.out.println("총 거래 수: " + totalCount);
        // list.forEach(System.out::println);
    }

    // 5. updateTranStatus 테스트
    @Test
    public void testUpdateTranStatus() throws Exception {
        int prodNo = 10001; // 실제 존재하는 상품 번호
        int newTranCode = 2; // 배송 상태 코드 예: 2 = 배송중

        purchaseService.updateTranStatus(prodNo, newTranCode);

        // 해당 상품의 거래를 가져와서 상태 코드가 변경되었는지 확인
        Search search = new Search();
        search.setSearchCondition("0"); // buyer_id
        search.setSearchKeyword("user01");
        search.setCurrentPage(1);
        search.setPageSize(10);

        Map<String, Object> resultMap = purchaseService.getPurchaseList(search);
        List<Purchase> list = (List<Purchase>) resultMap.get("list");

        for (Purchase p : list) {
            if (p.getPurchaseProd().getProdNo() == prodNo) {
                Assert.assertEquals(String.valueOf(newTranCode), p.getTranCode());
                break;
            }
        }
    }
}
