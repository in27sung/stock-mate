<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>비밀번호 확인</title>
</head>
<body>
    <script>
        // 서버에서 전달받은 비밀번호를 알림창으로 표시
        alert("${alertMessage}");

        // 알림창이 닫히면 메인 페이지로 리다이렉트
        window.location.href = "/";
    </script>
</body>
</html>
