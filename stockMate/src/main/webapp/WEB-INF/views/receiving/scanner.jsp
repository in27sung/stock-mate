<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QR 코드 및 바코드 스캔</title>
    <script src="https://unpkg.com/html5-qrcode/minified/html5-qrcode.min.js"></script>
</head>
<body>
    <h1>QR 코드 및 바코드 스캔</h1>
    <button id="start-scan">QR 코드/바코드 스캔 시작</button>
    
    <div id="reader" style="width: 300px; display: none;"></div>
    
    <p>스캔된 데이터: <span id="scan-result">여기에 스캔 결과가 표시됩니다.</span></p>
    
    <form id="qr-form" action="scanner" method="post" style="display: none;">
        <input type="hidden" id="qr-data" name="qrData">
        <button type="submit" id="submit-btn">서버로 전송</button>
    </form>

    <script>
        let scanner;

        // 스캔 시작 버튼 클릭 시
        document.getElementById('start-scan').addEventListener('click', () => {
            const readerDiv = document.getElementById('reader');
            readerDiv.style.display = 'block'; // QR 코드 및 바코드 스캔 영역 표시

            // Html5Qrcode 객체 초기화
            scanner = new Html5Qrcode("reader");

            scanner.start(
                { facingMode: "environment" }, // 후면 카메라 사용
                { fps: 10, qrbox: 250 }, // FPS 및 QR 코드 박스 크기 설정
                (decodedText) => {
                    // 스캔된 결과 출력
                    document.getElementById('scan-result').textContent = decodedText;
                    document.getElementById('qr-data').value = decodedText; // 결과를 hidden input에 저장
                    document.getElementById('qr-form').style.display = 'block'; // 서버로 전송할 수 있도록 버튼 표시
                    scanner.stop(); // 스캔이 끝난 후 중지
                },
                (error) => {
                    console.warn("스캔 오류:", error);
                }
            ).catch(err => {
                console.error("스캐너 초기화 오류:", err); // 초기화 오류
            });
        });
    </script>
</body>
</html>
