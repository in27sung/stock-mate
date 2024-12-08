<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>대시보드</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
        }
        .header {
            background-color: #222;
            padding: 20px;
            text-align: center;
            color: white;
        }
        .header img {
            width: 80px;
        }
        .nav {
            display: flex;
            justify-content: space-around;
            background-color: #007BFF;
            padding: 10px 0;
        }
        .nav a {
            color: white;
            text-decoration: none;
            padding: 10px 20px;
            background-color: #007BFF;
            border-radius: 5px;
        }
        .nav a:hover {
            background-color: #0056b3;
        }
        .dashboard {
            display: flex;
            margin: 20px;
        }
        .sidebar {
            width: 20%;
            background-color: #fff;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        .sidebar a {
            display: block;
            text-decoration: none;
            color: #333;
            margin: 10px 0;
            padding: 10px;
            border-radius: 5px;
        }
        .sidebar a:hover {
            background-color: #007BFF;
            color: white;
        }
        .content {
            flex: 1;
            background-color: #fff;
            margin-left: 20px;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        }
        .chart {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
        .chart-item {
            width: 45%;
        }
        .footer {
            background-color: #222;
            color: white;
            text-align: center;
            padding: 10px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <!-- Header -->
    <div class="header">
        <img src="https://via.placeholder.com/80" alt="Logo">
        <h1>대시보드</h1>
    </div>

    <!-- Navigation -->
    <div class="nav">
        <a href="#">주문</a>
        <a href="#">입고</a>
        <a href="#">출고</a>
        <a href="#">재고</a>
        <a href="#">관리자 페이지</a>
    </div>

    <!-- Dashboard Layout -->
    <div class="dashboard">
        <!-- Sidebar -->
        <div class="sidebar">
            <a href="#">Dashboard</a>
            <a href="#">내정보 조회/수정</a>
            <a href="#">비밀번호 변경</a>
            <a href="#">대시보드 사용법</a>
            <a href="#">환경 설정</a>
            <a href="#" style="color: red;">Log out</a>
        </div>

        <!-- Main Content -->
        <div class="content">
            <h2>Order Time</h2>
            <p>From 1-6 Dec, 2020</p>
            <div class="chart">
                <div class="chart-item">
                    <h3>Donut Chart</h3>
                    <img src="https://via.placeholder.com/300x200" alt="Donut Chart">
                </div>
                <div class="chart-item">
                    <h3>Bar Chart</h3>
                    <img src="https://via.placeholder.com/300x200" alt="Bar Chart">
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <div class="footer">
        회사 정보 - 사업자 번호, 연락처 등 유의 내용
    </div>
</body>
</html>
