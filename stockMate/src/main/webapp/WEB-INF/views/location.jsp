<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>창고 소개</title>
<style>
/* Reset */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f9f9f9;
    color: #333;
    line-height: 1.6;
}

/* Header Section */
.header {
    position: relative;
    height: 400px;
    background: url('resources/img/9950253.jpg') no-repeat center center/cover;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    color: white;
}

.header h1 {
    font-size: 3rem;
    font-weight: bold;
    text-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
}

/* Section Title */
.section-title {
    text-align: center;
    margin: 40px 0 20px;
    font-size: 2rem;
    color: #2a5298;
    font-weight: bold;
}

/* Content Layout */
.container {
    width: 90%;
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: space-between;
}

.container .image-box {
    flex: 1 1 45%;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.container .image-box img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.container .text-box {
    flex: 1 1 45%;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.container .text-box h2 {
    font-size: 1.8rem;
    margin-bottom: 10px;
    color: #2a5298;
}

.container .text-box p {
    font-size: 1.1rem;
    color: #555;
    line-height: 1.8;
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

/* Footer */
.footer {
    background-color: #333;
    color: white;
    text-align: center;
    padding: 20px;
    font-size: 0.9rem;
}

.footer a {
    color: #2a5298;
    text-decoration: none;
}

.footer a:hover {
    text-decoration: underline;
}



</style>
</head>
<body>
    <!-- Header Section -->
    <div class="header">
        <h1>창고 소개</h1>
    </div>

    <!-- Section Title -->
    <div class="section-title">창고 개요</div>

    <!-- Content Section -->
    <div class="container">
        <!-- Image Box -->
        <div class="image-box">
            <img src="resources/img/9950253.jpg" alt="창고 이미지">
        </div>

        <!-- Text Box -->
        <div class="text-box">
            <h2>용인창고</h2>
            <p>
                경기 용인시 처인구 양지면 주북로 105-5 본 창고는 연면적 11만 5천여 평 규모로,
                넓은 아드와 최적화된 물류 시스템을 갖춘 복합물류센터입니다.<br>
                99% 이상의 전용률과 효율적인 도크 구성으로 물류 프로세스를 신속하게 처리할 수 있습니다.
            </p>
        </div>
    </div>
   
   <div class="back-button">
            <a href="/">뒤로 돌아가기</a>
   </div>


    <!-- Footer Section -->
    <div class="footer">
        회사 정보 - 사업자 번호, 연락처 등 유의 사항<br>
        <a href="#">개인정보 처리방침</a> | <a href="#">이용약관</a>
    </div>
</body>
</html>
