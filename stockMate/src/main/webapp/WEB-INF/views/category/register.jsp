<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>카테고리 등록</title>
<link rel="stylesheet" href="<c:url value='/resources/css/registerStyle2.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/toastStyle.css' />">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
<script src="<c:url value='/resources/scripts/toast.js' />"></script>
<style>
    #alert-message {
        color: #007bff;
        font-size: 14px;
        margin-top: 10px;
        display: none;
    }
</style>
</head>
<body>
    <!-- 카테고리 리스트 이동 버튼 -->

	<div class="container fade-in">
		<button class="dashboard-btn" onclick="location.href='/dashboard';">대시보드</button>
		<button class="category-link-button" onclick="location.href='/category/list';" >카테고리 리스트</button>
        <h2>카테고리 등록 페이지</h2>

        <form action="/api/category/register" method="post">
            <!-- 상위 카테고리 선택 -->
            <label for="parentCategoryId">상위 카테고리:</label>
            <select id="parentCategoryId" name="parentId">
                <option value="">-- 상위 카테고리 선택 --</option>
                <c:forEach var="cat" items="${categoryList}">
                    <option value="${cat.categoryId}">${cat.categoryName}</option>
                </c:forEach>
            </select>
            <div id="alert-message">※ 상위 카테고리를 선택하지 않으면 입력한 카테고리가 대분류로 등록됩니다.</div>

            <!-- 카테고리 이름 -->
            <label for="categoryName">카테고리 이름:</label>
            <input type="text" id="categoryName" name="categoryName" required />
            <div id="name-feedback" style="color: #ff4d4d; font-size: 12px;"></div>

            <!-- 숨겨진 사업자 ID -->
            <input type="hidden" id="businessId" name="businessId" value="${sessionScope.businessId}" />

            <!-- 등록 버튼 -->
            <button type="submit" class="primary-button">등록</button>
            <div class="spinner"></div>
        </form>
    </div>
<script>
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");  // 폼 참조 추가
    const submitButton = document.querySelector("button[type='submit']");
    const spinner = document.querySelector(".spinner");
    const parentCategorySelect = document.getElementById("parentCategoryId");
    const categoryNameInput = document.getElementById("categoryName");

    // 폼 제출 이벤트
    form.addEventListener("submit", function (event) {
        event.preventDefault();  // 기본 폼 제출 방지
        
        const formData = new FormData(form);
        const plainData = Object.fromEntries(formData.entries());  // 여기서 변환 실행
        
        submitButton.classList.add("loading");
        submitButton.textContent = "등록 중...";
        spinner.style.display = "block";

        // AJAX 요청으로 폼 데이터 전송
        fetch("/api/category/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(plainData)  // JSON 데이터 전송
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("서버 응답 실패");
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                showToast("카테고리 등록 성공!", "success");
                categoryNameInput.value = "";  // 입력 필드 초기화
                updateCategoryDropdown();  // 드롭다운 업데이트
            } else {
                showToast("등록 실패: " + data.message, "error");
            }
        })
        .catch(error => {
            console.error("서버 오류:", error);
            showToast("서버 오류 발생", "error");
        })
        .finally(() => {
            submitButton.classList.remove("loading");
            submitButton.textContent = "등록";
            spinner.style.display = "none";
        });
    });

    // 카테고리 드롭다운 목록 업데이트
    function updateCategoryDropdown() {
        fetch("/api/category/list")  // 새 카테고리 목록 요청
        .then(response => {
            if (!response.ok) {
                throw new Error("목록 갱신 실패");
            }
            return response.json();
        })
        .then(categories => {
            const select = document.getElementById("parentCategoryId");
            select.innerHTML = '<option value="">-- 상위 카테고리 선택 --</option>';  // 초기화
            
            categories.forEach(cat => {
                const option = document.createElement("option");
                option.value = cat.categoryId;
                option.textContent = cat.categoryName;
                select.appendChild(option);
            });
        })
        .catch(error => {
            console.error("카테고리 목록 갱신 실패:", error);
            showToast("카테고리 목록 갱신 실패", "error");
        });
    }
});
</script>
</body>
</html>
