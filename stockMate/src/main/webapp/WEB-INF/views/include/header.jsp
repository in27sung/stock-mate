<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Stock Mate</title>
<!-- 파비콘 설정 -->
<link rel="icon" href="<c:url value='/resources/img/logo2_no_bg.png' />" type="image/x-icon">
<!-- 부트스트랩 및 CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/resources/css/headerStyle.css' />">
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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
				<button class="close-btn" hidden='true'>&times;</button> <!-- 닫기 버튼 -->
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
<script src="<c:url value='/resources/scripts/header.js' />"></script>
