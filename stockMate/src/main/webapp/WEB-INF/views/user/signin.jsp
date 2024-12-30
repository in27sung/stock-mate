<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/bannerStyle.css' />">
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #F2F2F2;
        }
        .container {
           
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            position: relative;
            top: -50px;
            left:20px;
        } 
        .login-box {
            width: 410px;
               background: #fff;
			    border-radius: 12px;
			    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
			    padding: 30px 30px;
			    text-align: center;
            
         
        }
        
        #loginForm{
        	padding-top:20px;
        }
        
        .login-box input[type="email"], .login-box input[type="password"] {
            width: 100%;
            height: 50px;
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 14px;
            box-sizing: border-box;
        }
        .login-box button {
            width: 100%;
            padding: 12px;
            background-color: #234582;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
        }
        .login-box button:hover {
            background-color: #ed8620;
        }
        .footer {
            text-align: center;
            margin-top: 20px;
            color: #666666;
            font-size: 12px;
        }
        .links {
            margin-top: 18px;
            display: flex;
            justify-content: space-between;
        }
        .links a {
            font-size: 15px;
            text-decoration: none;
            color: #234582;
        }
        .links a:hover {
            text-decoration: underline;
        }
        
        /* Fullscreen Header Section */
		body {
		    height: 100vh;
		    display: flex;
		    flex-direction: column;
		    justify-content: center;
		    align-items: center;
		    background: linear-gradient(135deg, #1e3c72, #2a5298);
		    color: #ffffff;
		    text-align: center;
		    position: relative;
		}
		
		body::before {
		    content: '';
		    position: absolute;
		    top: 0;
		    left: 0;
		    width: 100%;
		    height: 100%;
		    /* background: url('../resources/css/stockmate-removebg-preview.png') no-repeat center center/contain; */
		    opacity: 0.1;
		    z-index: 0;
		}
		
		
		
		.logo img{
			width : 90%;
			margin-bottom:-70px;
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
		
   

    <div class="container">
		
		<div class="logo">
			<a href="http://c7d2408t1p1.itwillbs.com/">
				<img alt="" src="../resources/img/stockmate-removebg-preview.png">
			</a>
		</div>

        <div class="login-box">
            <form id="loginForm" method="post">
                <!-- 이메일 필드 -->
                <input type="email" name="email" placeholder="이메일" required="required" autofocus="autofocus" aria-label="이메일 입력 필드" autocomplete="email">

                <!-- 비밀번호 필드 -->
                <input type="password" name="password" placeholder="비밀번호" required aria-label="비밀번호 입력 필드" autocomplete="current-password">

                <!-- 로그인 버튼 -->
                <button type="submit" aria-label="로그인 버튼">로그인</button>
            </form>
            <div class="links">
                <a href="/user/signup">회원가입</a>
                <a href="/user/findpassword">비밀번호 찾기</a>
            </div>
            <div class="footer">회사 정보 - 사업자 번호, 연락처 등 추가 정보</div>
        </div>
    </div>
</body>
</html>