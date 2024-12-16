<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>실시간 입고 관리</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function () {
    $("#barcodeInput").on("keyup", function (e) {
        if (e.key === "Enter") {
            let barcode = $(this).val().trim();
            if (barcode) {
                $.ajax({
                    url: "/receiving/scan",
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({ barcode: barcode }),
                    success: function (response) {
                        if (response.success) {
                            $("#stockInfo").html("재고 업데이트 성공! 남은 재고: <b>" + response.remainingStock + "</b>");
                        } else {
                            $("#stockInfo").html("오류 발생: " + response.message);
                            if (confirm("새 제품으로 등록하시겠습니까?")) {
                                window.location.href = "/product/register";
                        	}
                        }
                    },
                    error: function () {
                        $("#stockInfo").html("<h3 style='color: red;'>서버 오류가 발생했습니다.</h3>");
                    }
                });
                $(this).val(""); // 입력창 초기화
            }
        }
    });
});
</script>
</head>
<body>
   <header>
        <h1>실시간 입고 관리 시스템</h1>
        <a href="/receiving/main">입고 메인</a> | 
        <a href="/receiving/history">입고 내역</a> | 
        <a href="/dashboard">대쉬보드</a>
    </header>
    <main>
        <div>
            <label for="barcodeInput">바코드 입력:</label>
            <input type="text" id="barcodeInput" placeholder="바코드를 입력하세요" autofocus />
        </div>
        <div id="stockInfo" style="margin-top: 20px;">
            <h3>재고 정보가 여기에 표시됩니다.</h3>
        </div>
    </main>
</body>
</html>