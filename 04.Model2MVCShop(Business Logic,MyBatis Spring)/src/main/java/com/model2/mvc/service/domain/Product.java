package com.model2.mvc.service.domain;

import java.sql.Date;

//==>회원정보를 모델링(추상화/캡슐화)한 Bean
public class Product {
	
	///Field
	private int prodNo;
	private String prodName;
	private String prodDetail;
	private String manuDay;
	private int price;
	private String imageFile;
	private Date regDate;
	
	///Constructor
	public Product() {
	}
	///Method 
	public int getProdNo() {		return prodNo;	}
	public void setProdNo(int prodNo) {		this.prodNo=prodNo;	}
	
	public String getProdName() { return prodName;}
	public void setProdName(String prodName) { this.prodName=prodName; }
	
	public String getProdDetail() { return prodDetail; }
	public void setProdDetail(String prodDetail) {this.prodDetail=prodDetail;}
	public String getManuDay() { return manuDay; }
	public void setManuDay(String manuDay) { this.manuDay=manuDay; }
	
	public int getPrice() { return price; }
	public void setPrice(int price) {this.price = price; }
	
	public String getImageFile() { return imageFile; }
	public void setImageFile(String imageFile) { this.imageFile=imageFile;}
		
	public Date getRegDate() { return regDate; }
	public void setRegDate(Date regDate) { this.regDate=regDate; }
		
	@Override
	public String toString() {
		return "User Domain : " + prodNo + " / " +  prodName + " / " +  manuDay + " / " +  price + " / " +  imageFile + " / " + regDate ;
	}
	}