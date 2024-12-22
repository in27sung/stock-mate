<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카테고리 수정</title>
</head>
<body>

	<!-- delete.jsp -->
	<h2>카테고리 삭제</h2>
	<p>정말로 이 카테고리를 삭제하시겠습니까?</p>
	
	<form action="/category/delete" method="post">
	    <!-- 삭제할 카테고리의 ID를 전달 -->
	    <input type="hidden" name="categoryId" value="${category.categoryId}" />
	    <button type="submit">삭제</button>
	    <a href="/category/list">취소</a>
	</form>

</body>
</html>
