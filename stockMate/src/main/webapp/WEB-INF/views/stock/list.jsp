<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>재고 리스트</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/stockListStyle.css' />">
    <link rel="stylesheet" href="<c:url value='/resources/css/toastStyle.css' />">
    <script src="<c:url value='/resources/scripts/toast.js' />"></script>
</head>
<style>
    .active-sort {
        font-weight: bold;
        text-decoration: underline;
    }
</style>
<body>
    <div class="container">
        <h1>재고 리스트</h1>
        
        <!-- 대시보드 이동 버튼 -->
        <button class="dashboard-btn" onclick="location.href='<c:url value="/dashboard" />'">대시보드로 이동</button>
        
        <!-- 검색 필드 (우측 상단) -->
        <div class="search-box">
            <input type="text" id="searchInput" placeholder="상품명을 검색하세요..." onkeyup="filterTable()">
        </div>
        
        <table>
            <thead>
                <tr>
                    <!-- 상품명 정렬 -->
                    <th>
                        <a href="?sortColumn=product_name&sortOrder=${sortOrder eq 'asc' and sortColumn eq 'product_name' ? 'desc' : 'asc'}" class="${sortColumn eq 'product_name' ? 'active-sort' : ''}">
                            상품명
                            <c:if test="${sortColumn eq 'product_name'}">
                                <span class="sort-indicator">${sortOrder eq 'asc' ? '▲' : '▼'}</span>
                            </c:if>
                        </a>
                    </th>

                    <th>바코드</th>
                    
                    <!-- 카테고리명 정렬 -->
		            <th>
		                <a href="?sortColumn=category_name&sortOrder=${sortOrder eq 'asc' and sortColumn eq 'category_name' ? 'desc' : 'asc'}">
		                    카테고리명
		                    <c:if test="${sortColumn eq 'category_name'}">
		                        <span class="sort-indicator">${sortOrder eq 'asc' ? '▲' : '▼'}</span>
		                    </c:if>
		                </a>
		            </th>

                    <!-- 창고명 정렬 -->
					<th>
					    <a href="?sortColumn=warehouse_name&sortOrder=${sortColumn eq 'warehouse_name' and sortOrder eq 'asc' ? 'desc' : 'asc'}">
					        창고명
					        <c:if test="${sortColumn eq 'warehouse_name'}">
					            <span class="sort-indicator">${sortOrder eq 'asc' ? '▲' : '▼'}</span>
					        </c:if>
					    </a>
					</th>

                    <th>총 재고</th>
                    <th>
					    <a href="?sortColumn=available_stock&sortOrder=${sortColumn eq 'available_stock' and sortOrder eq 'asc' ? 'desc' : 'asc'}">
					        가용 재고
					        <c:if test="${sortColumn eq 'available_stock'}">
					            <span class="sort-indicator">${sortOrder eq 'asc' ? '▲' : '▼'}</span>
					        </c:if>
					    </a>
					</th>
                    <th>
                        <a href="?sortColumn=updated_at&sortOrder=${sortOrder eq 'asc' and sortColumn eq 'updated_at' ? 'desc' : 'asc'}">
                            마지막 수정 시간
                            <c:if test="${sortColumn eq 'updated_at'}">
                                <span class="sort-indicator">${sortOrder eq 'asc' ? '▲' : '▼'}</span>
                            </c:if>
                        </a>
                    </th>
					<th>
						QR 코드
					</th>
					<th>
						 바코드 
					</th>
                </tr>
            </thead>
            <tbody id="stockTableBody">
                <c:choose>
                    <c:when test="${not empty stockList}">
                        <c:forEach var="stock" items="${stockList}">
                            <tr>
                                <!-- 상품명 클릭 시 상품 상세 페이지 이동 -->
                                <td class="product-name">
                                    <a href="<c:url value='/product/detail' />?productId=${stock.productId}">
                                        ${stock.productName}
                                    </a>
                                </td>

                                <!-- 바코드 -->
                                <td>${stock.productBarcode}</td>

                                <!-- 카테고리명 -->
                                <td>${stock.categoryName}</td>

                                <!-- 창고명 클릭 시 창고 상세 페이지 이동 -->
                                <td>
                                    <a href="<c:url value='/warehouse/detail' />?warehouseId=${stock.warehouseId}">
                                        ${stock.warehouseName}
                                    </a>
                                </td>

                                <!-- 총 재고 -->
                                <td>${stock.totalQuantity}</td>

                                <!-- 사용 가능한 재고 (10 이하 강조) -->
                                <td>
                                    <c:choose>
                                        <c:when test="${stock.availableStock < 10}">
                                            <span class="low-stock">${stock.availableStock}</span>
                                        </c:when>
                                        <c:otherwise>${stock.availableStock}</c:otherwise>
                                    </c:choose>
                                </td>

                                <!-- 최근 수정 시간 -->
                                <td>
                                    <fmt:formatDate value="${stock.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss" />
                                </td>
								<td>            
								    <c:choose>
										<c:when test="${not empty stock.stockQrCodePath}">
