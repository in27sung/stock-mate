<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   	<link rel="stylesheet" href="<c:url value='/resources/css/passwordStyle.css' />">
    <title>비밀번호 찾기</title>
    <style>
        .alert-box {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.5);
            text-align: center;
            width: 300px;
        }
        .alert-box.active {
            display: block;
        }
        .alert-box button {
            margin-top: 20px;
            background-color: #007BFF;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        .alert-box button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <!-- Container -->
    <div class="container">
        <div class="back-button">
            <a href="/">&larr; 뒤로 가기</a>
        </div>
        <h1>비밀번호 찾기</h1>
        <form action="findpassword" method="post">
            <input type="email" id="email" name="email" placeholder="이메일" required>
            <input type="text" id="name" name="name" placeholder="이름" required>
            <button type="submit">비밀번호 찾기</button>
            
        </form>
    </div>
    
     <!-- 실패 시 errorMessage를 알림창으로 표시 -->
     <script type="text/javascript">
     
	    <c:if test="${not empty errorMessage}">
	        alert("${errorMessage}");
	    </c:if>
	    
	   </script>
    
    
    <!-- Footer -->
    <div class="footer">
        회사 정보 - 사업자 번호, 연락처 등 유의 내용
    </div>

</body>
</html>
