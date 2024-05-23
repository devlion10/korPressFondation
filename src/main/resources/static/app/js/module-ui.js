/**
 * JavaScript UI Library
 * @author
 * @copyright
 */

"use strict";

PUB.createNs("ui");



/**
 * UI 기능 코드
 * @namespace ui
 * @memberOf PUB
 */
PUB.ui = (function() {
	// dependency
	var util = PUB.util;

	return {
		/**
		 * 스케일 값
		 * @memberOf PUB.ui
		 */
		scaleValue: 1,
		/**
		 * 화면 스케일
		 * @memberOf PUB.ui
		 */
		setScale: function() {
		},
		/**
		 * 화면 스케일
		 * @memberOf PUB.ui
		 */
		initEvent: function() {
			
			$('.ui-accordmenu').accordionMenu();

			if($(".ui-scroll").exists()){
				$(".ui-scroll").each(function(){
					$(this).mCustomScrollbar();
				});
			}
		},
		/**
		 * 화면 스케일
		 * @memberOf PUB.ui
		 */
		ModalController:function(){
			$.fn.openModal = function (options) {
				this.each(function(index){
					if($(this).data('modalController') === undefined){
						var modalController = new ModalController(this, options);
						$(this).data('modalController', modalController);
					} else {
						var modalController = $(this).data('modalController');
					}
		
					if(modalController)
						modalController.show($(this));
				})
		
				return this;
			}
		
			$.fn.closeModal = function () {
				this.each(function(index){
					var modalController = $(this).data('modalController');
					if(modalController)
						modalController.hide($(this));
				})
		
				return this;
			}
		
			$.fn.layoutModal = function () {
				this.each(function(index){
					var modalController = $(this).data('modalController');
					if(modalController)
						modalController.center($(this));
				})
		
				return this;
			}
		
			function ModalController(selector, options){
				this.$modal = null;
				this.$openBtn = null;	// 열기 selector
				this.$closeBtn = null;	// 닫기 selector
				this.$targetBtn = null;
				this.$scroller = null;
				this.$opener = null;
		
				this._init(selector);
				this._initOptions(options);
				this._initEvent();
			}
		
			/* 요소 초기화 */
			ModalController.prototype._init=function(selector){
				this.$modal = $(selector);
				this.$closeBtn = this.$modal.find('.pop-close');
				//this.$header = this.$modal.find('.pop_head');
				//this.$content = this.$modal.find('.pop_cont');
				//this.$buttonWrap = this.$modal.find('.pop_foot');
		
				this.isShown = false;
			}
		
			// 옵션 초기화
			ModalController.prototype._initOptions=function(options){
				this.options = jQuery.extend({}, ModalController.defaultOptions, options);
			}
		
			ModalController.prototype.center=function(options){
				this.layout();
			}
		
			ModalController.prototype._initEvent=function(){
				var self = this;
		
				this.$closeBtn.on('click', function(e){
					e.preventDefault();
					e.stopPropagation();
					self.hide();
		
					if (self.options.opener) {
						$(self.options.opener).focus();
					}
				});
		
				$(window).on('resize resizeend', function (e) {
					if(self.$modal.is(':visible')){
						self.layout();
					}
				});
			}
		
			ModalController.prototype._createHolder=function(target){
				var me = this;
				this.$holder = $('<span class="ui-modal-holder" style="display:none;"></span>').insertAfter(this.$modal);
			}
		
			ModalController.prototype._replaceHolder=function(target){
				var me = this;
		
				if (me.$holder) {
					me.$modal.insertBefore(me.$holder);
					me.$holder.remove();
				}
			}
		
			ModalController.prototype._createModalContainer=function(){
				var me = this;
		
				me.scrollTop = $('html,body').scrollTop();
				me.$container = $('<div class="ui-modal-container" />');
		
				me.$container.css({
					'position': 'fixed',
					'top': 0,
					'left': 0,
					'right': 0,
					'bottom':0
					//'height': '100%'
				}).append(me.$modal.css({
					'zIndex': 2
				})).appendTo('body');
			}
		
			ModalController.prototype.layout=function(){
				var me = this,
					opts = me.options,width,
					height, attr,
					winHeight = $(window).height();
				
				
				me.$modal.css({
					'display': 'block',
					'visibility': 'hidden',
					'top': '',
					'left': '',
					'height': '',
					'width': ''
				});
		
				width = me.$modal.width();
				height = me.$modal.height();
		
				attr = {
					visibility: '',
					display: 'block'
				};
		
				if (height > winHeight) {
					attr.top = 0;
					attr.marginTop = opts.offsetTop
					attr.marginBottom = opts.offsetTop;
				} else {
					attr.top = (opts.forceTop > 0 ? opts.forceTop : (winHeight - height) / 2);
					attr.height = '';
				}
		
				me.$modal.stop().css(attr);
			}
		
			ModalController.prototype._removeModalContainer = function () {
				var me = this;
				me._replaceHolder();
				me.$container.remove();
				me.$dim = null;
				me.$container = null;
				if(me.options.removeComponent){
					me.$modal.remove();
				}
			}
		
			ModalController.prototype.show=function(){
				var me = this;
		
				if (this.isShown) {
					return;
				}
		
				this.isShown = true;
				this._createHolder();
				this._createModalContainer();
				this.layout();
				this.options.onShow();
				
		
				$('html,body').css({'overflow':'hidden'});
				//$('body').css({'position':'fixed', 'top': -me.scrollTop + 'px'});
			}
		
			ModalController.prototype.hide=function(e){
				var me = this;
		
				if(!this.isShown){
					return;
				}
		
				this.isShown = false;
		
				me.$modal.css({
					'position': '',
					'top': '',
					'left': '',
					'outline': '',
					'marginLeft': '',
					'marginTop': '',
					'backgroundClip': '',
					'zIndex': '',
					'display': ''
				});
		
				me._removeModalContainer();
				me.options.onHide();
				$('html,body').css({'overflow':''});
				//$('html,body').css({'position':'', 'top': ''});
				$(window).scrollTop(me.scrollTop);
			}
		
			// 기본 옵션값
			ModalController.defaultOptions = {
				onShow : function(){},
				onHide : function(){},
				removeComponent: false,
				overlay: true,
				forceTop: 0,
				offsetTop: 50,
				offsetLeft: 208
			}

			$('[data-control="modal"]').click(function(){
				var $target = $('#' + $(this).attr('data-handler'));
				$target.openModal();
			});
		},
		accordionMenu:function(){
			// accordionMenu 플러그인
			$.fn.accordionMenu=function(){
				// 선택자에 해당하는 요소 개수 만큼 AccordionMenu 객체 생성
				this.each(function(index){
					var accordionMenu = new AccordionMenu(this);
					$(this).data("accordionMenu", accordionMenu);
				   
				});
				return this;
			}
			
			// accordionMenu 선택처리 플러그인
			$.fn.selectAccordionMenuAt=function(selectIndex, animation){
				this.each(function(index){
					var accordionMenu = $(this).data("accordionMenu");
					if(accordionMenu)
						accordionMenu.setSelectMenuItemAt(selectIndex,animation);
				
				});
				return this;
			}

			function AccordionMenu(selector){
				// 프로퍼티 생성하기 
				this.$accordionMenu = null;
				this._$menuBody  = null;
				this._$menuItems = null;
				
				// 선택 메뉴 아이템
				this._$selectItem = null;
				this._selectIdx = null;
				
				this._init(selector);
				this._initEvent();   
			}
			
			// 요소 초기화 
			AccordionMenu.prototype._init=function(selector){
				this.$accordionMenu = $(selector);
				this._$menuBody = this.$accordionMenu.find(">li");
				this._$menuItems = this._$menuBody.find('.accord-head a')
			}
			
			// 이벤트 초기화 
			AccordionMenu.prototype._initEvent=function(){
				var objThis = this;
				
				// 선택 메뉴 아이템 처리
				this._$menuItems.click(function(e){
					var idx = $(this).parents('li').index();
					// 선택 메뉴 아이템 처리
					objThis.setSelectMenuItem(idx);
				})
			}
			
			/*
			 * 선택 메뉴 아이템 처리
			 * $item : 선택 메뉴 아이템
			 */
			AccordionMenu.prototype.setSelectMenuItem=function(index, animation){
				// 선택 메뉴 아이템 스타일 처리
				if(this._$selectItem){
					if(this._selectIdx!= index){ // 토글
						this._$selectItem.removeClass("open");
						this._$selectItem.find('.accord-contents').stop().slideUp(250);
					}
				}

				if(this._$menuBody.eq(index).hasClass("open")){
					this._$menuBody.eq(index).removeClass("open");
					this._$menuBody.eq(index).find('.accord-contents').stop().slideUp(250);
				} else {
					this._$menuBody.eq(index).addClass("open");
					this._$menuBody.eq(index).find('.accord-contents').stop().slideDown(250);
				}
				
				this._$selectItem = this._$menuBody.eq(index);
				this._selectIdx = index;
			}
			
			/* 
			 * animation : 애니메이션 이동 여부
			 */
			AccordionMenu.prototype.setSelectMenuItemAt=function(index, animation){
				this.setSelectMenuItem(index, animation);
			}
		},
		tabPanel :function(){
			$.fn.tabPanel=function(options){
				this.each(function(index){
					var tabPanel = new TabPanel(this, options);
					 $(this).data("tabPanel", tabPanel);
				})

				return this;
			}

			$.fn.selectTabPanel=function(tabIndex){
				this.each(function(index){
					var tabPanel =$(this).data("tabPanel");
					if(tabPanel)
						tabPanel.setSelectTabMenuItemAt(tabIndex);
				})

				return this;
			}

			function TabPanel(selector, options){
				this._$tabPanel = null;
				this._$tabMenu = null;
				this._$tabMenuItems = null;
				this._$selectTabMenuItem = null;

				this._$tabContents = null;
				this._$selectTabContent = null;

				this._options = null;

				this._init(selector);
				this._initEvent();
				this._initOptions(options);
				this.setSelectTabMenuItemAt(this._options.startIndex,false);
				if(window.location.pathname == '/'){
					// 메인 화면에서의 예외처리
					let firstTab = document.querySelectorAll('.bl_tab_menu.js_tab_menu li')[this._options.startIndex].attributes.style;
					if(firstTab !== undefined && firstTab.value == 'display:none'){
						this.setSelectTabMenuItemAt(this._options.startIndex+1,false);
					}
				}
			}

			// 요소 초기화
			TabPanel.prototype._init=function(selector){
				var me = this;

				this._$tabPanel = $(selector);
				this._$tabMenu = this._$tabPanel.find(".js_tab_menu");

				this._$tabMenuItems = this._$tabMenu.find("li");
				this._$tabContents = this._$tabPanel.find(".js_tab_contents .js_tab_item");

			}


			// 옵션 초기화
			TabPanel.prototype._initOptions=function(options){
				this._options = jQuery.extend({}, TabPanel.defaultOptions, options);
			}

			// 이벤트 초기화
			TabPanel.prototype._initEvent=function(){
				var self = this;
				this._$tabMenuItems.on("click",function(e){
					e.preventDefault();
					self.setSelectTabMenuItem($(this));
				})
			}

			// 탭 메뉴  아이템 선택
			TabPanel.prototype.setSelectTabMenuItem=function($item){
				if(this._$selectTabMenuItem){
					this._$selectTabMenuItem.removeClass("is_active");
				}
				if(window.location.pathname == '/'){
					// 메인
					$('.bl_tab_menu.js_tab_menu li').removeClass('is_active');
				}
				this._$selectTabMenuItem = $item;
				this._$selectTabMenuItem.addClass("is_active");

				var newIndex = this._$tabMenuItems.index(this._$selectTabMenuItem);
				this._showContentAt(newIndex);
			}

			// index 번째 탭메뉴 아이템 선택
			TabPanel.prototype.setSelectTabMenuItemAt=function(index){
				this.setSelectTabMenuItem(this._$tabMenuItems.eq(index));
			}

			// index에 맞는 탭 내용 활성화
			TabPanel.prototype._showContentAt=function(index){
				// 1. 활성화/비활성화 탭 내용 찾기
				$('.bl_tab_item.js_tab_item').css('display','none');

				var $hideContent = this._$selectTabContent;
				var $showContent =  this._$tabContents.eq(index);

				TabPanel.normalEffect.effect({
					$hideContent:$hideContent,
					$showContent:$showContent
				});

				// 4. 선택 탭 내용 업데이트
				this._$selectTabContent = $showContent;
			}

			// 일반 출력 효과
			TabPanel.normalEffect={
				effect:function(params){
					if(params.$hideContent){
						params.$hideContent.hide();
					}

					params.$showContent.show();
				}
			}

			// 기본 옵션값
			TabPanel.defaultOptions = {
				startIndex:0
			}
		}
	}
}());


