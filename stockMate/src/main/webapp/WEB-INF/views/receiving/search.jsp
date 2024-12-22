<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>입고 내역</title>
<link rel="stylesheet" type="text/css" href="/resources/css/receivingHistoryStyle.css">
</head>
<body>

	<h1>입고 내역</h1>
	<nav>
	<a href="/receiving/main">입고 메인</a><br><br>
	<form action="/receiving/insert3" method="POST">
    	<input type="submit" value="새로고침">
	</form>
	</nav>

	<!-- 기간별 검색 및 키워드 검색 폼 -->
	<form action="/receiving/search" method="get"
		onsubmit="return validateForm()" id="searchForm">
		<label for="startDate">시작 날짜:</label> <input type="date"
			id="startDate" name="startDate" value="${param.startDate}"> <label
			for="endDate">종료 날짜:</label> <input type="date" id="endDate"
			name="endDate" value="${param.endDate}"> <label for="keyword">상품명:</label>
		<input type="text" id="keyword" name="keyword"
			value="${param.keyword}">

		<button type="submit">검색</button>
		<button type="button" onclick="resetForm()">검색 초기화</button>
		<script>
			// 폼 초기화 함수
			function resetForm() {
				document.getElementById('searchForm').reset(); // 폼의 모든 입력값을 초기화
				// 날짜 input 초기화 (브라우저에서 기본값으로 설정됨)
				document.getElementById('startDate').value = '';
				document.getElementById('endDate').value = '';
				document.getElementById('keyword').value = '';
			}
		</script>



		<!-- 입고 내역 히스토리 -->
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
				<th>제품 단가</th>
				<th>창고 위치</th>
				<th>작업 메모</th>
			</tr>
			<c:forEach var="vo" items="${ReceivingList }">
				<tr>
					<td><a href="/receiving/scan?receivingShipmentNo=${vo.receivingShipmentNo}&orderItemId=${vo.orderItemId}" id="list">
			                ${vo.receivingShipmentNo}
			            </a></td>
					<td>     
		                 <c:choose>
		                    <c:when test="${vo.transactionType == 'INBOUND'}">입고</c:when>
		                    <c:when test="${vo.transactionType == 'OUTBOUND'}">출고</c:when>
		                    <c:otherwise>${vo.transactionType}</c:otherwise>
		                </c:choose>
		            </td>
					<td><fmt:formatDate value="${vo.createdAt}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
			            <c:choose>
			                <c:when test="${vo.status == 'PENDING'}">
			                    대기중
			                </c:when>
			                <c:when test="${vo.status == 'COMPLETED'}">
			                    완료됨
			                </c:when>
			                <c:otherwise>
			                    ${vo.status}
			                </c:otherwise>
			            </c:choose>
			        </td>
					<td>${vo.productId }</td>
					<td>${vo.productName }</td>
					<td>${vo.productDescription }</td>
					<td>${vo.changeQuantity }</td>
					<td>${vo.productPrice }</td>
					<td>${vo.warehouseId }</td>
					<td>${vo.memo }</td>
				</tr>
			</c:forEach>
		</table>

	</form>

	<c:choose>
		<c:when test="${empty ReceivingList}">
        <h1>검색 결과가 없습니다.</h1>
    </c:when>
	</c:choose>

	<div class="dataTables_paginate paging_simple_numbers" id="example2_paginate">
	    <ul class="pagination">
	        <c:if test="${pageVO.prev}">
	            <li class="paginate_button previous">
	                <a href="/receiving/history?page=${pageVO.startPage - 10}&pageSize=${pageVO.cri.pageSize}&startDate=${param.startDate}&endDate=${param.endDate}&keyword=${param.keyword}" aria-controls="example2" data-dt-idx="0" tabindex="0">Previous</a>
	            </li>
	        </c:if>
	        <c:forEach var="i" begin="${pageVO.startPage}" end="${pageVO.endPage}">
	            <li class="paginate_button ${(i == pageVO.cri.page) ? 'active' : ''}">
	                <a href="/receiving/history?page=${i}&pageSize=${pageVO.cri.pageSize}&startDate=${param.startDate}&endDate=${param.endDate}&keyword=${param.keyword}" aria-controls="example2" data-dt-idx="1" tabindex="0">${i}</a>
	            </li>
	        </c:forEach>
	        <c:if test="${pageVO.next}">
	            <li class="paginate_button">
	                <a href="/receiving/history?page=${pageVO.startPage + 1}&pageSize=${pageVO.cri.pageSize}&startDate=${param.startDate}&endDate=${param.endDate}&keyword=${param.keyword}" aria-controls="example2" data-dt-idx="7" tabindex="0">Next</a>
	            </li>
	        </c:if>
	    </ul>
	</div>

</body>
</html>