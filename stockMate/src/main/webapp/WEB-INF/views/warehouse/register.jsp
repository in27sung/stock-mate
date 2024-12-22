<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>창고 등록</title>
<link rel="stylesheet" href="<c:url value='/resources/css/registerStyle2.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/toastStyle.css' />">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
<script src="<c:url value='/resources/scripts/toast.js' />"></script>
</head>
<body>
    <div class="container">
        <button class="dashboard-btn" onclick="location.href='/dashboard';">대시보드로 돌아가기</button>
        <h1>창고 등록</h1>
        <form action="registerWarehouse" method="post">
            <!-- 사용자 ID와 비즈니스 ID -->
            <input type="hidden" name="userId" value="${userId}">
            <input type="hidden" name="businessId" value="${businessId}">
            
            <label for="warehouseName">창고 이름</label>
            <input type="text" id="warehouseName" name="warehouseName" required autofocus>
			
            <label for="warehouseRegion">창고 지역</label>
            <input type="text" id="warehouseRegion" name="warehouseRegion">
            
            <label for="warehouseLocation">상세 주소</label>
            <input type="text" id="warehouseLocation" name="warehouseLocation" required>

            <label for="warehouseCapacity">창고 용량</label>
            <input type="number" id="warehouseCapacity" name="warehouseCapacity" min="0">

            <label for="warehouseDescription">창고 상세 설명</label>
            <input type="text" id="warehouseDescription" name="warehouseDescription">

			<!-- 버튼 그룹 -->
			<div class="button-group">
				<button type="submit" class="primary-button">등록</button>
				<button type="reset" class="secondary-button">초기화</button>
			</div>
        </form>
    </div>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const form = document.querySelector("form");

            // 폼 제출 처리
            form.addEventListener("submit", function (event) {
                event.preventDefault();

                const formData = new FormData(form);
                const warehouseData = {};
                formData.forEach((value, key) => {
                    warehouseData[key] = value;
                });

                fetch("${pageContext.request.contextPath}/warehouse/register", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(warehouseData),
                })
                    .then((response) => response.json())
                    .then((data) => {
                        if (data.success) {
                            showToast(data.message, "success");
                            form.reset(); // 폼 초기화
                        } else {
                            showToast(data.message, "error");
                        }
                    })
                    .catch((error) => {
                        showToast(error.message || "서버 요청 중 오류가 발생했습니다.", "error");
                        console.error("Error:", error);
                    });
            });

            // 입력 검증 및 실시간 피드백
            const capacityInput = document.getElementById("capacity");
            capacityInput.addEventListener("input", function () {
                if (this.value < 0) {
                    this.value = 0; // 음수 방지
                }
            });
        });
    </script>
</body>
</html>
