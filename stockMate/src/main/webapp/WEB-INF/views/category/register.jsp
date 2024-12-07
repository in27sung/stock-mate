<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1> /views/category/register.jsp </h1>
	
	<form action="/category/register" method="post">
	    <label for="categoryName">카테고리 이름:</label>
	    <input type="text" id="categoryName" name="categoryName" required />
    
	    <label for="parentId">상위 카테고리 ID (대분류일 경우 비워둘 것):</label>
	    <input type="number" id="parentId" name="parentId" />
    
	    <!-- 사업자 ID는 사용자 세션에서 자동으로 처리될 예정 -->
	    <input type="hidden" id="businessId" name="businessId" value="${sessionScope.businessId}" />
	    <!-- level과 createdAt은 서버에서 자동 설정 -->
    
    	<button type="submit">등록</button>
	</form>

	
</body>
</html>