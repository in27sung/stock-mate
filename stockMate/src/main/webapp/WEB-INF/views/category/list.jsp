<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>카테고리 목록</title>
	<link rel="stylesheet" href="<c:url value='/resources/css/stockListStyle.css' />">
	<link rel="stylesheet" href="<c:url value='/resources/css/categoryListStyle.css' />">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <h1>카테고리 목록</h1>
	   	<!-- 대시보드로 이동 버튼 -->
    	<button class="dashboard-btn" onclick="location.href='<c:url value="/dashboard" />'">대시보드로 이동</button>
	    <br>
	    <a href="/category/register">카테고리 등록</a>
    <table border="1">
        <thead>
            <tr>
                <th>카테고리 이름</th>
                <th>상위 카테고리</th>
                <th>계층</th>
                <th>생성 날짜</th>
                <th>액션</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="category" items="${categories}">
                <!-- 상위 카테고리 -->
                <c:if test="${category.parentId == null}">
                    <tr>
                        <td>${category.categoryName}</td>
                        <td>없음</td>
                        <td>대분류</td>
                        <td><fmt:formatDate value="${category.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                            <form action="/category/edit" method="get" style="display: inline;">
                                <input type="hidden" name="categoryId" value="${category.categoryId}" />
                                <button type="submit">수정</button>
                            </form>
                            <form action="/category/delete" method="post" style="display: inline;" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                                <input type="hidden" name="categoryId" value="${category.categoryId}" />
                                <button type="submit">삭제</button>
                            </form>
                        </td>
                    </tr>

                    <!-- 하위 카테고리 -->
                    <c:forEach var="subCategory" items="${categories}">
                        <c:if test="${subCategory.parentId == category.categoryId}">
                            <tr>
                                <td style="padding-left: 20px;">└ ${subCategory.categoryName}</td>
                                <td>${category.categoryName}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${subCategory.level == 2}">중분류</c:when>
                                        <c:when test="${subCategory.level == 3}">소분류</c:when>
                                        <c:otherwise>소분류${subCategory.level - 3}</c:otherwise>
                                    </c:choose>
                                </td>
                                <td><fmt:formatDate value="${subCategory.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                <td>
                                    <form action="/category/edit" method="get" style="display: inline;">
                                        <input type="hidden" name="categoryId" value="${subCategory.categoryId}" />
                                        <button type="submit">수정</button>
                                    </form>
                                    <form action="/category/delete" method="post" style="display: inline;" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                                        <input type="hidden" name="categoryId" value="${subCategory.categoryId}" />
                                        <button type="submit">삭제</button>
                                    </form>
                                </td>
                            </tr>

                            <!-- 하위의 하위 카테고리 -->
                            <c:forEach var="childCategory" items="${categories}">
                                <c:if test="${childCategory.parentId == subCategory.categoryId}">
                                    <tr>
                                        <td style="padding-left: 40px;">└ ${childCategory.categoryName}</td>
                                        <td>${subCategory.categoryName}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${childCategory.level == 3}">소분류</c:when>
                                                <c:otherwise>소분류${childCategory.level - 3}</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><fmt:formatDate value="${childCategory.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td>
										    <form action="/category/edit" method="get" style="display: inline;">
										        <input type="hidden" name="categoryId" value="${childCategory.categoryId}" />
										        <button type="submit" 
										                class="edit-btn ${childCategory.parentId == null ? 'disabled-btn' : ''}" 
										                ${childCategory.parentId == null ? 'disabled' : ''}>
										            수정
										        </button>
										    </form>
										    <form action="/category/delete" method="post" style="display: inline;" 
										          onsubmit="return confirm('정말 삭제하시겠습니까?');">
										        <input type="hidden" name="categoryId" value="${childCategory.categoryId}" />
										        <button type="submit" 
										                class="delete-btn ${childCategory.parentId == null ? 'disabled-btn' : ''}" 
										                ${childCategory.parentId == null ? 'disabled' : ''}>
										            삭제
										        </button>
										    </form>
										</td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </c:forEach>
                </c:if>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
