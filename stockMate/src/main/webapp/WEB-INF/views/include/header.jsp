<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Stock Mate</title>
<!-- 파비콘 설정 -->
<link rel="icon" href="<c:url value='/resources/img/logo2_no_bg.png' />"
	type="image/x-icon">
<!-- 부트스트랩 및 CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<style>
/* 헤더 스타일 */
.navbar-brand img {
    height: 60px; /* 로고 이미지 높이 */
    margin-right: 10px;
}

.bg-primary {
    background-color: #5cb85c !important; /* 헤더 배경색 */
}

/* 메뉴 텍스트 색상 변경 */
.nav-link {
    color: rgb(239, 239, 239) !important; /* 메뉴 텍스트를 하얀색으로 변경 */
    transition: color 0.3s ease; /* 색상 변경 시 부드러운 애니메이션 */
    font-size: larger !important; /* 글자 크기를 키움 */
    text-align: right; /* 텍스트 오른쪽 정렬 */
}

/* 메뉴 항목 호버 시 색상 변경 */
.nav-link:hover {
    color: #f8f9fa !important; /* 호버 시 약간 더 밝은 하얀색 */
}

/* 모바일 메뉴가 열렸을 때 오른쪽 정렬 */
.navbar-nav {
    text-align: right;
}

/* 블러 효과 영역 */
.blur-overlay {
    position: absolute;
    top: 100%; /* 네비게이션 바로 아래 위치 */
    left: 0;
    width: 100%;
    height: 0; /* 초기 높이를 0으로 설정 */
    backdrop-filter: blur(5px);
    background-color: rgba(0, 0, 0, 0.3); /* 약간 어두운 반투명 배경 */
    transition: height 0.3s ease; /* 부드러운 높이 변경 */
    z-index: 999; /* 블러 효과가 메뉴 아래에 표시되도록 */
    pointer-events: none; /* 블러 영역 클릭 방지 */
}

/* 브라우저 파비콘 이미지 수정 */
link[rel="icon"] {
    max-height: 16px; /* 파비콘 높이 */
    max-width: 16px; /* 파비콘 폭 */
}

/* 모바일 메뉴 내부 간격 조정 */
.navbar-nav .nav-item {
    margin-bottom: 10px; /* 모바일 메뉴 항목 간격 */
}
</style>
</head>
<body>
<!-- 헤더 -->
<header class="bg-primary text-white fixed-top shadow">
    <div class="container">
        <nav class="navbar navbar-expand-lg navbar-dark">
            <!-- 로고 및 사이트 이름 -->
            <a class="navbar-brand d-flex align-items-center" href="/"> 
                <img src="<c:url value='/resources/img/logo_no_bg_white_text.png' />" alt="Stock Mate Logo" height="60">
            </a>
            <!-- 모바일 토글 버튼 -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <!-- 네비게이션 메뉴 -->
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto text-end"> <!-- 텍스트 오른쪽 정렬 -->
                    <li class="nav-item"><a class="nav-link" href="/user/signup">회원가입</a></li>
                    <li class="nav-item"><a class="nav-link" href="/dashboard">대시보드</a></li>
                    <li class="nav-item"><a class="nav-link" href="/howtouse">사용법</a></li>
                    <li class="nav-item"><a class="nav-link" href="/intro">회사소개</a></li>
                    <li class="nav-item"><a class="nav-link" href="/location">창고소개</a></li>
                    <li class="nav-item"><a class="nav-link" href="/price">임대료</a></li>
                    <li class="nav-item"><a class="nav-link" href="/consultation">상담문의</a></li>
                    <li class="nav-item"><a class="nav-link" href="https://map.naver.com" target="_blank">오시는 길</a></li>
                </ul>
            </div>
        </nav>
        <!-- 블러 처리 영역 -->
        <div class="blur-overlay"></div>
    </div>
</header>

<script>
	$(document).ready(function () {
	    // 모든 nav-link 요소에 클릭 이벤트 추가
	    const navLinks = $(".nav-link");
	    const navbarCollapse = $(".navbar-collapse");
	
	    navLinks.on("click", function () {
	        // Collapse가 열려 있는 경우 닫기
	        if (navbarCollapse.hasClass("show")) {
	            navbarCollapse.collapse("hide");
	        }
	    });
	});
    $(document).ready(function () {
        const navbarCollapse = $('#navbarNav');
        const blurOverlay = $('.blur-overlay');

        // 토글 버튼 클릭 시 처리
        $('.navbar-toggler').on('click', function () {
            if (navbarCollapse.hasClass('show')) {
                // 메뉴가 열려 있다면 블러 제거
                blurOverlay.css('height', '0');
            } else {
                // 메뉴가 닫혀 있다면 블러 추가
                const menuHeight = navbarCollapse[0].scrollHeight; // 메뉴 높이 계산
                blurOverlay.css('height', `${menuHeight}px`);
            }
        });

        // 메뉴가 닫힐 때 블러 제거
        navbarCollapse.on('hidden.bs.collapse', function () {
            blurOverlay.css('height', '0');
        });

        // 메뉴가 열릴 때 블러 추가
        navbarCollapse.on('shown.bs.collapse', function () {
            const menuHeight = navbarCollapse[0].scrollHeight;
            blurOverlay.css('height', `${menuHeight}px`);
        });
    });
</script>