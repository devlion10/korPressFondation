function snsShare(type){
    let url = window.document.location.href;
    let title = '';

    if($('.bl_boardView_subject').text() !== ''){
        title = $('.bl_boardView_subject').text();
    } else if($('.bl_boardTitle_tit').text() !== ''){
        title = $('.bl_boardTitle_tit').text()
    } else if($('#sns_title').text() !== ''){
        title = $('#sns_title').text()
    }
    switch (type){
        case 'naver' :
            window.open(`https://share.naver.com/web/shareView.nhn?url=${url}&title=${title}`)
            break;
        case 'facebook' :
            window.open("http://www.facebook.com/sharer/sharer.php?u=" + encodeURIComponent(url));
            break;
        case 'kakao' :
            Kakao.init('f0d4711ab9cce56da50f6a302a35700a');
            Kakao.Story.share({url: url});
            break;
        case 'twitter' :
            window.open("https://twitter.com/intent/tweet?turl=" + encodeURIComponent(url));
            break;
    //     case 'copy' :
    //         let textarea = document.createElement("textarea");
    //         document.body.appendChild(textarea);
    //
    //         textarea.value = url;
    //         textarea.select();
    //         document.execCommand("copy");
    //         document.body.removeChild(textarea);
    //         alert("URL이 복사되었습니다.")
    //         break;
    }
}
$(window).on("load", function (){
    $('.bl_title_sns').html(`
        <div class="btn_sns btn_naver" onclick="snsShare('naver')"></div>
        <div class="btn_sns btn_facebook" onclick="snsShare('facebook')"></div>
        <div class="btn_sns btn_kakao" onclick="snsShare('kakao')"></div>
        <div class="btn_sns btn_twitter" onclick="snsShare('twitter')"></div>
    `)
})