/**
 * Common Module
 */
let CommonUI = {};
let globalOrgCode = '';
let CommonUtil = {};
(function(_this){
	if( !_this ) _this = {};
	const sleep = (milliseconds) => {
		const date = Date.now();
		let currentDate = null;
		do {
			currentDate = Date.now();
		} while (currentDate - date < milliseconds);
	};


	const asyncForEach = async (array, callback, delay) => {
		for (let index = 0; index < array.length; index++) {
			await callback(array[index], index, array);
		}
	}

//날짜인지 아닌지 검사
	function isDate(date) {
		return (new Date(date) !== "Invalid Date") && !isNaN(new Date(date));
	}
	
//날짜 유효값넘기기
	function getDateWithNull(date){
		if(isDate(date)){
			return date;
		}else{
			return undefined;
		}
	}

	//문자열의 콤마를 제거하고 숫자로 변환
	function getNumString(s) {
		var rtn = s;
		if(typeof rtn ==="string"){
			rtn = parseFloat(s.replace(/,/gi, ""));
		}else if(typeof rtn ==="number"){
			rtn = parseFloat(s);
		}
		if (isNaN(rtn)) {
			return 0;
		}
		else {
			return rtn;
		}
	}

	//문자열 반환
	function getString(val , defaultValue){
		return val ? val : defaultValue ? defaultValue : "";
	}

	//숫자 천단위 콤마표시
	function numberWithCommas(x){
		return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	}

	//mimetype으로 이미지 판별
	function mimeDetectImage(mimetype){
		return mimetype.includes("image");
	}

	//파일용량 구하기
	function getfileSize(x) {
		var s = ['bytes', 'kB', 'MB', 'GB', 'TB', 'PB'];
		var e = Math.floor(Math.log(x) / Math.log(1024));
		return (x / Math.pow(1024, e)).toFixed(2) + " " + s[e];
	};

	//하이픈 제거
	function removeHypen(str){
		return str.replace(/-/g,"");
	}

	//개행문자 변환
	function nl2br(str){
		if (str == undefined || str == null){
			return "";
		}
		str = str.replace(/\r\n/ig, '<br />');
		str = str.replace(/\\n/ig, '<br />');
		str = str.replace(/\n/ig, '<br />');
		return str;
	}

	function mathAbs(num){
		if(Math.abs(num) === 0){
			return `<span class='math-abs abs-zero'>0</span>`
		}else if(num > 0){
			return `<span class='math-abs abs-plus'>${Math.abs(num)}</span>`
		}else{
			return `<span class='math-abs abs-minus'>${Math.abs(num)}</span>`
		}
	}

	function mathAbsNumberWithCommas(num){
		if(Math.abs(num) === 0){
			return `<span class='math-abs abs-zero'>0</span>`
		}else if(num > 0){
			return `<span class='math-abs abs-plus'>${numberWithCommas(Math.abs(num))}</span>`
		}else{
			return `<span class='math-abs abs-minus'>${numberWithCommas(Math.abs(num))}</span>`
		}
	}

	/**
	 * 유효한 사업자 등록번호인지 검사
	 * @param {*} number
	 * @returns
	 */
	function checkCorporateRegiNumber(number){
		var numberMap = number.replace(/-/gi, '').split('').map(function (d){
			return parseInt(d, 10);
		});
		if(numberMap.length == 10){
			var keyArr = [1, 3, 7, 1, 3, 7, 1, 3, 5];
			var chk = 0;
			keyArr.forEach(function(d, i){
				chk += d * numberMap[i];
			});
			chk += parseInt((keyArr[8] * numberMap[8])/ 10, 10);
			return Math.floor(numberMap[9]) === ( (10 - (chk % 10) ) % 10);
		}
		return false;
	}


	/**
	 * 사업자등록번호 하이픈 붙이기
	 * @param {*} num 사업자등록번호
	 * @param {*} type 0 : 123-45-*****, 1: 123-45-67890
	 * @returns
	 */
	function bizNoFormatter(num, type) {
		var formatNum = '';
		try{
			if (num.length == 10) {
				if (type == 0) {
					formatNum = num.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-*****');
				}else{
					formatNum = num.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-$3');
				}
			}
		}catch(e){
			formatNum = num;
			console.log(e);
		}
		return formatNum;
	}



	/**
	 * (주) 제거 법인명 변환
	 * @param {법인명} nm
	 */
	function getCompanyName(nm){
		return nm.replace('(주)', '').replace('㈜', '').trim();
	}


	function phoneFomatter(num,type){
		var formatNum = '';
		if(num.length==11){
			if(type==0){
				formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-****-$3');
			}else{
				formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
			}
		}else if(num.length==8){
			formatNum = num.replace(/(\d{4})(\d{4})/, '$1-$2');
		}else{
			if(num.indexOf('02')==0){
				if(type==0){
					formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-****-$3');
				}else{
					formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
				}
			}else{
				if(type==0){
					formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-***-$3');
				}else{
					formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
				}
			}
		}
		return formatNum;
	}


	function convertUTF8(filename){
		return Buffer.from(filename, 'latin1').toString('utf8');
	}


	function convertDateYYYYMMDDHHSS(str){
		return new Date(str.replace(/^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/, '$1-$2-$3 $4:$5:$6'));
	}

	function convertRecordFile(filename){
		let fileNameArray = filename.split("_");
		let result = {};
		let lastIndex = fileNameArray.length-1;
		let lastFileName = fileNameArray[lastIndex];
		fileNameArray[lastIndex] = lastFileName.split(".")[0];

		let fileExt = lastFileName.split(".")[lastFileName.split(".").length-1];
		if(fileExt != "m4a"){
			return false;
		}else if(fileNameArray.length > 2){
			result.type = "known";
			result.name = fileNameArray[0];
			result.phone = fileNameArray[1];
			result.filename = filename;
			result.date = convertDateYYYYMMDDHHSS(fileNameArray[lastIndex]);
			result.YYYYMMDD = moment(result.date).format("YYYY-MM-DD");
		}else{
			result.type = "unknown";
			result.phone = fileNameArray[0];
			result.filename = filename;
			result.date = convertDateYYYYMMDDHHSS(fileNameArray[lastIndex]);
			result.YYYYMMDD = moment(result.date).format("YYYY-MM-DD");
		}
		return result;
	}

	function getCodes(codes, codeValue){
		let result = _.filter(codes, (item)=>{
			return item.CODE_NM == codeValue;
		});
		return result;
	}

	function inputEventOnlyNumber(e){
		if(e) e.value = e.value.replace(/[^0-9]/g,'')
	}

	function checkNullArray(obj){
		return !obj || (obj && !obj.length);
	}

	function formatBytes(bytes, decimals = 2) {
		if (!+bytes) return '0 Bytes'
		const k = 1024
		const dm = decimals < 0 ? 0 : decimals
		const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
		const i = Math.floor(Math.log(bytes) / Math.log(k))
		return `${parseFloat((bytes / Math.pow(k, i)).toFixed(dm))} ${sizes[i]}`
	}

	/**
	 * 숫자만 입력되게
	 * @param str
	 * @returns {*}
	 */
	function onlyNumberReplace(str){
		return str.replace(/[^-0-9]/g,'');
	}
	_this.RegExp = {};
	// _this.RegExp.Email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
	// _this.RegExp.Email = /^[a-zA-Z0-9+-_\.]+@[a-zA-Z0-9-]+\.[a-zA-Z\.]$/i;
	_this.RegExp.Email = /^[0-9a-zA-Z+-_.]+@[0-9a-zA-Z-_.]+.[a-zA-Z]{2,3}$/i;
	// _this.RegExp.Password = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9~!@#$%^&*]{8,25}$/;
	_this.RegExp.Password = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!?<>@#$%^&*\(\)\{\}\[\]])[a-zA-Z0-9~!?<>@#$%^&*\(\)\{\}\[\]]{8,25}$/;
	_this.RegExp.Id = /^(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{5,11}$/;
	_this.onlyNumberReplace = onlyNumberReplace;
	_this.formatBytes = formatBytes;
	_this.inputEventOnlyNumber = inputEventOnlyNumber;
	_this.checkNullArray = checkNullArray;
	_this.getCodes = getCodes;
	_this.phoneFomatter = phoneFomatter;
	_this.convertDateYYYYMMDDHHSS = convertDateYYYYMMDDHHSS;
	_this.convertRecordFile = convertRecordFile;
	_this.convertUTF8 = convertUTF8;
	_this.getCompanyName = getCompanyName;
	_this.bizNoFormatter = bizNoFormatter;
	_this.checkCorporateRegiNumber = checkCorporateRegiNumber;
	_this.sleep = sleep;
	_this.nl2br = nl2br;
	_this.mathAbs = mathAbs;
	_this.mathAbsNumberWithCommas = mathAbsNumberWithCommas;
	_this.numberWithCommas = numberWithCommas;
	_this.mimeDetectImage = mimeDetectImage;
	_this.getfileSize = getfileSize;
	_this.getDateWithNull = getDateWithNull;
	_this.getNumString = getNumString;
	_this.isDate = isDate;
	_this.getString = getString;
	_this.asyncForEach = asyncForEach;
	_this.removeHypen = removeHypen;
})(CommonUtil);



(function(_globalInstance){
	//초기화
	if( !_globalInstance ) _globalInstance = {};

	/**
	 * 공통팝업 : 매체사/학교/기관 찾기
	 * @returns {Promise<CODE>}
	 */
	function searchMediaCompany(type){
		let _this = this;
		return new Promise(function(resolve, reject){
			let html = $(`
				<div class="pop_wrapper ui-modal" id="popSearch">
					<div class="pop_wrap pop_secondary">
						<div class="pop_cont">
							<form class="sch_box" id="popSearchForm">
								<input type="text" class="common_input2">
								<button type="submit" class="btn_type1 btn_blue"><img th:src="@{/assets/images/icons/btn_search.png}" alt="검색"> 검색</button>
							</form>
							<div class="bl_board">
								<div class="bl_board_head">
									<div class="bl_board_no">번호</div>
									<div class="bl_board_agency">${type == 1 ? '매체사' : type == 2 ? '기관' : type == 3 ? '학교' : ''}명</div>
									<div class="bl_board_subject">주소</div>
									<div ${type == 1 ? 'style="display: block"' : 'style="display: none"'} class="bl_board_state bl_board_state_sm">상태</div>
								</div>
								<div class="bl_board_body" style="max-height: 305px; overflow-y: auto"></div>
							</div>
							<div class="bl_card_empty">${type == 1 ? '매체사를' : type == 2 ? '기관을' : type == 3 ? '학교를' : ''} 검색 해주세요.</div>
						</div>
			
						<button type="button" class="pop_close pop-close">닫기</button>
					</div>
				</div>
			`);
			
			const forms = html.find(".sch_box");

			/** 검색 폼 이벤트 */
			forms.submit(function(e){
				e.preventDefault();
				_searchMediaCompanyInfo();
			});
			
			let _selectMediaCompanyInfo = function(value){
				modal.closeModal();
				resolve(value);
				globalOrgCode = value.organizationCode;
			};

			let _searchMediaCompanyInfo = function(page=0) { /** 매체사/기관/학교 찾기 */
				var subParams = {};
				subParams.organizationName = forms.find("input").val();
				if (!$.trim(subParams.organizationName)) return alert('검색어를 입력해주세요.');
				if (type != null) {
					subParams.organizationType = type //1: 매체사, 2: 기관, 3: 학교
				}
				let queryParams = Object.entries(subParams).filter(data => data != null).map(data => data.join('=')).join('&');
				if (page!=0) {queryParams += '&page=' + page}

				/** 매체사/기관/학교 정보 조회 */
				$.ajax({ //jquery ajax
					type:"get", //http method
					url:"/api/user/organization?" + queryParams, //값을 가져올 경로
					dataType:"json", //html, xml, text, script, json, jsonp 등 다양하게 쓸 수 있음
					success: function(data){   //요청 성공시 실행될 메서드
						let wrap = html.find('.bl_board > .bl_board_body');
						const paginateWrap = $('#popSearch > div > div')[0];
                        const paginate = document.createElement('div');
                        paginate.setAttribute('class', 'bl_paginate');

						if(data.content.length > 0) {
							wrap.empty();
							data.content.forEach(function(currentValue, currentIndex){
								if (currentValue !== undefined) {
									let tr = $("<div class='bl_board_unit'>"
										+ "<div class='bl_board_no'>" + ((page*10)+Number.parseInt(currentIndex + 1)) + "</div>"
										+ "<div class='bl_board_agency'>" + currentValue.organizationName + "</div>"
										+ "<a href='javascript: void(0)' class='bl_board_subject before_none'>" + (currentValue.organizationZipCode != null && currentValue.organizationZipCode != ""
											? "(" + currentValue.organizationZipCode + ")" + currentValue.organizationAddress1 + ", " + (currentValue.organizationAddress2 != null ? currentValue.organizationAddress2 : "")
											: "주소 정보 미존재(관리자 승인 필요 교육 기관)") + "</a>"
										+ (type == 1 ? "<div class='bl_board_state bl_board_state_sm'>" + (currentValue.isUsable == 'Y' ? "<span class='btn_type4 btn_green'>승인</span>" : "<span class='btn_type4 btn_red'>미승인</span>") + "</div>" : '')
										+ "</div>");
									tr.click(function(){
										if(currentValue.organizationZipCode != null){
											_selectMediaCompanyInfo(currentValue);
										}else{
											alert('관리자 승인이 필요한 기관입니다.');
										}
									});
									tr.appendTo(wrap);
								}
							});
							html.find('.bl_card_empty')[0].setAttribute("style", "display: none");
							if (document.querySelector('#popSearch > div > div > .bl_paginate') == null) {
                                paginateWrap.appendChild(paginate);
                            } else {
                                $('.bl_paginate').remove();
                                paginateWrap.appendChild(paginate);
                            }
                            pagination(data.pageable, false, '', false, function(pageNumber){
								_searchMediaCompanyInfo(pageNumber);
							});
						} else {
							wrap.html("");
							html.find('.bl_card_empty')[0].setAttribute("style", "display: block");
							html.find('.bl_card_empty')[0].setAttribute("style", "text-align: center");
							html.find('.bl_card_empty').text("조회 내역이 존재하지 않습니다.");
							if (document.querySelector('#popSearch > div > div > .bl_paginate') != null) {
                                $('.bl_paginate').remove();
                            }
						}
					},
					error: function(e){		 //요청 실패시 에러 확인을 위함
						if(e && e.responseJSON && e.responseJSON.resultMessage){
							alert(e.responseJSON.resultMessage);
						}else{
							alert('정보를 조회하는 과정에서 오류가 발생하였습니다.');
						}
						reject();
					}
				})
			}

			html.appendTo(document.body);
			let modal = html.openModal({
				removeComponent:true,
				onHide: function(){

				},
				onShow: function(){

				}
			});
		});
	}

	/**
	 * 공통팝업 : 매체사/학교/기관 등록신청
	 * @returns {Promise<CODE>}
	 */
	function registerOrg(registerType, journalistJoin) { 
		let _this = this;
		return new Promise(function(resolve, reject){
			let html = $(`
			<div class="pop_wrapper ui-modal" id="popMediaCompanyRegistration">
				<div class="pop_wrap pop_secondary">
					<div class="pop_cont">
						<!-- BOARD : WRITE -->
						<div class="bl_boardWrite">
							<table>
								<colgroup>
									<col class="hp_lg_w236">
									<col>
								</colgroup>
								<tbody>
									<tbody>
										<tr ${registerType == 1 ? 'style="display:none"' : registerType == 2 && journalistJoin ? 'style="display:none"' : registerType == 2 ? 'style="display:table-row"' : ''}>
											<th scope="row"><span class="el_required">*</span>구분</th>
											<td>
												<div class="radio_box">
													<input id="typeMediaCompany" type="radio" name="typeChk" checked>
													<label for="typeMediaCompany">
														<span></span>
														매체사
													</label>
												</div>
												<div class="radio_box">
													<input id="typeOrg" type="radio" name="typeChk">
													<label for="typeOrg">
														<span></span>
														기관
													</label>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row"><span class="el_required">*</span>${registerType == 1 ? '학교' : registerType == 2 && journalistJoin ? '매체사' : registerType == 2 ? '매체사/기관' : ''}명</th>
											<td>
												<input id="add_organizationName" type="text" class="common_input">
												<span class="guide_text mt-1">※ ${registerType == 1 ? '학교' : '기관'}명을 정확히 입력해주세요. 공백은 제외됩니다.</span>
											</td>
										</tr>
										<tr>
											<th scope="row"><span class="el_required">*</span>${registerType == 1 ? '학교' : registerType == 2 && journalistJoin ? '매체사' : registerType == 2 ? '매체사/기관' : ''} 전화번호</th>
											<td>
												<div class="common_input_box d-flex flex-column hp_md_w100p" style="align-items: baseline;">
													<div id="add_companyTel" class="phone">
														<select class="common_select" data-list="telList"></select>
														<div class="blank">-</div>
														<input type="text" maxlength="4" class="common_input" oninput="CommonUtil.inputEventOnlyNumber(this)"/>
														<div class="blank">-</div>
														<input type="text" maxlength="4" class="common_input" oninput="CommonUtil.inputEventOnlyNumber(this)"/>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row">${registerType == 1 ? '교장명' : registerType == 2 && journalistJoin ? '대표자명' : registerType == 2 ? '대표자명' : ''}</th>
											<td>
												<input type="text" maxlength="20" onchange="maxCheck(this)" id="add_representative" class="common_input form_input">
											</td>
										</tr>
										<!--<tr>
											<th scope="row"><span id="mediaCompanyChk" class="el_required">*</span>사업자등록증</th>
											<td>
												<div class="common_input_box">								
													<div class="d-flex flex-column hp_md_w100p">
														<div id="mainFileUpload" class="bl_upload js_upload hp_lg_w462 hp_md_w100p">
															<input type="text" id="file001" class="common_input bl_upload_path js_path" readonly >
															<label class="btn_type1 btn_md btn_gray bl_upload_btn">파일찾기</label>
															<input type="file" name="file" class="bl_upload_file js_file" accept=".gif, .jpg, .png, .pdf, .hwp, .hwpx, .doc, .docx, .ppt, .pptx, .xls, .xlsx, .zip" id="attachFilePath">
															<label for="file001" class="hidden_label">사업자등록증</label>
														</div>
														<p class="input_text"><span class="essential">*</span>파일형식 pdf, jpg, png, gif / 5MB 미만</p>
													</div>
												</div>
											</td>
										</tr>-->
										<tr ${registerType == 1 ? 'style="display: none"' : ''}>
											<th scope="row"><span id="mediaCompanyChk" class="el_required">*</span>사업자등록번호</th>
											<td>
												<div class="common_input_box">
													<div id="bizLicenseNumber" class="d-flex flex-column hp_w100p">
														<div class="company_number">
															<input type="text" class="common_input form_input"/>
														</div>
													</div>
												</div>
												<span class="guide_text mt-1">※ 하이픈(-)을 제외한 숫자만 입력해주세요.</span>
											</td>
										</tr>
										<tr>
											<th scope="row"><span class="el_required">*</span>${registerType == 1 ? '학교' : registerType == 2 && journalistJoin ? '매체사' : registerType == 2 ? '매체사/기관' : ''} 주소</th>
											<td>
												<div class="d-flex flex-column hp_md_w100p">
													<div class="d-flex">
														<input type="text" id="add_organizationZipCode" class="common_input form_input" disabled/>
														<div class="btn_type1  btn_md btn_gray" onclick="CommonUI.findAddressAddCompany()">찾아보기</div>
													</div>
													<input type="text" id="add_organizationAddress1" class="common_input" style="margin-top: 10px;" disabled />
													<input type="text" id="add_organizationAddress2" class="common_input" style="margin-top: 10px;" />
												</div>
											</td>
										</tr>
										<tr>
											<th scope="row">홈페이지</th>
											<td>
												<input type="text" class="common_input" id="homePage_pop">
											</td>
										</tr>
										<tr>
											<th scope="row">팩스번호</th>
											<td>
												<div class="common_input_box d-flex flex-column hp_md_w100p" style="align-items: baseline;">
													<div id="add_companyFax" class="fax">
														<select class="common_select" data-list="telList"></select>
														<div class="blank">-</div>
														<input type="text" maxlength="4" class="common_input" oninput="CommonUtil.inputEventOnlyNumber(this)"/>
														<div class="blank">-</div>
														<input type="text" maxlength="4" class="common_input" oninput="CommonUtil.inputEventOnlyNumber(this)"/>
													</div>
												</div>
											</td>
										</tr>
									</tbody>
								</tbody>
							</table>
						</div>
		
						<!-- GRID : BUTTON -->
						<div class="bl_gridBtns">
							<div class="bl_gridBtns_md">
								<a id="requestBtn" class="btn_type1 btn_md btn_blue">등록요청</a>
							</div>
						</div>
					</div>
		
					<button type="button" class="pop_close pop-close">닫기</button>
				</div>
			</div>
			`);
			
			html.appendTo(document.body);
			html.find(".common_select").each(function (idx, el) {
				CommonUI.dataListSelectBox(el);
			})
			html.find("#bizLicenseNumber input").on("propertychange change keyup paste input", function () {
				var currentVal = $(this).val();
				$(this).val(currentVal.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1'));
			});

			//타입 지정
			let type;
			if (registerType == 1) { //학교담당자
				type = 3;
			} else if (registerType == 2) { //기관담당자
				type = 1; //매체사로 초기 셋팅
			}
			$('input[name="typeChk"]').click(function () {
				if ($(this)[0].id == 'typeMediaCompany') {
					type = 1; //매체사
					$('#mediaCompanyChk').show();
				} else if ($(this)[0].id == 'typeOrg') { 
					type = 2; //기관
					$('#mediaCompanyChk').hide();
				}
			})

			//등록신청 api 요청
			html.find('#requestBtn').click(function () {
				if (type == 1) {
					createMediaCompany(); //매체사 등록신청 api
				} else if (type == 2 || type == 3) {
					createSchoolInfo(); //학교/기관 등록신청 api
				}
			});

			let modal = html.openModal({
				removeComponent:true,
				onHide: function(){},
				onShow: function(){}
			});

			/* 매체사 등록신청 */
			let createMediaCompany = function () {
				var subParams = {};
				var companyTelSel = html.find("#add_companyTel > select");
				var companyTelInput = html.find("#add_companyTel > input");
				var companyFaxSel = html.find("#add_companyFax > select");
				var companyFaxInput = html.find("#add_companyFax > input");
				// var organizationCode;

				subParams.organizationName = html.find('#add_organizationName').val();
				subParams.organizationZipCode = html.find("#add_organizationZipCode").val();
				subParams.organizationAddress1 = html.find("#add_organizationAddress1").val();
				subParams.organizationAddress2 = html.find("#add_organizationAddress2").val();
				subParams.organizationTelNumber = companyTelSel.val()+companyTelInput.eq(0).val()+companyTelInput.eq(1).val();
				subParams.organizationFaxNumber = companyFaxSel.val()+companyFaxInput.eq(0).val()+companyFaxInput.eq(1).val();
				subParams.organizationHomepage = html.find('#homePage_pop').val();
				subParams.bizLicenseNumber = html.find('#bizLicenseNumber > div > input').val();
				subParams.organizationRepresentativeName = html.find('#add_representative').val();

				if (companyFaxInput.eq(0).val() == '' && companyFaxInput.eq(1).val() == '') {
					// 팩스번호 빈값일 때 처리
					subParams.organizationFaxNumber = '';
				} else if (companyFaxInput.eq(0).val() == '' || companyFaxInput.eq(1).val() == '') { 
					alert('팩스번호가 올바르지 않습니다.'); return;
				}

				if (html.find('#add_organizationName').val() == '') { alert('매체사명은 필수입니다.'); return; }
				if (companyTelInput.eq(0).val() == '' && companyTelInput.eq(1).val() == '') { 
					alert('전화번호는 필수입니다.'); return;
				}
				if (companyTelInput.eq(0).val() == '' || companyTelInput.eq(1).val() == '') { 
					alert('전화번호가 올바르지 않습니다.'); return;
				}
				if(subParams.bizLicenseNumber == '') {
					alert('사업자 등록 번호는 필수입니다.'); return;
				}
				if(subParams.bizLicenseNumber.length > 11) {
					alert('사업자 등록 번호가 올바르지 않습니다.'); return;
				}
				if (html.find('#add_organizationName').val() == '' | html.find("#add_organizationAddress1").val() == '') { alert('주소는 필수입니다.'); return; }
	
				subParams.organizationType = "1"; //1: 매체사, 2: 기관, 3: 학교
				subParams.isUsable = 'Y';
				/** 매체사 등록 */
				$.ajax({ //jquery ajax
					type:"post", //http method
					url:"/api/user/organization/create", //값을 가져올 경로
					headers: {'Content-Type': 'application/json'},
					data: JSON.stringify(subParams),
					dataType:"json", //html, xml, text, script, json, jsonp 등 다양하게 쓸 수 있음
					success: function(data){   //요청 성공시 실행될 메서드
						html.find('.pop_close').click();
						resolve(data);
					},
					error: function(e){		 //요청 실패시 에러 확인을 위함
						if(e && e.responseJSON && e.responseJSON.resultMessage){
							alert(e.responseJSON.resultMessage);
						}else{
							alert('매체사를 등록하는 과정에서 오류가 발생하였습니다.');
						}
						//reject();
					}
				})
			}

			/* 학교/기관 등록 신청 */
			let createSchoolInfo = function () {
                var subParams = {};
				var companyTelSel = html.find("#add_companyTel > select");
				var companyTelInput = html.find("#add_companyTel > input");
				var companyFaxSel = html.find("#add_companyFax > select");
				var companyFaxInput = html.find("#add_companyFax > input");
				
                subParams.organizationName = html.find('#add_organizationName').val();
				subParams.organizationZipCode = html.find("#add_organizationZipCode").val();
				subParams.organizationAddress1 = html.find("#add_organizationAddress1").val();
				subParams.organizationAddress2 = html.find("#add_organizationAddress2").val();
				subParams.organizationTelNumber = companyTelSel.val()+companyTelInput.eq(0).val()+companyTelInput.eq(1).val();
				subParams.organizationFaxNumber = companyFaxSel.val()+companyFaxInput.eq(0).val()+companyFaxInput.eq(1).val();
				subParams.organizationHomepage = html.find('#homePage_pop').val();
				subParams.organizationRepresentativeName = html.find('#add_representative').val();
				
				if (companyFaxInput.eq(0).val() == '' && companyFaxInput.eq(1).val() == '') {
					// 팩스번호 빈값일 때 처리
					subParams.organizationFaxNumber = '';
				} else if (companyFaxInput.eq(0).val() == '' || companyFaxInput.eq(1).val() == '') { 
					alert('팩스번호가 올바르지 않습니다.'); return;
				}

				if (type == 3 && html.find('#add_organizationName').val() == '') { alert('학교명은 필수입니다.'); return; }
				if (type == 2 && html.find('#add_organizationName').val() == '') { alert('기관명은 필수입니다.'); return; }
				if (companyTelInput.eq(0).val() == '' && companyTelInput.eq(1).val() == '') { 
					alert('전화번호는 필수입니다.'); return;
				}
				if (companyTelInput.eq(0).val() == '' || companyTelInput.eq(1).val() == '') { 
					alert('전화번호가 올바르지 않습니다.'); return;
				}
				if (type == 2) {
					// 기관 등록 신청시 사업자등록번호 입력 및 체크
					subParams.bizLicenseNumber = html.find('#bizLicenseNumber > div > input').val();
					
					// 사업자등록번호 필수 체크 임시 주석 처리 (230202) -> 추후에 필수 체크로 변경시 html 필수 표시도 추가 필요
					// if(subParams.bizLicenseNumber == '') {
					// 	alert('사업자 등록 번호는 필수입니다.'); return;
					// }
					if(subParams.bizLicenseNumber.length > 11) {
						alert('사업자 등록 번호가 올바르지 않습니다.'); return;
					}
				}
				if (html.find('#add_organizationName').val() == '' | html.find("#add_organizationAddress1").val() == '') { alert('주소는 필수입니다.'); return; }

                // if($("#add_schoolTel > select").length>0){
                //     let companyTelS = $("#add_schoolTel > select");
                //     let companyTel = $("#add_schoolTel > input");
                //     subParams.organizationTelNumber = companyTelS.eq(0).val()+companyTel.eq(0).val()+companyTel.eq(1).val();

                // }else{
                //     let companyTel = $("#add_schoolTel > input");
                //     subParams.organizationTelNumber = companyTel.eq(0).val()+companyTel.eq(1).val()+companyTel.eq(2).val();
                // }

                // if($("#add_schoolFax > select").length>0){
                //     let companyFaxS = $("#add_schoolFax > select");
                //     let companyFax = $("#add_schoolFax > input");
                //     subParams.organizationFaxNumber = companyFaxS.eq(0).val()+companyFax.eq(0).val()+companyFax.eq(1).val();

                // }else{
                //     let companyFax = $("#add_schoolFax > input");
                //     subParams.organizationFaxNumber = companyFax.eq(0).val()+companyFax.eq(1).val()+companyFax.eq(2).val();
				// }
				
                // if(isMgTarget == true && subParams.bizLicenseNumber.length > 10) {
                //     alert('나이스 번호가 올바르지 않습니다.'); return;
                // }

				subParams.organizationType = type //1: 매체사, 2: 기관, 3: 학교
				subParams.isUsable = 'Y';
                /** 학교/기관 등록 */
                $.ajax({ //jquery ajax
                    type:"post", //http method
                    url:"/api/user/school/create", //값을 가져올 경로
                    headers: {'Content-Type': 'application/json'},
                    data: JSON.stringify(subParams),
                    dataType:"json", //html, xml, text, script, json, jsonp 등 다양하게 쓸 수 있음
                    success: function(data){   //요청 성공시 실행될 메서드
						html.find('.pop_close').click();
						resolve(data);
                    },
                    error: function(e){		 //요청 실패시 에러 확인을 위함
                        alert(e.responseJSON.resultMessage);
                    }
                })
            }
		});
	}

	/**
	 * 기관 등록 신청 폼 주소 찾기
	 */
	function findAddressAddCompany() {
		new daum.Postcode({
			oncomplete: function (data) {
				// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분
				// 각 주소의 노출 규칙에 따라 주소를 조합한다
				// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
				var addr = ''; // 주소 변수
				var extraAddr = ''; // 참고항목 변수

				//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
				if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
					addr = data.roadAddress;
				} else { // 사용자가 지번 주소를 선택했을 경우(J)
					addr = data.jibunAddress;
				}

				// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
				if (data.userSelectedType === 'R') {
					// 법정동명이 있을 경우 추가한다. (법정리는 제외)
					// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
					if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
						extraAddr += data.bname;
					}
					// 건물명이 있고, 공동주택일 경우 추가한다.
					if (data.buildingName !== '' && data.apartment === 'Y') {
						extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
					}
					// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
					if (extraAddr !== '') {
						extraAddr = ' (' + extraAddr + ')';
					}
					// 조합된 참고항목을 해당 필드에 넣는다.
					//document.getElementById("addressExtra").value = extraAddr;
				} else {
					//document.getElementById("addressExtra").value = '';
				}
				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				document.getElementById('add_organizationZipCode').value = data.zonecode;
				document.getElementById("add_organizationAddress1").value = addr;
				// 커서를 상세주소 필드로 이동한다.
				document.getElementById("add_organizationAddress2").focus();
			}
		}).open();
	}

	/**
	 * 데이터 picker 적용
	 * @param el
	 * @param callback
	 * @private
	 */
	function _elDatePicker(el, callback){
		el = el instanceof jQuery ? el : $(el);

		//초기화
		if(el.hasClass('hasDatepicker') == true) {
            el.siblings('.ui-datepicker-trigger,.ui-datepicker-apply').remove();
            el
            .removeAttr('id')
            .removeClass('hasDatepicker')
            .removeData('datepicker')
            .unbind();
		}

		let param = {};
		if (el.data("yearrange")) {
			param.yearRange = el.data("yearrange");
		} else {
			param.yearRange = 'c-50:c+20';
		}
		
		el.datepicker({
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
					clickDate(date.split('-')[0] + date.split('-')[1] + date.split('-')[2]);
				}
				if( callback && typeof callback == "function") callback();
			}
		});

		// sDate - eDate 보완
		el.find('.sdate').datepicker();
		el.find('.sdate').datepicker("option", "maxDate",el.find(".edate").val());
		el.find('.sdate').datepicker("option", "onClose", function (selectedDate) { 
			el.find('.edate').datepicker( "option", "minDate", selectedDate );
		});
		el.find('.edate').datepicker();
		el.find('.edate').datepicker("option", "minDate",el.find(".sdate").val());
		el.find('.edate').datepicker("option", "onClose", function (selectedDate) { 
			el.find('.sdate').datepicker( "option", "maxDate", selectedDate );
		});

		//datepicker 자동완성 기능 해제
		el.attr('autocomplete', 'off');
	}

	//data-upload="image" data-target="profile_img"
	function fileUploadImage(el){
		let _this = this;
		this.el = el instanceof jQuery ? el[0] : typeof el =="string" ? document.querySelector(el) : el;

		this.options = {
			target : this.el.dataset.target,
			wrap : undefined
		}
		if( document.querySelector(this.options.target).tagName != "IMG" ){
			this.options.wrap = document.querySelector(this.options.target);
			this.previewImage = new Image();
		}else{
			this.previewImage = document.querySelector(this.options.target)
		}

		this.el.addEventListener("change", function(e){
			if (e.target.files && e.target.files[0]) {
				var reader = new FileReader();
				reader.onload = function(evt) {
					_this.setImage( evt.target.result );
				};
				reader.readAsDataURL(e.target.files[0]);
			} else {
				_this.previewImage.style.display = "none";
			}
		});
	}
	fileUploadImage.prototype.setImage = function(src){
		let _this = this;
		_this.previewImage.src = src;
		_this.previewImage.onload = function(){
			if( _this.options.wrap ) {
				let ratio = _this.previewImage.width/_this.previewImage.height > 1 ? "가로" : "세로";
				$(_this.options.wrap).css({
					"background" : "none",
					"display": "flex",
					"justify-content": "center",
				});
				$(_this.previewImage).css({
					"max-width" : "100%",
					"max-height" : "100%",
					"object-fit" : ratio == "가로" ? "contain" : "contain"
				});
				$(_this.options.wrap).html(_this.previewImage);
			}
		}
	}

	function getNumberArray(input){
		const cleanInput = input.replaceAll(/[^0-9]/g, "");
		let result = "";
		const length = cleanInput.length;
		if(length === 8) {
			result = cleanInput.replace(/(\d{4})(\d{4})/, '$1-$2');
		} else if(cleanInput.startsWith("02") && (length === 9 || length === 10)) {
			result = cleanInput.replace(/(\d{2})(\d{3,4})(\d{4})/, '$1-$2-$3');
		} else if(!cleanInput.startsWith("02") && (length === 10 || length === 11)) {
			result = cleanInput.replace(/(\d{3})(\d{3,4})(\d{4})/, '$1-$2-$3');
		} else if(cleanInput.startsWith("0504") || cleanInput.startsWith("0505")){
			result = cleanInput.replace(/(\d{4})(\d{3,4})(\d{4})/, '$1-$2-$3');
		} else {
			result = undefined;
		}
		return result.split("-");
	}

	function laypopup(options){
		let opt = Object.assign({
			layerId : "meca_layer",
			title : "홈페이지 안내",
			content : "",
			img: "",
			link: "",
			width: "",
			height: "",
			startTop: 0,
			startLeft: 0,
			cache : true
		},options);
		var layerId=opt.layerId
		let html = $(`
					<div class="layerPopup mySlides" id=${opt.layerId} style="visibility: visible;">
						<div class="layerBox">`
							+(opt.title ? `<h4 class="title">${opt.title}</h4>` : '')+
							`<div class="cont">`
								/*<div>
									${opt.content}
								</div>`*/
								+ (opt.link ? `<a href="${opt.link}" target="_blank">` : '')
								+ (opt.img ? `<img src="/Public${opt.img}" style="width:100%; height: auto" />` : '')
								+ (opt.link ? `</a>` : '') +
							`</div>
							<form class="pop_form" name="pop_form">
								<div id="check" ${opt.cache == true ? 'style="display:inline-block;"':'style="display:none;"'}><input type="checkbox" name="chkbox" value="checkbox" id='${opt.layerId}_chk' ><label for="${opt.layerId}_chk">&nbsp&nbsp오늘 하루 동안 보지 않기</label></div>
								<div id="close" ><a class='close-button-layerpopup' href="#">닫기</a></div>
							</form>
						</div>
						</div>
					`);

		let setCookie=function( name, value, expiredays ){
			var todayDate = new Date();
			todayDate.setDate( todayDate.getDate() + expiredays );
			document.cookie = name+"="+escape( value )+";path=/;expires=" + todayDate.toGMTString()+";"
			// console.info("layerId : " , name, opt);
			// console.info("document.cookie : " , name+"=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";");
			// console.info("document.cookie : " , document.cookie);
		}

		//닫기 버튼 클릭 시
		html.find('.close-button-layerpopup').click(function(e){
			e.preventDefault();
			/*let form = html.find(".pop_form").get(0);
			if ( form.chkbox.checked ){
			    setCookie( opt.layerId, "done" , 1 );
			}
			html.css({visibility : "hidden" });
			html.remove();*/
			
			
			//전체팝업삭제
			document.body.querySelector('.slideshow-container').remove();
		});
		//html.find(".layerBox").css({left:opt.startLeft, top:opt.startTop}).draggable();
		//html.find(".layerBox").css({width:opt.width ? opt.width : '', height:opt.height ? opt.height : 'auto'});
		html.find(".layerBox").css({width:'552px' , height:'651px'});


		// '오늘 하루 보지 않기 클릭 시'
		html.find('#check').click(function(e){
			e.preventDefault();
			
			//setCookie( opt.layerId, "done" , 1 );
			
			//전체 적용
			setCookie( "entire_pops", "done" , 1 );
			//전체팝업삭제
			document.body.querySelector('.slideshow-container').remove();
			//html.css({visibility : "hidden" });
			//html.remove();
		});

		//if(document.cookie.indexOf(opt.layerId+"=done") < 0 ){
		if(document.cookie.indexOf('entire_pops'+"=done") < 0 ){     	// 쿠키 저장여부 체크
			$('.slideshowGroup').append(html);
			html.show();
			var ele=document.body.querySelector('#'+layerId);

			//ele.style.position='relative'
			//ele.style.top='183px'
			//ele.style.left='-148px'
			ele.style.padding='0 0 0 23px'

		}else {
			html.hide();
		}
		return html;
	}

	/**
	 * 매체 정보
	 * @param options
	 * @returns {Promise<void>}
	 */
	async function setMediaList(options){
		if( options.target ){
			$(options.target).hide();
		}
		if( !options.list ){
			let loading = $(`<div class="spinner-border text-primary" role="status"><span class="sr-only"></span></div>`);
			$(options.target).hide().after(loading);
			let ajaxData = await $.ajax({
				type: "get",
				url: "/api/user/media?size=1000000",
				headers: {'Content-Type': 'application/json'},
				dataType: "json",
				error: function(e) {
				}
			});
			if( ajaxData && ajaxData.content && ajaxData.content.length ){
				options.list = ajaxData.content;
				window.mediaDbList = ajaxData.content;
			}
			loading.remove();
		}
		let prefixId = "mediaList"+new Date().getTime();
		let html = $(`<select class="common_select media-selectbox-list" id="${prefixId}"><option value="">선택없음</option></select>`);
		let generalItem = function(findList, initValue){
			//값 초기화
			options.target.value = undefined;
			html.empty();
			html.append(`<option value="">선택없음</option>`);
			findList.forEach(function(item){
				html.append(`<option value="${item.mediaCode}" ${initValue && initValue == item.mediaCode? "selected" : ""}>${item.mediaName}</option>`);
			});
		}
		if( options.initOrgCode ){
			let findList = _.where(options.list, {organizationCode: options.initOrgCode });
			generalItem( findList, options.initMediaCode);
		}

		if( options.rejoin ){ //재가입시 매체 리스트 불러오도록 적용
			let findList = _.where(options.list, { organizationCode: options.organizationCodeTarget.value });
			generalItem(findList);
		}
		
		$(options.organizationCodeTarget).change(function(e){
			let findList = _.where(options.list, {organizationCode:options.organizationCodeTarget.value});
			generalItem(findList);
		});
		html.change(function(e){ options.target.value = e.target.value; });
		let labelEl = $('<label for="'+prefixId+'" class="hidden_label">매체 선택</label>');
		$(options.target).hide().after(html).after(labelEl);
	}
	_globalInstance.setMediaList = setMediaList;
	_globalInstance.Laypopup = laypopup;
	_globalInstance.getNumberArray = getNumberArray;
	_globalInstance.fileUploadImage = fileUploadImage;
	_globalInstance.datePicker = _elDatePicker;
	_globalInstance.searchMediaCompany = searchMediaCompany;
	_globalInstance.registerOrg = registerOrg;
	_globalInstance.findAddressAddCompany = findAddressAddCompany;
})(CommonUI);

