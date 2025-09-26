package com.model2.mvc.service.purchase.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDao")
public class PurchaseDaoImpl implements PurchaseDao {

    @Autowired
    private SqlSession sqlSession;

    public PurchaseDaoImpl() {
        System.out.println("==> " + getClass() + " 생성자 호출");
    }

    public void setSqlSession(SqlSession sqlSession) {
        System.out.println("==> setSqlSession() 호출");
        this.sqlSession = sqlSession;
    }

    @Override
    public void insertPurchase(Purchase purchase) throws Exception {
        int result = sqlSession.insert("PurchaseMapper.insertPurchase", purchase);
        System.out.println("==> insertPurchase() 실행 결과: " + result + " row(s) inserted");
    }

    @Override
    public Purchase findPurchase(int tranNo) throws Exception {
        Purchase purchase = sqlSession.selectOne("PurchaseMapper.findPurchase", tranNo);
        System.out.println("==> findPurchase() 실행 결과: " + purchase);
        return purchase;
    }

    @Override
    public List<Purchase> getPurchaseList(Search search) throws Exception {
        List<Purchase> list = sqlSession.selectList("PurchaseMapper.getPurchaseList", search);
        System.out.println("==> getPurchaseList() 실행 결과: " + list.size() + " row(s) found");
        return list;
    }

    @Override
    public int getTotalCount(Search search) throws Exception {
        int count = sqlSession.selectOne("PurchaseMapper.getTotalCount", search);
        System.out.println("==> getTotalCount() 실행 결과: " + count);
        return count;
    }

    @Override
    public void updatePurchase(Purchase purchase) throws Exception {
        int result = sqlSession.update("PurchaseMapper.updatePurchase", purchase);
        System.out.println("==> updatePurchase() 실행 결과: " + result + " row(s) updated");
    }

    @Override
    public void updatePurchaseDelivery(int prodNo, int tranCode) throws Exception {
        // 파라미터를 하나의 Map으로 묶어서 전달
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        params.put("prodNo", prodNo);
        params.put("tranCode", tranCode);

        int result = sqlSession.update("PurchaseMapper.updatePurchaseDelivery", params);
        System.out.println("==> updatePurchaseDelivery() 실행 결과: " + result + " row(s) updated");
    }
}
