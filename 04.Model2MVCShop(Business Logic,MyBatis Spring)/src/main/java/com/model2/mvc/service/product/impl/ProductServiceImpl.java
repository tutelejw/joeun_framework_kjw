package com.model2.mvc.service.product.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    public ProductServiceImpl() {
        System.out.println("==> " + getClass() + " 생성자 호출");
    }

    public void setProductDao(ProductDao productDao) {
        System.out.println("==> setProductDao 호출됨");
        this.productDao = productDao;
    }

    @Override
    public void addProduct(Product product) throws Exception {
        productDao.addProduct(product);
        System.out.println("==> addProduct 완료");
    }

    @Override
    public Product getProduct(int prodNo) throws Exception {
        Product product = productDao.findProduct(String.valueOf(prodNo));
        System.out.println("==> getProduct 결과 : " + product);
        return product;
    }

    @Override
    public Map<String, Object> getProductList(Search search) throws Exception {
        Map<String, Object> map = productDao.getProductList(search);
        System.out.println("==> getProductList 결과 Map : " + map);
        return map;
    }

    @Override
    public void updateProduct(Product product) throws Exception {
        productDao.updateProduct(product);
        System.out.println("==> updateProduct 완료");
    }

    @Override
    public int getTotalCount(Search search) throws Exception {
        int count = productDao.getTotalCount(search);
        System.out.println("==> getTotalCount in Service : " + count);
        return count;
    }
}
