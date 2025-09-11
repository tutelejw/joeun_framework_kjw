package com.model2.mvc.service.product;

import java.util.Map;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductDao {

    public void addProduct(Product product) throws Exception;

    public Product findProduct(String prodNo) throws Exception;

    public int getTotalCount(Search search) throws Exception;

    public Map<String, Object> getProductList(Search search) throws Exception;

    public void updateProduct(Product product) throws Exception;
}
