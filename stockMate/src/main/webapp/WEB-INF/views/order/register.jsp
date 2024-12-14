<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주문 발주 페이지</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/OrderStyle.css' />">
</head>
<body>
    <!-- 메인 컨테이너 -->
    <div class="container">
        <h1 style="margin-bottom: 20px;">주문 발주</h1>
        
        <!-- 주문 폼 시작 -->
        <form id="orderForm" action="/order/register" method="post">
            <!-- 주문 정보 카드 -->
            <div class="card">
                <div class="card-header">주문 정보</div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="createdAt" class="form-label required-label">발주일자</label>
                                <input type="date" class="form-control" id="createdAt" name="createdAt" required>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="orderNumber" class="form-label">주문번호</label>
                                <input type="text" class="form-control" id="orderNumber" name="orderNumber" readonly>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 재고 정보 컨테이너 (동적으로 카드가 추가됨) -->
            <div id="stockInfoContainer">
            </div>

            <!-- 주문 추가 버튼 -->
            <div class="text-center" style="margin: 20px 0;">
                <button type="button" class="btn btn-success" id="addOrder">주문 추가</button>
            </div>

            <!-- 재고 정보 카드 템플릿 -->
            <div class="card stock-info-card" id="stockInfoTemplate">
                <div class="card-header">
                    <div class="d-flex justify-content-between align-items-center">
                        <span>재고 정보</span>
                        <button type="button" class="btn btn-danger delete-btn">삭제</button>
                    </div>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-12">
                            <button type="button" class="btn btn-primary w-100 stock-select-btn">
                                재고 선택
                            </button>
                        </div>
                    </div>

                    <input type="hidden" class="stock-id" name="orderItems[0].stockId">
                    <input type="hidden" class="product-id" name="orderItems[0].productId">
                    <input type="hidden" class="warehouse-id" name="orderItems[0].warehouseId"><div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="form-label">제품명</label>
                                <input type="text" class="form-control product-name" readonly>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="form-label">창고명</label>
                                <input type="text" class="form-control warehouse-name" readonly>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="form-label">바코드</label>
                                <input type="text" class="form-control barcode" readonly>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="form-label">총 재고</label>
                                <input type="number" class="form-control total-quantity" readonly>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="form-label">예약 수량</label>
                                <input type="number" class="form-control reserved-quantity" readonly>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="form-label">가용 재고</label>
                                <input type="number" class="form-control available-stock" readonly>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="form-label required-label">주문수량</label>
                                <input type="number" class="form-control order-quantity" 
                                       name="orderItems[0].quantity" min="1" value="1" required>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="form-label">단위</label>
                                <input type="text" class="form-control base-unit" readonly>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="form-label">단가</label>
                                <input type="number" step="0.01" class="form-control unit-price" 
                                       name="orderItems[0].unitPrice" readonly>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label class="form-label">소계</label>
                                <input type="number" step="0.01" class="form-control subtotal-price" readonly>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <label class="form-label">비고</label>
                                <textarea class="form-control remarks" name="orderItems[0].remarks" rows="2"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 총계 정보 카드 -->
            <div class="card">
                <div class="card-header">총계 정보</div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label class="form-label">총 주문금액</label>
                                <input type="number" class="form-control" id="grandTotal" name="totalPrice" readonly>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!-- 히든 필드 -->
            <input type="hidden" id="createdBy" name="createdBy" value="${sessionScope.userId}">

            <!-- 폼 제출/초기화 버튼 -->
            <div class="text-center" style="margin-top: 20px;">
                <button type="submit" class="btn btn-success" style="margin-right: 10px;">주문 등록</button>
                <button type="button" class="btn btn-secondary" id="resetBtn">초기화</button>
            </div>
        </form>
    </div>

    <!-- 재고 선택 모달 -->
    <div id="stockListModal" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5>재고 목록</h5>
                    <button type="button" class="btn-close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <div style="overflow-x: auto;">
                        <table class="stock-table">
                            <thead>
                                <tr>
                                    <th>재고ID</th>
                                    <th>제품명</th>
                                    <th>바코드</th>
                                    <th>창고명</th>
                                    <th>창고ID</th>
                                    <th>총 재고</th>
                                    <th>예약수량</th>
                                    <th>가용재고</th>
                                    <th>단가</th>
                                    <th>단위</th>
                                    <th>선택</th>
                                </tr>
                            </thead>
                            <tbody id="stockListBody">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 로딩 인디케이터 -->
    <div id="loadingIndicator" class="loading" style="display: none;">
        <div class="spinner"></div>
    </div>

    <!-- jQuery 라이브러리 -->
    <script
	  src="https://code.jquery.com/jquery-3.7.1.min.js"
	  integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	  crossorigin="anonymous"></script>
    <script>
    // 전역 변수 선언
	let currentItemIndex = 0;
	
	// 페이지 로드 시 초기화
	$(document).ready(function() {
	    // 현재 날짜 설정
	    document.getElementById('createdAt').value = new Date().toISOString().split('T')[0];
	    
	    // 주문번호 생성 호출
	    generateNewOrderNumber();
	    
	    // 초기 재고 정보 카드 숨기기
	    $('#stockInfoTemplate').hide();
	    
	    // 이벤트 리스너 설정
	    setupEventListeners();
	    
	    // 첫 번째 재고 정보 카드 추가
	    addOrder();
	});
			// 재고 정보 카드 추가
		function addOrder() {
		    // 템플릿 복제
		    const newCard = $('#stockInfoTemplate').clone()
		        .removeAttr('id')
		        .show();
		    
		    // 인덱스 설정
		    const index = $('.stock-info-card:visible').length;
		    updateCardIndexes(newCard, index);
		    
		    // 컨테이너에 추가
		    $('#stockInfoContainer').append(newCard);
		    
		    // 삭제 버튼 상태 업데이트
		    updateDeleteButtonStatus();
		}
		
		// 카드 인덱스 업데이트
		function updateCardIndexes(card, index) {
		    card.find('input, textarea').each(function() {
		        const name = $(this).attr('name');
		        if (name) {
		            $(this).attr('name', name.replace(/\[\d+\]/, `[${index}]`));
		        }
		    });
		}
		
		// 모든 주문 항목의 인덱스 재정렬
		function updateOrderIndexes() {
		    $('.stock-info-card:visible').each(function(index) {
		        updateCardIndexes($(this), index);
		    });
		}
		
		// 모달 관련 함수들
		function openModal() {
		    $('#stockListModal').addClass('show');
		    loadAvailableStocks();
		}
		
		function closeModal() {
		    $('#stockListModal').removeClass('show');
		}
		
		// 재고 목록 로드
		function loadAvailableStocks() {
		    showLoading();
		    $.ajax({
		        url: '/order/findAvailableStocks',
		        type: 'GET',
		        dataType: 'json',
		        success: function(data) {
		            renderStockList(data);
		        },
		        error: function(xhr, status, error) {
		            console.error('Error:', error);
		            $('#stockListBody').html(
		                '<tr><td colspan="11" class="text-center">재고 목록을 불러오는데 실패했습니다.</td></tr>'
		            );
		        },
		        complete: hideLoading
		    });
		}
		
		// 재고 목록 렌더링
		function renderStockList(data) {
		    const tbody = $('#stockListBody');
		    tbody.empty();
		    
		    if (data && data.length > 0) {
		        data.forEach(function(stock) {
		            const row = createStockRow(stock);
		            tbody.append(row);
		        });
		    } else {
		        tbody.html('<tr><td colspan="11" class="text-center">가용 재고가 없습니다.</td></tr>');
		    }
		}

		// 재고 행 생성
		function createStockRow(stock) {
		    const row = $('<tr>');
		    
		    [
		        stock.stockId,
		        stock.product.name,
		        stock.product.barcode || '-',
		        stock.warehouseName,
		        stock.warehouseId,
		        stock.totalQuantity,
		        stock.reservedQuantity,
		        stock.availableStock,
		        stock.product.price,
		        stock.product.baseUnit
		    ].forEach(text => {
		        row.append($('<td>').text(text));
		    });
		    
		    // 선택 버튼 추가
		    const selectBtn = $('<button>')
		        .addClass('btn btn-success')
		        .text('선택')
		        .on('click', () => selectStock(stock));
		    
		    row.append($('<td>').append(selectBtn));
		    
		    return row;
		}
			
			// 이벤트 리스너 설정
			function setupEventListeners() {
			    // 주문 추가 버튼
			    $('#addOrder').on('click', addOrder);
			    
			    // 폼 제출
			    $('#orderForm').on('submit', function(e) {
			        e.preventDefault();
			        if (validateForm()) {
			            submitOrder();
			        }
			    });
			    
			    // 초기화 버튼
			    $('#resetBtn').on('click', function() {
			        if (confirm('모든 입력을 초기화하시겠습니까?')) {
			            resetForm();
			        }
			    });
			    
			    // 수량 변경 이벤트
			    $(document).on('change', '.order-quantity', function() {
			        validateQuantity($(this));
			    });
			    
			    // 재고 선택 버튼
			    $(document).on('click', '.stock-select-btn', function() {
			        currentItemIndex = $(this).closest('.stock-info-card').index();
			        openModal();
			    });
			    
			    // 삭제 버튼
			    $(document).on('click', '.delete-btn', function() {
			        const card = $(this).closest('.stock-info-card');
			        if ($('.stock-info-card:visible').length > 1) {
			            card.remove();
			            updateOrderIndexes();
			            updateGrandTotal();
			        } else {
			            alert('최소 하나의 주문 항목이 필요합니다.');
			        }
			    });
			    
			    // 모달 닫기 버튼
			    $('.btn-close').on('click', closeModal);
			    
			    // 모달 외부 클릭
			    $('#stockListModal').on('click', function(e) {
			        if ($(e.target).is('#stockListModal')) {
			            closeModal();
			        }
			    });
			}
			
			// 주문번호 생성
			function generateNewOrderNumber() {
			    showLoading();
			    $.ajax({
			        url: '/order/generateOrderNumber',
			        type: 'GET',
			        dataType: 'text',
			        success: function(orderNumber) {
			            if (orderNumber) {
			                $('#orderNumber').val(orderNumber.trim());
			            } else {
			                console.error('주문번호 생성 실패');
			                alert('주문번호 생성에 실패했습니다.');
			            }
			        },
			        error: function(xhr, status, error) {
			            console.error('주문번호 생성 오류:', error);
			            alert('주문번호 생성에 실패했습니다.');
			        },
			        complete: hideLoading
			    });
			}
			
		// 재고 선택
		function selectStock(stock) {
		    const card = $('.stock-info-card:visible').eq(currentItemIndex);
		    
		    // 숨겨진 필드 업데이트
		    card.find('.stock-id').val(stock.stockId);
		    card.find('.product-id').val(stock.product.productId);
		    card.find('.warehouse-id').val(stock.warehouseId);
		    
		    // 표시 필드 업데이트
		    card.find('.product-name').val(stock.product.name);
		    card.find('.warehouse-name').val(stock.warehouseName);
		    card.find('.barcode').val(stock.product.barcode);
		    card.find('.total-quantity').val(stock.totalQuantity);
		    card.find('.reserved-quantity').val(stock.reservedQuantity);
		    card.find('.available-stock').val(stock.availableStock);
		    card.find('.base-unit').val(stock.product.baseUnit);
		    card.find('.unit-price').val(stock.product.price);
		    
		    // 주문 수량 최대값 설정
		    const quantityInput = card.find('.order-quantity');
		    quantityInput.attr('max', stock.availableStock);
		    validateQuantity(quantityInput);
		    
		    closeModal();
		}
		
		// 수량 유효성 검사
		function validateQuantity(input) {
		    const card = input.closest('.stock-info-card');
		    const quantity = parseInt(input.val()) || 0;
		    const available = parseInt(card.find('.available-stock').val()) || 0;
		    
		    if (quantity < 1) {
		        alert('주문 수량은 1 이상이어야 합니다.');
		        input.val(1);
		    } else if (quantity > available) {
		        alert('주문 수량이 가용 재고를 초과할 수 없습니다.');
		        input.val(available);
		    }
		    
		    calculateSubtotal(card);
		}
		
		// 소계 계산
		function calculateSubtotal(card) {
		    const quantity = parseInt(card.find('.order-quantity').val()) || 0;
		    const unitPrice = parseFloat(card.find('.unit-price').val()) || 0;
		    const subtotal = quantity * unitPrice;
		    
		    card.find('.subtotal-price').val(subtotal.toFixed(2));
		    updateGrandTotal();
		}
		
		// 총계 업데이트
		function updateGrandTotal() {
		    let total = 0;
		    $('.subtotal-price:visible').each(function() {
		        total += parseFloat($(this).val()) || 0;
		    });
		    $('#grandTotal').val(total.toFixed(2));
		}
		
		// 삭제 버튼 상태 업데이트
		function updateDeleteButtonStatus() {
		    const buttons = $('.delete-btn');
		    buttons.prop('disabled', buttons.length <= 1);
		}
		
				// 폼 유효성 검사
		function validateForm() {
		    let isValid = true;
		    let hasUnselectedItems = false;
		    
		    $('.stock-info-card:visible').each(function() {
		        const card = $(this);
		        if (!card.find('.stock-id').val()) {
		            hasUnselectedItems = true;
		            return false;
		        }
		        
		        const quantity = parseInt(card.find('.order-quantity').val());
		        if (!quantity || quantity < 1) {
		            alert('모든 주문의 수량은 1 이상이어야 합니다.');
		            isValid = false;
		            return false;
		        }
		    });
		    
		    if (hasUnselectedItems) {
		        alert('모든 주문에 대해 재고를 선택해주세요.');
		        return false;
		    }
		    
		    return isValid;
		}
		
		// 주문 제출
		function submitOrder() {
	    const orderItems = [];
	    
	    $('.stock-info-card:visible').each(function(index) {
	        const card = $(this);
	        orderItems.push({
	            orderId: 0,  // 새로운 주문이므로 0으로 설정
	            productId: parseInt(card.find('.product-id').val()),
	            warehouseId: parseInt(card.find('.warehouse-id').val()),
	            quantity: parseInt(card.find('.order-quantity').val()),
	            unitPrice: parseFloat(card.find('.unit-price').val()),
	            remarks: card.find('.remarks').val(),
	            stotalPrice: parseFloat(card.find('.subtotal-price').val()) // 소계 추가
	        });
	    });
	
	    const formData = {
	        orderNumber: $('#orderNumber').val(),
	        createdAt: $('#createdAt').val(),
	        totalPrice: parseFloat($('#grandTotal').val()),
	        createdBy: $('#createdBy').val(),
	        orderItems: orderItems
	    };
	
	    showLoading();
	    $.ajax({
	        url: '/order/register',
	        type: 'POST',
	        contentType: 'application/json',
	        data: JSON.stringify(formData),
	        success: function(response) {
	            if (response.status === 'success') {
	                alert('주문이 성공적으로 등록되었습니다.');
	                resetForm();
	            } else {
	                alert(response.message || '주문 등록에 실패했습니다.');
	            }
	        },
	        error: function(xhr, status, error) {
	            console.error('Error:', error);
	            alert('주문 등록에 실패했습니다.');
	        },
	        complete: hideLoading
	    });
	}
		
		// 폼 초기화
		function resetForm() {
		    $('#orderForm')[0].reset();
		    $('#stockInfoContainer').empty();
		    $('#grandTotal').val('0.00');
		    
		    // 날짜 재설정
		    document.getElementById('createdAt').value = new Date().toISOString().split('T')[0];
		    
		    // 주문번호 새로 생성
		    generateNewOrderNumber();
		    
		    // 첫 번째 재고 정보 카드 추가
		    addOrder();
		}
		
		// 로딩 인디케이터 제어 쌈뽕한 느낌 추가 사실 필요없음
		function showLoading() {
		    $('#loadingIndicator').show();
		}
		
		// 로딩 인디케이터 제어 쌈뽕한 느낌 추가 사실 필요없음
		function hideLoading() {
		    $('#loadingIndicator').hide();
		}
		</script>	
</body>
</html>
	