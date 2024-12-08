<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #333;
	color: white;
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

.container {
	width: 500px;
	background-color: #fff;
	color: #000;
	padding: 30px 20px;
	border-radius: 10px;
	text-align: center;
	box-shadow: 0 0 15px rgba(0, 0, 0, 0.5);
}

.container img {
	width: 100px;
	margin-bottom: 20px;
}

.container input {
	width: 90%;
	margin: 10px 0;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 5px;
	font-size: 16px;
}

.actions {
	display: flex;
	justify-content: space-between;
	margin-top: 20px;
}

.actions button {
	background-color: #007BFF;
	color: white;
	border: none;
	padding: 12px 20px;
	cursor: pointer;
	border-radius: 5px;
	width: 48%;
	font-size: 16px;
}

.actions button:hover {
	background-color: #0056b3;
}

footer {
	margin-top: 20px;
	color: #999;
	font-size: 12px;
}
</style>
</head>
<body>
	<div class="container">
		<img src="https://via.placeholder.com/100" alt="Logo">
		<h2>회원가입</h2>
		<form id="signupForm" action="/user/signup" method="post">
			<input type="text" name="email" placeholder="이메일" required>
			<input type="password" name="password" placeholder="비밀번호" required>
			<input type="text" name="name" placeholder="이름" required>
			<input type="text" name="phone" placeholder="전화번호" required>
			<input type="text" name="role" placeholder="역할 (admin, manager, staff)" required>

			<div class="actions">
				<button type="submit">가입하기</button>
				<button type="button" onclick="window.location.href='/'">메인페이지로</button>
			</div>
		</form>
		<footer> 회사 정보 - 사업자 번호, 연락처 등 유의 내용 </footer>
	</div>
</body>
</html>

</body>
</html>