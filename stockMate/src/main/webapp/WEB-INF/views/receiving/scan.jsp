<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>QR 코드 스캔 및 상품 정보</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html5-qrcode/2.2.1/html5-qrcode.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/receivingScanStyle.css">
<script>
$(document).ready(function () {
    $("#barcodeInput").on("keyup", function (e) {
        if (e.key === "Enter") {
            let barcode = $(this).val().trim();
            let receivingShipmentNo = $("#receivingShipmentNo").val().trim();
            let orderItemId = $("#orderItemId").val().trim();
            if ((barcode && receivingShipmentNo && orderItemId)) {
                $.ajax({
                    url: "/receiving/scan",
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        barcode: barcode,
                        receivingShipmentNo: parseInt(receivingShipmentNo),
                        orderItemId: parseInt(orderItemId)
                    }),
                    success: function (response) {
                        if (response.success) {
                            $("#stockInfo").html(
                                "<table border='1' style='border-collapse: collapse; width: 100%;'>" +
                                    "<thead>" +
                                        "<tr>" +
                                            "<th>제품명</th>" +
                                            "<th>총 재고량</th>" +
                                            "<th>입고처리할 남은 수량</th>" +
                                            "<th>단가</th>" +
                                        "</tr>" +
                                    "</thead>" +
                                    "<tbody>" +
                                        "<tr>" +
                                            "<td>" + response.productName + "</td>" +
                                            "<td>" + response.remainingStock + "</td>" +
                                            "<td>" + response.reservedQuantity + "</td>" +
                                            "<td>" + response.productPrice + "</td>" +
                                        "</tr>" +
                                    "</tbody>" +
                                "</table>"
                            );
                        } else {
                            alert("더이상 입고할 수량이 없습니다.");
                            if(confirm("발주를 새로 등록 하시겠습니까?")) {
                                window.location.href = "/order/register";
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
        <nav>
        <h1>실시간 입고 관리 시스템</h1>
        <a href="/receiving/main">입고 메인</a>
        <a href="/receiving/history">입고 내역</a>
        <a href="/dashboard">대쉬보드</a>
           </nav>
    </header>
 
    <main>
		<div>
		    <label for="receivingShipmentNo">입고 번호:</label>
		    <input type="number" id="receivingShipmentNo" value="${receivingShipmentNo }">
		</div>
		<div>
            <label for="orderItemId">주문 항목 ID:</label>
            <input type="number" id="orderItemId" value="${orderItemId }" />
        </div>
        <div>
            <label for="barcodeInput">바코드 입력:</label>
            <input type="text" id="barcodeInput" placeholder="바코드를 입력하세요" autofocus />
        </div>
        <div id="stockInfo" style="margin-top: 20px;">
        
            <h3>재고 정보가 여기에 표시됩니다.</h3>
        </div>
        <!-- 입고 내역 히스토리 -->
		<table border="1">
			<tr>
				<th>입고 번호</th>
				<th>입고 출고</th>
				<th>입고 일자</th>
				<th>입고 상태</th>
				<th>제품 번호</th>
				<th>제품명</th>
				<th>옵션명</th>
				<th>입고 수량</th>
				<th>제품 단가</th>
				<th>창고 위치</th>
				<th>작업 메모</th>
			</tr>
			<c:forEach var="vo" items="${rsn }">
				<tr>
					<td>${vo.receivingShipmentNo}</td>
					<td>     
		                 <c:choose>
		                    <c:when test="${vo.transactionType == 'INBOUND'}">입고</c:when>
		                    <c:when test="${vo.transactionType == 'OUTBOUND'}">출고</c:when>
		                    <c:otherwise>${vo.transactionType}</c:otherwise>
		                </c:choose>
		            </td>
					<td><fmt:formatDate value="${vo.createdAt}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
			            <c:choose>
			                <c:when test="${vo.status == 'PENDING'}">
			                    대기중
			                </c:when>
			                <c:when test="${vo.status == 'COMPLETED'}">
			                    완료됨
			                </c:when>
			                <c:otherwise>
			                    ${vo.status}
			                </c:otherwise>
			            </c:choose>
			        </td>
					<td>${vo.productId }</td>
					<td>${vo.productName }</td>
					<td>${vo.productDescription }</td>
					<td>${vo.changeQuantity }</td>
					<td>${vo.productPrice }</td>
					<td>${vo.warehouseId }</td>
					<td>${vo.memo }</td>
				</tr>
				</c:forEach>
		</table>
    </main>

</body>
</html>
