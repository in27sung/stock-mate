<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 확인</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/passwordStyle.css' />">
</head>
<body>
<!--  -->
 	<script type="text/javascript"> 
     
	    <c:if test="${not empty errorMessage}">
	        alert("${errorMessage}");
	    </c:if>
	    
	   </script>
	   
    <!-- Container -->
    <div class="container">
        <div class="back-button">
            <a href="/dashboard">
                <svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 0 24 24" width="24">
                    <path d="M0 0h24v24H0z" fill="none"/>
                    <path d="M15.41 7.41L14 6l-6 6 6 6 1.41-1.41L10.83 12z"/>
                </svg>
                뒤로 가기
            </a>
        </div>
        <h1>비밀번호를 입력하세요</h1>
        <form method="post">
            <input type="password" name="password" placeholder="비밀번호" required>
            <button type="submit">비밀번호 변경하기</button>
        </form>
    </div>

    <!-- Footer -->
    <div class="footer">
        회사 정보 - 사업자 번호, 연락처 등 유의 내용
    </div>
</body>
</html>
