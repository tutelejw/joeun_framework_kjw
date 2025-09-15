package spring.model2.control;

/*
 * FileName : ModelAndView.java
 * 	:: Controller 역할 중
 * 		- Model 과 View 연결 : ObjectScope 사용 
 * 		- Navigation : forward / sendRedirect 이용
 * 	:: 두 역할의 정적인 부분만 Modeling 한 Bean  
*/	
public class  ModelAndView{
	
	//Field 
	private String viewName;
	private String modelName;
	private Object modelObject;
	
	///Constructor
	public ModelAndView(){
	}
	public ModelAndView(String viewName){
		this.viewName = viewName;
	}
	public ModelAndView(String viewName,String modelName,Object modelObject){
		this.viewName = viewName;
		this.modelName = modelName;
		this.modelObject = modelObject;
	}
	
	///Method
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Object getModelObject() {
		return modelObject;
	}
	public void setModelObject(Object modelObject) {
		this.modelObject = modelObject;
	}
	
}//end of class