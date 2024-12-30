<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>재고 리스트</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/stockListStyle.css' />">
</head>
<body>
    <div class="container">
        <h1>재고 리스트</h1>
        
        <!-- 대시보드 이동 버튼 -->
        <button class="dashboard-btn" onclick="location.href='<c:url value="/dashboard" />'">대시보드로 이동</button>
        
        <!-- 검색 필드 (우측 상단) -->
        <div class="search-box">
            <input type="text" id="searchInput" placeholder="상품명을 검색하세요..." onkeyup="filterTable()">
        </div>
        
        <table>
            <thead>
                <tr>
                    <!-- 상품명 정렬 -->
                    <th>
                        <a href="?sortColumn=product_name&sortOrder=${sortOrder eq 'asc' and sortColumn eq 'product_name' ? 'desc' : 'asc'}">
                            상품명
                            <c:if test="${sortColumn eq 'product_name'}">
                                <span class="sort-indicator">${sortOrder eq 'asc' ? '▲' : '▼'}</span>
                            </c:if>
                        </a>
                    </th>

                    <th>바코드</th>
                    <th>카테고리명</th>

                    <!-- 창고명 정렬 -->
                    <th>
                        <a href="?sortColumn=warehouse_name&sortOrder=${sortOrder eq 'asc' and sortColumn eq 'warehouse_name' ? 'desc' : 'asc'}">
                            창고명
                            <c:if test="${sortColumn eq 'warehouse_name'}">
                                <span class="sort-indicator">${sortOrder eq 'asc' ? '▲' : '▼'}</span>
                            </c:if>
                        </a>
                    </th>

                    <th>총 재고</th>
                    <th>
					    <a href="?sortColumn=available_stock&sortOrder=${sortColumn eq 'available_stock' and sortOrder eq 'asc' ? 'desc' : 'asc'}">
					        가용 재고
					        <c:if test="${sortColumn eq 'available_stock'}">
					            <span class="sort-indicator">${sortOrder eq 'asc' ? '▲' : '▼'}</span>
					        </c:if>
					    </a>
					</th>
                    <th>
                        <a href="?sortColumn=updated_at&sortOrder=${sortOrder eq 'asc' and sortColumn eq 'updated_at' ? 'desc' : 'asc'}">
                            마지막 수정 시간
                            <c:if test="${sortColumn eq 'updated_at'}">
                                <span class="sort-indicator">${sortOrder eq 'asc' ? '▲' : '▼'}</span>
                            </c:if>
                        </a>
                    </th>
                </tr>
            </thead>
            <tbody id="stockTableBody">
                <c:choose>
                    <c:when test="${not empty stockList}">
                        <c:forEach var="stock" items="${stockList}">
                            <tr>
                                <!-- 상품명 클릭 시 상품 상세 페이지 이동 -->
                                <td class="product-name">
                                    <a href="<c:url value='/product/detail' />?productId=${stock.productId}">
                                        ${stock.productName}
                                    </a>
                                </td>

                                <!-- 바코드 -->
                                <td>${stock.productBarcode}</td>

                                <!-- 카테고리명 -->
                                <td>${stock.categoryName}</td>

                                <!-- 창고명 클릭 시 창고 상세 페이지 이동 -->
                                <td>
                                    <a href="<c:url value='/warehouse/detail' />?warehouseId=${stock.warehouseId}">
                                        ${stock.warehouseName}
                                    </a>
                                </td>

                                <!-- 총 재고 -->
                                <td>${stock.totalQuantity}</td>

                                <!-- 사용 가능한 재고 (10 이하 강조) -->
                                <td>
                                    <c:choose>
                                        <c:when test="${stock.availableStock < 10}">
                                            <span class="low-stock">${stock.availableStock}</span>
                                        </c:when>
                                        <c:otherwise>${stock.availableStock}</c:otherwise>
                                    </c:choose>
                                </td>

                                <!-- 최근 수정 시간 -->
                                <td>
                                    <fmt:formatDate value="${stock.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss" />
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="7">No stock data available.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
    <!-- 검색 기능 JavaScript -->
    <script>
	    function filterTable() {
	        const input = document.getElementById('searchInput');
	        const filter = input.value.toLowerCase().replace(/\s+/g, ''); // 입력값에서 공백 제거
	        const rows = document.querySelectorAll('#stockTableBody tr');
	
	        rows.forEach(row => {
	            // 상품명이 있는 td의 첫 번째 요소를 찾음
	            const productName = row.getElementsByTagName('td')[0]?.textContent.toLowerCase().replace(/\s+/g, '');
	
	            // 공백이 제거된 문자열을 비교하여 필터링
	            row.style.display = productName.includes(filter) ? '' : 'none';
	        });
	    }
    </script>
</body>
</html>
