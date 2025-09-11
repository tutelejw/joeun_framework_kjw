package com.model2.mvc.service.product.test;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


/*
 *	FileName :  ProductServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest2 {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdName("testProductName");
		product.setProdDetail("detail");
		product.setManuDay("20250910");
		product.setPrice(99999);
		product.setImageFile("1111112222222");
		product.setRegDate(Date.valueOf("2025-09-10"));
		
		productService.addProduct(product);
		
		product = productService.getProduct(10065);

		//==> console 확인
		System.out.println(product);
		
		//==> API 확인
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("detail", product.getProdDetail());
		Assert.assertEquals("20250910", product.getManuDay());
		Assert.assertEquals(99999, product.getPrice());

	}
	
	@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
	
		product = productService.getProduct(10065);

		//==> console 확인
		//System.out.println(product);
		
		//==> API 확인
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("detail", product.getProdDetail());
		Assert.assertEquals("20250910", product.getManuDay());
		Assert.assertEquals(99999, product.getPrice());

		Assert.assertNotNull(productService.getProduct(10000));
		Assert.assertNotNull(productService.getProduct(10001));
	}
	
	@Test
	 public void testUpdateProduct() throws Exception{
		 
		Product product = productService.getProduct(10065);
		Assert.assertNotNull(product);
		
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("detail", product.getProdDetail());
		Assert.assertEquals("20250910", product.getManuDay());
		Assert.assertEquals(99999, product.getPrice());

		product.setProdName("change");
		product.setProdDetail("777-7777-7777");
		product.setManuDay("20001212");
		product.setPrice(12345);
		
		productService.updateProduct(product);
		
		product = productService.getProduct(10065);
		Assert.assertNotNull(product);
		
		//==> console 확인
		//System.out.println(product);
			
		//==> API 확인
		Assert.assertEquals("change", product.getProdName());
		Assert.assertEquals("777-7777-7777", product.getProdDetail());
		Assert.assertEquals("20001212", product.getManuDay());
		Assert.assertEquals(12345, product.getPrice());
	 }
	 

	
	 //==>  주석을 풀고 실행하면....
	 @Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 

}