<%-- 										<a href="${stock.stockQrCodePath.replace('/Users/Insung/Documents/upload', '/upload')}"  --%>
										    <a href="${stock.stockQrCodePath.replace('/usr/local/tomcat/webapps/upload', '/upload')}" 
										       class="btn-download"
										       download="${stock.stockQrCodePath.substring(stock.stockQrCodePath.lastIndexOf('/') + 1)}">
										        다운로드
										    </a>
										</c:when>
								        <c:otherwise>
											<button class="btn-generate"
											        data-product-id="${stock.productId != null ? stock.productId : 'unknown'}"
											        data-product-name="${stock.productName != null ? stock.productName : 'unknown'}">
											    생성
											</button>							   
			                            </c:otherwise>
								    </c:choose>
								</td>
								<td>
								    <c:choose>
								        <c:when test="${not empty stock.barcodePath}">
<%-- 										    <a href="${stock.barcodePath != null ? stock.barcodePath.replace('/Users/Insung/Documents/upload', '/upload') : ''}" --%>
										    <a href="${stock.barcodePath != null ? stock.barcodePath.replace('/usr/local/tomcat/webapps/upload', '/upload') : ''}"
										       class="btn-download"
										       download="${stock.barcodePath != null ? stock.barcodePath.substring(stock.barcodePath.lastIndexOf('/') + 1) : ''}">
										        다운로드
										    </a>
										</c:when>
										<c:otherwise>
										    <button class="btn-barcode-generate"
										            data-barcode-number="${stock.productBarcode != null ? stock.productBarcode : '바코드 없음'}">
										        생성
										    </button>
										</c:otherwise>
								    </c:choose>
								</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="7">No stock data available.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
