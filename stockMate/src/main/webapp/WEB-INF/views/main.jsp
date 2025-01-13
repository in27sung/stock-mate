<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/views/include/header.jsp" %>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>메인 페이지</title>
<link rel="stylesheet" href="<c:url value='/resources/css/bannerStyle.css' />">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/resources/css/custom.css' />">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<style>
.header {
    height: 87vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background: url('<c:url value="/resources/img/main.png" />') no-repeat center center/cover;
    color: #ffffff;
    text-align: center;
    position: relative;
}

/* 로고 스타일 */
.logo {
    position: absolute;
    top: 20px;
    left: 20px;
    width: 150px;
    cursor: pointer;
    z-index: 1000; /* 로고를 항상 최상단에 표시 */
    transition: transform 0.3s ease;
}

.logo:hover {
    transform: scale(1.1); /* 로고에 마우스를 올렸을 때 크기 확대 효과 */
}

.header h1, .header p {
    text-align: left;
    margin-left: 50%;
}

.bg-primary {
    --bs-bg-opacity: 0.3 !important;
    background-color: rgb(119 77 214 / 8%) !important;
}
/* 모달 뒷배경 흐리게 처리 */
.modal-backdrop {
    background-color: rgba(0, 0, 0, 0.6) !important;
    backdrop-filter: blur(5px);
}

/* 모달 콘텐츠 스타일 */
.modal-content {
    border-radius: 15px;
    border: none;
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
    background: linear-gradient(135deg, #f7f7f7, #ffffff);
}

/* 모달 헤더 스타일 */
.modal-header {
    border-bottom: 2px solid #007bff;
}

/* 모달 제목 */
.modal-title {
    font-size: 1.5rem;
}

/* 모달 버튼 스타일 */
.modal-footer .btn-primary {
    background-color: #007bff;
    border: none;
    transition: all 0.3s;
}

.modal-footer .btn-primary:hover {
    background-color: #0056b3;
    transform: scale(1.05);
}

.modal-footer .btn-secondary {
    background-color: #6c757d;
    border: none;
    transition: all 0.3s;
}

.modal-footer .btn-secondary:hover {
    background-color: #5a6268;
    transform: scale(1.05);
}

/* 기본 설정 */
*, ::after, ::before {
    box-sizing: border-box;
    color: inherit; /* 기본 상속 값 */
}

/* 모달 내부 스타일 */
.modal-content {
    background-color: #ffffff; /* 흰색 배경 */
    color: #000000 !important; /* 검은색 텍스트 */
    border-radius: 10px;
}

.modal-header, .modal-body, .modal-footer {
    color: #000000 !important; /* 모달 텍스트만 검은색 */
}
</style>
</head>
<body>
    	<%-- 에러 메시지 표시 --%>
		<c:if test="${not empty errorMessage}">
		    <div class="error-banner">${errorMessage}</div>
		</c:if>
		
		<%-- 성공 메시지 표시 --%>
		<c:if test="${not empty successMessage}">
			<div class="success-banner">${successMessage}</div>
		</c:if>

    <!-- Fullscreen Header -->
    <div class="header">
        <!-- 타이틀과 설명 -->
        <h1>Warehouse Management Solutions</h1>
        <p>스마트한 재고 관리와 최적화된 물류 시스템을 제공합니다.</p>
    </div>

    <!-- Footer -->
	<%@ include file="/WEB-INF/views/include/footer.jsp" %>
	<script src="<c:url value='/resources/scripts/header.js' />"></script>
</body>
</html>