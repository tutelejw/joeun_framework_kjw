package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


//==> 회원관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")    	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")   	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	//@RequestMapping("/addProductView.do")
	//public String addProductView() throws Exception {
	@RequestMapping(value="addProduct", method=RequestMethod.GET)
	public String addProduct() throws Exception {

		System.out.println("/product/addProductView : GET");
		
		return "redirect:/product/addProductView.jsp";
	}
	
	//@RequestMapping("/addProduct.do")
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct( @ModelAttribute("product") Product product
//			,@RequestParam("fileName") MultipartFile file) throws Exception {
		) throws Exception {

	    System.out.println("=========== /product/ProductController.addProduct 호출됨 ===========");

	    // 🧪 디버깅 로그 출력: 입력된 product 정보 확인
	    System.out.println("▶ 상품번호 (prodNo): " + product.getProdNo());
	    System.out.println("▶ 상품명 (prodName): " + product.getProdName());
	    System.out.println("▶ 상품상세정보 (prodDetail): " + product.getProdDetail());
	    System.out.println("▶ 제조일자 (manuDate): " + product.getManuDate());
	    System.out.println("▶ 가격 (price): " + product.getPrice());
	    System.out.println("▶ 상품이미지 (fileName): " + product.getFileName());
		//Business Logic
		productService.addProduct(product);
		
		//return "redirect:/product/productView.jsp";
		return "forward:/product/addProductView.jsp";
	}
	
	//@RequestMapping("/getProduct.do")
	@RequestMapping(value="getProduct", method=RequestMethod.GET)
	public String getProduct( @RequestParam("prodNo") String prodNo , Model model ) throws Exception {
		
		System.out.println("/product/getProduct : GET");
		//Business Logic
		Product product = productService.getProduct(Integer.parseInt(prodNo));
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/getProduct.jsp";
	}
	
//	@RequestMapping("/updateProductView.do")
//	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{
	@RequestMapping(value="updateProduct", method=RequestMethod.GET)
	public String updateProduct( int prodNo , Model model, HttpSession session) throws Exception{

		System.out.println("/product/updateProduct");
		//Business Logic
		Product product = productService.getProduct(prodNo); 
		// Model 과 View 연결
		model.addAttribute("product", product);
		session.setAttribute("product", product);  // ✅ 세션에도 저장!

		return "forward:/product/updateProduct.jsp";
	}
	
	//@RequestMapping("/updateProduct.do")
	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct( @ModelAttribute("product") Product product , Model model , HttpSession session) throws Exception{

		System.out.println("/product/updateProduct");
		//Business Logic
		productService.updateProduct(product);
		
		//String sessionId=String.valueOf(((Product)session.getAttribute("product")).getProdNo());
		int sessionId=((Product)session.getAttribute("product")).getProdNo();
//		if(sessionId.equals(product.getProdNo())){
		if(sessionId == product.getProdNo()){
			session.setAttribute("product", product);
		}
		
		return "redirect:/product/getProduct?prodNo="+product.getProdNo();
	}
	
	
	
//	@RequestMapping("/listProduct.do")
	@RequestMapping(value="listProduct")
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/product/listProduct : GET/POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);

if (map.get("list") != null) {
    for (Product product : (List<Product>) map.get("list")) {
        System.out.println("[DEBUG] 상품번호 prodNo = " + product.getProdNo());
    }
}
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}
}