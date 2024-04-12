let contents = [];
let content = null;
let containTextType = null;
let containText = null;
let page = null;
let size = null;
let totalPages = null;
let sequenceNo = null;
let preSequenceNo = null;
let nextSequenceNo = null;
let selectIdx = null;

let isNextPage = false;
let isPrePage = false;

let globalKeyValue = ''

function preNextCont(apiURL, key, cont, refType = 0, options = {}){
    const param = new URLSearchParams(decodeURIComponent(window.location.search).replace("?", ""));

    globalKeyValue = key; // 키값선언
    contents = cont;
    containTextType = param.get("containTextType") || options.containTextType;
    containText = param.get("containText") || options.containTextType;
    page = parseInt(param.get("page")) || options.page;
    size = parseInt(param.get("size")) || options.size;
    totalPages = parseInt(param.get("totalPages")) || options.containTextType;
    sequenceNo = window.location.pathname.split('/')[window.location.pathname.split('/').length -1];
    preSequenceNo = param.get("preSequenceNo") || options.preSequenceNo;
    nextSequenceNo = param.get("nextSequenceNo") || options.nextSequenceNo;
    selectIdx = param.get("selectIdx") || options.selectIdx;

    try {
        isPrePage = $.parseJSON(param.get("isPrePage") || options.isPrePage);
    } catch (e) {
        isPrePage = false;
    }
    try {
        isNextPage = $.parseJSON(param.get("isNextPage") || options.isNextPage);
    } catch(e) {
        isNextPage = false;
    }

    if(preSequenceNo && preSequenceNo.length > 0){
        $.ajax({
            type:"get",
            url: apiURL,
            data: refType == 0 ? { sequenceNo: preSequenceNo,  page: 0, size: size} : { sequenceNo: preSequenceNo,  page: 0, size: size, materialCategory: refType},
            dataType:"json",
            success: function(data){
                if(data.content.length > 0){
                    const prePage = document.querySelector('#prePage');
                    prePage.setAttribute('onclick', `viewPage('${data.content[0][globalKeyValue]}')`)
                    prePage.className = 'bl_navPager_div';
                    prePage.getElementsByTagName('dd')[0].textContent = data.content[0].title;
                }
            },
            error: function (){
                console.log("이전 글 통신에러");
            }
        })
    } else if (Number.parseInt(page) <= Number.parseInt(totalPages)){
        $.ajax({
            type: "get",
            url: apiURL,
            data: refType == 0 ? { page: isPrePage ? page : page+1, size: size, containText: containText, containTextType: containTextType} : { page: isPrePage ? page : page+1, size: size, containText: containText, containTextType: containTextType, materialCategory: refType},
            dataType: "json",
            success: function (data){
                if(data.content.length > 0){
                    const prePage = document.querySelector('#prePage');
                    prePage.setAttribute('onclick', `viewPage('${data.content[isPrePage ? 1 : 0][globalKeyValue]}')`);
                    prePage.className = 'bl_navPager_div'
                    prePage.getElementsByTagName('dd')[0].textContent = data.content[isPrePage ? 1 : 0].title
                }
            },
            error: function (){
                console.log("이전 글 통신에러");
            }
        })
    }

    if(nextSequenceNo && nextSequenceNo.length > 0){
        $.ajax({
            type:"get",
            url: apiURL,
            data: refType == 0 ? { sequenceNo: nextSequenceNo, page: 0, size: size } : { sequenceNo: nextSequenceNo, page: 0, size: size, materialCategory: refType },
            dataType: "json",
            success: function (data){
                if(data.content.length > 0){
                    const nextPage = document.querySelector('#nextPage');
                    nextPage.setAttribute('onclick', `viewPage("${data.content[0][globalKeyValue]}")`);
                    nextPage.className = 'bl_navPager_div'
                    nextPage.getElementsByTagName('dd')[0].textContent = data.content[0].title;
                }
            },
            error: function (){
                console.log("이후 글 통신에러")
            }
        })
    } else if(0 <= Number.parseInt(page) && (isNextPage ? page : page - 1) >= 0){
        $.ajax({
            type:"get",
            url: apiURL,
            data: refType == 0 ? { page: isNextPage ? page : page -1, size: size, containText: containText, containTextType: containTextType } : { page: isNextPage ? page : page -1, size: size, containText: containText, containTextType: containTextType, materialCategory: refType },
            dataType: "json",
            success: function (data){
                if(data.content.length > 0){
                    const nextPage = document.querySelector("#nextPage");
                    nextPage.setAttribute('onclick',`viewPage('${data.content[isNextPage ? data.content.length - 2: data.content.length - 1][globalKeyValue]}')`)
                    nextPage.className = 'bl_navPager_div';
                    nextPage.getElementsByTagName('dd')[0].textContent = data.content[isNextPage ? data.content.length - 2: data.content.length - 1].title
                }
            },
            error: function (){
                console.log("이후 글 통신에러");
            }
        })
    }
}

