<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>승인 대기 사용자 리스트</title>
<%--     <link rel="stylesheet" href="<c:url value='/resources/css/productListStyle.css' />"> --%>
    <link rel="stylesheet" href="<c:url value='/resources/css/toastStyle.css' />">
    <style>
        /* 기본 스타일 */
        body {
            font-family: 'Arial', sans-serif;
            margin: 20px;
            background-color: #f8f9fa;
            color: #333;
        }
        h1 {
            text-align: center;
            color: #007bff;
        }

        /* 테이블 스타일 */
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            overflow: hidden;
        }
        thead {
            background-color: #007bff;
            color: white;
        }
        th, td {
            padding: 10px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }
        tbody tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tbody tr:hover {
            background-color: #e9ecef;
        }

        /* 버튼 스타일 */
        .approve-btn {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 5px;
            cursor: pointer;
            transition: 0.3s;
        }
        .approve-btn:hover {
            background-color: #218838;
        }

        /* 드롭다운 스타일 */
        select {
            padding: 6px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        /* 검색 입력 필드 */
        #searchInput {
            margin-bottom: 15px;
            padding: 8px;
            width: 100%;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

           /* 토스트 메시지 */
/*         #toast { */
/*             visibility: hidden; */
/*             position: fixed; */
/*             bottom: 30px; */
/*             left: 50%; */
/*             transform: translateX(-50%); */
/*             background-color: #28a745; */
/*             color: white; */
/*             padding: 12px; */
/*             border-radius: 5px; */
/*             box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3); */
/*             z-index: 999; */
/*         } */
    </style>
</head>
<body>
    <div id="toast-container"></div> <!-- 토스트 메시지 컨테이너 -->

    <div class="container">
        <button class="dashboard-btn" onclick="location.href='<c:url value="/dashboard" />'">대시보드로 이동</button>
        <h1 class="page-title">승인 대기 사용자 리스트</h1>
        
        <input type="text" id="searchInput" placeholder="이름, 이메일 등으로 검색..." />

        <table class="user-table">
            <thead>
                <tr>
                    <th>사용자 ID</th>
                    <th>이메일</th>
                    <th>이름</th>
                    <th>역할</th>
                    <th>전화번호</th>
                    <th>생성일</th>
                    <th>상태</th>
                    <th>작업</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${pendingUsers}">
                    <tr data-user-id="${user.approvedUserId}">
                        <td>${user.approvedUserId}</td>
                        <td>${user.email}</td>
                        <td>${user.userName}</td>
                        <td>${user.userRole}</td>
                        <td>${user.telNumber}</td>
                        <td>${user.createdAt}</td>
                        <td>
                            <select class="status-dropdown">
                                <option value="APPROVED" ${user.userStatus == 'APPROVED' ? 'selected' : ''}>승인</option>
                                <option value="PENDING" ${user.userStatus == 'PENDING' ? 'selected' : ''}>대기</option>
                                <option value="REJECTED" ${user.userStatus == 'REJECTED' ? 'selected' : ''}>거부</option>
                            </select>
                        </td>
                        <td>
                            <button class="btn-save" data-user-id="${user.approvedUserId}">저장</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
	<script>
	document.addEventListener("DOMContentLoaded", () => {
	    /* 검색 기능 */
	    document.getElementById("searchInput").addEventListener("keyup", function () {
	        const searchValue = this.value.toLowerCase();
	        document.querySelectorAll(".user-table tbody tr").forEach(row => {
	            const rowText = row.textContent.toLowerCase();
	            row.style.display = rowText.includes(searchValue) ? "" : "none";
	        });
	    });

	    /* 승인 상태 저장 요청 */
	    function saveUserStatus(approvedUserId, userStatus, button) {
	        setButtonLoading(button, true);

	        fetch(`/api/admin/approve`, {
	            method: "POST",
	            headers: { "Content-Type": "application/json" },
	            body: JSON.stringify({ approvedUserId: approvedUserId, userStatus: userStatus }),
	        })
	            .then(response => {
	                if (!response.ok) {
	                    throw new Error(`Error: ${response.status}`);
	                }
	                return response.json();
	            })
	            .then(data => {
	                if (data.success) {
	                    showToast("상태가 성공적으로 업데이트되었습니다.", "success");
	                } else {
	                    throw new Error(data.message);
	                }
	            })
	            .catch(error => {
	                console.error("Error updating user status:", error.message);
	                showToast("상태 업데이트 실패: " + error.message, "error");
	            })
	            .finally(() => setButtonLoading(button, false));
	    }

	    /* 버튼 상태 관리 */
	    function setButtonLoading(button, isLoading) {
	        button.disabled = isLoading;
	        button.textContent = isLoading ? "저장 중..." : "저장";
	    }

	    /* 토스트 메시지 표시 */
	    function showToast(message, type) {
	        const toast = document.createElement("div");
	        toast.className = `toast ${type}`;
	        toast.textContent = message;

	        document.body.appendChild(toast);
	        setTimeout(() => {
	            toast.classList.add("show");
	        }, 10);

	        setTimeout(() => {
	            toast.classList.remove("show");
	            setTimeout(() => {
	                document.body.removeChild(toast);
	            }, 300);
	        }, 4000);
	    }

	    /* 드롭다운 초기화 및 클릭 제한 */
	    document.querySelectorAll(".status-dropdown").forEach(dropdown => {
	        // 초기 상태를 "대기"로 설정
	        dropdown.value = "PENDING";

	        // 클릭 시 "승인" 또는 "거부"만 선택 가능하도록 설정
	        dropdown.addEventListener("mousedown", function () {
	            const pendingOption = dropdown.querySelector('option[value="PENDING"]');
	            if (pendingOption) {
	                pendingOption.remove();
	            }
	        });
	    });

	    /* 저장 버튼 이벤트 추가 */
	    document.querySelectorAll(".btn-save").forEach(button => {
	        button.addEventListener("click", () => {
	            const $row = button.closest("tr");
	            const approvedUserId = $row.getAttribute("data-user-id"); // approvedUserId로 변경
	            const userStatus = $row.querySelector(".status-dropdown").value;

	            // 디버깅: 값 확인
	            console.log(`Saving User Status - Approved User ID: ${approvedUserId}, User Status: ${userStatus}`);

	            if (!approvedUserId || !userStatus) {
	                console.error("Invalid Data: Missing Approved User ID or User Status");
	                alert("유효하지 않은 데이터입니다. 다시 시도해주세요.");
	                return;
	            }

	            // API 요청
	            saveUserStatus(approvedUserId, userStatus, button);
	        });
	    });
	});
   </script>
</body>
</html>
