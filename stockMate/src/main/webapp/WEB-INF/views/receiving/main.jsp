<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>입고 메인 페이지</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<h1>입고 메인</h1>

${ReceivingList }
${YesterdayReceivingList }
${TDBYReceivingList }


<!-- 그래프 추가 -->
<h2>가장 많이 나간 상품 수량</h2>
<canvas id="myChart" width="400" height="200"></canvas>

<script>
    const labels = ["그저께", "어제", "오늘"];
    const data = {
        labels: labels,
        datasets: [{
            label: '가장 많이 나간 상품 수량',
            data: [
                /* 데이터 추출 로직 작성 */
            ],
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
        }]
    };

    const config = {
        type: 'bar',
        data: data,
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    };

    window.onload = function () {
        const ctx = document.getElementById('myChart').getContext('2d');
        new Chart(ctx, config);
    };
</script>


<!-- 오늘 입고 리스트 -->
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
		<th>수량 단위</th>
		<th>작업 메모</th>
		<th>공급사 회사 이름</th>
	</tr>
	<c:forEach var="vo" items="${ReceivingList }">
	<tr>
		<td>${vo.receivingShipmentNo }</td>
		<td>${vo.transactionType }</td>
		<td><fmt:formatDate value="${vo.createdAt}" pattern="yyyy-MM-dd" /></td>
		<td>${vo.status }</td>
		<td>${vo.productId }</td>
		<td>${vo.name }</td>
		<td>${vo.description }</td>
		<td>${vo.changeQuantity }</td>
		<td>${vo.transactionUnit }</td>
		<td>${vo.memo }</td>
		<td>${vo.companyName }</td>
	</tr>
	</c:forEach>
</table>


<!-- 어제 입고 리스트 -->
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
		<th>수량 단위</th>
		<th>작업 메모</th>
		<th>공급사 회사 이름</th>
	</tr>
	<c:forEach var="vo" items="${YesterdayReceivingList }">
	<tr>
		<td>${vo.receivingShipmentNo }</td>
		<td>${vo.transactionType }</td>
		<td><fmt:formatDate value="${vo.createdAt}" pattern="yyyy-MM-dd" /></td>
		<td>${vo.status }</td>
		<td>${vo.productId }</td>
		<td>${vo.name }</td>
		<td>${vo.description }</td>
		<td>${vo.changeQuantity }</td>
		<td>${vo.transactionUnit }</td>
		<td>${vo.memo }</td>
		<td>${vo.companyName }</td>
	</tr>
	</c:forEach>
</table>



<!-- 그저께 입고 리스트 -->
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
		<th>수량 단위</th>
		<th>작업 메모</th>
		<th>공급사 회사 이름</th>
	</tr>
	<c:forEach var="vo" items="${TDBYReceivingList }">
	<tr>
		<td>${vo.receivingShipmentNo }</td>
		<td>${vo.transactionType }</td>
		<td><fmt:formatDate value="${vo.createdAt}" pattern="yyyy-MM-dd" /></td>
		<td>${vo.status }</td>
		<td>${vo.productId }</td>
		<td>${vo.name }</td>
		<td>${vo.description }</td>
		<td>${vo.changeQuantity }</td>
		<td>${vo.transactionUnit }</td>
		<td>${vo.memo }</td>
		<td>${vo.companyName }</td>
	</tr>
	</c:forEach>
</table>



</body>
</html>