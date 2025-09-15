package spring.web.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

	
	// default constructor
	public TestController () {
		System.out.println(":: TestController default Constructor call");
	}
	
	@RequestMapping("/testView01.do")
	public ModelAndView testView01() {
		System.out.println("[testView01() start.........]"); // <<== 디버깅용
		//viewName 값만 갖는 ModelAndView 객체 Return
		return new ModelAndView("/test/testView.jsp");
	}
	
	@RequestMapping("/testView02.do")
	public String testView02() {
		System.out.println("[testView02() start.........]"); // <<== 디버깅용
		//viewName 값만 갖는 ModelAndView 객체 Return
		return "/test/testView.jsp";
	}
	
	@RequestMapping("/testView03.do")
	public String testView03(@RequestParam("abc") int no, 
			@RequestParam("def") String name) {
		System.out.println("[testView03() start.........]"); // <<== 디버깅용
		System.out.println("no : " +no+ "=== name : " + name);
		//viewName 값만 갖는 ModelAndView 객체 Return
		return "/test/testView.jsp";
	}
	
	@RequestMapping("/testView04.do")
	public String testView04(@RequestParam("abc") int no, 
			@RequestParam(value="def", required=false) String name) {
		System.out.println("[testView04() start.........]"); // <<== 디버깅용
		System.out.println("no : " +no+ "=== name : " + name);
		//viewName 값만 갖는 ModelAndView 객체 Return
		return "/test/testView.jsp";
	}
	
	@RequestMapping("/testView05.do")
	public String testView05(@RequestParam(value="abc",defaultValue="1") int no, 
			@RequestParam(value="def", required=false) String name) {
		System.out.println("[testView05() start.........]"); // <<== 디버깅용
		System.out.println("no : " +no+ "=== name : " + name);
		//viewName 값만 갖는 ModelAndView 객체 Return
		return "/test/testView.jsp";
	}
	
}// end of class