/* 매체사/학교/기관 찾기 셋팅 ---------- */
function selectMediaCompanyInfo(code) {
	/* 매체사/기관/학교명, 매체사/기관/학교코드 셋팅 */
	document.querySelector('#organizationName').value = code.organizationName;
	if( document.querySelector('#organizationCode') ) document.querySelector('#organizationCode').value = code.organizationCode;
	if( document.querySelector('#representative') ) document.querySelector('#representative').value = code.organizationRepresentativeName;

	if (document.querySelector('#bizLicenseNumber') != null && code.organizationType != "3") {
		bizLicenseNumSet(code.bizLicenseNumber); /* 사업자번호 / 나이스번호 셋팅 */
	}
	addressSet(code.organizationZipCode, code.organizationAddress1, code.organizationAddress2); /* 주소 셋팅 */
	telNumSet(code.organizationTelNumber); /* 전화번호 셋팅 */
	faxNumSet(code.organizationFaxNumber); /* 팩스번호 셋팅 */
}

/* 매체사/학교/기관 등록신청 셋팅 ---------- */
function registerOrgInfo(code) {
	/* 매체사/기관/학교명, 매체사/기관/학교코드 셋팅 */
	document.querySelector('#organizationName').value = code.organizationName;
	if( document.querySelector('#organizationCode') ) document.querySelector('#organizationCode').value = code.organizationCode;
	if (document.querySelector('#bizLicenseNumber') != null && code.organizationType != "3") {
		// bizLicenseNumSet(code.bizLicenseNumber); /* 사업자번호 / 나이스번호 셋팅 */
		document.querySelector('#bizLicenseNumber > div > input').value = code.bizLicenseNumber.replace(/^(\d{3})(\d{2})(\d{5})$/gm, '$1$2$3');
	}
	addressSet(code.organizationZipCode, code.organizationAddress1, code.organizationAddress2); /* 주소 셋팅 */
	telNumSet(code.organizationTelNumber); /* 전화번호 셋팅 */
	faxNumSet(code.organizationFaxNumber); /* 팩스번호 셋팅 */
	if (code.organizationHomepage) {
		document.querySelector('#homepage').value = code.organizationHomepage; /* 홈페이지 셋팅 */
	}
}

