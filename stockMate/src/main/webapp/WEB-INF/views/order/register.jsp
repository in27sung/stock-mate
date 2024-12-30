<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>주문 페이지</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/OrderStyle.css' />">
</head>

<body>
    <!-- 메인 컨테이너 -->
    <div class="container">
        <h1 style="margin-bottom: 20px;">주문 페이지</h1>

        <div>
            <a class="btn btn-success" href="/dashboard" style="text-decoration: none;">대시 보드</a>
            <a class="btn btn-success" href="/order/orderList" style="text-decoration: none;">주문목록</a>
        </div>

        <!-- 주문 폼 시작 -->
        <form id="orderForm" action="/order/register" method="post">
            <!-- 주문 정보 카드 -->
            <div class="card">
                <div class="card-header">주문 정보</div>
                <div class="card-body">
                    <div class="row mb-3">
                        <div class="col-12">
                            <div class="form-group">
                                <label class="form-label required-label">주문 유형</label>
                                <div class="form-check">
                                    수주
                                    <input class="form-check-input" type="radio" name="orderType" id="outbound" value="OUTBOUND" required>
                                    <label class="form-check-label" for="outbound">
                                    </label>
                                    발주
                                    <label class="form-check-label" for="inbound">
                                        <input class="form-check-input" type="radio" name="orderType" id="inbound" value="INBOUND" required>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
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
                    <input type="hidden" class="warehouse-id" name="orderItems[0].warehouseId">

                    <!-- 예약수량을 hidden으로 변경 -->
                    <input type="hidden" class="form-control reserved-quantity" readonly>

                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="form-label">제품명</label>
                                <input type="text" class="form-control product-name" readonly>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="form-label">창고명</label>
                                <input type="text" class="form-control warehouse-name" readonly>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="form-label">바코드</label>
                                <input type="text" class="form-control barcode" readonly>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="form-label">총 재고</label>
                                <input type="number" class="form-control total-quantity" readonly>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="form-label">가용 재고</label>
                                <input type="number" class="form-control available-stock" readonly>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="form-label required-label">주문수량</label>
                                <input type="number" class="form-control order-quantity" name="orderItems[0].quantity" min="1" value="1" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="form-label">단위</label>
                                <input type="text" class="form-control base-unit" readonly>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="form-label">단가</label>
                                <input type="number" step="0.01" class="form-control unit-price" name="orderItems[0].unitPrice" readonly>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="form-label">소계</label>
                                <input type="number" step="0.01" class="form-control subtotal-price" readonly>
                            </div>
                        </div>
                    </div>
                    <div class="row mt-3">
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
            </div>
            <!-- 히든 필드 -->
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
<!--                                     <th>재고ID</th> -->
                                    <th>제품명</th>
                                    <th>바코드</th>
                                    <th>창고명</th>
<!--                                     <th>창고ID</th> -->
                                    <th>총 재고</th>
