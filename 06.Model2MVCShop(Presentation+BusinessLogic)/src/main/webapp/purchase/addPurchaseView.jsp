<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="vo" value="${requestScope.vo}" />

<c:set var="user" value="${sessionScope.user}" />

<html>
<head>
    <title>상품정보조회</title>
    <link rel="stylesheet" href="/css/admin.css" type="text/css">
</head>

<body bgcolor="#ffffff" text="#000000">

<!-- 구매자 정보 출력 -->
<p>구매자 ID: ${user.userId}</p>
<p>구매자 이름: ${user.userName}</p>
<p>연락처: ${user.phone}</p>

<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td width="15" height="37"><img src="/images/ct_ttl_img01.gif" width="15" height="37"></td>
        <td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="93%" class="ct_ttl01">상품구매</td>
                    <td width="20%" align="right">&nbsp;</td>
                </tr>
            </table>
        </td>
        <td width="12" height="37"><img src="/images/ct_ttl_img03.gif" width="12" height="37"></td>
    </tr>
</table>

<form action="/addPurchase.do" method="post">

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <!-- 상품번호 -->
    <tr>
        <td width="104" class="ct_write">
            상품번호 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
        </td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td class="ct_write01">
            ${vo.prodNo}
            <input type="hidden" name="prodNo" value="${vo.prodNo}" />
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <!-- 상품명 -->
    <tr>
        <td width="104" class="ct_write">
            상품명 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
        </td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td class="ct_write01">${vo.prodName}</td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <!-- 상품상세정보 -->
    <tr>
        <td width="104" class="ct_write">
            상품상세정보 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
        </td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td class="ct_write01">${vo.prodDetail}</td>
    </tr>
    <tr><td height "1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <!-- 제조일자 -->
    <tr>
        <td width="104" class="ct_write">제조일자</td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td class="ct_write01">${vo.manuDate}</td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <!-- 가격 -->
    <tr>
        <td width="104" class="ct_write">가격</td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td class="ct_write01">${vo.price}</td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <!-- 등록일자 -->
    <tr>
        <td width="104" class="ct_write">등록일자</td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td class="ct_write01">
            <fmt:formatDate value="${vo.regDate}" pattern="yyyy-MM-dd" />
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <!-- 구매자아이디 -->
    <tr>
        <td width="104" class="ct_write">
            구매자아이디 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
        </td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td class="ct_write01">${user.userId}</td>
        <input type="hidden" name="userId" value="${user.userId}" />
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <!-- 구매방법 -->
    <tr>
        <td width="104" class="ct_write">구매방법</td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td class="ct_write01">
            <select name="paymentOption" class="ct_input_g" style="width: 100px; height: 19px" maxLength="20">
                <option value="1" <c:if test="${param.paymentOption == '1'}">selected</c:if>>현금구매</option>
                <option value="2" <c:if test="${param.paymentOption == '2'}">selected</c:if>>신용구매</option>
            </select>
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <!-- 사용자 이름 -->
    <tr>
        <td width="104" class="ct_write">${user.userName}</td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td class="ct_write01">
            <input type="text" name="userName" class="ct_input_g" style="width: 100px; height: 19px"
                   maxLength="20" value="${user.userName}" />
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <!-- 연락처 -->
    <tr>
        <td width="104" class="ct_write">구매자연락처</td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td class="ct_write01">
            <input type="text" name="phone" class="ct_input_g" style="width: 100px; height: 19px"
                   maxLength="20" value="${user.phone}" />
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <!-- 주소 -->
    <tr>
        <td width="104" class "ct_write">구매자주소</td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td class="ct_write01">
            <input type="text" name="receiverAddr" class="ct_input_g" style="width: 100px; height: 19px"
                   maxLength="20" value="" />
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <!-- 구매요청사항 -->
    <tr>
        <td width="104" class="ct_write">구매요청사항</td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td class="ct_write01">
            <input type="text" name="receiverRequest" class="ct_input_g" style="width: 100px; height: 19px"
                   maxLength="20" />
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>

    <!-- 배송희망일자 -->
    <tr>
        <td width="104" class="ct_write">배송희망일자</td>
        <td bgcolor="D6D6D6" width="1"></td>
        <td width="200" class="ct_write01">
            <input type="date" name="receiverDate" class="ct_input_g" style="width: 130px; height: 19px" />
        </td>
    </tr>
    <tr><td height="1" colspan="3" bgcolor="D6D6D6"></td></tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
    <tr>
        <td width="53%"></td>
        <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="17" height="23">
                        <img src="/images/ct_btnbg01.gif" width="17" height="23"/>
                    </td>
                    <td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
                        <button type="submit" style="all:unset; cursor:pointer;">구매</button>
                    </td>
                    <td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"></td>
                    <td width="30"></td>
                    <td width="17" height="23"><img src="/images/ct_btnbg01.gif" width="17" height="23"></td>
                    <td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
                        <a href="javascript:history.go(-1);">확인</a>
                    </td>
                    <td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>

</form>

</body>
</html>
