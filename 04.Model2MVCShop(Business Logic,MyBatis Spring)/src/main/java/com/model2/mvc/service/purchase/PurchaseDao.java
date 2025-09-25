package com.model2.mvc.service.purchase;

import java.util.List;
import java.util.Map;

import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.common.Search;

public interface PurchaseDao {

    public void insertPurchase(Purchase purchase) throws Exception;

    public Purchase findPurchase(int tranNo) throws Exception;

    public List<Purchase> getPurchaseList(Search search) throws Exception;

    public int getTotalCount(Search search) throws Exception;

    public void updatePurchase(Purchase purchase) throws Exception;

    public void updatePurchaseDelivery(int prodNo, int tranCode) throws Exception;
}
