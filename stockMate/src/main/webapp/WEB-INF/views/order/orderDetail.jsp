<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 상세 정보</title>
<link rel="stylesheet" href="<c:url value='/resources/css/OrderStyle.css' />">
</head>
<body>
    <div class="container">
        <div class="header">
            <h2>주문 상세 정보</h2>
         <a class="btn btn-success" href="/order/orderList" style="text-decoration: none;">목록으로</a>
        </div>
        <!-- 주문 기본 정보 -->
        <div class="order-info">
        <br>
            <h3>주문 기본 정보</h3>
            
            <table>
                <tr>
                    <th>주문번호</th>
                    <th>주문일자</th>
                    <th>주문유형</th>
                    <th>총 주문금액</th>
                </tr>
                <tr>
                    <td>${order.orderNumber}</td>
                    <td><fmt:formatDate value="${order.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                   	<td>
	                   	<c:choose>
	                       <c:when test="${order.orderType =='INBOUND'}">발주</c:when>
	                       <c:when test="${order.orderType =='OUTBOUND'}">수주</c:when>
	                     <%--   <c:otherwise>값이 없습니다</c:otherwise> --%>
	                   </c:choose>
	                </td>
                    <!-- 포매팅 관련 문제로 인해 잠시 대기 금액에 대해서 다 표 기 할지 올림할지 모르겠음 pattern="#,###.##"  -->
                    <td><fmt:formatNumber value="${order.totalPrice}" />원</td>
                </tr>
            </table>
        </div>
        <br>
        <h3>주문 상세 항목</h3>
<div class="card order-items">
    <table>
        <tr>
            <th>No.</th>
            <th>제품명</th>
            <th>제품바코드</th>
            <th>창고</th>
            <th>수량</th>
            <th>단위</th>
            <th>단가(원)</th>
            <th>소계(원)</th>
            <th>상태</th>
            <th>비고</th>
        </tr>
        <c:forEach var="item" items="${orderItems}" varStatus="status">
            <tr>
                <td>${status.count}</td>
                <td>${item.productName}</td>
                <td>${item.productBarcode}</td>
                <td>${item.warehouseName}</td>
                <td><fmt:formatNumber value="${item.quantity}" pattern="#,###" /></td>
                <td>${item.baseUnit}</td>
                <!-- 포매팅 대기 pattern="#,###" -->
                <td><fmt:formatNumber value="${item.unitPrice}" />원</td>
                <!-- 포매팅 대기  pattern="#,###"-->
                <td><fmt:formatNumber value="${item.subtotalPrice}"  />원</td>
                <td>${item.shipmentStatus}</td>
                <td>${item.remarks}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty orderItems}">
            <tr>
                <td colspan="9" class="text-center">주문 상세 내역이 없습니다.</td>
            </tr>
        </c:if>
    </table>
</div>
</body>
</html>