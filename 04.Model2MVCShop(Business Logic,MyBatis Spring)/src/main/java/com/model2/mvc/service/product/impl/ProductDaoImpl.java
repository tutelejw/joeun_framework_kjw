package com.model2.mvc.service.product.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductDao;

@Repository("productDao")
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private SqlSession sqlSession;

    public ProductDaoImpl() {
        System.out.println("==> " + getClass() + " 생성자 호출");
    }

    public void setSqlSession(SqlSession sqlSession) {
        System.out.println("==> setSqlSession 호출됨");
        this.sqlSession = sqlSession;
    }

    @Override
    public void addProduct(Product product) throws Exception {
        int result = sqlSession.insert("ProductMapper.insertProduct", product);
        System.out.println("==> insertProduct 실행 결과 : " + result);
    }

    @Override
    public Product findProduct(String prodNo) throws Exception {
        Product product = sqlSession.selectOne("ProductMapper.findProduct", prodNo);
        System.out.println("==> findProduct 결과 : " + product);
        return product;
    }

    @Override
    public int getTotalCount(Search search) throws Exception {
        int count = sqlSession.selectOne("ProductMapper.getTotalCount", search);
        System.out.println("==> getTotalCount 결과 : " + count);
        return count;
    }

    @Override
    public Map<String, Object> getProductList(Search search) throws Exception {
        List<Product> list = sqlSession.selectList("ProductMapper.getProductList", search);
        int totalCount = getTotalCount(search);

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("totalCount", totalCount);

        System.out.println("==> getProductList 결과 : " + list.size() + "건, totalCount=" + totalCount);
        return map;
    }

    @Override
    public void updateProduct(Product product) throws Exception {
        int result = sqlSession.update("ProductMapper.updateProduct", product);
        System.out.println("==> updateProduct 실행 결과 : " + result);
    }
}
