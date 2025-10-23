package spring.web.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.domain.User;

@RestController
@RequestMapping("/userAPI/*")
public class UserRestController {
	///Field
	///Constructor
	public UserRestController(){
		System.out.println(":: UserRestController default Contructor call");
	}
	
	
	//1.1 Client 로 Domain Object 만 전송할 경우.
	//요청 미디어타입 : application/json ==> 즉 데이터교환 json 
	//http://127.0.0.1:8080/Spring20/app/userAPI/getUser?name=user02&age=10
	@RequestMapping(value="getUser" , method=RequestMethod.GET )
	public User getUser	(	@RequestParam("name") String name,
												@RequestParam("age") int age) throws Exception{
	
		System.out.println();
		System.out.println(name);
		System.out.println(age);
		
		User returnUser = new User();
		returnUser.setUserId("AAA");
		returnUser.setUserName("GET:이순신");
		returnUser.setAge(100);
		System.out.println(returnUser);
		
		//Thread.sleep(10000);
		
		return returnUser;
	}

	// 1.1 Client 로 Domain Object + 기타 Data 를 JSON 으로 전송할 경우.
	//요청 미디어타입 : application/json ==> 즉 데이터교환 json 
	//http://127.0.0.1:8080/Spring20/app/userAPI/getUserMore/user01?name=user02&age=10
	@RequestMapping(value="getUserMore/{value}" , method=RequestMethod.GET )
	public Map<String, Object> getUserMore	(	@PathVariable String value,
														@RequestParam("name") String name,
														@RequestParam("age") int age) throws Exception{
		System.out.println();
		System.out.println(value);
		System.out.println(name);
		System.out.println(age);
		
		User returnUser01 = new User();
		returnUser01.setUserId("AAA");
		returnUser01.setUserName("GET:이순신AAA");
		returnUser01.setAge(100);
		System.out.println(returnUser01);
		
		User returnUser02 = new User();
		returnUser02.setUserId("BBB");
		returnUser02.setUserName("GET:이순신BBB");
		returnUser02.setAge(200);
		System.out.println(returnUser02);
		
		User returnUser03 = new User();
		returnUser03.setUserId("CCC");
		returnUser03.setUserName("GET:이순신CCC");
		returnUser03.setAge(300);
		System.out.println(returnUser03);
		
		List<User> list =  new ArrayList<User>();
		list.add(returnUser01);
		list.add(returnUser02);
		list.add(returnUser03);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user",list);
		//==> 단순히 name=value 의 Data 를 저장할 경우는 ????
		map.put("message","ok");

		return map;
	}

	// 2.1 Client 로 Domain Object 만 전송할 경우.
	//http://127.0.0.1:8080/Spring20/app/userAPI/getUser
	// @ModelAttribute("user") User user : Client 의 Form Data => User 바인딩. 
	// @RequestBody User user : Client JSON Datga => User 바인딩
	@RequestMapping(value="getUser" , method=RequestMethod.POST )
	public User getUser	( @RequestBody User user ) throws Exception{
		System.out.println();
		System.out.println("[ Client JSON Data ]");
		System.out.println("1 : "+user);
		
		System.out.println("[To Client Data]");
		User returnUser = new User();
		returnUser.setUserId("AAA");
		returnUser.setUserName("POST:이순신");
		System.out.println("2 : "+returnUser);
		
		return returnUser;
	}
	
	
	
	// 2.1 Client 로 Domain Object + 기타 Data 를 JSON 으로 전송할 경우.
	//http://127.0.0.1:8080/Spring20/app/userAPI/getUserMore/user01
	// @ModelAttribute("user") User user : Client 의 Form Data => User 바인딩. 
	// @RequestBody User user : Client JSON Datga => User 바인딩	
	@RequestMapping(value="getUserMore/{value}" , method=RequestMethod.POST )
	public Map<String, Object> getUserMore	(  	@PathVariable String value, 
														@RequestBody User user ) throws Exception{	
		System.out.println();
		System.out.println("[ Client JSON Data ]");
		System.out.println(value);
		System.out.println("1 : "+user);
		
		System.out.println("[To Client Data]");
		User returnUser01 = new User();
		returnUser01.setUserId("AAA");
		returnUser01.setUserName("GET:이순신AAA");
		returnUser01.setAge(100);
		System.out.println(returnUser01);
		
		User returnUser02 = new User();
		returnUser02.setUserId("BBB");
		returnUser02.setUserName("GET:이순신BBB");
		returnUser02.setAge(200);
		System.out.println(returnUser02);
		
		User returnUser03 = new User();
		returnUser03.setUserId("CCC");
		returnUser03.setUserName("GET:이순신CCC");
		returnUser03.setAge(300);
		System.out.println(returnUser03);
		
		List<User> list =  new ArrayList<User>();
		list.add(returnUser01);
		list.add(returnUser02);
		list.add(returnUser03);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user",list);
		//==> 단순히 name=value 의 Data 를 저장할 경우는 ????
		map.put("message","ok");

		return map;
	}

	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ==> React : react-myweb01 : 추가된 부분.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//==> 로그인 수행 : HttpSession 사용 //==> 내부적으로 cookie 사용함.
	//http://127.0.0.1:8080/Spring20/app/userAPI/login  : POST  
	//{"userId":"user01","password":"user01"}
	@RequestMapping(value="login" , method=RequestMethod.POST )
	public User login	( @RequestBody User user , HttpSession session) throws Exception{
			System.out.println();
			System.out.println("[ From Client Data ]");
			
			System.out.println(user);
			
			if( user.getUserId().equals("user01") && user.getPassword().equals("user01")) {
				user.setActive(true);
				session.setAttribute("user", user);
			}
			
			System.out.println("[To Client Data]");
			
			System.out.println(user);
			
			return user;
	}	
	
	//==> 로그인 확인 : HttpSession 사용 //==> 내부적으로 cookie 사용함.
	//http://127.0.0.1:8080/Spring20/app/userAPI/checkLogin  : GET
	@RequestMapping(value="checkLogin" , method=RequestMethod.GET )
	public User checkLogin(HttpSession session) throws Exception{
	
		System.out.println("http://127.0.0.1:8080/Spring20/app/userAPI/checkLogin  : GET");
		
		User user = (User)session.getAttribute("user");
		
		System.out.println(user);
		
		return user;
	}	
	
	//==> 로그아웃 : HttpSession 사용 //==> 내부적으로 cookie 사용함.
	//http://127.0.0.1:8080/Spring20/app/userAPI/logout  : POST
	@RequestMapping(value="logout" , method=RequestMethod.POST )
	public void logout(HttpSession session) throws Exception{
	
	System.out.println("http://127.0.0.1:8080/Spring20/userAPI/logout  : POST");
	
	session.invalidate();
	
	}		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

}