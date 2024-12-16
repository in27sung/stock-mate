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
	    const beepSound = new Audio("/resources/audio/beep.mp3"); // 비프 소리 경로

	    let reader = new Html5Qrcode("reader");
	    let scannerRunning = false;
	    const scannedProducts = {}; // 스캔된 상품 수량 추적 객체
	    let isScanning = false; // 중복 스캔 방지 플래그

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
	        .catch(err => {
	            console.error("카메라 목록 불러오기 오류:", err);
	            alert("카메라 정보를 불러오지 못했습니다. 브라우저 권한을 확인해주세요.");
	        });

	    // **스캐너 시작/종료 버튼 이벤트**
	    toggleScannerBtn.addEventListener('click', () => {
	        if (scannerRunning) stopScanner();
	        else startScanner();
	    });

	    // **스캐너 시작**  
	    function startScanner() {
	        const cameraId = cameraSelect.value || { facingMode: "environment" }; // 기본 설정  
	        reader.start(
	            cameraId,
	            { fps: 10, qrbox: 250 },
	            onScanSuccess,
	            onScanError
	        ).then(() => {
	            scannerRunning = true;
	            toggleScannerBtn.textContent = "웹캠 끄기";
	        }).catch(err => {
	            console.error("스캐너 시작 오류:", err);
	            alert("스캐너를 시작할 수 없습니다. 다른 카메라를 선택하거나 권한을 확인해주세요.");
	        });
	    }

	    // **스캐너 종료**  
	    function stopScanner() {
	        reader.stop().then(() => {
	            scannerRunning = false;
	            toggleScannerBtn.textContent = "웹캠 시작";
	        }).catch(err => console.error("스캐너 종료 오류:", err));
	    }

	    // **스캔 성공 시 호출**  
	    function onScanSuccess(decodedText) {
	        if (isScanning) return; // 중복 방지  
	        isScanning = true;
	        beepSound.play();
	        console.log("Scanned Text:", decodedText); // 디버깅 로그 추가

	        if (scannedProducts[decodedText]) {
	            scannedProducts[decodedText].quantity++;
	            updateTableRow(decodedText);
	            isScanning = false;
	        } else {
	            fetch("/api/qrcode/scan", {
	                method: "POST",
	                headers: { "Content-Type": "application/json" },
	                body: JSON.stringify({ productId: decodedText }) // productId 전송
	            })
	            .then(response => response.json())
	            .then(data => {
	                console.log("Server Response:", data); // 서버 응답 확인 (디버깅)
	                if (data.success) {
	                    const product = {
	                        productName: data.productName,
	                        productBarcode: data.productBarcode,
	                        productPrice: data.productPrice
	                    };
	                    scannedProducts[decodedText] = { ...product, quantity: 1 };
	                    addTableRow(decodedText, scannedProducts[decodedText]);
	                } else {
	                    alert("상품 정보를 찾을 수 없습니다.");
	                }
	            })
	            .catch(err => console.error("오류 발생:", err))
	            .finally(() => isScanning = false);
	        }
	    }

	    // **스캔 실패 시 호출**  
	    function onScanError(errorMessage) {
	        console.warn("스캔 오류:", errorMessage);
	    }

	    // **테이블에 상품 정보 추가**  
	    function addTableRow(barcode, product) {
	        const tr = document.createElement('tr');

	        const tdName = document.createElement('td');
	        tdName.textContent = product.productName || "N/A";

	        const tdBarcode = document.createElement('td');
	        tdBarcode.textContent = product.productBarcode || "N/A";

	        const tdPrice = document.createElement('td');
	        tdPrice.textContent = product.productPrice || "N/A";

	        const tdQuantity = document.createElement('td');
	        tdQuantity.id = `quantity-${barcode}`;
	        tdQuantity.textContent = "1";

	        tr.appendChild(tdName);
	        tr.appendChild(tdBarcode);
	        tr.appendChild(tdPrice);
	        tr.appendChild(tdQuantity);

	        productListBody.appendChild(tr);
	    }

	    // **수량 업데이트**  
	    function updateTableRow(barcode) {
	        const quantityCell = document.getElementById(`quantity-${barcode}`);
	        quantityCell.textContent = scannedProducts[barcode].quantity;
	    }
	});
    </script>
</body>
</html>
