<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비즈니스 인증</title>
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
            top: -72px;
         
        } 
        .login-box {
            width: 410px;
          
               background: #fff;
			    border-radius: 12px;
			    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
			    padding: 30px 30px;
			    text-align: center;
            
         
        }

        
        .login-box input[type="text"]{
            width: 100%;
            height: 55px;
            padding: 12px;
            margin-bottom: 14px;
            margin-top : 8px;
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
            margin-top: 10px;
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
		
		h2{
			color:#25498a;
            margin-bottom: 30px; /* 제목과 폼 간 거리 */
            font-size: 22px;
		}
		
    </style>
    
</head>
<body>

 <!-- http://localhost:8088/business/verify -->
 
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
			<a href="http://localhost:8088/">
				<img alt="" src="../resources/css/stockmate-removebg-preview.png">
			</a>
</div>



<div class="login-box">

<h2>비즈니스 인증</h2>	
    <form action="/business/verify" method="post">
   
        <input type="text" id="businessNumber" name="businessNumber" required placeholder="사업자 등록 번호">
    
        <input type="text" id="businessName" name="businessName" required placeholder="회사 이름">
      
        <button type="submit">비즈니스 인증</button>
    </form>
    
    </div>
    
    </div>
</body>
</html>
