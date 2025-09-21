package spring.web.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import spring.domain.User;

@RestController
@ResponseBody
//@RequestMapping("/userAPI/")
@RequestMapping("/userAPI/*")
public class UserRestController {

	public UserRestController() {
		System.out.println(":: UserRestController default Contructor call ");
	}
	
	//http://127.0.0.1:8080/Spring13/app/userAPI/getUser?name=user02&age=10
	@RequestMapping(value="getUser", method=RequestMethod.GET)
	
	public User getUser(@RequestParam("name") String name, @RequestParam("age") int age) throws Exception{
		System.out.println();
		System.out.println(name);
		System.out.println(age);
		
		User returnUser = new User();
		returnUser.setUserId("AAA");
		returnUser.setUserName("GET:이순신");
		returnUser.setAge(100);
		System.out.println(returnUser);
		
		return returnUser;
	}
	
	//http://127.0.0.1:8080/Spring13/app/userAPI/getUserMore/user01?name=user02&age=10
	@RequestMapping(value="getUserMore/{value}", method=RequestMethod.GET)
	public Map getUserUserMore(@PathVariable String value, @RequestParam("name") String name, @RequestParam("age") int age) throws Exception{
		System.out.println();
		System.out.println(value);
		System.out.println(name);
		System.out.println(age);
		
		User returnUser = new User();
		returnUser.setUserId("AAA");
		returnUser.setUserName("GET:이순신");
		returnUser.setAge(100);
		System.out.println(returnUser);
		
		Map map = new HashMap();
		map.put("user",  returnUser);
		map.put("message", "OK");
		return map;
	}
	
	//http://127.0.0.1:8080/Spring13/userAPI/getUser
	@RequestMapping(value="getUser", method=RequestMethod.POST)
	public User getUser(@RequestBody User user) throws Exception{
		System.out.println();
		System.out.println("[Form Client Data]");
		System.out.println("1 : " + user );
		
		System.out.println("[To Client Data]");
		User returnUser = new User();
		returnUser.setUserId("AAA");
		returnUser.setUserName("POST:이순신");
		System.out.println("2: " + returnUser);
		
		return returnUser;
	}
	
	//http://127.0.0.1:8080/Spring13/app/userAPI/getUserMore/user01
	@RequestMapping(value="getUserMore/{value}", method=RequestMethod.POST)
	public Map getUserUserMore(@PathVariable String value, @RequestBody User user) throws Exception{
		System.out.println();
		System.out.println("[Form Client Data]");
		System.out.println(value);
		System.out.println("1 : " + user );
		
		System.out.println("[To Client Data]");
		User returnUser = new User();
		returnUser.setUserId("AAA");
		returnUser.setUserName("POST:이순신");
		System.out.println("2: " + returnUser);
		
		Map map = new HashMap();
		map.put("user",  returnUser);
		map.put("message", "OK");
		return map;
	}
}
