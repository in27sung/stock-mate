<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세정보</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style type="text/css">
.error-banner {
	width: 100%;
	background-color: #FCE4E4;
	color: #D32F2F;
	text-align: center;
	padding: 10px 0;
	font-size: 14px;
	font-weight: 500;
	position: absolute;
	top: 0;
	left: 0;
	z-index: 1000;
	border-bottom: 1px solid #F5C6C6;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.success-banner {
	width: 100%;
	background-color: #E6F4EA;
	color: #2E7D32;
	text-align: center;
	padding: 10px 0;
	font-size: 14px;
	font-weight: 500;
	position: absolute;
	top: 0;
	left: 0;
	z-index: 1000;
	border-bottom: 1px solid #C8E6C9;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* 공통 버튼 스타일 */
.btn {
    display: inline-block;
    padding: 10px 20px;
    background-color: #4CAF50; /* 버튼 배경색: 녹색 */
    color: #FFFFFF; /* 텍스트 색상: 흰색 */
    text-decoration: none; /* 링크 밑줄 제거 */
    border-radius: 5px; /* 둥근 모서리 */
    font-size: 14px; /* 폰트 크기 */
    font-weight: bold; /* 굵은 텍스트 */
    text-align: center; /* 텍스트 가운데 정렬 */
    border: none; /* 버튼 테두리 제거 */
    cursor: pointer; /* 마우스 커서 포인터로 변경 */
    transition: background-color 0.3s ease; /* 호버 애니메이션 */
}

.btn:hover {
    background-color: #388E3C; /* 호버 시 버튼 배경색 */
    color: #FFFFFF; /* 호버 시 텍스트 색상 */
}

	
</style>
</head>
<body>
	<h1>상품 상세정보</h1>
	
	<!-- 에러 메시지가 있을 경우 상단 배너로 표시 -->
	<c:if test="${not empty errorMessage}">
		<div class="error-banner">${errorMessage}</div>
	</c:if>
	
	<!-- 성공 메시지가 있을 경우 상단 배너로 표시 -->
	<c:if test="${not empty successMessage}">
		<div class="success-banner">${successMessage}</div>
	</c:if>
	    <a href="/product/list">상품 리스트</a>
	<!-- 상품 상세 페이지 -->
	<c:if test="${product != null}">
	    <p>카테고리명: ${categoryName}</p>
	    <p>상품명: ${product.productName}</p>
	    <p>바코드: ${product.productBarcode}</p>
		<p>기본 단위: ${product.baseUnit}</p>
		<p>세트 크기: ${product.setSize}</p>
		<p>가격: ${product.productPrice}</p>
		<p>설명: ${product.productDescription}</p>
	    
		<!-- QR 코드 생성 버튼 -->
		<c:if test="${product.productQrCodePath == null}">
		    <form action="/product/generateQR" method="get" style="display:inline;">
		        <input type="hidden" name="productId" value="${product.productId}">
		        <button type="submit" class="btn">QR 코드 생성</button>
		    </form>
		</c:if>
		
		<!-- QR 코드 다운로드 버튼 -->
		<c:if test="${product.productQrCodePath != null}">
		    <form action="/product/downloadQr" method="get" style="display:inline;">
		        <input type="hidden" name="productId" value="${product.productId}">
		        <button type="submit" class="btn">QR 코드 다운로드</button>
		    </form>
		</c:if>
		
		<p>등록일: ${product.createdAt}</p>
		<!-- 수정일이 등록일과 다를 경우에만 표시 -->
	    <c:if test="${product.updatedAt != product.createdAt}">
	        <p>수정일: ${product.updatedAt}</p>
	    </c:if>

		
	</c:if>

	<c:if test="${product == null}">
		<p>해당 상품 정보가 없습니다.</p>
	</c:if>
	
</body>
</html>