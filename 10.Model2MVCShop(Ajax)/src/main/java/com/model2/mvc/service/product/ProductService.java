package com.model2.mvc.service.product;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;


//==> 회원관리에서 서비스할 내용 추상화/캡슐화한 Service  Interface Definition  
public interface ProductService {
	
	// 상품추가
	public void addProduct(Product product) throws Exception;
	
	// 상품정보확인
	public Product getProduct(int prodNo) throws Exception;
	
	// 상품정보리스트 
	public Map<String , Object> getProductList(Search search) throws Exception;
	
	// 상품정보수정
	public void updateProduct(Product product) throws Exception;
	
	//게시판 Page 처리를 위한 전체Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception ;
	
	// [추가] 자동완성용 상품명 목록 조회
    public List<String> getProductNameList(String keyword) throws Exception;
}