<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>메인 페이지</title>
<link rel="stylesheet" href="<c:url value='/resources/css/bannerStyle.css' />">
<style>
/* Reset */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #121212;
    color: #ffffff;
    line-height: 1.6;
}

/* Fullscreen Header Section */
.header {
    height: 87vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background: linear-gradient(135deg, #1e3c72, #2a5298);
    color: #ffffff;
    text-align: center;
    position: relative;
}

.header::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: url('resources/img/stockmate-removebg-preview.png') no-repeat center center/contain;
    opacity: 0.1;
    z-index: 0;
}

.header h1 {
    font-size: 4rem;
    font-weight: bold;
    margin-bottom: 20px;
    z-index: 1;
}

.header p {
    font-size: 1.5rem;
    margin-top: 10px;
    color: #dcdcdc;
    z-index: 1;
}

/* Navigation */
.nav {
    display: flex;
    justify-content: center;
    padding: 10px 0;
    background: #333333;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.nav a {
    color: #ffffff;
    text-decoration: none;
    margin: 10px 15px;
    padding: 15px 25px;
    font-weight: bold;
    border: 2px solid #007BFF;
    border-radius: 10px;
    transition: all 0.3s ease;
    box-shadow: 0 0 10px #007BFF, 0 0 20px #0056b3;
    font-size: 16px;
}

.nav a:hover {
    background: #007BFF;
    color: #fff;
    transform: scale(1.1);
    box-shadow: 0 0 15px #007BFF, 0 0 30px #0056b3;
}

/* Footer */
.footer {
    background: #333333;
    padding: 20px 10px;
    text-align: center;
    font-size: 0.9rem;
    box-shadow: 0 -4px 8px rgba(0, 0, 0, 0.3);
}

.footer a {
    color: #fff;
    text-decoration: none;
    transition: color 0.3s;
    margin: 5px 5px;
}

.footer a:hover {
    color: #007BFF;
/*     text-decoration: underline; */
}

/* 모달 기본 스타일 */
.modal {
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);
    justify-content: center;
    align-items: center;
    z-index: 1000;
    animation: fadeIn 0.3s ease-in-out;
}

/* 모달 내용 */
.modal-content {
    background-color: #ffffff;
    color: #333333;
    padding: 30px;
    border-radius: 15px;
    width: 50%;
    max-width: 600px;
    position: relative;
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.5);
    border: 3px solid #007BFF;
    transform: translateY(-30px);
    animation: slideDown 0.3s ease-in-out;
}

/* 닫기 버튼 */
.close {
    position: absolute;
    top: 10px;
    right: 15px;
    font-size: 24px;
    font-weight: bold;
    color: #333333;
    cursor: pointer;
}

.close:hover {
    color: red;
}



@keyframes fadeIn {
    from {
        background-color: rgba(0, 0, 0, 0);
    }
    to {
        background-color: rgba(0, 0, 0, 0.6);
    }
}

