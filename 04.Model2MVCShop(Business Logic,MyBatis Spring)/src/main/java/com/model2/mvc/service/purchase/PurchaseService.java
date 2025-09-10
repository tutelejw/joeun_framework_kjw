package com.model2.mvc.service.purchase;

import java.util.Map;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseService {

    // 구매 등록
    public void addPurchase(Purchase purchase) throws Exception;

    // 거래 번호(tranNo)로 구매 조회
    public Purchase getPurchase(int tranNo) throws Exception;

    // 상품 번호(prodNo)로 구매 조회
    public Purchase getPurchaseByProdNo(int prodNo) throws Exception;

    // 구매 정보 수정
    public void updatePurchase(Purchase purchase) throws Exception;

    // 배송 상태만 수정
    public void updateTranStatusCode(Purchase purchase) throws Exception;

    // 구매 목록 조회
    public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception;
}