<!-- jQuery CDN 추가 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script>
	    $(document).ready(function () {
	        // 검색 필터 기능
	        const filterTable = () => {
	            const filter = $("#searchInput").val().toLowerCase().replace(/\s+/g, '');
	            $("#stockTableBody tr").each(function () {
	                const productName = $(this).find("td.product-name").text().toLowerCase().replace(/\s+/g, '');
	                $(this).toggle(productName.includes(filter));
	            });
	        };
	
	        // 검색 필터 이벤트
	        $("#searchInput").on("keyup", filterTable);
	
	        // QR 코드 생성 버튼 이벤트 추가
			$(".btn-generate").on("click", function () {
			    const button = $(this);
			    const productId = button.data("product-id");
			    const productName = button.data("product-name");
			
			    if (!productId) {
			        alert("유효하지 않은 상품 ID입니다.");
			        return;
			    }
			    generateQRCode(productId, productName, button);
			});
	
	        /* QR 코드 생성 요청 */
	        function generateQRCode(productId, productName, button) {
	            setButtonLoading(button, true);
	
	            fetch(`/api/qrcode/generate`, {
	                method: "POST",
	                headers: { "Content-Type": "application/json" },
	                body: JSON.stringify({ productId: productId }),
	            })
	            .then(response => response.json())
				.then(data => {
				    if (data.success) {
				        showToast(data.message, "success");
				
				        const qrCodePath = data.qrCodePath;
				        const qrCodeFileName = data.qrCodeFileName;  // 서버에서 받은 파일명 그대로 사용
				
				        button.data("qrCodePath", qrCodePath);
				        button.data("fileName", qrCodeFileName);  // 서버에서 받은 파일명 사용
				
				        changeToDownloadButton(button, qrCodeFileName);
				    }
				})
	            .catch(error => {
	                console.error("QR 코드 생성 오류:", error.message);
	                showToast("QR 코드 생성 중 오류가 발생했습니다.", "error");
	            })
	            .finally(() => setButtonLoading(button, false));
	        }
	     // 바코드 생성 요청
	        $(".btn-barcode-generate").on("click", function () {
	            const button = $(this);
	            let productBarcode = button.data("barcode-number");  // productBarcode 사용

	            console.log("바코드 생성 요청 - 원본 바코드: ", productBarcode);

	            // 공백 및 특수 문자 제거
	            productBarcode = String(productBarcode).replace(/\s+/g, '').trim();

	            // 바코드 유효성 검사
	            if (!productBarcode || productBarcode.length !== 13) {
	                showToast("유효하지 않은 바코드입니다. (13자리 필수): " + productBarcode, "error");
	                return;
	            }

	            // 바코드 생성 요청
	            fetch("/api/barcode/generate", {
	                method: "POST",
	                headers: { "Content-Type": "application/json" },
	                body: JSON.stringify({ productBarcode: productBarcode })
	            })
	            .then(response => response.json())
	            .then(data => {
	                if (data.success) {
	                    const barcodePath = data.barcodePath;
	                    const barcodeFileName = data.barcodeFileName;

	                    const downloadLink = $("<a>")
	                        .addClass("btn-download")
	                        .attr("href", barcodePath)
	                        .attr("download", barcodeFileName)
	                        .text("다운로드");

	                    button.replaceWith(downloadLink);
	                    showToast("바코드가 성공적으로 생성되었습니다.", "success");
	                } else {
	                    showToast(data.message || "바코드 생성 실패", "error");
	                }
	            })
	            .catch(error => {
	                showToast("바코드 생성 중 서버 오류 발생", "error");
	            });
	        });
	
	        // QR 코드 다운로드 요청 (fetch 방식)
	        function downloadQRCode(productId, barcode = null) {
	            const requestData = barcode ? { productBarcode: barcode } : { productId: productId };
	
	            fetch(`/api/qrcode/download`, {
	                method: "POST",
	                headers: { "Content-Type": "application/json" },
	                body: JSON.stringify(requestData),
	            })
	            .then(response => {
	                if (!response.ok) {
	                    throw new Error("다운로드 서버 오류: " + response.status);
	                }
	                const contentDisposition = response.headers.get("Content-Disposition");
	                const fileNameMatch = contentDisposition && contentDisposition.match(/filename="(.+?)"/);
	                const fileName = fileNameMatch ? decodeURIComponent(fileNameMatch[1]) : `qr_code_${productId || barcode}.png`;
	
	                return response.blob().then(blob => ({ blob, fileName }));
	            })
	            .then(({ blob, fileName }) => {
	                const url = window.URL.createObjectURL(blob);
	                const link = document.createElement("a");
	                link.href = url;
	                link.download = fileName;
	                link.click();
	                window.URL.revokeObjectURL(url);
	                showToast("QR 코드 다운로드 성공", "success");
	            })
	            .catch(error => {
	                console.error("QR 코드 다운로드 오류:", error.message);
	                showToast("QR 코드 다운로드 실패: " + error.message, "error");
	            });
	        }
	
	        // QR 코드 다운로드 버튼으로 전환
			function changeToDownloadButton(button, fileName) {
			    const qrCodePath = button.data("qrCodePath");
			
			    if (!qrCodePath) {
			        console.error("QR 코드 경로가 존재하지 않습니다.");
			        return;
			    }
			
			    const downloadButton = $("<a>")
			        .addClass("btn-download")
			        .text("다운로드")
			        .attr("href", qrCodePath)
			        .attr("download", fileName);  // 서버에서 받은 파일명 그대로 사용
			
			    button.replaceWith(downloadButton);
			}
	
	        // 버튼 로딩 상태 관리
	        function setButtonLoading(button, isLoading) {
	            if (isLoading) {
	                button.prop("disabled", true).text("처리 중...");
	            } else {
	                button.prop("disabled", false).text("QR 코드 생성");
	            }
	        }
	    });
	</script>
</body>
</html>
