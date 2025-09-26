package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseDao purchaseDao;

    public PurchaseServiceImpl() {
        System.out.println("==> " + getClass() + " 생성자 호출");
    }

    public void setPurchaseDao(PurchaseDao purchaseDao) {
        System.out.println("==> setPurchaseDao() 호출");
        this.purchaseDao = purchaseDao;
    }

    @Override
    public void addPurchase(Purchase purchase) throws Exception {
        purchaseDao.insertPurchase(purchase);
        System.out.println("==> addPurchase() 완료");
    }

    @Override
    public Purchase getPurchase(int tranNo) throws Exception {
        Purchase purchase = purchaseDao.findPurchase(tranNo);
        System.out.println("==> getPurchase() 실행 결과: " + purchase);
        return purchase;
    }

    @Override
    public Map<String, Object> getPurchaseList(Search search) throws Exception {
        List<Purchase> list = purchaseDao.getPurchaseList(search);
        int totalCount = purchaseDao.getTotalCount(search);

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("totalCount", totalCount);

        System.out.println("==> getPurchaseList() 실행 결과: " + list.size() + " row(s), totalCount=" + totalCount);
        return map;
    }

    @Override
    public void updatePurchase(Purchase purchase) throws Exception {
        purchaseDao.updatePurchase(purchase);
        System.out.println("==> updatePurchase() 완료");
    }

    @Override
    public void updatePurchaseDelivery(int prodNo, int tranCode) throws Exception {
        purchaseDao.updatePurchaseDelivery(prodNo, tranCode);
        System.out.println("==> updateTranStatus() 완료");
    }
}
