// import './active.js';
// import './file.js';
// import  './pagination.js';

var context = window,
	$win = $(context),
	$body = $('body'),
	$doc = $(document);

function mainSlideBtn(index){
	const prevBtn = $('#__webSizebtnPrev');
	const nextBtn = $('#__webSizebtnNext');

	if(index == 1){
		// 미션
		prevBtn.text('언론인연수');
		nextBtn.text('미디어교육');
		prevBtn.css('backgroundColor',"#1986C8");
		nextBtn.css('backgroundColor',"#d55c0c");
	} else if(index == 2){
		// 미디어
		prevBtn.text('미션');
		nextBtn.text('언론인연수');
		prevBtn.css('backgroundColor',"#194f7b");
		nextBtn.css('backgroundColor',"#1986C8");
	} else if(index == 0){
		// 언론
		prevBtn.text('미디어교육');
		nextBtn.text('미션');
		prevBtn.css('backgroundColor',"#d55c0c");
		nextBtn.css('backgroundColor',"#194f7b");
	}
}

(function($) {
	"use strict";
	// off, on
	$.fn.offon = function(type, f) {
		return this.off(type).on(type, f);
	};
	
	// length
	$.fn.exists = function() {
		return this.length > 0;
	};
	
	// space ([margin, padding], [horizontal, vertical, top, right, bottom, left])
	$.fn.space = function(property, option) {
		var property = String(property),
			option = String(option),
			value = 0;
		
		if(option == "horizontal") {
			if($(this).css(property + "-left") && $(this).css(property + "-left").replace(/\D/g,"")) {
				value += Number($(this).css(property + "-left").replace(/\D/g, ""));
			}
			if($(this).css(property + "-right") && $(this).css(property + "-right").replace(/\D/g,"")) {
				value += Number($(this).css(property + "-right").replace(/\D/g, ""));
			}
		} else if(option == "vertical") {
			if($(this).css(property + "-top") && $(this).css(property + "-top").replace(/\D/g,"")) {
				value += Number($(this).css(property + "-top").replace(/\D/g, ""));
			}
			if($(this).css(property + "-bottom") && $(this).css(property + "-bottom").replace(/\D/g,"")) {
				value += Number($(this).css(property + "-bottom").replace(/\D/g, ""));
			}
		} else {
			if($(this).css(property + "-" + option) && $(this).css(property + "-" + option).replace(/\D/g,"")) {
				value = Number($(this).css(property + "-" + option).replace(/\D/g, ""));
			}
		}
		
		return value;
	};

	$.fn.noop = function () {
		return this;
	};

}(jQuery));


(function ($) {
	"use strict";
	var $root = $(document.documentElement).addClass('js'),
		isTouch = ('ontouchstart' in context),
		isMobile = ('orientation' in context) || isTouch || window.IS_MOBILE === true;
	
	isTouch && $root.addClass('touch');
	isMobile && $root.addClass('mobile');

	// 모바일 디바이스
	window.IS_MOBILE = window.IS_MOBILE;
	window.isTouch = isTouch;

	// 해상도별 사이즈 기준이 변하는 시점에 changemediasize라는 이벤트를 별도로 발생시킨다.
	$win.on('resize.changemediasize', (function () {
		var sizes = [{
			mode: 'mobile',
			min: 0,
			max: 767
		},
		{
			mode: 'tablet',
			min: 768,
			max: 1024
		},{
			mode: 'desktop',
			min: 1025,
			max: 100000
		}],
		handleChangeMediaSize;

		handleChangeMediaSize = function () {
			var w = $win.width();

			
			
			for (var i = 0, size; size = sizes[i]; i++) {
				if (w > size.min && w <= size.max) {
					size.width = w;
					// 반응형일 때만 body에 클래스 토글
					switch (size.mode) {
						case 'desktop':
							$('body').removeClass('mobile tablet').addClass('desktop');
							break;
						case 'tablet':
							$('body').removeClass('desktop mobile').addClass('tablet');
							break;
						case 'mobile':
							$('body').removeClass('desktop tablet').addClass('mobile');
							break;
					}

					$win.trigger('changemediasize', false);
					break;
				}
			}
		};

		// 초기에 한번 실행
		$(function() {
			handleChangeMediaSize();
		});

		
		return handleChangeMediaSize;
	})());

	window.consts = {
		MOBILE_SIZE: 767,
		TABLET_SIZE: 1024
	};

	window.isMobileMode = window.isMobileSize = function (w) {
		if (w === undefined) {
			w = $win.width();
		}

		return window.IS_MOBILE === true || w < window.consts.TABLET_SIZE;
	};

	window.isTouchMode = function () {
		var ag = (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini|Mobi|mobi/i.test(navigator.userAgent));
		
		return window.isTouch === true && ag;
	};
})(jQuery);


