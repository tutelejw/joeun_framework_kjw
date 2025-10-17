package com.model2.mvc.service.domain;

import java.sql.Date;

//==>회원정보를 모델링(추상화/캡슐화)한 Bean
public class Product {
	
	///Field
	private int prodNo;
	private String prodName;
	private String prodDetail;
	private String manuDate;
	private int price;
	private String fileName; 
	private Date regDate;
	private String proTranCode;
	
	///Constructor
	public Product() {
	}
	///Method 
	
	public String getProTranCode() { return proTranCode;}
	public void setProTranCode(String proTranCode) {this.proTranCode=proTranCode;}
	
	public int getProdNo() {		return prodNo;	}
	public void setProdNo(int prodNo) {		this.prodNo=prodNo;	}
	
	public String getProdName() { return prodName;}
	public void setProdName(String prodName) { this.prodName=prodName; }
	
	public String getProdDetail() { return prodDetail; }
	public void setProdDetail(String prodDetail) {this.prodDetail=prodDetail;}
	public String getManuDate() { return manuDate; }
	public void setManuDate(String manuDate) { this.manuDate=manuDate; }
	
	public int getPrice() { return price; }
	public void setPrice(int price) {this.price = price; }
	
	public String getFileName() { return fileName; }
	public void setFileName(String fileName) { this.fileName=fileName;}
		
	public Date getRegDate() { return regDate; }
	public void setRegDate(Date regDate) { this.regDate=regDate; }
		
	@Override
	public String toString() {
		return "Product Domain : " +getClass().getSimpleName() + " :: " + prodNo + " / " +  prodName + " / " +  manuDate + " / " +  price + " / " +  fileName + " / " + regDate ;
	}
	}