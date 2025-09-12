package mybatis.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

//@Aspect
public class PojoAspectJ {
		
		///Constructor
		public PojoAspectJ() {
			System.out.println(":: PojoAspectJ Default Constructor");
			System.out.println("sfdajkldsfkjdsa"+getClass());
			System.out.println("sfdajkldsfkjdsa "+getClass().getSimpleName());
		}
		
		public void work() {
			System.out.println("work() pointcut call..............................");
		}
		

		public void before(JoinPoint joinPoint) {
			System.out.println( "[before LOG] " + getClass() + ".before() start ");
			System.out.println( "[before LOG] targetObject : " + joinPoint.getTarget().getClass().getName());
			System.out.println( "[before LOG] targetObject method  :  " + joinPoint.getSignature().getName());
			if(joinPoint.getArgs().length != 0) {
				System.out.println("[before LOG] targetObject Call: " + "method argument : " + joinPoint.getArgs()[0]);
			}
			System.out.println( "[before LOG] " + getClass() + ".before() end ");
		}
		
		
		public void afterReturning (JoinPoint joinPoint, Object returnValue){
			System.out.println( "[after LOG] " + getClass() + ".afterReturning() start ");
			System.out.println( "[after LOG] 타겟 객체 :  " + joinPoint.getTarget().getClass().getName());
			System.out.println( "[after LOG] 타겟 객체의 호출된 method :  " + joinPoint.getSignature().getName());
			System.out.println( "[after LOG] 타겟 객체의 호출후 return value : " + returnValue);
			System.out.println( "[after LOG] " + getClass() + ".afterReturning() end ");
		}
		
	//	@Around("work()")
		public Object invoke(ProceedingJoinPoint joinPoint)  throws Throwable {
			System.out.println( "[Around before] " + getClass() + ".invoke() start ");
			System.out.println( "[Around before] 타겟 객체  : " + joinPoint.getTarget().getClass().getName());
			System.out.println( "[Around before] 타겟 객체의 호출될 method :  " + joinPoint.getSignature().getName());
			
			if(joinPoint.getArgs().length !=0 ) {
				System.out.println( "[Around before] 타겟 객체의 호출할 " +  " method에 전달되는 인자 : " + joinPoint.getArgs()[0]);
			}
			Object obj = joinPoint.proceed();
			
			System.out.println( "[Around after] 타겟 객체의 호출 후 return value : " + obj);
			System.out.println( "[Around before] " + getClass() + ".invoke() end ");
			
			return obj;
		}
		
		//@AfterThrowing(pointcut="work()", throwing="throwable")
		public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
			System.out.println( "[exception] " + getClass() + ".afterThrowing() start ");
			System.out.println( "[exception]  타겟 객체 : "+ joinPoint.getTarget().getClass().getName());
			System.out.println( "[exception]  타겟 객체 호출 된 method : " + joinPoint.getSignature().getName());
			System.out.println( "[exception]  Exception 발생..... ");
			System.out.println( "[exception]  Exception Message : " + throwable.getMessage());
			System.out.println( "[exception] " + getClass() + ".afterThrowing() end ");
		}
}
























