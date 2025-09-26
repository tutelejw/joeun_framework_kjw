package com.model2.mvc.service.domain;


import java.sql.Date;

public class Purchase {

    private int tranNo;
    private Product purchaseProd;     // 연관 객체
    private User buyer;              // 연관 객체

    private String paymentOption;
    private String receiverName;
    private String receiverPhone;
    private String divyAddr;
    private String divyRequest;
    private String tranCode;
    private Date orderDate;
    private Date divyDate;

    public Purchase() {
    }

    // ===== Getter / Setter =====

    public int getTranNo() {
        return tranNo;
    }

    public void setTranNo(int tranNo) {
        this.tranNo = tranNo;
    }

    public Product getPurchaseProd() {
        return purchaseProd;
    }

    public void setPurchaseProd(Product purchaseProd) {
        this.purchaseProd = purchaseProd;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getDivyAddr() {
        return divyAddr;
    }

    public void setDivyAddr(String divyAddr) {
        this.divyAddr = divyAddr;
    }

    public String getDivyRequest() {
        return divyRequest;
    }

    public void setDivyRequest(String divyRequest) {
        this.divyRequest = divyRequest;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDivyDate() {
        return divyDate;
    }

    public void setDivyDate(Date divyDate) {
        this.divyDate = divyDate;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "tranNo=" + tranNo +
                ", purchaseProd=" + purchaseProd +
                ", buyer=" + buyer +
                ", paymentOption='" + paymentOption + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", divyAddr='" + divyAddr + '\'' +
                ", divyRequest='" + divyRequest + '\'' +
                ", tranCode='" + tranCode + '\'' +
                ", orderDate=" + orderDate +
                ", divyDate=" + divyDate +
                '}';
    }
}

