package com.model2.mvc.web.purchase;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@Controller
public class PurchaseController {

    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;

    @Autowired
    @Qualifier("purchaseServiceImpl")
    private PurchaseService purchaseService;
    
    // ✅ 공통 프로퍼티 (list에서 사용)
    @Value("#{commonProperties['pageUnit']}")
    private int pageUnit;

    @Value("#{commonProperties['pageSize']}")
    private int pageSize;
    
    // ✅ [1] 구매 등록 화면 진입
    @RequestMapping("/addPurchaseView.do")
    public String addPurchaseView(@RequestParam("prodNo") int prodNo, HttpSession session, Model model) throws Exception {

        System.out.println("==== [디버깅] /addPurchaseView.do 요청 ====");
        System.out.println("▶ 상품 번호: " + prodNo);

        // 1. 상품 정보 조회
        Product product = productService.getProduct(prodNo);
        if (product == null) {
            throw new IllegalStateException("상품 정보를 찾을 수 없습니다. prodNo=" + prodNo);
        }

        // 2. 로그인된 사용자 조회
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new IllegalStateException("로그인된 사용자 정보가 없습니다.");
        }

        // 3. JSP에서 사용할 데이터 설정
        model.addAttribute("vo", product);  // 상품 정보
        model.addAttribute("user", user);   // 로그인 유저

        return "forward:/purchase/addPurchaseView.jsp";
    }

    // ✅ [2] 구매 등록 처리
    @RequestMapping("/addPurchase.do")
    public String addPurchase(
            @ModelAttribute("purchase") Purchase purchase,
            @RequestParam("prodNo") int prodNo,
            HttpSession session,
            Model model) throws Exception {

        System.out.println("==== [디버깅] /addPurchase.do 요청 ====");
        System.out.println("▶ 상품번호 : " + prodNo);

        // 1. 로그인된 사용자 확인
        User buyer = (User) session.getAttribute("user");
        if (buyer == null) {
            throw new IllegalStateException("로그인된 사용자 정보가 없습니다.");
        }

        // 2. 상품 객체 설정
        Product product = new Product();
        product.setProdNo(prodNo);

        // 3. purchase 객체 보완
        purchase.setBuyer(buyer);
        purchase.setPurchaseProd(product);
        purchase.setTranCode("1"); // 기본 상태
        purchase.setOrderDate(new Date(System.currentTimeMillis()));

        // 4. 저장
        purchaseService.addPurchase(purchase);

        // 5. 다음 페이지 이동
        return "redirect:/listProduct.do";
    }
    
    // ✅ [3] 구매 목록 조회
    @RequestMapping("/listPurchase.do")
    public String listPurchase(
            @ModelAttribute("search") Search search,
            HttpSession session,
            Model model) throws Exception {

        System.out.println("==== [디버깅] /listPurchase.do 요청 ====");

        // 로그인 유저 확인 (필요한 경우 필터로 이전)
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new IllegalStateException("로그인된 사용자 정보가 없습니다.");
        }

        // 페이지 기본값 설정
        if (search.getCurrentPage() == 0) {
            search.setCurrentPage(1);
        }
        search.setPageSize(pageSize);

        // 검색 조건에 따라 구매 내역 조회
        Map<String, Object> map = purchaseService.getPurchaseList(search);

        int totalCount = (map.get("totalCount") != null) ? (Integer) map.get("totalCount") : 0;
        Page resultPage = new Page(search.getCurrentPage(), totalCount, pageUnit, pageSize);

        model.addAttribute("list", map.get("list"));
        model.addAttribute("resultPage", resultPage);
        model.addAttribute("search", search);

        return "forward:/purchase/listPurchase.jsp";
    }
    
 // ✅ [4] 구매 상세 조회
    @RequestMapping("/getPurchase.do")
    public String getPurchase(@RequestParam("tranNo") int tranNo, Model model) throws Exception {
        System.out.println("==== [디버깅] /getPurchase.do 요청 ====");
        System.out.println("▶ 거래번호(tranNo): " + tranNo);

        // 1. 서비스 호출
        Purchase purchase = purchaseService.getPurchase(tranNo);

        if (purchase == null) {
            throw new IllegalStateException("해당 거래를 찾을 수 없습니다. tranNo=" + tranNo);
        }

        // 2. JSP에 전달
        model.addAttribute("vo", purchase);

        return "forward:/purchase/getPurchase.jsp";
    }
    // ✅ [5] 구매 수정 화면 진입
    @RequestMapping("/updatePurchaseView.do")
    public String updatePurchaseView(@RequestParam("tranNo") int tranNo, Model model) throws Exception {
        System.out.println("==== [디버깅] /updatePurchaseView.do 요청 ====");
        System.out.println("▶ tranNo: " + tranNo);

        Purchase purchase = purchaseService.getPurchase(tranNo);
        if (purchase == null) {
            throw new IllegalStateException("해당 거래 정보를 찾을 수 없습니다. tranNo = " + tranNo);
        }

        model.addAttribute("vo", purchase);
        return "forward:/purchase/updatePurchase.jsp";
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        binder.registerCustomEditor(java.sql.Date.class, new CustomDateEditor(sdf, true));
    }
    
    // ✅ [6] 구매 수정 처리
    @RequestMapping("/updatePurchase.do")
    public String updatePurchase(
            @ModelAttribute("purchase") Purchase purchase,
            @RequestParam("tranNo") int tranNo,
            HttpSession session) throws Exception {

        System.out.println("==== [디버깅] /updatePurchase.do 요청 ====");
        System.out.println("▶ tranNo: " + tranNo);
        System.out.println("▶ 입력된 배송자 이름: " + purchase.getReceiverName());
        System.out.println("▶ 입력된 배송 날짜: " + purchase.getDivyDate());

        // tranNo 명시적으로 설정 (혹시 @ModelAttribute에 없을 경우 대비)
        purchase.setTranNo(tranNo);

        // 1. DB 업데이트
        purchaseService.updatePurchase(purchase);

        // 2. 세션 정보 갱신 (필요시)
        Purchase sessionPurchase = (Purchase) session.getAttribute("vo");
        if (sessionPurchase != null && sessionPurchase.getTranNo() == tranNo) {
            session.setAttribute("vo", purchase);
            System.out.println("✅ 세션 내 Purchase 정보 갱신 완료");
        } else {
            System.out.println("ℹ️ 세션에 해당 거래 정보가 없거나 불일치함. 갱신 생략.");
        }

        return "redirect:/getPurchase.do?tranNo=" + tranNo;
    }



    
    // ✅ [7] 구매 상태코드 변경 처리
    @RequestMapping("/updatePurchaseDelivery.do")
    public String updatePurchaseDelivery(
            @RequestParam("prodNo") int prodNo,
            @RequestParam("tranCode") int tranCode,
            Model model) throws Exception {

        System.out.println("==== [디버깅] /updatePurchaseDelivery.do 요청 ====");
        System.out.println("▶ prodNo: " + prodNo + ", tranCode: " + tranCode);

        try {
            // 서비스 호출
            purchaseService.updatePurchaseDelivery(prodNo, tranCode);

            // 메뉴 값에 따라 리다이렉트 위치 조절 가능
            return "redirect:/listProduct.do?menu=manage";

            // 또는 아래로 변경 가능
            // return "redirect:/listPurchase.do?menu=manage";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", "배송 상태 변경 중 오류 발생");
            return "forward:/error.jsp";
        }
    }


}
