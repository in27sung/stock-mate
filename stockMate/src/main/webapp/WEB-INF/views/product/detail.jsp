<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 상세 정보</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/warehouseDetailStyle.css' />">
</head>
<body>
    <div class="container">
        <!-- 대시보드 이동 버튼 -->
        <button class="dashboard-btn" onclick="location.href='/dashboard';">대시보드로 돌아가기</button>

        <!-- 페이지 타이틀 -->
        <h1>상품 상세 정보</h1>
        
        <!-- 상품 상세 테이블 -->
        <table>
            <tr>
                <th>카테고리명</th>
                <td>${categoryName}</td>
            </tr>
            <tr>
                <th>상품명</th>
                <td>${product.productName}</td>
            </tr>
            <tr>
                <th>바코드</th>
                <td>${product.productBarcode}</td>
            </tr>
            <tr>
                <th>기본 단위</th>
                <td>${product.baseUnit}</td>
            </tr>
            <tr>
                <th>세트 크기</th>
                <td>${product.setSize}</td>
            </tr>
            <tr>
			    <th>가격</th>
			    <td>
			        <fmt:formatNumber value="${product.productPrice}" type="number" maxFractionDigits="0" /> 원
			    </td>
			</tr>
            <tr>
                <th>상세 설명</th>
                <td>
                    <c:choose>
                        <c:when test="${not empty product.productDescription}">
                            ${product.productDescription}
                        </c:when>
                        <c:otherwise>N/A</c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>등록 날짜</th>
                <td><fmt:formatDate value="${product.createdAt}" pattern="yyyy-MM-dd" /></td>
            </tr>
            <tr>
                <th>수정 날짜</th>
                <td>
                    <c:choose>
                        <c:when test="${product.updatedAt != product.createdAt}">
                            <fmt:formatDate value="${product.updatedAt}" pattern="yyyy-MM-dd" />
                        </c:when>
                        <c:otherwise>등록 이후 수정 없음</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>

        <!-- QR 코드 버튼 -->
        <div style="text-align: center; margin-top: 20px;">
            <c:if test="${product.productQrCodePath == null}">
                <form action="/product/generateQR" method="get" style="display:inline;">
                    <input type="hidden" name="productId" value="${product.productId}">
                    <button type="submit" class="btn btn-green">상품정보 QR 코드 생성</button>
                </form>
            </c:if>
            <c:if test="${product.productQrCodePath != null}">
                <form action="/product/downloadQr" method="get" style="display:inline;">
                    <input type="hidden" name="productId" value="${product.productId}">
                    <button type="submit" class="btn btn-blue">상품정보 QR 코드 다운로드</button>
                </form>
            </c:if>
        </div>

        <!-- 목록으로 돌아가기 버튼 -->
        <button class="dashboard-btn" onclick="location.href='/stock/list';" style="margin-top: 15px;">목록으로 돌아가기</button>
    </div>
</body>
</html>
