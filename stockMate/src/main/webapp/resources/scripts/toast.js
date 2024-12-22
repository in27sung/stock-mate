function showToast(message, type, position = "top") {
    // 토스트 요소 생성
    const toast = document.createElement("div");
    toast.className = `toast ${type} ${position}`; // success/error와 위치 클래스
    toast.textContent = message;

    // DOM에 추가
    document.body.appendChild(toast);

    // 표시 애니메이션
    setTimeout(() => {
        toast.classList.add("show");
    }, 10); // 약간의 딜레이로 애니메이션 적용

    // 자동 제거
    setTimeout(() => {
        toast.classList.remove("show");
        setTimeout(() => {
            document.body.removeChild(toast);
        }, 300); // 애니메이션 시간과 동기화
    }, 4000); // 4초 후 사라짐
}