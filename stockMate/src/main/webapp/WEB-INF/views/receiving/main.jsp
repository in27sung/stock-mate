<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>입고 메인</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

</head>
<body>

<h1>입고 메인</h1>

<a href="/receiving/history">입고 내역</a>
<a href="/dashboard">대쉬보드</a>
<form action="/receiving/insert1" method="POST">
    <input type="submit" value="새로고침">
</form>

<!-- 오늘 입고 리스트 -->
<h2>오늘 입고 리스트</h2>
<table border="1">
    <tr>
        <th>입고 번호</th>
        <th>입고 출고</th>
        <th>입고 일자</th>
        <th>입고 상태</th>
        <th>제품 번호</th>
        <th>제품명</th>
        <th>옵션명</th>
        <th>입고 수량</th>
        <th>작업 메모</th>
    </tr>
    <c:forEach var="vo" items="${ReceivingList}">
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

<!-- 어제 입고 리스트 -->
<h2>어제 입고 리스트</h2>
<table border="1">
    <tr>
        <th>입고 번호</th>
        <th>입고 출고</th>
        <th>입고 일자</th>
        <th>입고 상태</th>
        <th>제품 번호</th>
        <th>제품명</th>
        <th>옵션명</th>
        <th>입고 수량</th>
        <th>작업 메모</th>
    </tr>
    <c:forEach var="vo" items="${YesterdayReceivingList}">
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

<!-- 그저께 입고 리스트 -->
<h2>그저께 입고 리스트</h2>
<table border="1">
    <tr>
        <th>입고 번호</th>
        <th>입고 출고</th>
        <th>입고 일자</th>
        <th>입고 상태</th>
        <th>제품 번호</th>
        <th>제품명</th>
        <th>옵션명</th>
        <th>입고 수량</th>
        <th>작업 메모</th>
    </tr>
    <c:forEach var="vo" items="${TDBYReceivingList}">
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
