<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상담문의</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
        }

        .content {
            width: 60%;
            margin-bottom: 20px;
            background-color: #005bac;
            color: white;
            padding: 10px;
            border-radius: 8px;
        }

        .content h1 {
            color: white;
            text-align: center;
        }

        .content h3 {
            text-align: center;
        }

        .content p {
            margin: 8px 0;
            line-height: 1.4;
            text-align: center;
        }

        .form-container {
            width: 25%;
            background-color: #005bac;
            color: white;
            padding: 10px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .form-container h2 {
            color: white;
            text-align: center;
            margin-bottom: 10px;
        }

        .form-container input, .form-container textarea, .form-container button {
            width: 96%;
            margin-bottom: 8px;
            padding: 8px;
            border: none;
            border-radius: 5px;
        }

        .form-container input[type="radio"] {
            width: auto;
        }

        .form-container button {
            background-color: #ffcc00;
            color: black;
            font-weight: bold;
            cursor: pointer;
        }

        .form-container button:hover {
            background-color: #ffaa00;
        }

   .back-button {
    display: flex;
    justify-content: center; /* 수평 중앙 정렬 */
    margin: 40px auto; /* 위아래 여백 추가 */
}

.back-button a {
    text-decoration: none;
    padding: 15px 30px;
    background-color: #007BFF;
    color: #fff;
    border-radius: 10px;
    font-size: 20px;
    font-weight: bold;
    font-family: 'Roboto', sans-serif;
    transition: background-color 0.3s ease, transform 0.3s ease;
}

.back-button a:hover {
    background-color: #0056b3;
    transform: scale(1.05);
}


        .footer {
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- 메시지 알림 -->
        <% 
            String message = (String) request.getAttribute("message");
            String error = (String) request.getAttribute("error");
        %>
        <% if (message != null) { %>
            <script>
                alert("<%= message %>");
            </script>
        <% } %>
        <% if (error != null) { %>
            <script>
                alert("<%= error %>");
            </script>
        <% } %>

        <!-- 상단 내용 -->
        <div class="content">
            <h1>상담을 통해 궁금한 점을 즉시 해결하세요.</h1>

            <h3>궁금한 점을 빠르게 확인할 수 있습니다.</h3>
            <p>
                • 연락처를 남겨주시면 영업 시간 기준 1일~2일 이내에 연락 드립니다.<br>
                • 전문가와의 상담을 통해 문의사항을 빠르고 정확하게 해결하세요.
            </p>

            <h3>효과적인 시스템을 느껴보세요</h3>
            <p>
                • 우리 회사의 업무에 어떻게 적용할 수 있을지 확인할 수 있습니다.<br>
                • 프로그램 도입 여부를 빠르게 판단할 수 있습니다.
            </p>
        </div>

        <!-- 상담 신청 폼 -->
        <div class="form-container">
            <h2>상담문의</h2>
            <form action="/" method="post">
                <input type="text" name="company" placeholder="회사명" required>
                <input type="text" name="name" placeholder="이름" required>
                <input type="text" name="contact" placeholder="연락처" required>
                <input type="email" name="email" placeholder="E-mail" required>
                <div>
                    <label><input type="radio" name="contactMethod" value="전화" required> 전화</label>
                    <label><input type="radio" name="contactMethod" value="이메일"> 이메일</label>
                </div>
                <textarea name="inquiry" placeholder="문의내용" rows="5" required></textarea>
                <div>
                    <input type="checkbox" name="privacyConsent" required> 개인정보 수집에 동의함 (필수)
                </div>
                <button type="submit">상담신청</button>
            </form>

            <div class="footer">
                궁금한 점이 있다면 지금 확인하세요!<br>
                <strong>1588-2929</strong>
            </div>
        </div>
          <div class="back-button">
            <a href="/">뒤로 돌아가기</a>
   			</div>
    </div>
</body>
</html>
