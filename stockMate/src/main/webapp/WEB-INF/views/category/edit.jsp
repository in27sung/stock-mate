<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카테고리 수정</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="<c:url value='/resources/css/editStyle.css' />">

</head>
<body>
    <div class="container">
        <h2>카테고리 수정</h2>
        <form action="/category/update" method="post">
            <input type="hidden" name="categoryId" value="${category.categoryId}" />

            <label for="categoryName">카테고리 이름:</label>
            <input type="text" id="categoryName" name="categoryName" value="${category.categoryName}" required />

            <label for="parentId">상위 카테고리:</label>
            <select id="parentId" name="parentId">
                <option value="">대분류 선택</option>
                <c:forEach var="parent" items="${parentCategories}">
                    <option value="${parent.categoryId}" 
                            ${parent.categoryId == category.parentId ? 'selected' : ''}>
                        ${parent.categoryName}
                    </option>
                </c:forEach>
            </select>

            <button type="submit">수정</button>
        </form>

        <!-- 목록 페이지로 돌아가기 -->
        <a href="/category/list">목록으로</a>
    </div>
</body>
</html>
