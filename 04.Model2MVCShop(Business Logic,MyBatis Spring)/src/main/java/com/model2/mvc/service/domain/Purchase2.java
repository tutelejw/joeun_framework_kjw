package com.model2.mvc.service.domain;

import java.sql.Date;

public class Purchase2 {

    /// Field
    private int tranNo;
    private Product purchaseProd;   // product 테이블
    private User buyer;             // users 테이블
    private String paymentOption;
    private String receiverName;
    private String receiverPhone;
    private String deMailAddr;
    private String dlvyRequest;
    private String tranStatusCode;

    private Date orderDate;
    private Date dlvyDate;

    /// Constructor
    public Purchase2() {
    }

    /// Getter / Setter
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

    public String getDeMailAddr() {
        return deMailAddr;
    }

    public void setDeMailAddr(String deMailAddr) {
        this.deMailAddr = deMailAddr;
    }

    public String getDlvyRequest() {
        return dlvyRequest;
    }

    public void setDlvyRequest(String dlvyRequest) {
        this.dlvyRequest = dlvyRequest;
    }

    public String getTranStatusCode() {
        return tranStatusCode;
    }

    public void setTranStatusCode(String tranStatusCode) {
        this.tranStatusCode = tranStatusCode;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDlvyDate() {
        return dlvyDate;
    }

    public void setDlvyDate(Date dlvyDate) {
        this.dlvyDate = dlvyDate;
    }

    /// toString
    @Override
    public String toString() {
        return "Purchase{" +
                "tranNo=" + tranNo +
                ", purchaseProd=" + (purchaseProd != null ? purchaseProd.getProdName() : null) +
                ", buyer=" + (buyer != null ? buyer.getUserId() : null) +
                ", paymentOption='" + paymentOption + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", deMailAddr='" + deMailAddr + '\'' +
                ", dlvyRequest='" + dlvyRequest + '\'' +
                ", tranStatusCode='" + tranStatusCode + '\'' +
                ", orderDate=" + orderDate +
                ", dlvyDate=" + dlvyDate +
                '}';
    }
}

