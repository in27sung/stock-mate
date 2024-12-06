<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	<c:forEach var="vo" items="${ReceivingList }">
	<tr>
		<td>${vo.receiving_shipment_no }</td>
		<td>${vo.transaction_type }</td>
		<td><fmt:formatDate value="${vo.created_at}" pattern="yyyy-MM-dd" /></td>
		<td>${vo.status }</td>
		<td>${vo.product_id }</td>
		<td>${vo.name }</td>
		<td>${vo.description }</td>
		<td>${vo.change_quantity }</td>
		<td>${vo.transaction_unit }</td>
		<td>${vo.memo }</td>
		<td>${vo.company_name }</td>
	</tr>
	</c:forEach>
</table>



</body>
</html>