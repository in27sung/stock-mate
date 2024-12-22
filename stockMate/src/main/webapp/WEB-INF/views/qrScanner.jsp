<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>QR 코드 스캔 및 상품 정보</title>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/html5-qrcode/2.2.1/html5-qrcode.min.js"></script>
<style>
body {
	font-family: Arial, sans-serif;
	text-align: center;
	margin: 0;
	padding: 0;
}

h1 {
	margin-top: 20px;
}

.scanner-container {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	margin: 20px auto;
	max-width: 600px;
}

#reader {
	width: 100%;
	max-width: 500px;
	height: 400px;
	border: 2px solid #ddd;
}

button {
	padding: 10px 20px;
	font-size: 16px;
	cursor: pointer;
}

table {
	width: 80%;
	margin: 20px auto;
	border-collapse: collapse;
}

th, td {
	border: 1px solid #ddd;
	padding: 10px;
	text-align: center;
}
</style>
</head>
<body>
	<h1>QR 코드 스캔 및 상품 정보</h1>

	<!-- 카메라 선택 및 제어 버튼 -->
	<div class="scanner-container">
		<select id="cameraSelect"></select>
		<button id="toggleScannerBtn">웹캠 시작</button>
		<div id="reader"></div>
	</div>

	<table>
		<thead>
			<tr>
				<th>상품명</th>
				<th>바코드</th>
				<th>단가</th>
				<th>수량</th>
			</tr>
		</thead>
		<tbody id="productList"></tbody>
	</table>

	<script>
	document.addEventListener('DOMContentLoaded', () => {
	    const cameraSelect = document.getElementById('cameraSelect');
	    const toggleScannerBtn = document.getElementById('toggleScannerBtn');
	    const productListBody = document.getElementById('productList');
	    const beepSound = new Audio("/resources/audio/beep.mp3");

	    let reader = new Html5Qrcode("reader");
	    let scannerRunning = false;
	    const scannedProducts = {}; // productId 기반 상품 추적 객체
	    let lastScannedTime = 0;

	    // **카메라 목록 불러오기**
	    Html5Qrcode.getCameras()
	        .then(devices => {
	            if (devices.length) {
	                devices.forEach((device, index) => {
	                    const option = document.createElement('option');
	                    option.value = device.id;
	                    option.textContent = device.label || `Camera ${index + 1}`;
	                    cameraSelect.appendChild(option);
	                });
	            } else {
	                alert("카메라를 찾을 수 없습니다. 권한을 확인해주세요.");
	            }
	        })
	        .catch(err => console.error("카메라 목록 오류:", err));

	    // **스캐너 시작/종료 이벤트**
	    toggleScannerBtn.addEventListener('click', () => {
	        if (scannerRunning) stopScanner();
	        else startScanner();
	    });

	    function startScanner() {
	        const cameraId = cameraSelect.value || { facingMode: "environment" };
	        reader.start(
	            cameraId,
	            { fps: 10, qrbox: 250 },
	            onScanSuccess,
	            onScanError
	        ).then(() => {
	            scannerRunning = true;
	            toggleScannerBtn.textContent = "웹캠 끄기";
	        }).catch(err => console.error("스캐너 오류:", err));
	    }

	    function stopScanner() {
	        reader.stop().then(() => {
	            scannerRunning = false;
	            toggleScannerBtn.textContent = "웹캠 시작";
	        });
	    }

	    // **스캔 성공 시 호출**
	    function onScanSuccess(decodedText) {
	        const currentTime = new Date().getTime();
	        if (currentTime - lastScannedTime < 1000) return; // 1초 제한
	        lastScannedTime = currentTime;

	        beepSound.play();

	        fetch("/api/qrcode/scan", {
	            method: "POST",
	            headers: { "Content-Type": "application/json" },
	            body: JSON.stringify({ productId: decodedText }) // QR 코드 데이터 전송
	        })
	        .then(response => response.json())
	        .then(data => {
	            if (data.success) {
	                const productId = data.productId; // 서버에서 반환된 productId 사용
	                if (scannedProducts[productId]) {
	                    // 이미 스캔된 상품의 수량 증가
	                    scannedProducts[productId].quantity++;
	                    updateTableRow(productId);
	                } else {
	                    // 새로운 상품 추가
	                    scannedProducts[productId] = {
	                        productName: data.productName,
	                        productBarcode: data.productBarcode,
	                        productPrice: data.productPrice,
	                        quantity: 1
	                    };
	                    addTableRow(productId, scannedProducts[productId]);
	                }
	            } else {
	                alert("상품 정보를 찾을 수 없습니다.");
	            }
	        })
	        .catch(err => console.error("오류 발생:", err));
	    }

	    function onScanError(errorMessage) {
	        console.warn("스캔 오류:", errorMessage);
	    }

	    // **테이블에 상품 정보 추가**
	    function addTableRow(productId, product) {
	        const tr = document.createElement('tr');

	        const tdName = document.createElement('td');
	        tdName.textContent = product.productName || "N/A";

	        const tdBarcode = document.createElement('td');
	        tdBarcode.textContent = product.productBarcode || "N/A";

	        const tdPrice = document.createElement('td');
	        tdPrice.textContent = product.productPrice || "N/A";

	        const tdQuantity = document.createElement('td');
	        tdQuantity.id = `quantity-${productId}`;
	        tdQuantity.textContent = product.quantity;

	        tr.appendChild(tdName);
	        tr.appendChild(tdBarcode);
	        tr.appendChild(tdPrice);
	        tr.appendChild(tdQuantity);

	        productListBody.appendChild(tr);
	    }

	    // **수량 업데이트**
	    function updateTableRow(productId) {
	        const quantityCell = document.getElementById(`quantity-${productId}`);
	        if (quantityCell) {
	            quantityCell.textContent = scannedProducts[productId].quantity;
	        }
	    }
	});
    </script>
</body>
</html>