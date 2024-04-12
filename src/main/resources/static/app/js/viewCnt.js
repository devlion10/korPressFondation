function viewCntUpdate(type){

    let apiURL;
    let key = window.location.href;
    key = key.split('?')[0]
    key = key.split('/')[key.split('/').length - 1];

    switch (type){
        case 'eduPlan':
            // 수업지도안
            apiURL = '/api/communication/class-guide/update-view-count/'
            break;
        case 'noti':
            // 공지사항
            apiURL = '/api/service-center/notice/update-view-count/'
            break;
        case 'press':
            // 행사소개(보도자료) -> 행사소개
            apiURL = '/api/edu-center/press/update-view-count/'
            break;
        case 'archive':
            // 자료실(교재자료, 영상자료, 연구/통계)
            apiURL = '/api/communication/archive/update-view-count/'
            break;
        case 'review':
            // 교육 후기방
            apiURL = '/api/communication/review/update-view-count/'
            break;
    }

    apiURL = apiURL + key;

    // 조회수 업데이트
    $.ajax({ //jquery ajax
        type: "PUT", //http method
        url: apiURL,
        headers: {'Content-Type': 'application/json'},
        success: function (data) {
            if (type != 'eduPlan') {
                if ($('#viewCount').length > 0) {
                    let viewCnt = $('#viewCount').text();
                    $('#viewCount').text(Number(viewCnt) + 1);
                } else {
                    let viewCnt = $('.bl_boardInfo dd').last().text();
                    $('.bl_boardInfo dd').last().text(Number(viewCnt) +1);
                }
            }
        },
        error: function(e) {
            console.log('에러')
        }

    })
}