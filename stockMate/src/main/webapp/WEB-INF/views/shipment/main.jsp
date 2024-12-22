<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>출고 메인</title>
<link rel="stylesheet" type="text/css" href="/resources/css/receivingMainStyle.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

</head>
<body>

<h1>출고 메인</h1>

<nav>
<a href="/shipment/history">출고 내역</a>
<a href="/dashboard">대시보드</a><br><br>
<form action="/shipment/insert1" method="POST">
    <input type="submit" value="새로고침">
</form>
</nav>


<!-- 오늘 출고 리스트 -->
<h2>오늘 출고 리스트</h2>
<table border="1">
    <tr>
        <th>출고 번호</th>
        <th>입고 출고</th>
        <th>출고 일자</th>
        <th>출고 상태</th>
        <th>제품 번호</th>
        <th>제품명</th>
        <th>옵션명</th>
        <th>출고 수량</th>
        <th>작업 메모</th>
    </tr>
    <c:forEach var="vo" items="${ShipmentList}">
        <tr>
            <td>${vo.receivingShipmentNo}</td>
            <td>     
                 <c:choose>
                    <c:when test="${vo.transactionType == 'INBOUND'}">입고</c:when>
                    <c:when test="${vo.transactionType == 'OUTBOUND'}">출고</c:when>
                    <c:otherwise>${vo.transactionType}</c:otherwise>
                </c:choose>
            </td>
            <td><fmt:formatDate value="${vo.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
            <td>
                <c:choose>
                    <c:when test="${vo.status == 'PENDING'}">대기중</c:when>
                    <c:when test="${vo.status == 'COMPLETED'}">완료됨</c:when>
                    <c:otherwise>${vo.status}</c:otherwise>
                </c:choose>
            </td>
            <td>${vo.productId}</td>
            <td>${vo.productName}</td>
            <td>${vo.productDescription}</td>
            <td>${vo.changeQuantity}</td>
            <td>${vo.memo}</td>
        </tr>
    </c:forEach>
</table>

<!-- 어제 출고 리스트 -->
<h2>어제 출고 리스트</h2>
<table border="1">
    <tr>
        <th>출고 번호</th>
        <th>입고 출고</th>
        <th>출고 일자</th>
        <th>출고 상태</th>
        <th>제품 번호</th>
        <th>제품명</th>
        <th>옵션명</th>
        <th>출고 수량</th>
        <th>작업 메모</th>
    </tr>
    <c:forEach var="vo" items="${YesterdayShipmentList}">
        <tr>
            <td>${vo.receivingShipmentNo}</td>
            <td>     
                 <c:choose>
                    <c:when test="${vo.transactionType == 'INBOUND'}">입고</c:when>
                    <c:when test="${vo.transactionType == 'OUTBOUND'}">출고</c:when>
                    <c:otherwise>${vo.transactionType}</c:otherwise>
                </c:choose>
            </td>
            <td><fmt:formatDate value="${vo.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
            <td>
                <c:choose>
                    <c:when test="${vo.status == 'PENDING'}">대기중</c:when>
                    <c:when test="${vo.status == 'COMPLETED'}">완료됨</c:when>
                    <c:otherwise>${vo.status}</c:otherwise>
                </c:choose>
            </td>
            <td>${vo.productId}</td>
            <td>${vo.productName}</td>
            <td>${vo.productDescription}</td>
            <td>${vo.changeQuantity}</td>
            <td>${vo.memo}</td>
        </tr>
    </c:forEach>
</table>

<!-- 그저께 출고 리스트 -->
<h2>그저께 출고 리스트</h2>
<table border="1">
    <tr>
        <th>출고 번호</th>
        <th>입고 출고</th>
        <th>출고 일자</th>
        <th>출고 상태</th>
        <th>제품 번호</th>
        <th>제품명</th>
        <th>옵션명</th>
        <th>출고 수량</th>
        <th>작업 메모</th>
    </tr>
    <c:forEach var="vo" items="${TDBYShipmentList}">
        <tr>
            <td>${vo.receivingShipmentNo}</td>
            <td>     
                 <c:choose>
                    <c:when test="${vo.transactionType == 'INBOUND'}">입고</c:when>
                    <c:when test="${vo.transactionType == 'OUTBOUND'}">출고</c:when>
                    <c:otherwise>${vo.transactionType}</c:otherwise>
                </c:choose>
            </td>
            <td><fmt:formatDate value="${vo.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
            <td>
                <c:choose>
                    <c:when test="${vo.status == 'PENDING'}">대기중</c:when>
                    <c:when test="${vo.status == 'COMPLETED'}">완료됨</c:when>
                    <c:otherwise>${vo.status}</c:otherwise>
                </c:choose>
            </td>
            <td>${vo.productId}</td>
            <td>${vo.productName}</td>
            <td>${vo.productDescription}</td>
            <td>${vo.changeQuantity}</td>
            <td>${vo.memo}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
