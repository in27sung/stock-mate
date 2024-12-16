<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>창고 상세 정보</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/warehouseDetailStyle.css' />">
</head>
<body>
    <div class="container">
		<button class="dashboard-btn" onclick="location.href='/dashboard';">대시보드로 돌아가기</button>
        <h1>창고 상세 정보</h1>
        <table>
            <tr>
                <th>창고 이름</th>
                <td>${warehouseDetail.warehouseName}</td>
            </tr>
            <tr>
                <th>창고 지역</th>
                <td>
                    <c:choose>
                        <c:when test="${not empty warehouseDetail.warehouseRegion}">
                            ${warehouseDetail.warehouseRegion}
                        </c:when>
                        <c:otherwise>N/A</c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>상세 주소</th>
                <td>${warehouseDetail.warehouseLocation}</td>
            </tr>
            <tr>
                <th>회사명</th>
                <td>${warehouseDetail.businessName}</td>
            </tr>
            <tr>
                <th>관리자명</th>
                <td>
                    <c:choose>
                        <c:when test="${not empty warehouseDetail.managerName}">
                            ${warehouseDetail.managerName}
                        </c:when>
                        <c:otherwise>N/A</c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>창고 용량</th>
                <td>${warehouseDetail.warehouseCapacity}</td>
            </tr>
            <tr>
                <th>상세 설명</th>
                <td>
                    <c:choose>
                        <c:when test="${not empty warehouseDetail.warehouseDescription}">
                            ${warehouseDetail.warehouseDescription}
                        </c:when>
                        <c:otherwise>N/A</c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>상태</th>
                <td>${warehouseDetail.warehouseStatus}</td>
            </tr>
            <tr>
                <th>등록 날짜</th>
                <td>${warehouseDetail.createdAt}</td>
            </tr>
            <tr>
                <th>수정 날짜</th>
                <td>${warehouseDetail.updatedAt}</td>
            </tr>
        </table>
        <button onclick="location.href='/warehouse/list'">목록으로 돌아가기</button>
    </div>
</body>
</html>
