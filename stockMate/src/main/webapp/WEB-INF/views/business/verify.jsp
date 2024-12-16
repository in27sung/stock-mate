<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비즈니스 인증</title>
<style>

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
</style>
</head>
<body>
    <h1>비즈니스 인증</h1>

	<%-- 에러 메시지 표시 --%>
	<c:if test="${not empty errorMessage}">
	    <div class="error-banner">${errorMessage}</div>
	</c:if>
	
	<%-- 성공 메시지 표시 --%>
	<c:if test="${not empty successMessage}">
		<div class="success-banner">${successMessage}</div>
	</c:if>

    <form action="/business/verify" method="post">
        <label for="businessNumber">사업자 등록 번호:</label>
        <input type="text" id="businessNumber" name="businessNumber" required placeholder="사업자 등록 번호">
        <br><br>
        <label for="companyName">회사 이름:</label>
        <input type="text" id="companyName" name="companyName" required placeholder="회사 이름">
        <br><br>
        <button type="submit">비즈니스 인증</button>
    </form>
</body>
</html>