<!--                                     <th>예약수량</th> -->
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
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <!-- JavaScript 코드 시작 -->
    <script>
    /**
     * 주문 관리 시스템 JavaScript 코드
     * - 재고 주문(수주/발주) 처리를 위한 프론트엔드 로직
     * - jQuery 기반으로 구현됨
     */
     
        // DOM 셀렉터 상수 정의 - 자주 사용되는 HTML 요소들의 선택자를 미리 정의
        const SELECTORS = {
            CARD: {
                CONTAINER: '#stockInfoContainer', // 재고 정보 카드들을 모두 담고 있는 최상위 컨테이너
                TEMPLATE: '#stockInfoTemplate', // 새로운 재고 카드를 생성할 때 사용할 템플릿
                VISIBLE: '.stock-info-card:visible', // 현재 화면에 표시된 재고 정보 카드들
                STOCK_SELECT_BTN: '.stock-select-btn', // 각 카드의 재고 선택 버튼
                DELETE_BTN: '.delete-btn' // 각 카드의 삭제 버튼
            },
            FORM: {
                ORDER: '#orderForm', // 전체 주문 폼
                ORDER_TYPE: 'input[name="orderType"]:checked', // 선택된 주문 유형 (수주/발주)
                CREATED_AT: '#createdAt', // 주문 생성 날짜 입력 필드
                ORDER_NUMBER: '#orderNumber', // 주문 번호 입력 필드
                GRAND_TOTAL: '#grandTotal' // 총 주문 금액 표시 필드
            },
            MODAL: {
                CONTAINER: '#stockListModal', // 재고 선택 모달
                CLOSE_BTN: '.btn-close', // 모달 닫기 버튼
                STOCK_LIST: '#stockListBody' // 재고 목록이 표시되는 테이블 본문
            },
            LOADING: '#loadingIndicator' // 로딩표시 인디게이터
        };

	    // 현재 선택 중인 재고 카드의 인덱스를 저장하는 전역 변수 모달에서 재고선택 시 사용
        let currentItemIndex = 0;

        /**
         * 페이지 초기화 함수
		 * - DOM이 완전히 로드된 후 실행됨
		 * - 초기 날짜 설정, 주문번호 생성, 이벤트 리스너 설정 등을 수행
         */
        $(document).ready(function() {
        	// 현재 날짜를 주문 생성 날짜로 설정
            document.getElementById('createdAt').value = new Date().toISOString().split('T')[0];

            // 새로운 주문번호 생성 요청
            generateNewOrderNumber();

            // 재고 정보 카드 템플릿을 화면에서 숨김
            $('#stockInfoTemplate').hide();

            // 모든 이벤트 리스너 설정
            setupEventListeners();

            // 첫 번째 재고 정보 카드 추가
            addOrder();
        });

        /**
         * 계산 관련 유틸리티
         *주문 금액 계산과 관련된 함수들을 포함
         */
        const calculateUtils = {
            // 단일 항목의 소계 계산
            // 파라미터 숫자 quantity - 주문 수량
     		// 파라미터 숫자 unitPrice - 단가
     		// 리턴 숫자 - 계산된 소계 금액
     		
            getItemTotal(quantity, unitPrice) {
                return (parseInt(quantity) || 0) * (parseFloat(unitPrice) || 0);
            },

            // 전체 주문의 총계 계산
            // 파라미터 배열 items - 주문 항목 배열
            // 리턴 숫자 계산된 총계 금액

            getGrandTotal(items) {
                let total = 0;
                items.forEach(item => {
                    total += this.getItemTotal(item.quantity, item.unitPrice);
                });
                return total;
            }
        };

        /**
         * UI 업데이트 유틸리티
         * 화면 표시와 관련된 함수들을 포함
         */
        const uiUtils = {
            // 숫자를 통화 형식으로 포맷팅
            // 파라미터 숫자 value - 변환할 숫자
            // 리턴 문자 - "###,###원" 형식의 문자열
            
            formatPrice(value) {
                return new Intl.NumberFormat('ko-KR').format(value) + '원';
            },

            // 금액 표시 업데이트
            // 파라미터 jQuery  element - 업데이트할 DOM 요소
            // 파라미터 숫자 value - 표시할 금액

            updatePriceDisplay(element, value) {
                element
                    .val(value)
                    .prop('type', 'text')
                    .val(this.formatPrice(value));
            }
        };

        /**
         * 데이터 검증 유틸리티
         * 입력값 검증 관련 함수들을 포함
         */
        const validationUtils = {
            // 주문 수량 검증
            // 수량이 1 이상인지 확인
		    // 수주의 경우 가용 재고를 초과하지 않는지 확인
		    //파라미터 jQuery input - 수량 입력 필드
		    //리턴 불린  - 검증 통과 여부

            
            
            validateQuantity(input) {
                const card = input.closest('.stock-info-card');
                const orderType = $('input[name="orderType"]:checked').val();
                const quantity = parseInt(input.val()) || 0;
                const available = parseInt(card.find('.available-stock').val()) || 0;

                if (quantity < 1) {
                    alert('주문 수량은 1 이상이어야 합니다.');
                    input.val(1);
                    return false;
                }

                if (orderType === 'OUTBOUND' && quantity > available) {
                    alert('수주 수량이 가용 재고를 초과할 수 없습니다.');
                    input.val(available);
                    return false;
                }

                return true;
            },

            // 전체 폼 검증
            // 주문 유형이 선택되었는지 확인
            // 모든 주문 항목에 재고가 선택되었는지 확인
            // 리턴 불린 - 검증 통과 여부
            validateForm() {
                const orderType = $('input[name="orderType"]:checked').val();
                if (!orderType) {
                    alert('주문 유형을 선택해주세요.');
                    return false;
                }

                let isValid = true;
                $('.stock-info-card:visible').each(function() {
                    const card = $(this);
                    if (!card.find('.stock-id').val()) {
                        alert('모든 주문에 대해 재고를 선택해주세요.');
                        isValid = false;
                        return false;
                    }
                });

                return isValid;
            }
        };
        /**
         * 주문 카드 관련 함수들 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 주석 추가 여기 까지 하다가 말음..... 더는 못해..
         */
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

        /**
         * 모달 관련 함수들
         */
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

        /**
         * 재고 목록 표시 관련 함수들
         */
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
//                 stock.stockId,
                stock.product.productName,
                stock.product.productBarcode || '-',
                stock.warehouseName,
