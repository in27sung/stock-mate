<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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

.roles {
	display: flex;
	justify-content: space-between;
	margin: 20px 0;
}

input[type="radio"] {
	display: none; /* 라디오 버튼 숨기기 */
}

label {
	background-color: #007BFF;
	color: white;
	padding: 12px 20px;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
	flex: 1;
	text-align: center;
	margin: 0 5px;
	transition: background-color 0.3s ease;
}

label:hover {
	background-color: #0056b3; /* hover 시 색상 */
}

input[type="radio"]:checked+label {
	background-color: #28a745; /* 선택된 버튼 색상: 초록색 */
	color: white;
	border: 2px solid #28a745;
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
		<form id="signupForm" action="/user/join" method="post"
			onsubmit="return validateForm()">
			<input type="text" id="email" name="email" placeholder="이메일" required>
			<input type="password" id="password" name="password"
				placeholder="비밀번호" required> <input type="text" id="name"
				name="name" placeholder="이름" required> <input type="text"
				id="phone" name="phone" placeholder="전화번호" required> <input
				type="hidden" id="role" name="role">

			<div class="roles">
				<!-- 관리자 버튼 -->
				<input type="radio" id="role-admin" name="role" value="admin">
				<label for="role-admin">관리자</label>

				<!-- 매니저 버튼 -->
				<input type="radio" id="role-manager" name="role" value="manager">
				<label for="role-manager">매니저</label>

				<!-- 회원 버튼 -->
				<input type="radio" id="role-member" name="role" value="member">
				<label for="role-member">회원</label>
			</div>

			<div class="actions">
				<button type="submit">가입하기</button>
				<button type="button" onclick="goToMain()">메인페이지로</button>
			</div>
		</form>
		<footer> 회사 정보 - 사업자 번호, 연락처 등 유의 내용 </footer>
	</div>

	<script>
	    function validateForm() {
	        const email = document.getElementById('email').value.trim();
	        const password = document.getElementById('password').value.trim();
	        const name = document.getElementById('name').value.trim();
	        const phone = document.getElementById('phone').value.trim();
	        const roleSelected = document.querySelector('input[name="role"]:checked');
	
	        if (!email || !password || !name || !phone) {
	            alert('모든 필드를 입력하세요!');
	            return false;
	        }
	
	        if (!roleSelected) {
	            alert('역할을 선택하세요!');
	            return false;
	        }
	
	        console.log(`Selected role: ${roleSelected.value}`); // 디버깅용
	        return true;
	    }
    </script>
</body>
</html>