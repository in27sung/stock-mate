<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>비밀번호 변경</title>
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
        /* Modal Style */
        .modal {
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
        .modal.active {
            display: block;
        }
        .modal button {
            background-color: #007BFF;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        .modal button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <!-- Container -->
    <div class="container">
        <div class="back-button">
            <a href="dashboard.jsp">&larr; 뒤로 가기</a>
        </div>
        <h1>비밀번호 변경</h1>
        <form id="passwordForm">
            <input type="email" id="email" name="email" placeholder="현재 이메일" required>
            <input type="password" id="newPassword" name="newPassword" placeholder="변경할 비밀번호" required>
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="한 번 더 입력" required>
            <button type="button" onclick="validatePassword()">확인</button>
        </form>
    </div>

    <!-- Modal -->
    <div id="successModal" class="modal">
        <p>비밀번호 변경 완료되었습니다.</p>
        <button onclick="closeModal()">닫기</button>
    </div>

    <!-- Footer -->
    <div class="footer">
        회사 정보 - 사업자 번호, 연락처 등 유의 내용
    </div>

    <script>
        function validatePassword() {
            const email = document.getElementById('email').value;
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (!email) {
                alert('이메일을 입력하세요.');
                return;
            }

            if (newPassword !== confirmPassword) {
                alert('비밀번호가 일치하지 않습니다. 다시 확인해주세요.');
                return;
            }

            if (newPassword.length < 6) {
                alert('비밀번호는 최소 6자리 이상이어야 합니다.');
                return;
            }

            // 비밀번호가 유효하면 모달 표시
            showModal();
        }

        function showModal() {
            const modal = document.getElementById('successModal');
            modal.classList.add('active');
        }

        function closeModal() {
            const modal = document.getElementById('successModal');
            modal.classList.remove('active');
            // 실제 서버로 데이터 전송 (필요 시 주석 해제)
            // document.getElementById('passwordForm').submit();
        }
    </script>
</body>
</html>
