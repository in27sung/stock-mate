<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>입출고 리스트</title>
</head>
<body>
    <h1>스캔된 입출고 리스트</h1>
    <table id="shipmentList">
        <thead>
            <tr>
                <th>상품 ID</th>
                <th>창고 ID</th>
                <th>수량</th>
                <th>거래 유형</th>
            </tr>
        </thead>
        <tbody>
            <!-- 리스트가 동적으로 추가됩니다 -->
        </tbody>
    </table>

    <script>
        // Example: Fetch data and update the table dynamically
        function updateshipmentList(transaction) {
            const tableBody = document.getElementById("shipmentList").getElementsByTagName("tbody")[0];
            const row = tableBody.insertRow();
            row.innerHTML = `
                <td>${transaction.productId}</td>
                <td>${transaction.warehouseId}</td>
                <td>${transaction.quantity}</td>
                <td>${transaction.transactionType}</td>
            `;
        }

        // Example: Simulate new transaction data
        updateSeceivingList({ productId: 1, warehouseId: 2, quantity: 50, transactionType: 'INBOUND' });
    </script>
</body>
</html>