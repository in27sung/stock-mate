<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>
<body>
	<h1>상품 등록</h1>

	<!-- 상품 등록 폼 -->
	<form method="post" action="/product/register">
		
        <!-- 카테고리 선택 필드 -->
        <label for="categoryId">카테고리 선택:</label> 
        <select id="categoryId" name="categoryId" required>
            <option value="">-- 카테고리 선택 --</option> <!-- 기본값 옵션 추가 -->
            <c:forEach var="cat" items="${categoryList}">
                <option value="${cat.categoryId}">${cat.categoryName}</option>
            </c:forEach>
        </select><br><br>

		<!-- 상품명 필드 -->
		<label for="name">상품명:</label> 
		<input type="text" id="name" name="name" required autofocus placeholder="예: 무선 마우스" /><br>
		<br>
		
		<!-- 바코드와 자동 생성 버튼 -->
		<label for="barcode">바코드:</label> 
		<input type="text" id="barcode" name="barcode" placeholder="예: 1234567890123" pattern="\d{13}"/>
		<button type="button" id="generateBarcode">자동 생성</button><br>
		<br>

		<!-- 기본 단위 필드: 예를 들어 '개', '박스' 등을 placeholder -->
		<label for="baseUnit">기본 단위:</label> 
		<input type="text" id="baseUnit" name="baseUnit" required placeholder="예: 박스" /><br>
		<br>

		<!-- 세트 크기 필드: number로 입력받고 예시 표시 -->
		<label for="setSize">세트 크기:</label> 
		<input type="number" id="setSize" name="setSize" required placeholder="예: 10 (한 세트 당 10개)" /><br>
		<br>

		<!-- 기본 단가 필드: 숫자/가격에 맞는 placeholder -->
		<label for="price">기본 단가:</label> 
		<input type="number" id="price" name="price" required placeholder="예: 15000" /><br>
		<br>
		
		<!-- 상품 설명 필드: textarea에 placeholder -->
		<label for="description">상품 설명:</label><br>
		<textarea id="description" name="description"
			placeholder="상품에 대한 추가 설명을 입력하세요." rows="5" cols="50"></textarea>
		<br>
		<br>

		<!-- 등록 버튼 -->
		<button type="submit">상품 등록</button>
	</form>
	
    <!-- JavaScript -->
    <script>
        // 바코드 자동 생성 버튼 동작
        document.getElementById('generateBarcode').addEventListener('click', function() {
            const randomBarcode = String(Math.floor(1000000000000 + Math.random() * 9000000000000));
            document.getElementById('barcode').value = randomBarcode;
        });
    </script>
</body>
</html>