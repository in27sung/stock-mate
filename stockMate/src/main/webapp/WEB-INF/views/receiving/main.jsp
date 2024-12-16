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
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        /* 전체 페이지 스타일 */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        h1 {
            text-align: center;
            margin-top: 30px;
            color: #2c3e50;
        }

        a {
            display: inline-block;
            padding: 10px 20px;
            margin: 20px 0;
            font-size: 16px;
            color: #fff;
            background-color: #3498db;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        a:hover {
            background-color: #2980b9;
        }

        /* 테이블 스타일 */
        table {
            width: 90%;
            max-width: 1200px;
            margin-bottom: 40px;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ddd;
        }

        th {
            background-color: #3498db;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #ecf0f1;
        }

        /* 테이블 헤더 크기 조정 */
        th {
            font-size: 14px;
        }

        td {
            font-size: 13px;
        }

        /* 추가된 구분선 */
        hr {
            width: 90%;
            max-width: 1200px;
            margin-top: 40px;
            margin-bottom: 40px;
            border: 0;
            border-top: 1px solid #ddd;
        }

        /* 작은 화면에서 더 나은 보기 */
        @media (max-width: 768px) {
            table {
                width: 100%;
                font-size: 12px;
            }

            a {
                font-size: 14px;
            }

            th, td {
                padding: 8px;
            }
        }
    </style>
</head>
<body>

<h1>입고 메인</h1>

<a href="/receiving/history">입고 내역</a>
<a href="/receiving/scan">실시간 입고</a>
<a href="/dashboard">대쉬보드</a>
<form action="/receiving/insert1" method="POST">
   <input type="submit" value="새로고침">
</form>

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
	</tr>
	<c:forEach var="vo" items="${ReceivingList }">
	<tr>
		<td>${vo.receivingShipmentNo }</td>
		<td>${vo.transactionType }</td>
		<td><fmt:formatDate value="${vo.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<td>${vo.status}</td>
		<td>${vo.productId }</td>
		<td>${vo.productName }</td>
		<td>${vo.productDescription }</td>
		<td>${vo.changeQuantity }</td>
		<td>${vo.transactionUnit }</td>
		<td>${vo.memo }</td>
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
	</tr>
	<c:forEach var="vo" items="${YesterdayReceivingList }">
	<tr>
		<td>${vo.receivingShipmentNo }</td>
		<td>${vo.transactionType }</td>
		<td><fmt:formatDate value="${vo.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<td>${vo.status }</td>
		<td>${vo.productId }</td>
		<td>${vo.productName }</td>
		<td>${vo.productDescription }</td>
		<td>${vo.changeQuantity }</td>
		<td>${vo.transactionUnit }</td>
		<td>${vo.memo }</td>
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
	</tr>
	<c:forEach var="vo" items="${TDBYReceivingList }">
	<tr>
		<td>${vo.receivingShipmentNo }</td>
		<td>${vo.transactionType }</td>
		<td><fmt:formatDate value="${vo.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<td>${vo.status }</td>
		<td>${vo.productId }</td>
		<td>${vo.productName }</td>
		<td>${vo.productDescription }</td>
		<td>${vo.changeQuantity }</td>
		<td>${vo.transactionUnit }</td>
		<td>${vo.memo }</td>
	</tr>
	</c:forEach>
</table>



</body>
</html>