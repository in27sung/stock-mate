<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>메인 페이지</title>
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

body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-color: #333;
	color: white;
}

.header {
	background-color: #222;
	padding: 20px;
	text-align: center;
}

.header img {
	width: 80px;
}

.nav {
	display: flex;
	justify-content: center;
	background-color: #444;
	padding: 10px 0;
}

.nav a {
	color: white;
	text-decoration: none;
	margin: 0 15px;
	padding: 10px 20px;
	background-color: #007BFF;
	border-radius: 5px;
	font-weight: bold;
}

.nav a:hover {
	background-color: #0056b3;
}

.content {
	padding: 20px;
	text-align: center;
}

.content h2 {
	margin-bottom: 20px;
}

.content img {
	max-width: 100%;
	height: auto;
	margin-bottom: 20px;
}

.content .row {
	display: flex;
	justify-content: space-around;
	margin-top: 20px;
}

.content .column {
	flex: 1;
	margin: 0 10px;
}

.content .column img {
	width: 100%;
	border-radius: 10px;
}

.footer {
	background-color: #222;
	color: #ccc;
	padding: 20px;
	text-align: center;
	font-size: 12px;
}
</style>
</head>
<body>
    <!-- Header -->
    <div class="header">
        <img src="https://via.placeholder.com/80" alt="About Us Logo">
        <h1>최초 메인 페이지 - 데모</h1>
    </div>
  
  	<!-- 에러 메시지가 있을 경우 상단 배너로 표시 -->
	  <c:if test="${not empty errorMessage}">
		  <div class="error-banner">${errorMessage}</div>
	  </c:if>

    <!-- Navigation -->
    <div class="nav">
        <a href="user/signup">회원가입</a>
        <a href="user/signin">로그인</a>
        <a href="user/howtouse">대시보드 사용법</a>
        <a href="user/consultation">상담문의</a>
        <a href="intro">회사소개</a>
    </div>

    <!-- Content -->
    <div class="content">
        <h2>고퀄리티 교회 현수막을 제작합니다.</h2>
        <p>현수막은 다양한 곳에 홍보물로 많이 사용되고 있습니다.<br>
        홀리라이프 현수막을 통해 고퀄리티 디자인을 경험해보세요.</p>

        <div class="row">
            <div class="column">
                <img src="https://via.placeholder.com/300x200" alt="Example Banner">
            </div>
            <div class="column">
                <img src="https://via.placeholder.com/300x200" alt="World Map">
            </div>
        </div>

        <div>
            <h3>대시보드 사용법</h3>
            <ul>
                <li>gif 영상</li>
                <li>jpg 이미지</li>
            </ul>
        </div>
    </div>

    <!-- Footer -->
    <div class="footer">
        회사 정보 - 사업자 번호, 연락처 등 유의 내용
    </div>
</body>
</html>