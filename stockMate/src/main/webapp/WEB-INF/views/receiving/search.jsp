<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>입고 내역</title>
<style>
/* 전체 페이지 스타일 */
/* 페이지네이션 컨테이너 스타일 */
.dataTables_paginate {
    text-align: center;
    margin-top: 20px;
}

/* 페이지네이션 목록 스타일 */
.dataTables_paginate ul.pagination {
    display: inline-flex;
    padding-left: 0;
    list-style: none;
    margin: 0;
}

/* 페이지 번호 버튼 스타일 */
.dataTables_paginate ul.pagination .paginate_button {
    padding: 8px 16px;
    margin: 0 4px;
    border: 1px solid #ddd;
    border-radius: 4px;
    background-color: #fff;
    color: #3498db;
    font-size: 14px;
    cursor: pointer;
    transition: background-color 0.3s ease, border-color 0.3s ease;
}

/* 활성 페이지 번호 스타일 */
.dataTables_paginate ul.pagination .paginate_button.active {
    background-color: #3498db;
    color: #fff;
    border-color: #3498db;
}

/* 비활성화된(이전/다음) 버튼 스타일 */
.dataTables_paginate ul.pagination .paginate_button.disabled {
    background-color: #f4f4f4;
    color: #ccc;
    cursor: not-allowed;
    border-color: #ddd;
}

/* 이전 및 다음 버튼 스타일 */
.dataTables_paginate ul.pagination .paginate_button.previous,
.dataTables_paginate ul.pagination .paginate_button.next {
    font-weight: bold;
}

/* 이전/다음 버튼의 hover 효과 */
.dataTables_paginate ul.pagination .paginate_button:hover {
    background-color: #2980b9;
    color: white;
    border-color: #2980b9;
}

/* 이전/다음 버튼 활성화 시 색상 */
.dataTables_paginate ul.pagination .paginate_button.previous:not(.disabled),
.dataTables_paginate ul.pagination .paginate_button.next:not(.disabled) {
    background-color: #3498db;
    color: #fff;
}

/* 페이지 번호 클릭 시 효과 */
.dataTables_paginate ul.pagination .paginate_button:focus,
.dataTables_paginate ul.pagination .paginate_button:hover {
    outline: none;
    background-color: #2980b9;
    color: white;
}

/* 반응형 스타일 - 모바일에서 페이지네이션 크기 조정 */
@media (max-width: 768px) {
    .dataTables_paginate ul.pagination .paginate_button {
        font-size: 12px;
        padding: 6px 12px;
    }
}


body {
	font-family: 'Arial', sans-serif;
	background-color: #f4f4f9;
	margin: 0;
	padding: 0;
	color: #333;
}

/* 타이틀 스타일 */
h1 {
	text-align: center;
	margin-top: 30px;
	color: #2c3e50;
	font-size: 36px;
	font-weight: 700;
	text-transform: uppercase;
	letter-spacing: 1.5px;
}

h1 a {
	text-decoration: none;
	color: #3498db;
	font-size: 20px;
	display: block;
	margin-top: 10px;
	text-align: center;
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

h1 a:hover {
	color: #2980b9;
}

/* 검색 폼 스타일 */
form {
	width: 80%;
	max-width: 1000px;
	margin: 30px auto;
	padding: 20px;
	background-color: #fff;
	border-radius: 8px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

label {
	margin-right: 10px;
	font-size: 14px;
}

input[type="date"], input[type="text"] {
	padding: 8px;
	margin-right: 20px;
	font-size: 14px;
	border: 1px solid #ddd;
	border-radius: 4px;
	width: 180px;
}

button {
	padding: 8px 15px;
	background-color: #3498db;
	color: #fff;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 14px;
	transition: background-color 0.3s ease;
}

button:hover {
	background-color: #2980b9;
}

/* 테이블 스타일 */
table {
	width: 90%;
	margin: 40px auto;
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
	background-color: #f9f9f9;
}

tr:hover {
	background-color: #ecf0f1;
}

/* 결과가 없을 경우 메시지 스타일 */
.no-results {
	text-align: center;
	font-size: 18px;
	color: #e74c3c;
	margin-top: 30px;
}

/* 반응형 스타일 */
@media ( max-width : 768px) {
	form {
		width: 95%;
	}
	table {
		width: 100%;
	}
	th, td {
		font-size: 12px;
	}
	label {
		font-size: 12px;
	}
	input[type="date"], input[type="text"] {
		font-size: 12px;
		padding: 6px;
		width: 150px;
	}
	button {
		font-size: 12px;
		padding: 6px 12px;
	}
	
}
</style>
</head>
<body>

	<h1>입고 내역</h1>
	<a href="/receiving/main">입고 메인</a>
	<a href="/receiving/scan">실시간 입고</a>
	<form action="/receiving/insert3" method="POST">
    	<input type="submit" value="새로고침">
	</form>

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
		<button type="button" onclick="resetForm()">초기화</button>
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
				<th>수량 단위</th>
				<th>제품 단가</th>
				<th>창고 위치</th>
				<th>작업 메모</th>
			</tr>
			<c:forEach var="vo" items="${ReceivingList }">
				<tr>
					<td>${vo.receivingShipmentNo }</td>
					<td>${vo.transactionType }</td>
					<td><fmt:formatDate value="${vo.createdAt}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${vo.status}</td>
					<td>${vo.productId }</td>
					<td>${vo.productName }</td>
					<td>${vo.productDescription }</td>
					<td>${vo.changeQuantity }</td>
					<td>${vo.transactionUnit }</td>
					<td>${vo.warehouseId }</td>
					<td>${vo.transactionUnit }</td>
					<td>${vo.productPrice }</td>
					<td>${vo.memo }</td>
				</tr>
			</c:forEach>
		</table>

	</form>

	<c:choose>
		<c:when test="${empty ReceivingList}">
        검색 결과가 없습니다.
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