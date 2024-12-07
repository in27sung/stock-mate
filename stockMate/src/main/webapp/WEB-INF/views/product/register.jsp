<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>상품 등록</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- jQuery 라이브러리 -->

</head>
<body>
	<h1>상품 등록</h1>

	<!-- 상품 등록 폼 시작 -->
	<form method="post" action="" enctype="multipart/form-data">
		<!-- 창고 드롭다운 -->
		<div>
			<!-- 			<label for="warehouse">창고 선택:</label> <select id="warehouse" -->
			<%-- 				name="warehouse" th:if="${hasWarehouses}" --%>
			<%-- 				th:each="warehouse : ${warehouses}" th:value="${warehouse.id}" --%>
			<%-- 				th:text="${warehouse.name}"></select> --%>
			<%-- 			<button th:if="${!hasWarehouses}" --%>
			<!-- 				onclick="location.href='/warehouse/register'">창고 등록</button> -->
			<label for="warehouseSearch">창고 검색:</label> <input type="text"
				id="warehouseSearch" placeholder="창고 검색..." autocomplete="off">
			<div id="warehouseDropdown" style="display: none;"></div>
			<button id="warehouseRegister" style="display: none;"
				onclick="location.href='/warehouse/register'">창고 등록</button>
		</div>
		<!-- 카테고리 드롭다운 -->
		<label for="category">카테고리 선택:</label> <select id="category"
			name="category" th:if="${hasCategories}"
			th:each="category : ${categories}" th:value="${category.id}"
			th:text="${category.name}"></select>
		<button th:if="${!hasCategories}"
			onclick="location.href='/category/register'">카테고리 등록</button>
		<!-- 카테고리 선택 -->
		<div>
			<label for="categoryId">*카테고리:</label> <select id="categoryId"
				name="categoryId" required="required">
				<option value="" disabled selected>카테고리를 선택하세요</option>
				<!-- 카테고리 목록을 드롭다운으로 표시 -->
				<c:forEach var="category" items="${categories}">
					<option value="${category.categoryId}">${category.categoryName}</option>
				</c:forEach>
			</select>
		</div>

		<!-- 상품명 -->
		<div>
			<label for="name">*상품명:</label> <input id="name"
				placeholder="상품명을 입력하세요" required="required" />
		</div>

		<!-- 바코드 -->
		<div>
			<label for="barcode">바코드:</label> <input id="barcode"
				placeholder="바코드 (NULL 가능)" />
		</div>

		<!-- 기본 단위 드롭다운 -->
		<div>
			<label for="baseUnit">*기본 단위:</label> <input id="baseUnit"
				placeholder="단위를 입려해주세요 (예: 개, 박스)" required="required" />
			<!-- <select path="baseUnit" -->
			<!-- 				id="baseUnit" required="true"> -->
			<!-- 				<option value="개">개</option> -->
			<!-- 				<option value="박스">박스</option> -->
			<!-- 				<option value="롤">롤</option> -->
			<!-- 				<option value="팩">팩</option> -->
			<!-- 				<option value="세트">세트</option> -->
			</
			<!-- select -->
		</div>

		<!-- 세트 크기 -->
		<div>
			<label for="setSize">*세트 크기:</label> <input id="setSize"
				placeholder="세트 크기를 입력하세요 (예: 1박스 = 100개)" required="required" />
		</div>

		<!-- 상품 가격 -->
		<div>
			<label for="price">*상품 가격:</label> <input id="price" type="number"
				placeholder="상품 가격을 입력하세요" required="required" />
		</div>

		<!-- 사업자 ID -->
		<div>
			<label for="businessId">사업자 ID:</label> <input id="businessId"
				placeholder="사업자 ID를 입력하세요" required="required" />
		</div>

		<!-- QR 코드 경로 -->
		<div>
			<label for="qrCodePath">QR 코드 경로:</label> <input id="qrCodePath"
				placeholder="QR 코드 경로" />
		</div>

		<!-- 바코드 이미지 경로 -->
		<div>
			<label for="barcodePath">바코드 이미지 경로:</label> <input id="barcodePath"
				placeholder="바코드 이미지 경로" />
		</div>

		<!-- 상품 설명 -->
		<div>
			<label for="description">상품 설명:</label>
			<textarea id="description" placeholder="상품에 대한 설명을 입력하세요" rows="4"
				cols="50"></textarea>
		</div>

		<!-- 제출 버튼 -->
		<div>
			<button type="submit">등록</button>
		</div>
	</form>
	<!-- JavaScript 코드는 바디 끝에서 실행 -->
	<script>
		$(document)
				.ready(
						function() {
							const $searchInput = $('#warehouseSearch');
							const $dropdown = $('#warehouseDropdown');
							const $registerButton = $('#warehouseRegister');

							// 검색어 입력 시 처리
							$searchInput.on('input', function() {
								const query = $(this).val().trim();
								if (query === '') {
									// 검색어가 없으면 전체 목록 요청
									loadAllWarehouses();
								} else {
									// 검색어로 필터링된 목록 요청
									searchWarehouses(query);
								}
							});

							function loadAllWarehouses() {
								// 전체 창고 목록 가져오기 (Ajax)
								$.ajax({
									url : '/api/warehouses/all',
									method : 'GET',
									success : function(data) {
										updateDropdown(data);
										$dropdown.show();
										$registerButton.hide();
									},
									error : function() {
										console.error('전체 목록 로드 실패');
										$dropdown.hide();
										$registerButton.show();
									}
								});
							}
							function searchWarehouses(keyword) {
								// 검색 결과 가져오기 (Ajax)
								$.ajax({
									url : '/api/warehouses/search',
									method : 'GET',
									data : {
										keyword : keyword
									},
									success : function(data) {
										if (data.length > 0) {
											// 검색 결과가 있을 때
											updateDropdown(data); // 드롭다운 업데이트
											$dropdown.show(); // 드롭다운 표시
											$registerButton.hide(); // 등록 버튼 숨김
										} else {
											// 검색 결과가 없을 때
											$dropdown.hide(); // 드롭다운 숨김
											$registerButton.show(); // 등록 버튼 표시
										}
									},
									error : function() {
										console.error('검색 실패');
										$dropdown.hide();
										$registerButton.show();
									}
								});
							}
							function updateDropdown(data) {
								$dropdown.empty();
								data
										.forEach(function(warehouse) {
											const item = $(`<div class="dropdown-item">${warehouse.name}</div>`);
											$dropdown.append(item);
										});
							}
							// 드롭다운 항목 선택 시 처리
							$dropdown.on('click', '.dropdown-item', function() {
								const warehouseId = $(this).data('id');
								const warehouseName = $(this).text();

								// 선택한 창고 이름을 입력 필드에 표시
								$searchInput.val(warehouseName);

								// 선택된 창고 ID를 처리
								console.log(`선택된 창고 ID: ${warehouseId}`);

								$dropdown.hide(); // 드롭다운 숨김
							});

							// 페이지 로드 시 전체 목록 로드
							loadAllWarehouses();

							// 드롭다운 외부 클릭 시 숨김 처리
							$(document)
									.on(
											'click',
											function(e) {
												if (!$(e.target)
														.closest(
																'#warehouseDropdown, #warehouseSearch').length) {
													$dropdown.hide();
												}
											});
						});
	</script>
</body>
</html>