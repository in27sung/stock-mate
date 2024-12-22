<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회사 소개</title>
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

        /* Header */
        .header {
            position: relative;
            height: 300px;
            background: url('resources/img/9950253.jpg') no-repeat center center/cover;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            color: white;
        }



        .header h1 {
            font-size: 3.5rem;
            font-weight: bold;
            text-shadow: 0 4px 8px rgba(0, 0, 0, 0.4);
        }

        /* Section */
        .section {
            width: 90%;
            max-width: 1200px;
            margin: 40px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .section h2 {
            font-size: 2rem;
            color: #2a5298;
            margin-bottom: 15px;
        }

        .section p {
            font-size: 1.1rem;
            color: #555;
            line-height: 1.8;
        }

        /* Footer */
        .footer {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 20px;
            margin-top: 30px;
            font-size: 0.9rem;
        }

        .footer a {
            color: #2a5298;
            text-decoration: none;
        }

        .footer a:hover {
            text-decoration: underline;
        }
                .back-button {
            margin-top: 30px;
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

        
    </style>
</head>
<body>
    <!-- Header Section -->
    <div class="header">
        <h1>회사 소개</h1>
    </div>

    <!-- Company Info Section -->
    <div class="section">
        <h2>회사 정보</h2>
        <p><strong>회사명:</strong> ㈜stockmate</p>
        <p><strong>대표이사:</strong> 黃仁成</p>
        <p><strong>설립일:</strong> 1999년 8월</p>
        <p><strong>주사업:</strong> 웹 기반 ERP, 그룹웨어, 웹메일 개발 및 서비스</p>
        <p><strong>사업장:</strong> 부산 부산진구 동천로 109 삼한골든게이트 ㈜stockmate</p>
    </div>

    <!-- Support Team Section -->
    <div class="section">
        <h2>고객 지원팀</h2>
        <p>고객님과의 최접점 고객지원팀이 있습니다.</p>
        <p>고객지원팀은 고객사와의 상담업무를 주된 업무로 하며 다양한 문의를 처리합니다.</p>
        <p>전화 상담 요청 및 지정된 전문 상담사를 통해 고객님의 궁금증을 해결합니다.</p>
        <p>고객의 "고맙습니다"라는 말이 가장 큰 보람입니다.</p>
    </div>

    <!-- DB Team Section -->
    <div class="section">
        <h2>DB팀</h2>
        <p>DB팀은 프로그램의 기본 뼈대를 튼튼히 잡아주는 중요한 팀입니다.</p>
        <p>데이터를 설계하고 보안을 유지하며 고객사 자료를 안전하게 관리합니다.</p>
        <p>DA(Data Architect)와 DBA(DataBase Admin)로 나누어져 있으며, 새로운 기능 기획안을 기반으로 DB 설계와 도면을 작성합니다.</p>
    </div>

    <!-- Development Team Section -->
    <div class="section">
        <h2>개발팀</h2>
        <p>개발팀은 stock-mate ERP의 모든 기능을 구현합니다.</p>
        <p>고객사 제안과 전략적 개발 기능을 기반으로 개발 스케줄을 잡고 기능을 개발합니다.</p>
        <p>완성된 기능이 무사히 업그레이드되었을 때의 뿌듯함을 느끼며, 고객 만족도를 위해 지속적으로 노력하고 있습니다.</p>
    </div>

	  <div class="back-button">
         <a href="/">뒤로 돌아가기</a>
     </div>

    <!-- Footer -->
    <div class="footer">
        회사 정보 - 사업자 번호, 연락처 등 유의 사항<br>
        <a href="#">개인정보 처리방침</a> | <a href="#">이용약관</a>
    </div>
    

   

    
    
</body>
</html>
