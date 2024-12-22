<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>상품 등록</title>
<link rel="stylesheet" href="<c:url value='/resources/css/registerStyle2.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/toastStyle.css' />">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
<script src="<c:url value='/resources/scripts/toast.js' />"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<div class="container fade-in">
		<button class="dashboard-btn" onclick="location.href='/dashboard';">대시보드</button>
		<h1>상품 등록</h1>
		<form method="post" action="/product/register">
			<!-- 카테고리 선택 -->
			<label for="categoryId">카테고리 선택</label>
			<div class="category-group">
				<select id="categoryId" name="categoryId" required>
					<option value="">-- 카테고리 선택 --</option>
					<c:forEach var="cat" items="${categoryList}">
						<option value="${cat.categoryId}">${cat.categoryName}</option>
					</c:forEach>
				</select> 
				<a href="/category/register" class="category-link">새로운 카테고리 등록</a>
			</div>

			<!-- 상품명 -->
			<label for="productName">상품명</label> 
			<input type="text" id="productName" name="productName" required placeholder="예: 무선 마우스">

			<!-- 바코드 -->
			<label for="productBarcode">바코드</label>
			<div class="barcode-group">
				<input type="text" id="productBarcode" name="productBarcode"
					placeholder="예: 1234567890123" pattern="\d{13}">
				<button type="button" id="generateBarcode" class="barcode-btn">자동
					생성</button>
			</div>

			<!-- 기본 단위 -->
			<label for="baseUnit">기본 단위</label> <input type="text" id="baseUnit"
				name="baseUnit" required placeholder="예: 박스">

			<!-- 세트 크기 -->
			<label for="setSize">세트 크기</label> <input type="number" id="setSize"
				name="setSize" required placeholder="예: 10 (한 세트 당 10개)">

			<!-- 기본 단가 -->
			<label for="productPrice">기본 단가</label> <input type="number"
				id="productPrice" name="productPrice" required
				placeholder="예: 15000">

			<!-- 상품 설명 -->
			<label for="productDescription">상품 설명</label>
			<textarea id="productDescription" name="productDescription" rows="4"
				placeholder="상품에 대한 추가 설명을 입력하세요."></textarea>

			<!-- 버튼 그룹 -->
			<div class="button-group">
				<button type="submit" class="primary-button">등록</button>
				<button type="reset" class="secondary-button">초기화</button>
			</div>
		</form>
	</div>
	<script>
	    function generateEAN13Barcode() {
	        const countryCode = [8, 8, 0];
	        const randomDigits = Array.from({ length: 9 }, () => Math.floor(Math.random() * 10));
	        const digits = countryCode.concat(randomDigits);
	        const checkDigit = calculateCheckDigit(digits);
	        return digits.join('') + checkDigit;
	    }

	    function calculateCheckDigit(digits) {
	        let sum = 0;
	        digits.forEach((digit, index) => {
	            const weight = index % 2 === 0 ? 1 : 3;
	            sum += digit * weight;
	        });
	        const remainder = sum % 10;
	        return remainder === 0 ? 0 : 10 - remainder;
	    }

	    document.getElementById('generateBarcode').addEventListener('click', function () {
	        const randomBarcode = generateEAN13Barcode();
	        document.getElementById('productBarcode').value = randomBarcode;
	    });
	    
	    document.addEventListener("DOMContentLoaded", () => {
	        // 서버에서 전달된 토스트 메시지 읽기
	        const toastMessage = "${toastMessage}";
	        const toastType = "${toastType}";

	        // 메시지가 존재하면 토스트 표시
	        if (toastMessage) {
	            showToast(toastMessage, toastType);
	        }
	    });
	</script>
</body>
</html>
