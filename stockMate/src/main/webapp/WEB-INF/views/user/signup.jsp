<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="stylesheet" href="style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #333;
        }

        .signup-container {
            width: 600px; /* 컨테이너 폭 */
            padding: 20px 25px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
            text-align: center;
        }

        .signup-container h2 {
            color:#25498a;
            margin-bottom: 30px; /* 제목과 폼 간 거리 */
            font-size: 24px;
        }

        .signup-container form {
            display: flex;
            flex-direction: column;
            gap: 20px; /* 입력 필드 간 거리 */
        }

        .signup-container input,
        .signup-container select {
            width: 100%;
            padding: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }

        .signup-container input::placeholder {
            color: #aaa;
        }

        .buttons {
            display: flex;
            justify-content: space-between;
            gap: 10px; /* 버튼 간 간격 */
            padding-bottom: 25px;
        }

        .btn {
            flex: 1;
            padding: 15px 0;
            font-size: 16px;
            font-weight: bold;
            border-radius: 5px;
            border: none;
            color: #fff;
            cursor: pointer;
            text-align: center;
        }

        .btn-primary {
            background-color:#25498a;
        }

        .btn-primary:hover {
            background-color: #ed8620;
        }
/* 
        .btn-secondary {
            background-color: #ed8620;
        }

        .btn-secondary:hover {
            background-color: #eb9744;;
        }
 */
        .footer {
            margin-top: 30px;
            font-size: 14px;
            color: #aaa;
            text-align: center;
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
			width : 80%;
			margin-bottom:-70px;
		}
		
		 .container {
           
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            position: relative;
      	 	top: -35px;
         
        } 
		
    </style>
</head>
<body>

<div class="container">
	<div class="logo">
			<a href="http://c7d2408t1p1.itwillbs.com/">
				<img alt="" src="../resources/img/stockmate-removebg-preview.png">
			</a>
		</div>
		
    <div class="signup-container">
		
        <h2>회원가입</h2>

        <form action="/user/signup" method="post">
            <input type="email" placeholder="이메일" name="email" required>
            <input type="password" placeholder="비밀번호" name="password" required>
            <input type="text" placeholder="이름" name="userName" required>
            <input type="tel" placeholder="전화번호" name="telNumber" required>

            <!-- 역할 드롭다운 메뉴 -->
            <select name="userRole" required>
                <option value="" disabled selected>역할 선택</option>
                <option value="ADMIN">관리자</option>
                <option value="MANAGER">매니저</option>
                <option value="STAFF">직원</option>
            </select>

            <div class="buttons">
                <!-- 가입하기 버튼 -->
                <button type="submit" class="btn btn-primary">가입하기</button>
                
                <!-- 메인페이지로 버튼 -->
                <!-- <button type="button" class="btn btn-secondary" onclick="location.href='http://localhost:8088/'">메인페이지로</button> -->
            </div>
        </form>
        
        </div>

        <div class="footer">
            회사 정보 - 사업자 번호, 연락처 등 유의 내용
        </div>
    </div>
</body>
</html>