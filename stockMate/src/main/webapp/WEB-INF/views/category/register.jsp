<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카테고리 등록</title>
</head>
<body>
	<h1> /views/category/register.jsp </h1>
	
	<form action="/category/register" method="post">
	    <!-- 상위 카테고리 선택 -->
	    <label for="parentCategoryId">상위 카테고리:</label>
	    <select id="parentCategoryId" name="parentId">
	        <option value="">-- 상위 카테고리 선택 --</option>
	        <c:forEach var="cat" items="${categoryList}">
	            <option value="${cat.categoryId}">${cat.categoryName}</option>
	        </c:forEach>
	    </select>
	
	    <!-- 카테고리 이름 -->
	    <label for="categoryName">카테고리 이름:</label>
	    <input type="text" id="categoryName" name="categoryName" required />
	
	    <!-- 숨겨진 사업자 ID -->
	    <input type="hidden" id="businessId" name="businessId" value="${sessionScope.businessId}" />
	
	    <!-- 등록 버튼 -->
	    <button type="submit">등록</button> <br>
	    <a href="/category/list" class="btn btn-primary">카테고리 리스트로 이동</a>
	</form>

	
</body>
</html>