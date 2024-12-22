<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>재고 등록</title>
<link rel="stylesheet" href="<c:url value='/resources/css/registerStyle2.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/toastStyle.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/modalStyle.css' />">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
<script src="<c:url value='/resources/scripts/toast.js' />"></script>
</head>
<body>
    <div class="container fade-in">
        <button class="dashboard-btn" onclick="location.href='/dashboard';">대시보드로 돌아가기</button>
        <button class="dashboard-btn" onclick="location.href='/stock/list';">재고 리스트</button>
        <h1>재고 등록</h1>
		<form action="<c:url value='/stock/register' />" method="post">            <!-- 창고 선택 -->
            <div class="form-group">
                <label for="warehouseSelect">창고 선택</label>
                <select id="warehouseSelect" name="warehouseId" required>
                    <option value="">창고 목록을 불러오는 중...</option>
                </select>
            </div>
            <!-- 상품 선택 -->
            <div class="form-group">
                <label>상품 선택</label><br>
                <button type="button" id="productSelectBtn" class="secondary-button">상품 선택</button>
                <input type="text" id="selectedProduct" placeholder="선택된 상품이 표시됩니다" readonly>
                <input type="hidden" id="selectedProductId" name="productId">
            </div>
            <!-- 수량 입력 -->
            <div class="form-group">
                <label for="totalQuantity">초기 수량</label>
                <input type="number" id="totalQuantity" name="totalQuantity" min="1" required>
            </div>
            <!-- 재고 설명 입력 -->
			<div class="form-group">
			    <label for="description">재고 설명</label>
			    <textarea id="description" name="description" rows="3" placeholder="재고에 대한 설명을 입력하세요"></textarea>
			</div>
            <!-- 제출 버튼 -->
            <button type="submit" class="primary-button">재고 등록</button>
        </form>
        <div class="spinner" id="globalSpinner"></div>
    </div>
	<!-- 상품 목록 모달 -->
	<div class="modal-backdrop" id="productModal">
	    <div class="modal-container">
	        <!-- 모달 헤더 -->
	        <div class="modal-header">
	            <h5 class="modal-title">상품 선택</h5>
	            <!-- X 버튼: 오른쪽 상단에 배치 -->
	            <button class="close" id="closeProductModal">&times;</button>
	        </div>
	
	        <!-- 모달 바디 -->
	        <div class="modal-body">
	            <!-- 검색 필드: 테이블 우측 상단에 배치 -->
            	<div class="search-container">
	                <input type="text" id="productSearch" placeholder="상품명 검색..." class="search-input">
	                <i class="fa fa-search search-icon"></i>
	            </div>
	
	            <!-- 로딩 스피너 -->
	            <div class="spinner hidden" id="productSpinner"></div>
	
	            <!-- 상품 목록 테이블 -->
	            <div class="table-container">
	                <table class="table" id="productTable">
	                    <thead>
	                        <tr>
	                            <th>바코드</th>
	                            <th>상품명</th>
	                            <th>단위</th>
	                            <th>세트크기</th>
	                            <th>단가</th>
	                            <th>선택</th>
	                        </tr>
	                    </thead>
	                    <tbody id="productListBody"></tbody>
	                </table>
	            </div>
	
	            <!-- 상품이 없을 때 메시지 -->
	            <div id="noProductMsg" class="hidden">상품이 없습니다.</div>
	        </div>
	    </div>
	</div>
	<c:if test="${not empty toastMessage}">
	    <script>
	        const toastMessage = "${toastMessage}";
	        const toastType = "${toastType}";
	        document.addEventListener("DOMContentLoaded", () => {
	            showToast(toastMessage, toastType);
	        });
	    </script>
	</c:if>
	<script>
	document.addEventListener('DOMContentLoaded', () => {
    loadWarehouses();

    const contextPath = "${pageContext.request.contextPath}";
    const productSelectBtn = document.getElementById('productSelectBtn');
    const productModal = document.getElementById('productModal');
    const closeProductModal = document.getElementById('closeProductModal');
    const productTable = document.getElementById('productTable');
    const productListBody = document.getElementById('productListBody');
    const productSpinner = document.getElementById('productSpinner');
    const noProductMsg = document.getElementById('noProductMsg');
    const productSearch = document.getElementById('productSearch');
    const form = document.querySelector("form");

    productSelectBtn.addEventListener('click', () => {
        productModal.classList.add('active');
        loadProducts();
    });

    closeProductModal.addEventListener('click', () => {
        productModal.classList.remove('active');
    });

    productSearch.addEventListener('input', () => {
        const query = productSearch.value.toLowerCase();
        Array.from(productListBody.querySelectorAll('tr')).forEach(row => {
            const name = row.querySelector('.p-name').textContent.toLowerCase();
            row.style.display = name.includes(query) ? '' : 'none';
        });
    });

    productListBody.addEventListener('click', e => {
        if (e.target.classList.contains('select-product-btn')) {
            const productId = e.target.dataset.id;
            const productName = e.target.dataset.name;
            document.getElementById('selectedProduct').value = productName;
            document.getElementById('selectedProductId').value = productId;
            productModal.classList.remove('active');
        }
    });
});

