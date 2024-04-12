(function(_globalInstance){
    //초기화
    if( !_globalInstance ) _globalInstance = {};
    /* 공통 select data ----------- */
    //년도
    const yearList = [];
    const yearListValue = [];
    const startYear = 2010;
    const currentYear = new Date().getFullYear();
    yearList.push('년도');
    yearListValue.push(0);
    for (let i = currentYear + 1; i >= startYear; i--) {
        yearList.push(i+'년');
        yearListValue.push(i);
    }

    //월
    const monthList = [];
    const monthListValue = [];
    monthList.push('월');
    monthListValue.push(0);
    for (let i = 1; i <= 12; i++) {
        monthList.push(i+'월');
        let item = String(i);
        item = item.padStart(2, "0");
        monthListValue.push(item);
    }

    //차수
    const numList = [];
    const numListValue = [];
    const listNum = 20;
    numList.push('차수');
    numListValue.push(0);
    for (let i = 1; i <= listNum; i++) {
        numList.push(i+'차');
        numListValue.push(i);
    }

    //시
    const hourList = [];
    const hourListValue = [];
    hourList.push('시');
    hourListValue.push(0);
    for (let i = 0; i <= 23; i++) {
        let hour = String(i);
        hour = hour.padStart(2, "0");
        hourList.push(hour);
        hourListValue.push(hour);
    }

    //분
    const minuteList = [];
    const minuteListValue = [];
    minuteList.push('분');
    minuteListValue.push(60);
    for (let i = 0; i <= 11; i++) {
        let minute = String(i*5);
        minute = minute.padStart(2, "0");
        minuteList.push(minute);
        minuteListValue.push(minute);
    }

    //정렬순 - 리스트 개수
    const countSortList = ['10개씩','20개씩','30개씩','50개씩','100개씩'];
    const countSortListValue = [10,20,30,50,100];

    //제출처
    const submissionList = ['제출처','서울','세종','경남','인천','대전','부산','대구','울산','광주','경기','강원','충북','충남','전북','전남','경북','제주','헤외'];

    //검색(제목, 작성자)
    const schList = ['전체','제목','작성자'];

    //지역
    const regionList = ['지역','서울','세종','경남','인천','대전','부산','대구','울산','광주','경기','강원','충북','충남','전북','전남','경북','제주','해외'];

    //지사
    const branchList = ['전체','본사','세종대전지사','부산지사','광주지사','대구지사'];
    const branchListValue = ['0','HEAD','SEDA','BU','GW','DA'];

    //이메일
    const emailList = ['직접입력','naver.com','daum.net','hanmail.net','gmail.com','nate.com','hotmail.com'];

    //전화번호
    const telList = ['02','031','032','033','041','042','043','044','051','052','053','054','055','061','062','063','064','070','0504','0505'];

    //핸드폰번호
    const phoneList = ['010','011','016','017','018','019'];

    //은행
    const bankList = ['선택','기업은행','국민은행','농협중앙회','단위농협','우리은행','대구은행','외환은행','SC제일은행','부산은행','새마을금고','한국씨티은행','광주은행','경남은행','수협','신협','전북은행','제주은행','산림조합','우체국','하나은행','신한은행','동양종금증권','한국투자증권','삼성증권','미래에셋','우리투자증권','현대증권','SK증권','신한금융투자','하이증권','HMC증권','대신증권','하나대투증권','동부증권','유진증권','메리츠증권','신영증권','대우증권'];

    function _dataListSelectBox(item){

        item = item instanceof jQuery ? item[0] : item;
        if (item.getAttribute('data-list') != null) {
            let dataList = item.getAttribute('data-list');
            let dataListValue;
            switch(dataList) {
                case "yearList":
                    dataList = yearList;
                    dataListValue = yearListValue;
                    break;
                case "monthList":
                    dataList = monthList;
                    dataListValue = monthListValue;
                    break;
                case "numList":
                    dataList = numList;
                    dataListValue = numListValue;
                    break;
                case "hourList":
                    dataList = hourList;
                    dataListValue = hourListValue;
                    break;
                case "minuteList":
                    dataList = minuteList;
                    dataListValue = minuteListValue;
                    break;
                case "countSortList":
                    dataList = countSortList;
                    dataListValue = countSortListValue;
                    break;
                case "submissionList":
                    dataList = submissionList;
                    dataListValue = submissionList;
                    break;
                case "schList":
                    dataList = schList;
                    dataListValue = schList;
                    break;
                case "regionList":
                    dataList = regionList;
                    dataListValue = regionList;
                    break;
                case "branchList":
                    dataList = branchList;
                    dataListValue = branchListValue;
                    break;
                case "emailList":
                    dataList = emailList;
                    dataListValue = emailList;
                    break;
                case "telList":
                    dataList = telList;
                    dataListValue = telList;
                    break;
                case "phoneList":
                    dataList = phoneList;
                    dataListValue = phoneList;
                    break;
                case "bankList":
                    dataList = bankList;
                    dataListValue = bankList;
                    break;
            }

            for (let i = 0; i < dataList.length; i++) {
                let option = document.createElement("option");
                option.value = dataListValue[i];
                option.text = dataList[i];
                item.appendChild(option);
            }
        }
    }

    $(document).ready(function () {
        /* select option 생성 ----------- */
        let __select = document.querySelectorAll('select[class*="common_select"]');
        if (__select.length > 0) {
            __select.forEach(item => {
                _dataListSelectBox(item);
            });
        }
    });

    _globalInstance.dataListSelectBox = _dataListSelectBox;
})(CommonUI)


/* select 렌더링 후 selected 처리 ----------- */
function setSelected(val, targetId) {
    const target = document.getElementById(targetId);
    const len = target.options.length;

    for (let i = 0; i < len; i++) {
        //option value가 입력 받은 value 값과 일치할 경우 selected
        if (target.options[i].value == val){
            target.options[i].selected = true;
        }
    }  
}

/* 이메일 select option '직접입력' 처리 ----------- */
function domainWriteChk() {
    if (document.querySelector('#email3').value == '직접입력') {
        document.querySelector('#email2').value = '';
        document.querySelector('#email2').setAttribute('placeholder', '직접입력');
    } else {
        document.querySelector('#email2').removeAttribute('placeholder');
    }
}