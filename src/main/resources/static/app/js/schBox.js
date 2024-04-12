function schBox(data11){
    // data11 = ['containTextType','containText']
    if( !data11 ) return console.warn('정보가 없습니다.');
    for(let i=0; i<data11.length; i++){
        let tmp = data11[i];

    }
    // const LOCATION = window.location;
    const nowParam = window.location.search;
    const param = new URLSearchParams(nowParam);

    const CONTAINTEXTTYPE = param.get('containTextType');
    const CONTAINTEXT = param.get('containText');
    const NOTICETYPE = param.get('noticeType');
    const MYQNATYPE = param.get('myQnaType');
    const PARTTYPE = param.get('materialType');

    const containTextType = document.getElementById('containTextType');
    const containText = document.getElementById('containText');
    const noticeType = document.getElementById('noticeType');
    const myQnaType = document.getElementById('myQnaType');
    const materialType = document.getElementById('materialType');

    // containTextType !== null && (containTextType.value = CONTAINTEXTTYPE);
    // CONTAINTEXTTYPE == null ? containTextType.value = '' : containTextType.value = CONTAINTEXTTYPE;
    if(containTextType != null){
        if(CONTAINTEXTTYPE == null){
            containTextType.value = '1';
        } else {
            containTextType.value = CONTAINTEXTTYPE;
        }
    }
    if(containText != null){
        if(CONTAINTEXT == null){
            containText.value = '1';
        } else {
            containText.value = CONTAINTEXT;
        }
    }
    if(noticeType != null){
        if(NOTICETYPE == null){
            noticeType.value = '1';
        } else {
            noticeType.value = NOTICETYPE;
        }
    }
    if(myQnaType != null){
        if(MYQNATYPE == null){
            myQnaType.value = '1';
        } else {
            myQnaType.value = MYQNATYPE;
        }
    }
    if(materialType&&materialType!="0"){
        if(PARTTYPE == null){
            materialType.value = '0';
        } else {
            materialType.value = PARTTYPE;
        }
    }
    // CONTAINTEXT == null ? containText.value = '' : containText.value = CONTAINTEXT;
    // NOTICETYPE == null ? noticeType.value = '' : noticeType.value = NOTICETYPE;
    // MYQNATYPE == null ? myQnaType.value = '' : myQnaType.value = MYQNATYPE;
}