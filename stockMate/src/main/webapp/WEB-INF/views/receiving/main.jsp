<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

<h1>입고 메인</h1>

${ReceivingList }

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
	<c:forEach var="vo" items="${ReceivingList}">
	<tr>
		<td>${vo.receivingshipmentNo }</td>
		<td>${vo.transactionType }</td>
		<td>${vo.createdAt }</td>
		<td>${vo.status }</td>
		<td>${vo.productId }</td>
		<td>${vo.name }</td>
		<td>${vo.description }</td>
		<td>${vo.changeQuantity }</td>
		<td>${vo.transactionUnit }</td>
		<td>${vo.description }</td>
		<td>${vo.companyName }</td>
	</tr>
	</c:forEach>
</table>



</body>
</html>