@keyframes slideDown {
    from {
        transform: translateY(-50px);
        opacity: 0;
    }
    to {
        transform: translateY(0);
        opacity: 1;
    }
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

 <!-- Navigation -->
    <div class="nav">
        <a href="user/signup">회원가입</a>
        <a href="/dashboard">대시보드</a>
        <a href="/howtouse">대시보드 사용법</a>
        <a href="/intro">회사소개</a>
        <a href="/location">창고소개</a>
        <a href="/price">임대료</a>
        <a href="/consultation">상담문의</a>
        <a href="https://map.naver.com/p/search/%EA%B2%BD%EA%B8%B0%EB%8F%84%20%EC%9A%A9%EC%9D%B8%EC%8B%9C%20%EC%B2%98%EC%9D%B8%EA%B5%AC%20%EC%96%91%EC%A7%80%EB%A9%B4%20%EC%A3%BC%EB%B6%81%EB%A1%9C%20103-1/address/14164717.7610606,4473895.3924035,%EA%B2%BD%EA%B8%B0%EB%8F%84%20%EC%9A%A9%EC%9D%B8%EC%8B%9C%20%EC%B2%98%EC%9D%B8%EA%B5%AC%20%EC%96%91%EC%A7%80%EB%A9%B4%20%EC%A3%BC%EB%B6%81%EB%A1%9C%20103-1,new?searchType=address&isCorrectAnswer=true&c=15.00,0,0,0,dh" target="_blank">오시는 길</a>
    </div>
    
    
    <!-- Fullscreen Header -->
    <div class="header">
        <h1>Warehouse Management Solutions</h1>
        <p>스마트한 재고 관리와 최적화된 물류 시스템을 제공합니다.</p>
    </div>

   

    <!-- Footer -->
    <div class="footer">
   		 <div class="footera">
	        <a href="javascript:void(0);" onclick="showTerms();">이용약관</a> | 
	        <a href="javascript:void(0);" onclick="showPrivacyPolicy();">개인정보 취급방침</a> | 
	        <a href="javascript:void(0);" onclick="showEmailPolicy();">이메일주소무단수집거부</a>
        </div>
    </div>

    <!-- '이용약관' 모달 -->
    <div id="termsModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('termsModal');">&times;</span>
            <h2>이용약관</h2>
            <p>
                 <strong>제 1조 총칙 </strong><br>
                이 약관은 stock-mate(이하 "회사")가 제공하는 모든 서비스의 이용조건 및 절차를 규정함을 목적으로 합니다.
            </p>
            <p>
                 <strong>제 2조 약관의 효력과 변경 </strong><br>
                (1) 이 약관은 본 사이트에 공지함으로써 효력을 발휘합니다.<br>
                (2) 회사는 필요 시 약관을 변경할 수 있으며, 변경된 약관은 사이트에 게시하거나 기타 방법으로 공지합니다.
            </p>
        </div>
    </div>

    <!-- '개인정보취급방침' 모달 -->
    <div id="privacyModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('privacyModal');">&times;</span>
            <h2>개인정보취급방침</h2>
            <p>
                안성창고 회원의 개인 정보 보호를 매우 중요시하며, <br>
                <strong>[정보통신망 이용 촉진 등에 관한 법률]</strong>을 준수하고 있습니다.<br>
                고객님의 개인 정보 보호를 위해 최선을 다하겠습니다.
            </p>
            <p>
                본 방침은 2020년 10월 19일부터 시행됩니다.
            </p>
        </div>
    </div>

    <!-- '이메일주소무단수집거부' 모달 -->
    <div id="emailPolicyModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal('emailPolicyModal');">&times;</span>
            <h2>이메일주소무단수집거부</h2>
            <p>
                본 사이트에 게시된 이메일 주소가 전자우편 수집 프로그램이나 그 밖의 기술적 장치를 이용하여 무단으로 
                수집되는 것을 거부하며, 이를 위반 시 정보통신망 법에 의해 형사처분됨을 유념하시기 바랍니다.
            </p>
            <p>
                정보통신망 법 제50조 2<br>
                <strong>[전자우편 주소의 무단 수집 행위 등 금지]</strong><br>
                1. 누구든지 전자우편 주소의 수집을 거부하는 의사가 명시된 인터넷 홈페이지에서 자동으로 전자우편 주소를 수집하는 프로그램 또는 기술적 장치를 이용하여 전자우편 주소를 수집하여서는 아니 된다.<br>
                2. 누구든지 제1항의 규정을 위반하여 수집된 전자우편 주소를 판매, 유통하여서는 아니 된다.<br>
                3. 누구든지 제1항 및 2항의 규정에 의하여 수집, 판매 및 유통이 금지된 전자우편 주소임을 알고 이를 정보 전송에 이용하여서는 아니 된다.
            </p>
        </div>
    </div>

    <script>
        function showTerms() {
            document.getElementById("termsModal").style.display = "flex";
        }

        function showPrivacyPolicy() {
            document.getElementById("privacyModal").style.display = "flex";
        }

        function showEmailPolicy() {
            document.getElementById("emailPolicyModal").style.display = "flex";
        }

        function closeModal(modalId) {
            document.getElementById(modalId).style.display = "none";
        }
    </script>
</body>
</html>