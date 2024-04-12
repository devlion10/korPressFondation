let iframe;
let iframe_wrap;
let bindEvtFlag = false;

function openPop(id, src, popupId) {
    document.body.classList.add('scroll_lock');

    iframe = $("#"+popupId);
    if(id == ''){
        iframe.attr("src", src);
    } else {
        iframe.attr("src", src+'/'+id);
    }

    iframe_wrap = iframe[0].parentElement;
    iframe_wrap.style.display = 'block';

    let dimm = document.createElement('div');
    dimm.classList.add('dimm');
    document.body.appendChild(dimm);

    // 팝업 바깥 클릭시 closePop() 실행
    // iframe.get(0).onload = function () {
    //     bindEvtFlag = true;
    //     dimm.addEventListener('click', function () { closePop(); })
    // };
}

function closePop() {
    document.body.classList.remove('scroll_lock');

    iframe_wrap.style.display = 'none';

    let dimm = document.querySelector('.dimm');
    if(dimm) dimm.remove();

    iframe.attr("src", "about:blank");
}

//이용 및 약관 : 윈도우 팝업 실행시 header, footer 숨김
function openWindowPopHide() {
    $('body').addClass('winPop');
}

// iframe이 로드된 후 iframe 사이즈 postMessage()를 사용하여 상위 페이지로 전달
window.addEventListener('load', function () {
    let message = { height: document.body.scrollHeight, width: document.body.scrollWidth };	
    window.top.postMessage(message, "*");

    // 상위페이지에서 iframeSizeUpdate 함수 호출
    let fr = document.querySelector(".iframe_wrap iframe");
    if (fr != null) {
        iframeSizeUpdate();
    }
});

// 팝업 페이지에서 수신된 iframe 사이즈 업데이트 (상위 페이지에서 함수 호출 필요)
function iframeSizeUpdate() {
    let fr = document.querySelector(".iframe_wrap iframe");
    
    window.addEventListener('message', function(e) {
        let message = e.data;
        // if (fr.height == '100%' || fr.height == '') {
        //     fr.style.height = message.height + 'px';
        // }
    }, false);	
}