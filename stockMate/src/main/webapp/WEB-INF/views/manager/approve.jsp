<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>승인 대기 직원 리스트</h1>
<input type="text" id="searchInput" placeholder="이름, 이메일 등으로 검색..." />
<table>
    <thead>
        <tr>
            <th>직원 ID</th>
            <th>이메일</th>
            <th>이름</th>
            <th>역할</th>
            <th>전화번호</th>
            <th>생성일</th>
            <th>상태</th>
            <th>작업</th>
        </tr>
    </thead>
    <tbody id="staffTableBody"></tbody>
</table>

<script>
    // Staff 리스트 조회
    fetch('/api/manager/staff')
        .then(response => response.json())
        .then(staffList => {
            const tableBody = document.getElementById('staffTableBody');
            staffList.forEach(staff => {
                const row = `
                    <tr data-user-id="${staff.approvedUserId}">
                        <td>${staff.approvedUserId}</td>
                        <td>${staff.email}</td>
                        <td>${staff.userName}</td>
                        <td>${staff.userRole}</td>
                        <td>${staff.telNumber}</td>
                        <td>${staff.createdAt}</td>
                        <td>
                            <select class="status-dropdown">
                                <option value="APPROVED">승인</option>
                                <option value="PENDING" selected>대기</option>
                                <option value="REJECTED">거부</option>
                            </select>
                        </td>
                        <td>
                            <button class="btn-save">저장</button>
                        </td>
                    </tr>`;
                tableBody.innerHTML += row;
            });
        });

    // 저장 버튼 클릭 이벤트
    document.addEventListener("click", event => {
        if (event.target.classList.contains("btn-save")) {
            const row = event.target.closest("tr");
            const userId = row.getAttribute("data-user-id");
            const status = row.querySelector(".status-dropdown").value;

            fetch('/api/manager/approve', {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ approvedUserId: userId, userStatus: status }),
            })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
                if (data.success) row.remove();
            });
        }
    });
</script>