//                 stock.warehouseId,
                stock.totalQuantity,
                stock.availableStock,
                stock.product.productPrice,
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

        /**
         * 이벤트 리스너 설정
         */
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

        /**
         * 주문 번호 생성
         */
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

        /**
         * 재고 선택 처리
         */
        function selectStock(stock) {
            // 현재 표시된 재고 정보 카드 중 현재 인덱스에 해당하는 카드를 선택
            const card = $('.stock-info-card:visible').eq(currentItemIndex);

            // 주문 유형 가져오기 
            const orderType = $('input[name="orderType"]:checked').val();

            // 필드 업데이트
            card.find('.stock-id').val(stock.stockId);
            card.find('.product-id').val(stock.product.productId);
            card.find('.warehouse-id').val(stock.warehouseId);

            // 표시 필드 업데이트
            card.find('.product-name').val(stock.product.productName);
            card.find('.warehouse-name').val(stock.warehouseName);
            card.find('.barcode').val(stock.product.productBarcode);
            card.find('.total-quantity').val(stock.totalQuantity);
            card.find('.reserved-quantity').val(stock.reservedQuantity);
            card.find('.available-stock').val(stock.availableStock);
            card.find('.base-unit').val(stock.product.baseUnit);
            card.find('.unit-price').val(stock.product.productPrice);

            // 주문 수량 입력 필드 설정
            const quantityInput = card.find('.order-quantity');
            if (orderType === 'OUTBOUND') {
                quantityInput.attr('max', stock.availableStock);
            } else {
                quantityInput.removeAttr('max');
            }

            validateQuantity(quantityInput);
            closeModal();
        }

        /**
         * 수량 검증 및 계산
         */
        function validateQuantity(input) {
            const card = input.closest('.stock-info-card');
            const orderType = $('input[name="orderType"]:checked').val();

            const quantity = parseInt(input.val()) || 0;
            const available = parseInt(card.find('.available-stock').val()) || 0;

            if (quantity < 1) {
                alert('주문 수량은 1 이상이어야 합니다.');
                input.val(1);
            } else if (orderType === 'OUTBOUND' && quantity > available) {
                alert('수주 수량이 가용 재고를 초과할 수 없습니다.');
                input.val(available);
            }

            calculateSubtotal(card);
        }

        /**
         * 금액 계산 함수들
         */
        function calculateSubtotal(card) {
            const quantity = parseInt(card.find('.order-quantity').val()) || 0;
            const unitPrice = parseFloat(card.find('.unit-price').val()) || 0;
            const subtotal = quantity * unitPrice;

            card.find('.subtotal-price')
                .val(subtotal)
                .prop('type', 'text')
                .val(new Intl.NumberFormat('ko-KR').format(subtotal) + '원');

            updateGrandTotal();
        }

        function updateGrandTotal() {
            let total = 0;
            $('.stock-info-card:visible').each(function() {
                const card = $(this);
                const quantity = parseInt(card.find('.order-quantity').val()) || 0;
                const unitPrice = parseFloat(card.find('.unit-price').val()) || 0;
                total += quantity * unitPrice;
            });

            $('#grandTotal')
                .val(total)
                .prop('type', 'text')
                .val(new Intl.NumberFormat('ko-KR').format(total) + '원');
        }

        /**
         * 삭제 버튼 상태 관리
         */
        function updateDeleteButtonStatus() {
            const buttons = $('.delete-btn');
            buttons.prop('disabled', buttons.length <= 1);
        }

        /**
         * 폼 검증
         */
        function validateForm() {
            let isValid = true;
            let hasUnselectedItems = false;

            const orderType = $('input[name="orderType"]:checked').val();
            if (!orderType) {
                alert('주문 유형을 선택해주세요.');
                return false;
            }

            $('.stock-info-card:visible').each(function() {
                const card = $(this);
                if (!card.find('.stock-id').val()) {
                    hasUnselectedItems = true;
                    return false;
                }

                const quantity = parseInt(card.find('.order-quantity').val());
                const available = parseInt(card.find('.available-stock').val());

                if (!quantity || quantity < 1) {
                    alert('모든 주문의 수량은 1 이상이어야 합니다.');
                    isValid = false;
                    return false;
                }

                if (orderType === 'OUTBOUND' && quantity > available) {
                    alert('수주 수량이 가용 재고를 초과할 수 없습니다.');
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

        /**
         * 주문 제출 처리
         */
        function submitOrder() {
            const orderItems = [];

            $('.stock-info-card:visible').each(function(index) {
                const card = $(this);
                const quantity = parseInt(card.find('.order-quantity').val()) || 0;
                const unitPrice = parseFloat(card.find('.unit-price').val()) || 0;
                const subtotal = quantity * unitPrice;

                orderItems.push({
                    orderId: 0,
                    productId: parseInt(card.find('.product-id').val()),
                    warehouseId: parseInt(card.find('.warehouse-id').val()),
                    quantity: quantity,
                    unitPrice: unitPrice,
                    remarks: card.find('.remarks').val(),
                    stotalPrice: subtotal,
                    stockId: parseInt(card.find('.stock-id').val())
                });
            });
			// 주문 데이터 구성
            const formData = {
                orderNumber: $('#orderNumber').val(),
                createdAt: $('#createdAt').val(),
                totalPrice: orderItems.reduce((sum, item) => sum + item.stotalPrice, 0),
                createdBy: $('#createdBy').val(),
                orderItems: orderItems,
                orderType: $('input[name="orderType"]:checked').val()
            };
			// 서버에 주문 데이터 전송
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

        /**
         * 폼 초기화
         * 모든 입력 필드를 초기 상태로 복원
         */
        function resetForm() {
            $('#orderForm')[0].reset();
            $('#stockInfoContainer').empty();
            $('#grandTotal').val('0.00');
            $('input[name="orderType"]').prop('checked', false);

            document.getElementById('createdAt').value = new Date().toISOString().split('T')[0];
            generateNewOrderNumber();
            addOrder();
        }

        /**
         * 로딩 인디케이터 제어
         */
        function showLoading() {
            $('#loadingIndicator').show();
        }

        function hideLoading() {
            $('#loadingIndicator').hide();
        }
    </script>
</body>

</html>