<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>상품 리스트</title>
<link rel="stylesheet"
	href="<c:url value='/resources/css/productListStyle.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/css/toastStyle.css' />">

</head>
<body>
	<div id="toast-container"></div>
	<!-- 토스트 메시지 컨테이너 -->
	<div class="container">
	   	<!-- 대시보드로 이동 버튼 -->
        <div style="margin-bottom: 20px; text-align: right;">
            <a href="<c:url value='/dashboard' />" class="btn-dashboard">대시보드로 이동</a>
        </div>
		<h1 class="page-title">상품 리스트</h1>
		<table class="product-table">
			<thead>
				<tr>
					<th>상품 ID</th>
					<th>상품명</th>
					<th>바코드</th>
					<th>스캔용 QR 코드</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="product" items="${products}">
					<tr>
						<td>${product.productId}</td>
						<td>${product.productName}</td>
						<td>${product.productBarcode}</td>
						<td>            
						    <c:choose>
							    <c:when test="${not empty product.qrCode and not empty product.qrCode.qrCodePath}">
							        <a href="${product.qrCode.qrCodePath}" class="btn-download" download>QR 코드 다운로드</a>
							    </c:when>
							    <c:otherwise>
							        <button class="btn-generate" data-product-id="${product.productId}">QR 코드 생성</button>
							    </c:otherwise>
							</c:choose>
		            	</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<script>
    /* QR 코드 생성 요청 */
    function generateQRCode(productId, button) {
        setButtonLoading(button, true);

        fetch(`/api/qrcode/generate`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ productId: productId }),
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    showToast(data.message, "success"); // 성공 메시지 표시
                    changeToDownloadButton(button, productId); // 다운로드 버튼으로 전환
                } else {
                    throw new Error(data.message);
                }
            })
            .catch(error => {
                console.error("QR 코드 생성 오류:", error.message);
                showToast("error", "QR 코드 생성 중 오류가 발생했습니다."); // 에러 메시지 표시
            })
            .finally(() => setButtonLoading(button, false));
    }

    /* QR 코드 다운로드 요청 */
    function downloadQRCode(productId) {
        fetch(`/api/qrcode/download`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ productId: productId }),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("다운로드 서버 오류: " + response.status);
                }
                const contentDisposition = response.headers.get("Content-Disposition");
                const fileNameMatch = contentDisposition && contentDisposition.match(/filename\*=UTF-8''(.+)/);
                const fileName = fileNameMatch ? decodeURIComponent(fileNameMatch[1]) : `qr_code_${productId}.png`;

                return response.blob().then(blob => ({ blob, fileName }));
            })
            .then(({ blob, fileName }) => {
                console.log("QR 코드 파일 다운로드 성공: ", fileName);
                const url = window.URL.createObjectURL(blob);
                const link = document.createElement("a");
                link.href = url;
                link.download = fileName;
                link.click();
                window.URL.revokeObjectURL(url);
            })
            .catch(error => {
                console.error("QR 코드 다운로드 오류: ", error.message);
                showToast("error", "QR 코드 다운로드 실패: " + error.message);
            });
    }

    /* 버튼 상태 관리 함수 */
    function changeToDownloadButton(button, productId) {
        button.textContent = "QR 코드 다운로드";

        // 버튼 스타일 업데이트
        button.classList.remove("btn-generate");
        button.classList.add("btn-download");

        // 기존 버튼 이벤트 제거 후 새 이벤트 추가
        const newButton = button.cloneNode(true); // 기존 버튼 복제
        newButton.disabled = false; // 새 버튼 활성화
        newButton.addEventListener("click", () => {
            console.log(`Download button clicked for productId: ${productId}`);
            downloadQRCode(productId);
        });
        button.replaceWith(newButton); // 버튼을 새 버튼으로 교체
    }

    /* 로딩 상태 관리 함수 */
    function setButtonLoading(button, isLoading) {
        if (isLoading) {
            button.disabled = true;
            button.textContent = "처리 중...";
        } else {
            button.disabled = false;
            button.textContent = button.getAttribute("data-original-text") || "QR 코드 생성";
        }
    }

    /* Toast 메시지 표시 함수 */
    function showToast(message, type, position = "top") {
        const toast = document.createElement("div");
        toast.className = `toast ${type} ${position}`;
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

    /* 페이지 로드 후 버튼 이벤트 추가 */
    document.addEventListener("DOMContentLoaded", () => {
        // QR 코드 생성 버튼에 이벤트 추가
        document.querySelectorAll(".btn-generate").forEach(button => {
            const productId = button.getAttribute("data-product-id");
            button.addEventListener("click", () => generateQRCode(productId, button));
        });

        // QR 코드 다운로드 버튼에 이벤트 추가
        document.querySelectorAll(".btn-download").forEach(button => {
            const productId = button.getAttribute("data-product-id");
            button.addEventListener("click", () => downloadQRCode(productId));
        });
    });
</script>
</body>
</html>
