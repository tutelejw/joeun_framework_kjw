<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>구매하기</title></head>
<body>
    <h2>[ 구매 정보 입력 ]</h2>
    <form action="/addPurchase.do" method="post">
        <input type="hidden" name="prodNo" value="${product.prodNo}">
        
        <h4>구매자 정보</h4>
        아이디: ${sessionScope.user.userId}<br>
        이름: ${sessionScope.user.userName}<br><br>
        
        <h4>배송지 정보</h4>
        받는 사람: <input type="text" name="receiverName" value="${sessionScope.user.userName}"><br>
        연락처: <input type="text" name="receiverPhone" value="${sessionScope.user.phone}"><br>
        주소: <input type="text" name="dlvyAddr" value="${sessionScope.user.addr}"><br>
        배송 요청사항: <input type="text" name="dlvyRequest"><br>
        배송 희망일자: <input type="text" name="dlvyDate" placeholder="YYYYMMDD"><br><br>
        
        <h4>결제 정보</h4>
        결제 방식: 
        <select name="paymentOption">
            <option value="1">신용카드</option>
            <option value="2">무통장입금</option>
            <option value="3">카카오페이</option>
        </select><br><br>
        
        <input type="submit" value="결제하기">
    </form>
</body>
</html>