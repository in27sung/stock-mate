<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매니저 페이지</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
            text-align: center;
        }
        h1 {
            margin-top: 30px;
        }
        .container {
            margin-top: 50px;
        }
        button {
            width: 250px;
            height: 50px;
            margin: 10px;
            background-color: black;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #444;
        }
        footer {
            margin-top: 50px;
            background-color: #333;
            color: white;
            padding: 10px 0;
        }
    </style>


</head>
<body>
	<h1>매니저 메인</h1>
	
	<!-- 버튼 영역 -->
    <div class="container">
        <form action="/manager/approve" method="get">
            <button type="submit">가입 승인</button>
        </form>
        <form action="/manager/userList" method="get">
            <button type="submit">회원 목록 조회</button>
        </form>
		<form action="/manager/edit" method="get">
            <button type="submit">회원 수정 및 삭제</button>
        </form>
    </div>

    <!-- 푸터 -->
    <footer>
        <p>회사 정보 - 사업자 번호, 연락처 등등 푸터 내용</p>
    </footer>
	
</body>
</html>
