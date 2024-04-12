PUB.layout = (function() {
	// dependency
	var ui = PUB.ui;

	return {
		setInit: function() {
			var isMobile = window.isMobileMode();


			$('.header').removeClass('header__fold');
		},
		setLayout: function() {
			var me = this;
			var prevIsMobile = window.isMobileMode();
			var isMobile, isTouch;

			$(window).resize(function(){
				isMobile = window.isMobileMode(); // 모바일의 해상도 만족할 경우
				isTouch = window.isTouchMode(); // 모바일, 태블릿 디바이스 일 경우
				
				
				// 모바일의 해상도 만족할 경우
				if(prevIsMobile != isMobile){ // PC, 테블릿, 모바일 전환시에만 적용
					me.setInit();
				}

				// 211213 : 하이브리드 형태 레이아웃 구성
				if(isTouch){
					
				} else {
					
				}
			});

			setTimeout(function(){
				$(window).trigger('resize');
			}, 100)
		},
		initEvent: function() {
			var $body = $('html,body');
			var scrollTop = 0;

			/* [PC] 내비게이션  */
			$('.gnb .gnb_navLg > li').on('mouseenter', function () {
				$(this).addClass('is_active');
			}).on('mouseleave', function(){
				$(this).removeClass('is_active');
			});
			// tab key event
			$('.gnb .gnb_navLg > li').keyup(function(e){
				if (e.keyCode === 9 && !e.shiftKey) {
					$('.gnb .gnb_navLg > li').removeClass('is_active');
					$(this).addClass('is_active');
				} else if (e.keyCode === 9 && e.shiftKey) {
					$('.gnb .gnb_navLg > li').removeClass('is_active');
					$(this).addClass('is_active');
				}
			});
			$('.gnb .gnb_navLg > li:first-child').keydown(function (e) {
				if (e.keyCode === 9 && e.shiftKey) {
					$('.gnb .gnb_navLg > li:first-child').removeClass('is_active');
				}
			});
			$('.gnb .gnb_navLg > li:last-child .gnb_navMd > li:last-child').keydown(function (e) {
				if (e.keyCode === 9 && !e.shiftKey) {
					$('.gnb .gnb_navLg > li:last-child').removeClass('is_active');
				}
			});

			/* [PC] 전체보기  */
			$('.gnb .gnb_btnMenu').on('click', function(){
				$('.header').addClass('header__fold')
			});

			/* [PC] 전체보기 - 닫기  */
			$('.gnb .gnb_btnClose').on('click', function(){
				$('.header').removeClass('header__fold')
			})

			/* [MO] 모바일 내비게이션  */
			$('.gnd .gnd_btnMenu').on('click', function(){
				scrollTop = $(window).scrollTop();
				$('.gnd').addClass('gnd__open');
				$body.css({'position' : 'fixed', top : -scrollTop, 'overflow':'hidden'});
			});
			$('.gnd .gnd_btnClose').on('click', function(){
				$('.gnd').removeClass('gnd__open');
				$body.css({'position' : '', top : '', 'overflow':''});
				$(window).scrollTop(scrollTop);
			});


			/* [MO] 내비게이션  */
			$('.gnd .gnb_navLg > li > a').on('click', function(){
				scrollTop = $(window).scrollTop();
				
				var $li = $(this).parent('li');
				if($li.hasClass('is_active')){
					$li.removeClass('is_active');

				} else {
					$('.gnd .gnb_navLg > li').removeClass('is_active')
					$li.addClass('is_active')
				}
			});

			/* [메인] 배너 하단 메뉴바 */
			var mainSvc;

			$(window).resize(function(){
				if(!$('#mainSvc').exists()){
					return false;
				}

				// 모바일의 해상도 만족할 경우
				if(window.isMobileMode()){ // PC, 테블릿, 모바일 전환시에만 적용
					if(mainSvc){
						mainSvc.destroy();
					}
				} else {
					
					// mainSvc = new IScroll('#mainSvc', {
					// 	scrollX: true,
					// 	scrollY: false
					// });
					
				}
			});
			
			if ($("#mainSvc")[0] != null) {

				let wrapWidth = $('#mainSvc').outerWidth();
				let innerWidth = $('#mainSvc').children('.bl_iscroll_hor').children('ul').outerWidth();
				let differenceWidth = wrapWidth - innerWidth; // 차이

				if(differenceWidth > 0){
					// 내부값이 더 작음
					$('#mainSvc').children('.bl_iscroll_hor').css('width','100%');
				}
				let maxScrollLeft = $("#mainSvc")[0].scrollWidth - $("#mainSvc")[0].clientWidth;
				$("#mainSvc .bl_navRoll_btnPrev").on('click', function () {
					$("#mainSvc .bl_navRoll_btnPrev").attr('disabled', true);
					$("#mainSvc .bl_navRoll_btnNext").attr('disabled', false);
					$("#mainSvc .bl_navRoll_btnPrev").css("left", 0);
					$("#mainSvc .bl_navRoll_btnNext").css("right", 0);
					$("#mainSvc").stop().animate({ scrollLeft: -maxScrollLeft });
				});
				$("#mainSvc .bl_navRoll_btnNext").on('click', function () {
					$("#mainSvc .bl_navRoll_btnPrev").attr('disabled', false);
					$("#mainSvc .bl_navRoll_btnNext").attr('disabled', true);
					$("#mainSvc .bl_navRoll_btnPrev").css("left", maxScrollLeft);
					$("#mainSvc .bl_navRoll_btnNext").css("right", -maxScrollLeft);
					$("#mainSvc").stop().animate({ scrollLeft: maxScrollLeft });
				});
			}
		},
	}
}());