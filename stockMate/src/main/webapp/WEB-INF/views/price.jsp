<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>임대료 안내</title>
    <style>
        /* Reset */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
            color: #333;
            line-height: 1.8;
        }

        /* Header */
        .header {
            background: linear-gradient(135deg, #007BFF, #0056b3);
            color: white;
            text-align: center;
            padding: 30px 20px;
        }

        .header h1 {
            font-size: 3.5rem;
            font-weight: bold;
        }

        /* Container */
        .price-container {
            display: flex;
            justify-content: center;
            gap: 40px;
            margin: 50px auto;
            flex-wrap: wrap;
            width: 90%;
            max-width: 1200px;
        }

        /* Price Card */
        .price-card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 8px 15px rgba(0, 0, 0, 0.2);
            padding: 20px;
            text-align: center;
            width: 100%;
            max-width: 500px;
        }

        .price-card h2 {
            background-color: #004225;
            color: white;
            padding: 15px;
            font-size: 2rem;
            border-radius: 10px 10px 0 0;
        }

        /* Table */
        .price-card table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            font-size: 1.2rem;
        }

        table th {
            background-color: #007BFF;
            color: white;
            padding: 10px;
            text-align: center;
            font-size: 1.5rem;
        }

        table td {
            padding: 10px;
            text-align: left; /* 왼쪽 정렬 */
            border-bottom: 1px solid #ddd;
        }

        table td span {
            display: inline-block; /* 세부항목 정렬 */
            min-width: 100px; /* 항목 길이 통일 */
            font-weight: bold;
            color: #555;
        }

        table td strong {
            color: #007BFF;
            font-weight: bold;
        }

        table tr:nth-child(even) td {
            background-color: #f9f9f9;
        }

        /* Back Button */
        .back-button {
            display: flex;
            justify-content: center;
            margin: 30px auto;
        }

        .back-button a {
            text-decoration: none;
            padding: 15px 30px;
            background-color: #007BFF;
            color: white;
            border-radius: 10px;
            font-size: 1.5rem;
            font-weight: bold;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        .back-button a:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }

        /* Footer */
        .footer {
            text-align: center;
            padding: 20px;
            background-color: #333;
            color: white;
            font-size: 1rem;
        }
    </style>
</head>
<body>
    <!-- Header -->
    <div class="header">
        <h1>임대료 안내</h1>
    </div>

    <!-- Price Tables -->
    <div class="price-container">
        <!-- 20피트 기준 -->
        <div class="price-card">
            <h2>20피트 1대 기준</h2>
            <table>
                <tr>
                    <th>1층</th>
                </tr>
                <tr>
                    <td><span>기본료(10일):</span> 75,000 → <strong>73,000</strong></td>
                </tr>
                <tr>
                    <td><span>1일:</span> 7,500 → <strong>7,300</strong></td>
                </tr>
                <tr>
                    <td><span>1개월:</span> 220,000 → <strong>210,000</strong></td>
                </tr>
                <tr>
                    <th>2층</th>
                </tr>
                <tr>
                    <td><span>기본료(10일):</span> 52,000 → <strong>46,000</strong></td>
                </tr>
                <tr>
                    <td><span>1일:</span> 5,000 → <strong>4,600</strong></td>
                </tr>
                <tr>
                    <td><span>1개월:</span> 150,000 → <strong>130,000</strong></td>
                </tr>
            </table>
        </div>

        <!-- 40피트 기준 -->
        <div class="price-card">
            <h2>40피트 1대 기준</h2>
            <table>
                <tr>
                    <th>1층</th>
                </tr>
                <tr>
                    <td><span>기본료(10일):</span> 150,000 → <strong>135,000</strong></td>
                </tr>
                <tr>
                    <td><span>1일:</span> 15,000 → <strong>13,500</strong></td>
                </tr>
                <tr>
                    <td><span>1개월:</span> 440,000 → <strong>390,000</strong></td>
                </tr>
                <tr>
                    <th>2층</th>
                </tr>
                <tr>
                    <td><span>기본료(10일):</span> 104,000 → <strong>84,000</strong></td>
                </tr>
                <tr>
                    <td><span>1일:</span> 10,000 → <strong>8,400</strong></td>
                </tr>
                <tr>
                    <td><span>1개월:</span> 300,000 → <strong>240,000</strong></td>
                </tr>
            </table>
        </div>
    </div>

    <!-- Back Button -->
    <div class="back-button">
        <a href="/">뒤로 돌아가기</a>
    </div>

    <!-- Footer -->
    <div class="footer">
        ※ 임대료는 선불 기간에 따라 적용됩니다. 위탁관리는 1층 가격으로 적용됩니다.
    </div>
</body>
</html>
