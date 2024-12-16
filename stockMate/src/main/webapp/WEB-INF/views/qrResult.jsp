<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>QR 코드 결과</title>
</head>
<body>
    <h1>QR 코드 스캔 결과</h1>
    <p><b>QR 내용:</b> ${qrData.content}</p>
    <p><b>상세 정보:</b> ${qrData.details}</p>
    <button onclick="window.history.back()">다시 스캔</button>
</body>
</html>