<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 정보 수정</title>
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
        .info {
            width: 80%;
            margin-bottom: 20px;
        }
        .info input {
            width: 100%;
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
    </style>
</head>
<body>
    <!-- Container -->
    <div class="container">
        <div class="back-button">
            <a href="/user/dashboard">&larr; 뒤로 가기</a>
        </div>
        <h1>홍길동님의 회원정보</h1>
        <form id="editInfoForm" action="editinfo2" method="post" onsubmit="return handleFormSubmit(event);">
            <div class="info">
                <label for="email">이메일</label>
                <input type="email" id="email" name="email" value="itwill@naver.com" required>
                <label for="name">이름</label>
                <input type="text" id="name" name="name" value="홍길동" required>
                <label for="phone">전화번호</label>
                <input type="text" id="phone" name="phone" value="010-1234-1234" required>
            </div>
            <button type="submit">저장하기</button>
        </form>
    </div>

    <!-- Footer -->
    <div class="footer">
        회사 정보 - 사업자 번호, 연락처 등 유의 내용
    </div>

    <script>
    function handleFormSubmit(event) {
        event.preventDefault(); // 기본 제출 방지
        const form = event.target;

        // 폼 데이터 서버로 전송
        fetch(form.action, {
            method: form.method,
            body: new FormData(form),
        })
        .then(response => {
            if (response.ok) {
                // 성공 시 대시보드로 이동
                window.location.href = "/user/dashboard";
            } else {
                alert("저장 실패: 다시 시도해주세요.");
            }
        })
        .catch(error => {
            console.error("저장 중 오류 발생:", error);
            alert("오류가 발생했습니다. 관리자에게 문의해주세요.");
        });
    }
    </script>
</body>
</html>
