<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 변경</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/passwordStyle.css' />">
    
    <script>
        function redirectToDashboard(event) {
            event.preventDefault(); // Form submission 막기
            window.location.href = '/dashboard';
        }
    </script>
</head>
<body>
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
        <h1>비밀번호 변경</h1>
<form action="/user/changepassword2" method="post">
    <div>
        <label for="newPassword">변경할 비밀번호</label>
        <input type="password" id="newPassword" name="newPassword" placeholder="변경할 비밀번호 입력" required>
    </div>
    <div>
        <label for="confirmPassword">한 번 더 입력</label>
        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="한번 더 입력" required>
    </div>
    <button type="submit">정보 변경 하기</button>
</form>


    </div>

    <div class="footer">
        회사 정보 - 사업자 번호, 연락처 등 유의 내용
    </div>
</body>
</html>
