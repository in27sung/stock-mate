<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>인벤토리 목록</title>
</head>
<body>
    <h1> /inventory/list.jsp </h1>
    
    <!-- 필터링 폼 -->
    <form action="/inventory/list" method="GET">
        <label for="category">카테고리명:</label>
        <input type="text" name="category" id="category">
        
        <label for="productName">제품명:</label>
        <input type="text" name="productName" id="productName">
        
        <label for="optionName">옵션명:</label>
        <input type="text" name="optionName" id="optionName">
        
        <label for="quantity">수량:</label>
        <input type="number" name="quantity" id="quantity">
        
        <label for="price">가격:</label>
        <input type="number" name="price" id="price">
        
        <button type="submit">검색</button>
    </form>

    <h2>재고 목록</h2>
    
    <table border="1">
        <thead>
            <tr>
                <th>카테고리명</th>
                <th>제품번호</th>
                <th>제품명</th>
                <th>옵션명</th>
                <th>수량</th>
                <th>가격</th>
                <th>설명</th>
                <th>수정</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${inventoryList}">
                <tr>
                    <td>${item.categoryName}</td>
                    <td>${item.productNumber}</td>
                    <td>${item.productName}</td>
                    <td>${item.optionName}</td>
                    <td>${item.quantity}</td>
                    <td>${item.price}</td>
                    <td>${item.description}</td>
                    <td><a href="/inventory/edit?productId=${item.productId}">수정</a></td>
                    <td>
                        <form action="/inventory/delete" method="POST">
                            <input type="hidden" name="productId" value="${item.productId}">
                            <button type="submit">삭제</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
<!--     <a href="/inventory/register">새 상품 등록</a> -->
</body>
</html>
