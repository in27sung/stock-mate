/**
 * 
 */
$(document).ready(function() {
    const navbarCollapse = $('#navbarNav');
    const blurOverlay = $('.blur-overlay');
    const closeButton = $('.close-btn');

    // 햄버거 메뉴 클릭 시 동작
    $('.navbar-toggler').on('click', function() {
        if (navbarCollapse.hasClass('show')) {
            // 메뉴가 열려 있다면 닫기
            navbarCollapse.collapse('hide'); // Bootstrap의 'hide' 동작 호출
            blurOverlay.css('height', '0');
            closeButton.prop('hidden', true); // Close 버튼 숨김
        } else {
            // 메뉴가 닫혀 있다면 열기
            blurOverlay.css({
                'height': '89vh', // 전체 화면 높이
                'width': '55%', // 블러 너비
                'top': '0', // 상단 고정
                'float': 'right',
            });

            navbarCollapse.css({
                'right': '0', // 오른쪽 끝에 메뉴 고정
                'left': 'auto', // 왼쪽은 비활성화
                'width': '300px', // 메뉴 너비 설정
            });

            navbarCollapse.collapse('show'); // Bootstrap의 'show' 동작 호출
            closeButton.prop('hidden', false); // Close 버튼 표시
        }
    });

    // 닫기 버튼 클릭 시 메뉴 닫기
    closeButton.on('click', function() {
        navbarCollapse.collapse('hide'); // Bootstrap의 'hide' 동작 호출
        blurOverlay.css('height', '0');
        closeButton.prop('hidden', true); // Close 버튼 숨김
    });

    // 메뉴 닫힐 때 블러 제거 및 버튼 숨기기
    navbarCollapse.on('hidden.bs.collapse', function() {
        blurOverlay.css('height', '0');
        navbarCollapse.css('width', '0'); // 메뉴 너비 제거
        closeButton.prop('hidden', true); // Close 버튼 숨김
    });

    // 메뉴 열릴 때 블러 추가 및 버튼 표시
    navbarCollapse.on('shown.bs.collapse', function() {
        blurOverlay.css({
            'height': '87vh', // 전체 화면 블러 크기 설정
            'width': '55%',
            'top': '0',
            'float': 'right',
        });
        closeButton.prop('hidden', false); // Close 버튼 표시
    });
});