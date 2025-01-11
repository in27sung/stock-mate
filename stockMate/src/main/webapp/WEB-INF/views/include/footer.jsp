<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<footer class="bg-dark text-light py-4">
	<div class="container text-center">
		<!-- 로고 또는 사이트 이름 -->
		<h5 class="text-uppercase fw-bold mb-4">Stock Mate</h5>

		<!-- 소셜 미디어 아이콘 -->
		<div class="mb-3">
			<a href="https://www.instagram.com/in95sung/" target="_blank"
				class="text-light me-3"> <i class="bi bi-instagram fs-4"></i></a> <a
				href="https://www.linkedin.com/in/insung-hwang-104969244/"
				target="_blank" class="text-light"> <i
				class="bi bi-linkedin fs-4"></i>
			</a>
		</div>

		<!-- 푸터 링크 -->
		<ul class="list-inline mb-3">
			<li class="list-inline-item"><a href="javascript:void(0);"
				onclick="showModal('#termsModal');"
				class="text-light text-decoration-none">이용약관</a></li>
			<li class="list-inline-item"><a href="javascript:void(0);"
				onclick="showModal('#privacyModal');"
				class="text-light text-decoration-none">개인정보취급방침</a></li>
			<li class="list-inline-item"><a href="javascript:void(0);"
				onclick="showModal('#emailPolicyModal');"
				class="text-light text-decoration-none">이메일주소무단수집거부</a></li>
		</ul>

		<!-- 저작권 -->
		<p class="mb-0">&copy; 2025 Stock Mate. All rights reserved.</p>
	</div>

	<!-- 이용약관 모달 -->
	<div class="modal fade" id="termsModal" tabindex="-1"
		aria-labelledby="termsModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title fw-bold text-primary" id="termsModalLabel">이용약관</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p>
						<strong>제 1조 총칙</strong><br>이 약관은 Stock Mate가 제공하는 모든 서비스의 이용
						조건을 규정합니다.
					</p>
					<p>
						<strong>제 2조 약관의 효력과 변경</strong><br>(1) 약관은 공지 후 효력을 가집니다.<br>(2)
						변경된 약관은 사이트에 공지됩니다.
					</p>
					<p>
						<strong>제 3조 개인정보 보호</strong><br>개인정보는 법률에 따라 안전하게 보호됩니다.
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 개인정보취급방침 모달 -->
	<div class="modal fade" id="privacyModal" tabindex="-1"
		aria-labelledby="privacyModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title fw-bold text-primary" id="privacyModalLabel">개인정보취급방침</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p>Stock Mate는 회원의 개인정보를 소중히 여기며, 이를 안전하게 보호합니다.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 이메일주소무단수집거부 모달 -->
	<div class="modal fade" id="emailPolicyModal" tabindex="-1"
		aria-labelledby="emailPolicyModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title fw-bold text-primary"
						id="emailPolicyModalLabel">이메일주소무단수집거부</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<p>본 사이트에 게시된 이메일 주소는 무단 수집을 거부합니다.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>
	<script>
	    function showModal(modalId) {
	        const modal = new bootstrap.Modal(document.querySelector(modalId));
	        modal.show();
	    }
	</script>
</footer>