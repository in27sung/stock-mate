<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>내 정보 수정</title>
<link rel="stylesheet"
	href="<c:url value='/resources/css/passwordStyle.css' />">

</head>
<body>
	<!-- Container -->
	<div class="container">
		<!-- 뒤로가기 버튼 -->
		<div class="back-button">
			<a href="/dashboard"> <svg xmlns="http://www.w3.org/2000/svg"
					height="24" viewBox="0 0 24 24" width="24">
                    <path d="M0 0h24v24H0z" fill="none" />
                    <path
						d="M15.41 7.41L14 6l-6 6 6 6 1.41-1.41L10.83 12z" />
                </svg> 뒤로 가기
			</a>
		</div>

		<!-- 제목 -->
		<!-- 회원정보 수정 폼 -->
		<h1>회원정보 수정</h1>
		<form action="/user/updateInfo2" method="post">
			<div class="form-group">
				<label for="email">이메일</label> <input type="email" id="email"
					name="email" value="${userVO.email}" placeholder="이메일을 입력하세요"
					required readonly="readonly">
			</div>
			<div class="form-group">
				<label for="name">이름</label> <input type="text" id="userName"
					name="userName" value="${userVO.userName}" placeholder="이름을 입력하세요"
					required>
			</div>
			<div class="form-group">
				<label for="telNumber">전화번호</label> <input type="tel" id="telNumber"
					name="telNumber" value="${userVO.telNumber}"
					placeholder="전화번호를 입력하세요" required>
			</div>

			<div class="form-group">
				<label for="password">비밀번호</label> <input type="password"
					id="password" name="password" value="${userVO.password}"
					placeholder="비밀번호를 입력하세요" readonly="readonly">
			</div>
			<button type="submit">내 정보 수정하기</button>
		</form>

	</div>

	<!-- Footer -->
	<div class="footer">회사 정보 - 사업자 번호, 연락처 등 유의 내용</div>

</body>
</html>