function loadWarehouses() {
    fetch('/api/stock/warehouses')
        .then(res => res.json())
        .then(data => {
            const warehouseSelect = document.getElementById('warehouseSelect');
            warehouseSelect.innerHTML = '<option value="">창고를 선택하세요</option>';
            if (Array.isArray(data) && data.length > 0) {
                data.forEach(wh => {
                    const opt = document.createElement('option');
                    opt.value = wh.warehouseId;
                    opt.textContent = wh.warehouseName;
                    warehouseSelect.appendChild(opt);
                });
            } else {
                const opt = document.createElement('option');
                opt.value = '';
                opt.textContent = '창고 없음';
                warehouseSelect.appendChild(opt);
            }
        })
        .catch(err => {
            console.error('창고 정보를 불러오는 중 오류:', err);
            const warehouseSelect = document.getElementById('warehouseSelect');
            warehouseSelect.innerHTML = '<option value="">창고 불러오기 실패</option>';
        });
}

function loadProducts() {
    const productSpinner = document.getElementById('productSpinner');
    const productTable = document.getElementById('productTable');
    const productListBody = document.getElementById('productListBody');
    const noProductMsg = document.getElementById('noProductMsg');

    productSpinner.style.display = 'block';
    productTable.classList.add('hidden');
    noProductMsg.classList.add('hidden');

    fetch('/api/stock/products')
        .then(res => res.json())
        .then(data => {
            productSpinner.style.display = 'none';
            productListBody.innerHTML = ''; // 기존 테이블 초기화

            if (Array.isArray(data) && data.length > 0) {
                data.forEach(product => {
                    const tr = document.createElement('tr');

                    // 순서: 바코드, 상품명, 단위, 세트크기, 단가, 선택
                    const tdBarcode = document.createElement('td');
                    tdBarcode.textContent = product.productBarcode || 'N/A';

                    const tdName = document.createElement('td');
                    tdName.classList.add('p-name');
                    tdName.textContent = product.productName || 'N/A';

                    const tdUnit = document.createElement('td');
                    tdUnit.textContent = product.baseUnit || 'N/A';

                    const tdSetSize = document.createElement('td');
                    tdSetSize.textContent = product.setSize != null ? product.setSize : '0';

                    const tdPrice = document.createElement('td');
                    tdPrice.textContent = product.productPrice != null ? product.productPrice : 'N/A';

                    const tdButton = document.createElement('td');
                    const button = document.createElement('button');
                    button.className = 'btn btn-success select-product-btn';
                    button.setAttribute('data-id', product.productId || '');
                    button.setAttribute('data-name', product.productName || 'N/A');
                    button.textContent = '선택';

                    tdButton.appendChild(button);

                    tr.appendChild(tdBarcode);
                    tr.appendChild(tdName);
                    tr.appendChild(tdUnit);
                    tr.appendChild(tdSetSize);
                    tr.appendChild(tdPrice);
                    tr.appendChild(tdButton);

                    productListBody.appendChild(tr);
                });
                productTable.classList.remove('hidden'); // 테이블 표시
                // 성공 토스트 메시지
                showToast("상품 목록이 성공적으로 불러왔습니다.", "success");
            } else {
                noProductMsg.classList.remove('hidden');
                noProductMsg.textContent = '상품이 없습니다.';
                showToast("상품이 없습니다.", "info");
            }
        })
        .catch(err => {
            productSpinner.style.display = 'none';
            noProductMsg.classList.remove('hidden');
            noProductMsg.textContent = '상품 불러오기 실패';
            console.error("오류 발생:", err);

            // 실패 토스트 메시지
            showToast("상품 목록을 불러오는 중 오류가 발생했습니다.", "error");
        });
    
 	// 폼 제출 처리
    form.addEventListener("submit", function (event) {
        event.preventDefault(); // 기본 폼 제출 방지
        const formData = new FormData(form);
        const stockData = {};

        // FormData 데이터를 JSON 형식으로 변환
        formData.forEach((value, key) => stockData[key] = value);

        // 재고 등록 요청
        fetch(`${contextPath}/stock/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(stockData),
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    showToast(data.message, "success"); // 성공 토스트 메시지
                    form.reset(); // 폼 초기화
                } else {
                    showToast(data.message, "error"); // 실패 토스트 메시지
                }
            })
            .catch(error => {
                console.error("Error:", error);
                showToast("서버 요청 중 오류가 발생했습니다.", "error"); // 요청 실패 메시지
            });
    });
}
</script>
</body>
</html>