function bizLicenseNumSet(bizLicenseNum) { 
	if(bizLicenseNum) {
		/** 사업자번호 / 나이스번호가 존재할 경우 셋팅 */
		// if (bizLicenseNum.length === 10) {
		// 	document.querySelector('#bizLicenseNumber > div > input').value = bizLicenseNum.replace(/^(\d{3})(\d{2})(\d{5})$/gm, '$1$2$3');
		// } else {
		// 	document.querySelector('#organizationName').value = "";
		// 	document.querySelector('#bizLicenseNumber > div > input').value = "";
		// 	alert('사업자번호 / 나이스번호가 올바르지 않습니다.');
		// }

		document.querySelector('#bizLicenseNumber > div > input').value = bizLicenseNum.replace(/^(\d{3})(\d{2})(\d{5})$/gm, '$1$2$3');
	} else {
		document.querySelector('#organizationName').value = "";
		document.querySelector('#bizLicenseNumber > div > input').value = "";
	}
	// document.querySelector('#bizLicenseNumber').value = code.bizLicenseNumber.replace(/^(\d{3})(\d{2})(\d{5})$/gm, '$1-$2-$3');
}

function validatorBizLicenseNum(number){
	var numberMap = number.replace(/-/gi, '').split('').map(function (d){
		return parseInt(d, 10);
	});
	if(numberMap.length == 10){
		var keyArr = [1, 3, 7, 1, 3, 7, 1, 3, 5];
		var chk = 0;

		keyArr.forEach(function(d, i){
			chk += d * numberMap[i];
		});

		chk += parseInt((keyArr[8] * numberMap[8])/ 10, 10);
		return Math.floor(numberMap[9]) === ( (10 - (chk % 10) ) % 10);
	}

	return false;
}

