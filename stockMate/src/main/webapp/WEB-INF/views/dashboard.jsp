<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>대시보드</title>
    <style>
        /* Reset and Global Styles */
        body, html {
            margin: 0;
            padding: 0;
            font-family: 'Arial', sans-serif;
            background-color: #f5f5f5;
            color: #333;
            height: 100%;
        }

        /* Flex Container */
        .container {
            display: flex;
            flex-direction: column;
            min-height: 100vh; /* 화면 전체 높이를 채우기 */
        }
        
        .error-banner {
            width: 100%;
            background-color: #FCE4E4;
            color: #D32F2F;
            text-align: center;
            padding: 10px 0;
            font-size: 14px;
            font-weight: 500;
            position: absolute;
            top: 0;
            left: 0;
            z-index: 1000;
            border-bottom: 1px solid #F5C6C6;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        /* Content Section */
        .content {
            flex: 1; /* 콘텐츠 영역이 화면에서 확장되도록 설정 */
            display: flex;
            gap: 20px;
            padding: 20px;
            background-color: #f5f5f5;
        }

        /* Header Section */
        .header {
            background-color: #007BFF;
            color: white;
            padding: 20px;
            text-align: center;
            font-size: 24px;
            font-weight: bold;
        }

        .menu-bar {
            display: flex;
            justify-content: center;
            gap: 10px; /* 간격을 줄임 */
            background-color: #007BFF;
            padding: 15px 0;
        }

        .menu-bar a {
            display: flex;
            align-items: center; /* 세로 중앙 정렬 */
            justify-content: center; /* 가로 중앙 정렬 */
            color: white;
            padding: 15px 30px; /* 넓고 크게 설정 */
            background-color: #0056b3;
            border-radius: 10px;
            font-size: 18px; /* 글자 크기 증가 */
            font-weight: bold;
            transition: background-color 0.3s ease, transform 0.2s ease;
            text-align: center;
            text-decoration: none;
            cursor: pointer;
        }

        .menu-bar a:hover {
            background-color: #003f8c;
            transform: translateY(-5px);
        }

        /* Sidebar */
        .sidebar {
            width: 200px;
            background-color: #ffffff;
            padding: 20px;
            box-shadow: 2px 0 4px rgba(0, 0, 0, 0.1);
        }

        .sidebar a {
            display: block;
            padding: 10px 15px;
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 10px;
            color: #007BFF;
            border-radius: 5px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .sidebar a:hover {
            background-color: #f0f0f0;
        }

        .sidebar .disabled {
            color: red;
            pointer-events: none;
        }

        /* Main Content */
        .main-content {
            flex: 1;
            display: flex;
            flex-direction: column;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .main-content h2 {
            font-size: 20px;
            margin-bottom: 20px;
        }

        .charts {
            display: flex;
            gap: 20px;
            flex-wrap: wrap;
        }

        .chart {
            flex: 1 1 45%;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        .chart canvas {
            width: 100%;
            height: 250px;
        }

        /* Footer Section */
        footer {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 15px;
            font-size: 14px;
            position: relative;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>
    <div class="container">
   	   <!-- 에러 메시지가 있을 경우 상단 배너로 표시 -->
       <c:if test="${not empty errorMessage}">
           <div class="error-banner">${errorMessage}</div>
       </c:if>
        <!-- Header Section -->
        <div class="header">
            대시보드
        </div>
        <div class="menu-bar">
            <a href="/order/register">주문</a>
            <a href="/receiving/main">입고</a>
            <a href="/shipment/main">출고</a>
            <a href="/stock/list">재고</a>
            <a href="/user/admin">관리자 페이지</a>
        </div>

        <!-- Content Section -->
        <div class="content">
            <!-- Sidebar -->
            <div class="sidebar">
                <div class="disabled">Dashboard</div>
                <a href="product/register">상품 등록</a>
                <a href="warehouse/register">창고 등록</a>
                <a href="stock/register">재고 등록</a>
                <a href="profile">내정보 조회/수정</a>
                <a href="password">비밀번호 변경</a>
                <a href="help">대시보드 사용법</a>
                <a href="user/signout" style="color: red;">Sign out</a>
            </div>

            <!-- Main Content -->
            <div class="main-content">
                <h2>Order Time</h2>
                <div class="charts">
                    <!-- Donut Chart -->
                    <div class="chart">
                        <h3>Donut Chart</h3>
                        <canvas id="donutChart"></canvas>
                    </div>

                    <!-- Bar Chart -->
                    <div class="chart">
                        <h3>Bar Chart</h3>
                        <canvas id="barChart"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer Section -->
        <footer>
            회사 정보 - 사업자 번호, 연락처 등 Footer 내용
        </footer>
    </div>

    <!-- Chart.js Integration -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        const donutChart = new Chart(document.getElementById('donutChart'), {
            type: 'doughnut',
            data: {
                labels: ['Morning', 'Afternoon', 'Evening'],
                datasets: [{
                    data: [20, 40, 30],
                    backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56']
                }]
            }
        });

        const barChart = new Chart(document.getElementById('barChart'), {
            type: 'bar',
            data: {
                labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                datasets: [{
                    label: 'Orders',
                    data: [50, 75, 100, 150, 200, 250, 300],
                    backgroundColor: '#36A2EB'
                }]
            }
        });
    </script>
</body>
</html>

