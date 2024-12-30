<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>대시보드 사용법</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
        }
        .container {
            max-width: 1500px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 15px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.2);
            text-align: center;
        }

          .header {
    position: relative;
    height: 400px;
    background: url(resources/img/9950253.jpg) no-repeat center center / cover;
    display: flex
;
    justify-content: center;
    align-items: center;
    text-align: center;
    color: white;
        }
        .header h1 {
            font-size: 36px;
            font-family: 'Roboto', sans-serif;
            margin: 0;
        }
        .menu {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(500px, 1fr));
            gap: 30px;
            text-align: left;
            padding: 20px;
        }
        .menu-item {
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s, box-shadow 0.3s;
            font-family: 'Open Sans', sans-serif;
        }
        .menu-item:hover {
            transform: translateY(-10px);
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.2);
        }
        .menu-item strong {
            font-size: 22px;
            color: #333;
            display: block;
            text-align: center;
            margin-bottom: 10px;
        }
        .menu-item p {
            font-size: 18px;
            color: #555;
            line-height: 1.8;
            margin: 0;
            word-break: break-word;
        }
        .menu-item p:not(:last-child) {
            margin-bottom: 10px;
        }
        .back-button {
            margin-top: 30px;
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
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&family=Open+Sans:wght@400;600&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>대시보드 사용법</h1>
        </div>
        <div class="menu">
            <div class="menu-item">
                <strong>출고</strong>
                <p>업무 상황에 맞는 판매입력 방식입니다.</p>
                <p>주문, 구매 내역을 불러와 판매 전표를 작성할 수 있습니다.</p>
                <p>바코드를 스캔하여 출고 처리를 할 수 있습니다.</p>
            </div>
            <div class="menu-item">
                <strong>발주</strong>
                <p>다양한 구매입력 방식입니다.</p>
                <p>발주서, 주문서 내역을 불러와 간편하게 구매 전표로 저장할 수 있습니다.</p>
                <p>바코드를 스캔하여 손쉽게 입고 처리가 가능합니다.</p>
            </div>
            <div class="menu-item">
                <strong>창고 사용법</strong>
                <p>창고 관리의 기본과 고급 기능을 익히세요.</p>
                <p>재고 관리, 위치 설정, 이동 기록 등을 활용하여 효율적인 창고 운영이 가능합니다.</p>
            </div>
            <div class="menu-item">
                <strong>입고</strong>
                <p>입고 데이터의 자동화와 정확성 향상입니다.</p>
                <p>입고 확인서와 주문서를 기반으로 빠르고 정확한 재고 처리가 가능합니다.</p>
            </div>
            <div class="menu-item">
                <strong>주문</strong>
                <p>주문 내역 관리 및 처리입니다.</p>
                <p>고객 주문 데이터를 실시간으로 확인하고 처리할 수 있습니다.</p>
                <p>주문 상태를 추적하여 고객 만족도를 높일 수 있습니다.</p>
            </div>
            <div class="menu-item">
                <strong>관리자 페이지</strong>
                <p>시스템 설정과 사용자 관리입니다.</p>
                <p>사용자 권한 설정, 시스템 로그 확인, 전체 설정 변경이 가능합니다.</p>
            </div>
            <div class="menu-item">
                <strong>회원가입</strong>
                <p>신규 사용자 등록 및 권한 설정입니다.</p>
                <p>간단한 양식을 통해 새로운 사용자를 추가하고 권한을 부여할 수 있습니다.</p>
            </div>
            <div class="menu-item">
                <strong>상담 문의</strong>
                <p>사용자 문의사항 처리입니다.</p>
                <p>실시간 상담 요청을 확인하고 고객의 질문에 답변할 수 있습니다.</p>
            </div>
        </div>
        <div class="back-button">
            <a href="/">뒤로 돌아가기</a>
        </div>
    </div>
</body>
</html>