function addressSet(zipCode, addr1, addr2) {
	if(zipCode) { /** 우편번호가 존재할 경우 셋팅 */
		document.querySelector('#organizationZipCode').value = zipCode;
	} else document.querySelector('#organizationZipCode').value = "";
	if(addr1) { /** 주소1 존재할 경우 셋팅 */
		document.querySelector('#organizationAddress1').value = addr1;
	} else document.querySelector('#organizationAddress1').value = "";
	if(addr2) { /** 주소2 존재할 경우 셋팅 */
		document.querySelector('#organizationAddress2').value = addr2;
	} else document.querySelector('#organizationAddress2').value = "";
}

function telNumSet(telNum) { 
	if(telNum) {
		/** 전화번호 존재할 경우 셋팅 */
		let convertPhoneNum = telNum.replace(/(^02.{0}|^01.{1}|^050.{1}|^[0-9]{3})([0-9]*)([0-9]{4})/, "$1-$2-$3");
		let convertPhoneNum1 = convertPhoneNum.split('-')[0];
		let convertPhoneNum2 = convertPhoneNum.split('-')[1];
		let convertPhoneNum3 = convertPhoneNum.split('-')[2];

		if (document.querySelector('#schoolTel')) {
			document.querySelector('#schoolTel > input:nth-of-type(1)').value = convertPhoneNum1;
			document.querySelector('#schoolTel > input:nth-of-type(2)').value = convertPhoneNum2;
			document.querySelector('#schoolTel > input:nth-of-type(3)').value = convertPhoneNum3;
		} else {
			document.querySelector('#organizationTelNumber1').value = convertPhoneNum1;
			document.querySelector('#organizationTelNumber2').value = convertPhoneNum2;
			document.querySelector('#organizationTelNumber3').value = convertPhoneNum3;
		}
	} else {
		if (document.querySelector('#schoolTel')) {
			document.querySelector('#schoolTel > input:nth-of-type(1)').value = "";
			document.querySelector('#schoolTel > input:nth-of-type(2)').value = "";
			document.querySelector('#schoolTel > input:nth-of-type(3)').value = "";
		} else {
			document.querySelector('#organizationTelNumber1').value = "";
			document.querySelector('#organizationTelNumber2').value = "";
			document.querySelector('#organizationTelNumber3').value = "";
		}
	}
}

