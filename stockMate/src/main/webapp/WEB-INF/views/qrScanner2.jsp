<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>QR 코드 스캔 및 상품 정보</title>
<link rel="stylesheet"
	href="<c:url value='/resources/css/qrScannerStyle.css' />">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/html5-qrcode/2.2.1/html5-qrcode.min.js"></script>
<style>
/* 스타일 정의 */
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
	margin-bottom: 20px;
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
	    const cameraSelect = document.getElementById('cameraSelect');
	    const startScannerBtn = document.getElementById('startScannerBtn');
	    const reader = new Html5Qrcode("reader");
	    const productList = document.getElementById('productList');
	    const scannedProducts = {};
	    
        // 카메라 목록 불러오기
        Html5Qrcode.getCameras().then(devices => {
            if (devices && devices.length) {
                devices.forEach((device, index) => {
                    const option = document.createElement('option');
                    option.value = device.id;
                    option.textContent = device.label || `Camera ${index + 1}`;
                    cameraSelect.appendChild(option);
                });
            }
        }).catch(err => console.error("카메라 목록 불러오기 오류:", err));
       
        // 스캐너 시작/종료 버튼 이벤트
        toggleScannerBtn.addEventListener('click', () => {
            if (scannerRunning) {
                stopScanner();
            } else {
                startScanner();
            }
        });
        
        // 스캐너 시작
        function startScanner() {
            const cameraId = cameraSelect.value || { facingMode: "environment" }; // 후면 카메라 기본값
            reader.start(
                cameraId,
                { fps: 10, qrbox: 250 },
                onScanSuccess,
                onScanError
            ).then(() => {
                scannerRunning = true;
                toggleScannerBtn.textContent = "웹캠 끄기";
            }).catch(err => console.error("웹캠 시작 오류:", err));
        }
        
     	// 웹캠 시작 버튼 클릭 이벤트
        startScannerBtn.addEventListener('click', () => {
            const selectedCameraId = cameraSelect.value; // 선택된 카메라 ID
            if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
                alert("브라우저가 카메라 접근을 지원하지 않습니다.");
                return;
            }

            reader.start(
                selectedCameraId, // 선택된 카메라로 스캔
                { fps: 10, qrbox: 250 },
                onScanSuccess,
                onScanError
            ).catch(err => console.error("웹캠 시작 오류:", err));
        });
     	
        // 스캐너 종료
        function stopScanner() {
            reader.stop().then(() => {
                scannerRunning = false;
                toggleScannerBtn.textContent = "웹캠 시작";
            }).catch(err => console.error("웹캠 종료 오류:", err));
        }

        // QR 코드 스캔 성공 시 호출
        function onScanSuccess(decodedText) {
            if (!isValidBarcode(decodedText)) {
                alert("잘못된 QR 코드입니다.");
                return;
            }

            if (scannedProducts[decodedText]) {
                // 중복 스캔 시 수량만 증가
                scannedProducts[decodedText].quantity++;
                updateTableRow(decodedText);
                return;
            }

            fetch("/api/qrcode/scan", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "X-CSRF-TOKEN": getCsrfToken() // CSRF 보호
                },
                body: JSON.stringify({ productId: decodedText })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    scannedProducts[decodedText] = { ...data, quantity: 1 };
                    addTableRow(scannedProducts[decodedText]);
                } else {
                    alert("상품 정보를 불러올 수 없습니다.");
                }
            })
            .catch(error => console.error("오류:", error));
        }

        // QR 코드 스캔 실패 시 호출
        function onScanError(errorMessage) {
		    console.warn("스캔 오류:", errorMessage);
		    document.getElementById('reader').style.borderColor = 'red'; // 오류 시 테두리 강조
		    setTimeout(() => {
		        document.getElementById('reader').style.borderColor = '#ddd';
		    }, 1000);
		}

        // 상품 테이블에 추가
        function addTableRow(product) {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${product.productName}</td>
                <td>${product.productBarcode}</td>
                <td>${product.productPrice}</td>
                <td id="quantity-${product.productBarcode}">1</td>
            `;
            productList.appendChild(row);
        }

        // 수량 업데이트
        function updateTableRow(barcode) {
            const quantityCell = document.getElementById(`quantity-${barcode}`);
            quantityCell.textContent = scannedProducts[barcode].quantity;
        }
        // CSRF 토큰 가져오기 (서버가 발급해야 함)
//      function getCsrfToken() {
//          return document.querySelector('meta[name="csrf-token"]')?.content || '';
//      }

        // 바코드 값 검증
        function isValidBarcode(barcode) {
            return /^[A-Za-z0-9_-]+$/.test(barcode); // 간단한 검증: 알파벳, 숫자, 언더스코어 허용
        }
    </script>
</body>
</html>
