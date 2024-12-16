<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>재고 리스트</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background-color: #fff;
            table-layout: fixed;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
            word-break: break-word;
        }

        th {
            background-color: #007BFF;
            color: #fff;
            position: sticky;
            top: 0;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        a {
            color: #007BFF;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .low-stock {
            color: red;
            font-weight: bold;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>재고 리스트</h1>
        <table>
            <thead>
                <tr>
                    <th>상품명</th>
                    <th>바코드</th>
                    <th>카테고리명</th>
                    <th>창고명</th>
                    <th>창고 위치</th>
                    <th>총 재고</th>
                    <th>예약된 수량</th>
                    <th>사용 가능한 재고</th>
                    <th>최근 수정 시간</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty stockList}">
                        <c:forEach var="stock" items="${stockList}">
                            <tr>
                                <td>
                                    <!-- 상품명을 클릭하면 product/detail 페이지로 이동 -->
                                    <c:choose>
									    <c:when test="${not empty stock.product}">
									        <a href="<c:url value='/product/detail' />?productId=${stock.product.productId}">
									            ${stock.product.productName}
									        </a>
									    </c:when>
									    <c:otherwise>N/A</c:otherwise>
									</c:choose>
                                </td>
                                <td>${stock.product != null ? stock.product.productBarcode : 'N/A'}</td>
                                <td>${stock.category != null ? stock.category.categoryName : 'N/A'}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty stock.warehouse}">
                                            <a href="<c:url value='/warehouse/detail?warehouseId=${stock.warehouse.warehouseId}' />">${stock.warehouse.warehouseName}</a>
                                        </c:when>
                                        <c:otherwise>N/A</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${stock.warehouse != null ? stock.warehouse.warehouseLocation : 'N/A'}</td>
                                <td>${stock.totalQuantity != null ? stock.totalQuantity : 0}</td>
                                <td>${stock.reservedQuantity != null ? stock.reservedQuantity : 0}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${stock.availableStock != null && stock.availableStock < 10}">
                                            <span class="low-stock">${stock.availableStock}</span>
                                        </c:when>
                                        <c:otherwise>${stock.availableStock != null ? stock.availableStock : 0}</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty stock.updatedAt}">
                                            <fmt:formatDate value="${stock.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss" />
                                        </c:when>
                                        <c:otherwise>N/A</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="9">No stock data available.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</body>
</html>