$(document).ready(function(){
	var ui = PUB.ui;
	var layout = PUB.layout;

	layout.setLayout();
	layout.initEvent();
	
	ui.accordionMenu();
	ui.ModalController();
	ui.tabPanel();

	if($('.js_tab').exists()){
		$('.js_tab', this).tabPanel({
			startIndex:0
		});
	}

	$('#popQuesView').openModal();
	if(window.location.pathname == '/'){
		// 메인 비주얼
		// let date = new Date();
		// let month = date.getMonth() + 1; // 현재 월
		// let imageUrl;
		
		// let mainSlideImgTit = ['언론인연수', '미션', '미디어교육'];
		// let mainSlideImg_pc = [];
		// let mainSlideImg_tb = [];
		// let mainSlideImg_mo = [];
		// for (let i = 0; i < mainSlideImgTit.length; i++) {
		// 	mainSlideImg_pc.push($(`#jumbotromMainVisual [data-title='${mainSlideImgTit[i]}'] .bl_mainSlide_img.hp_show_pc`));
		// 	mainSlideImg_tb.push($(`#jumbotromMainVisual [data-title='${mainSlideImgTit[i]}'] .bl_mainSlide_img.hp_show_tb`));
		// 	mainSlideImg_mo.push($(`#jumbotromMainVisual [data-title='${mainSlideImgTit[i]}'] .bl_mainSlide_img.hp_show_mo`));
		// }

		// function mainSlideImgChange(season) {
		// 	for (let i = 0; i < mainSlideImgTit.length; i++) {
		// 		if (mainSlideImgTit[i]) {
		// 			if (i == 0) {
		// 				imageUrl = "/assets/images/main/visual_main_"+season;
		// 			} else {
		// 				imageUrl = "/assets/images/main/visual_main_"+season+(i+1);
		// 			}
		// 		}
		// 		mainSlideImg_pc[i].attr('src', imageUrl+'.png');
		// 		mainSlideImg_tb[i].attr('src', imageUrl+'@tablet.png');
		// 		mainSlideImg_mo[i].attr('src', imageUrl+'@mobile.png');
		// 	}
		// }
		
		// if ((month >= 6 && month <= 8) || (month >= 9 && month <= 11) || (month >= 12 || month <= 2) || (month >= 3 && month <= 5)) {
		// 	if (month >= 6 && month <= 8) { // 여름
		// 		mainSlideImgChange('summer')
		// 	} else if (month >= 9 && month <= 11) { // 가을
		// 		mainSlideImgChange('autumn')
		// 	} else if (month === 12 || month <= 2) { // 겨울
		// 		mainSlideImgChange('winter')
		// 	} else if (month >= 3 && month <= 5) { // 봄
		// 		mainSlideImgChange('spring')
		// 	}
		// }
		
		$('#jumbotromMainVisual').slick({
			speed: 500,
			fade: true,
			autoplay: false,
			autoplaySpeed: 1000,
			cssEase: 'linear',
			arrow: true,
			dots: true,
			infinite: true,
			initialSlide: slideIndex___,
			// initialSlide: 1,
			customPaging: function (slider, i) {
				var title = $(slider.$slides[i]).data('title');
				return '<a class="pager__item"><span>' + title + '</span></a>';
			},
			prevArrow: '<div class="slick-prev">이전</div>',
			nextArrow: '<div class="slick-next">다음</div>'
		});

		// slideIndex___에 따른 양쪽 값 변경
		mainSlideBtn(slideIndex___);
	}




	if ($('.slick-dots').exists()) { 
		$('.slick-dots').before('<div class="slick-dots-wrap ly_grid"></div>');
		$('.slick-dots-wrap').append($('.slick-dots'));
	}

	$('#nextSlideBtn').click(function () {
		$('#jumbotromMainVisual').slick('slickNext');
	});

	$('#prevSlideBtn').click(function(){
		$('#jumbotromMainVisual').slick('slickPrev');
	})

	$('#__webSizebtnNext').click(function () {
		$('#jumbotromMainVisual').slick('slickNext');
	});

	$('#__webSizebtnPrev').click(function(){
		$('#jumbotromMainVisual').slick('slickPrev');
	})

	// 배너영역1 슬라이드
	// $('#bl_bnnrMain_slider').slick({
	// 	speed: 500,
	// 	fade: true,
	// 	autoplay: true,
	// 	autoplaySpeed: 2000,
	// 	cssEase: 'linear',
	// 	arrow: true,
	// 	dots: false,
	// 	infinite: true,
	// 	prevArrow: '<div class="slick-prev">이전</div>',
	// 	nextArrow: '<div class="slick-next">다음</div>'
	// });

	// const bnnr_slide_cnt = $('#bl_bnnrMain_slider li').length;
	// if(bnnr_slide_cnt > 1){
	// 	if ($('#bl_bnnrMain_slider').exists()) {
	// 		$(`<div class="slick-arrow-bg">
	// 		<div class="paging">
	// 			<span class="page-active">1</span>
	// 			<span class="page-blank">/</span>
	// 			<span class="page-last">5</span>
	// 		</div>
	// 	</div>`).appendTo('#bl_bnnrMain_slider');

	// 		let activeNum;
	// 		$('#bl_bnnrMain_slider .page-last').text(bnnr_slide_cnt);
	// 		let pageNumChk = setInterval(() => {
	// 			activeNum = $('#bl_bnnrMain_slider .slick-active').attr('page-num');
	// 			$('#bl_bnnrMain_slider .page-active').text(activeNum);
	// 		}, 100);
	// 	}
	// }

	//메인 퀵메뉴
	if ($('.quick_menu').exists()) { 
		let currentPosition = parseInt($(".quick_menu").css("top"));
		$(window).scroll(function() {
			let position = $(window).scrollTop(); 
			$(".quick_menu").stop().animate({"top":position+currentPosition+"px"},1000);
		});
	}

	if($(".el_datePicker").exists()){
		$(".el_datePicker").each(function() {
			let param = {};
			if ($(this).data("yearrange")) {
				param.yearRange = $(this).data("yearrange");
			} else {
				param.yearRange = 'c-50:c+20';
			}

			$(this).datepicker(Object.assign({
				dateFormat: "yy-mm-dd",
				yearRange: param.yearRange,
				
				prevText: '이전 달',
				nextText: '다음 달',

				changeYear: true, // 년 셀렉트박스 표시
				changeMonth: true, // 월 셀렉트박스 표시

				monthNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"], //달력의 월 부분 Tooltip 텍스트
				monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"], //달력의 월 부분 텍스트

				dayNames: [ "일", "월", "화", "수", "목", "금", "토" ], //달력의 요일 부분 Tooltip 텍스트
				dayNamesShort: [ "일", "월", "화", "수", "목", "금", "토" ], //달력의 요일 부분 텍스트
				dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"], //달력의 요일 부분 텍스트

				firstDay: 0,  // 시작 요일: 1 - 월요일, 0 - 일요일
				showMonthAfterYear: true, //년도 먼저 나오고, 뒤에 월 표시
				yearSuffix: '년', //달력의 년도 부분 뒤에 붙는 텍스트

				// 선택한 데이터 추출
				onSelect: function(date) {
					// let date = $.datepicker.formatDate("yymmdd", $(".el_datePicker").datepicker("getDate"));
					//
					// date = $(".el_datePicker").val();
					// date = date.split('.'); // 데이터 값 년, 월, 일로 쪼개기
					//
					// let year, month;
					// year = date[0];
					// month = date[1];
					//
					// // 년, 월 데이터 넣기
					// let cal_year = document.querySelector('.cal_year');
					// let cal_month = document.querySelector('.cal_month');
					// if (cal_year != null) {
					// 	cal_year.innerText = year;
					// }
					// if (cal_month != null) {
					// 	cal_month.innerText = month;
					// }
					if( typeof clickDate == 'function'){
						// 함수가 선언이 되어있을 경우에만 메인에서만 씀
						if( closePopCalList && typeof closePopCalList == "function") closePopCalList();
						clickDate(date.split('-')[0]+date.split('-')[1]+date.split('-')[2])
					}
				}
			}));
		});

		// sDate - eDate 보완
		for(let i=0; i<$('.sdate').length; i++){
			$('.sdate').eq(i).datepicker();
			$('.sdate').eq(i).datepicker("option", "maxDate",$(".edate").eq(i).val());
			$('.sdate').eq(i).datepicker("option", "onClose", function ( selectedDate ) {
				$(".edate").eq(i).datepicker( "option", "minDate", selectedDate );
			});

			$('.edate').eq(i).datepicker();
			$('.edate').eq(i).datepicker("option", "minDate", $(".sdate").eq(i).val());
			$('.edate').eq(i).datepicker("option", "onClose", function ( selectedDate ) {
				$(".sdate").eq(i).datepicker( "option", "maxDate", selectedDate );
				// $(".bizOrgAplyLsnDtlYmd").each((index,item) =>{
				// 	$(item).datepicker( "option", "maxDate", selectedDate )
				// });
			});
		}

		//datepicker 자동완성 기능 해제
		for(let i=0; i<$('.el_datePicker').length; i++){
			$('.el_datePicker').eq(i).attr('autocomplete', 'off');
		}
	}


	if($('#navTab2').exists()){
		var navTab = new IScroll('#navTab2', {
			scrollX: true,
			scrollY: false
		});
	}


	// 메인 비주얼
	$('#cardSlider').slick({
		infinite: true,
		slidesToShow: 4,
		slidesToScroll: 1,
		arrow:true,
		dots:false,
		responsive: [
			{
				breakpoint: 1024,
				settings: {
					slidesToShow: 3,
				}
			},
			{
				breakpoint: 768,
				settings: {
					slidesToShow: 2,
				}
			},
			{
				breakpoint: 480,
				settings: {
					slidesToShow: 1,
				}
			}
		]
	});
});

//수업지도안 교과 선택 리스트let subjectChkList = [];