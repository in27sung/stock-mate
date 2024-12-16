<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 찾기</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
        }
        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            background-color: #fff;
            margin: 0 15%;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 30px;
            text-align: center;
        }
        .back-button {
            text-align: left;
            margin-bottom: 20px;
        }
        .back-button a {
            text-decoration: none;
            color: #007BFF;
            font-size: 18px;
        }
        .back-button a:hover {
            text-decoration: underline;
        }
        h1 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }
        input {
            width: 80%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }
        button {
            width: 80%;
            padding: 10px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            margin: 5px 0;
        }
        button:hover {
            background-color: #0056b3;
        }
        .footer {
            background-color: #222;
            color: #ccc;
            text-align: center;
            padding: 10px;
            position: fixed;
            bottom: 0;
            width: 100%;
        }
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
            <a href="/user/main">&larr; 뒤로 가기</a>
        </div>
        <h1>비밀번호 찾기</h1>
        <form id="passwordForm">
            <input type="email" id="email" name="email" placeholder="이메일" required>
            <input type="text" id="name" name="name" placeholder="이름" required>
            <button type="button" onclick="showSuccess()">비밀번호 찾기</button>
            <button type="button" onclick="showError()">비밀번호 찾기 - 정보 오류</button>
        </form>
    </div>

    <!-- Success Alert -->
    <div id="successAlert" class="alert-box">
        <p>비밀번호 확인<br>1234</p>
        <button onclick="closeAlert()">확인</button>
    </div>

    <!-- Error Alert -->
    <div id="errorAlert" class="alert-box">
        <p>이메일 또는 이름이 존재하지 않습니다.<br>다시 한 번 확인해주세요.</p>
        <button onclick="closeAlert()">확인</button>
    </div>

    <!-- Footer -->
    <div class="footer">
        회사 정보 - 사업자 번호, 연락처 등 유의 내용
    </div>

    <script>
        function showSuccess() {
            const alert = document.getElementById('successAlert');
            alert.classList.add('active');
        }

        function showError() {
            const alert = document.getElementById('errorAlert');
            alert.classList.add('active');
        }

        function closeAlert() {
            const successAlert = document.getElementById('successAlert');
            const errorAlert = document.getElementById('errorAlert');
            successAlert.classList.remove('active');
            errorAlert.classList.remove('active');
        }
    </script>
</body>
</html>
