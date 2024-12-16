<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>재고 등록</title>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> -->
<link rel="stylesheet" href="<c:url value='/resources/css/registerStyle.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/modalStyle.css' />">
</head>
<body>
    <div class="container">
        <button class="dashboard-btn" onclick="location.href='/dashboard';">대시보드로 돌아가기</button>
        <h1>재고 등록</h1>
        <form id="stockForm" method="post" action="/stock/register">
            <!-- 창고 선택 -->
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
                <label for="quantity">초기 수량</label>
                <input type="number" id="quantity" name="quantity" min="1" required>
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
            <div class="modal-header">
                <h5 class="modal-title">상품 선택</h5>
                <button class="close" id="closeProductModal">&times;</button>
            </div>
            <div class="modal-body">
                <div class="search-field">
                    <input type="text" id="productSearch" placeholder="상품명 검색...">
                    <i class="fa fa-search"></i>
                </div>
                <div class="spinner" id="productSpinner"></div>
                <table class="table hidden" id="productTable">
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
                <div id="noProductMsg" class="hidden" style="text-align:center; margin-top:20px; color:#666;">상품이 없습니다.</div>
            </div>
        </div>
    </div>

	<script>
	document.addEventListener('DOMContentLoaded', () => {
    loadWarehouses();

    const productSelectBtn = document.getElementById('productSelectBtn');
    const productModal = document.getElementById('productModal');
    const closeProductModal = document.getElementById('closeProductModal');
    const productTable = document.getElementById('productTable');
    const productListBody = document.getElementById('productListBody');
    const productSpinner = document.getElementById('productSpinner');
    const noProductMsg = document.getElementById('noProductMsg');
    const productSearch = document.getElementById('productSearch');

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
            } else {
                noProductMsg.classList.remove('hidden');
                noProductMsg.textContent = '상품이 없습니다.';
            }
        })
        .catch(err => {
            productSpinner.style.display = 'none';
            noProductMsg.classList.remove('hidden');
            noProductMsg.textContent = '상품 불러오기 실패';
            console.error("오류 발생:", err);
        });
}
</script>
</body>
</html>