function faxNumSet(faxNum) { 
	if (faxNum) {
		/** 팩스번호 존재할 경우 셋팅 */
		let convertFaxNum = faxNum.replace(/(^02.{0}|^01.{1}|^050.{1}|^[0-9]{3})([0-9]*)([0-9]{4})/, "$1-$2-$3");
		let convertFaxNum1 = convertFaxNum.split('-')[0];
		let convertFaxNum2 = convertFaxNum.split('-')[1];
		let convertFaxNum3 = convertFaxNum.split('-')[2];

		if (typeof convertFaxNum2 == 'undefined' || typeof convertFaxNum3 == 'undefined') { 
			if (document.querySelector('#schoolFax')) {
				document.querySelector('#schoolFax > input:nth-child(3)').value = "";
				document.querySelector('#schoolFax > input:nth-child(5)').value = "";
			} else {
				document.querySelector('#organizationFaxNumber2').value = "";
				document.querySelector('#organizationFaxNumber3').value = "";
			}
			return false;
		}

		if (document.querySelector('#schoolFax')) {
			document.querySelector('#schoolFax > input:nth-child(1)').value = convertFaxNum1;
			document.querySelector('#schoolFax > input:nth-child(3)').value = convertFaxNum2;
			document.querySelector('#schoolFax > input:nth-child(5)').value = convertFaxNum3;
		} else {
			document.querySelector('#organizationFaxNumber1').value = convertFaxNum1;
			document.querySelector('#organizationFaxNumber2').value = convertFaxNum2;
			document.querySelector('#organizationFaxNumber3').value = convertFaxNum3;
		}
	} else {
		if (document.querySelector('#schoolFax')) {
			document.querySelector('#schoolFax > input:nth-child(1)').value = "";
			document.querySelector('#schoolFax > input:nth-child(3)').value = "";
			document.querySelector('#schoolFax > input:nth-child(5)').value = "";
		} else {
			document.querySelector('#organizationFaxNumber1').value = "";
			document.querySelector('#organizationFaxNumber2').value = "";
			document.querySelector('#organizationFaxNumber3').value = "";
		}
	}
}