function generalViewParam(lists, currentSerialNo) {
    let result = {};
    let tempNo = currentSerialNo;

    if (Number(tempNo) !== NaN) {
        // 숫자
        tempNo = Number(tempNo);
    }

    const evenIndex = lists.findIndex((item) => {
        return String(item[globalKeyValue]) === String(currentSerialNo)
    });
    let preSequenceNo;
    let nextSequenceNo;

    if (evenIndex <= 0 || evenIndex >= lists.length - 1) {
        if (lists[0][globalKeyValue] <= tempNo) {
            preSequenceNo = sequenceNo;
            nextSequenceNo = null;
        } else {
            preSequenceNo = null;
            nextSequenceNo = sequenceNo;
        }
    } else {
        preSequenceNo = lists[evenIndex + 1][globalKeyValue];
        nextSequenceNo = lists[evenIndex - 1][globalKeyValue];
    }

    if (tempNo > Number(sequenceNo)) {
        // 다음글
        selectIdx = Number(selectIdx) - 1;
    } else {
        // 이전글
        selectIdx = Number(selectIdx) + 1;
    }

    if (Number(selectIdx) >= size) {
        // 이전 페이지
        selectIdx = 0
    } else if (Number(selectIdx) < 0) {
        // 다음 페이지
        selectIdx = (size - 1)
    }

    if (evenIndex < 0 && preSequenceNo == null && nextSequenceNo != null) {
        isPrePage = true;
        isNextPage = false;
        result.page = page + 1;
    } else if (evenIndex < 0 && nextSequenceNo == null && preSequenceNo != null) {
        isNextPage = true;
        isPrePage = false;
        result.page = page - 1;
    } else {
        isNextPage = false;
        isPrePage = false;
        result.page = page;
    }


    result.size = size;
    result.totalPages = totalPages;
    result.preSequenceNo = preSequenceNo;
    result.nextSequenceNo = nextSequenceNo;
    result.containTextType = containTextType;
    result.containText = containText;
    result.selectIdx = evenIndex;

    result.isPrePage = isPrePage;
    result.isNextPage = isNextPage;
    return result;
}


function viewPage(currentSerialNo){
    let tempNo = currentSerialNo;
    const evenIndex = contents.findIndex((item) => String(item[globalKeyValue]) === String(tempNo));
    let preSequenceNo;
    let nextSequenceNo;

    if (evenIndex <= 0 || evenIndex >= contents.length - 1) {
        if(contents[0][globalKeyValue] <= tempNo) {
            preSequenceNo = sequenceNo;
            nextSequenceNo = null;
        } else {
            preSequenceNo = null;
            nextSequenceNo = sequenceNo;
        }
    } else {
        preSequenceNo = contents[evenIndex + 1][globalKeyValue];
        nextSequenceNo = contents[evenIndex - 1][globalKeyValue];
    }

    if( tempNo > sequenceNo ){
        // 다음글
        selectIdx = Number(selectIdx)-1;
    } else {
        // 이전글
        selectIdx = Number(selectIdx)+1;
    }

    if(Number(selectIdx) >= size){
        // 이전 페이지
        selectIdx = 0
    } else if(Number(selectIdx) < 0){
        // 다음 페이지
        selectIdx = (size -1)
    }


    const params = {};
    if(evenIndex < 0 && preSequenceNo == null && nextSequenceNo != null) {
        isPrePage = true;
        isNextPage = false;
        params.page = page + 1;
    } else if(evenIndex < 0 && nextSequenceNo == null && preSequenceNo != null) {
        isNextPage = true;
        isPrePage = false;
        params.page = page - 1;
    } else {
        isNextPage = false;
        isPrePage = false;
        params.page = page;
    }


    params.size = size;
    params.totalPages = totalPages;
    params.preSequenceNo = preSequenceNo;
    params.nextSequenceNo = nextSequenceNo;
    params.containTextType = containTextType;
    params.containText = containText;
    params.selectIdx = selectIdx;

    params.isPrePage = isPrePage;
    params.isNextPage = isNextPage;

    const queryParams = Object.entries(params).filter(data => data != null).map(data => data.join('=')).join('&');
    const originPath = window.location.pathname
    const deletePath = window.location.pathname.split('/')[window.location.pathname.split('/').length -1];
    const makePath = originPath.replace(deletePath,'');

    location.href = makePath + tempNo + "?" + queryParams;

}