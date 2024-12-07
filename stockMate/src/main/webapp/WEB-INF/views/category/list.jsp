<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1> /views/category/list.jsp </h1>

	<h2>카테고리 목록 정보 출력</h2>
	${getAllCategories }

	<table border="1">
	    <tr>
	        <td>카테고리 ID</td>
	        <td>상위 카테고리 ID</td>
	        <td>카테고리 이름</td>
	        <td>레벨</td>
	        <td>생성 날짜</td>
	    </tr>
	    <!-- categories 리스트를 반복하여 테이블로 출력 -->
	    <c:forEach var="category" items="${categories}">
	        <tr>
	            <td>${category.categoryId}</td>
	            <td>${category.parentId != null ? category.parentId : '없음'}</td>
	            <td>${category.categoryName}</td>
	            <td>${category.level == 1 ? '대분류' : '소분류'}</td>
	            <td>${category.createdAt}</td>
	        </tr>
	    </c:forEach>
	</table>

	<!-- 카테고리 등록 링크 -->
	<a href="/category/register">카테고리 등록</a>


</body>
</html>