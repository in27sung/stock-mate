<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세정보</title>
<script type="text/javascript">
		// 상품 등록 완료시 상세 페이지로 이동
		var result = "${result}"
		
		if(result == "complete") {
			alert("상품 등록 완료")
		}
		
	</script>
</head>
<body>
	<h1>상품 상세정보</h1>


	<!-- product 객체가 null인지 검사 후 처리 가능 -->
	<c:if test="${product != null}">
		<p>상품명: ${product.name}</p>
		<p>바코드: ${product.barcode}</p>
		<p>카테고리 ID: ${product.categoryId}</p>
		<p>가격: ${product.price}</p>
		<p>기본 단위: ${product.baseUnit}</p>
		<p>세트 크기: ${product.setSize}</p>
		<p>설명: ${product.description}</p>
		<p>QR코드 경로: ${product.qrCodePath}</p>
		<p>바코드 이미지 경로: ${product.barcodePath}</p>
		<p>등록일: ${product.createdAt}</p>
		<p>수정일: ${product.updatedAt}</p>

		<!-- QR코드 다운로드 버튼(기능 구현 시 링크 또는 버튼 추가 가능) -->
		<a href="/product/downloadQr?id=${product.productId}">QR코드 다운로드</a>
	</c:if>

	<c:if test="${product == null}">
		<p>해당 상품 정보가 없습니다.</p>
	</c:if>

</body>
</html>

</body>
</html>