const MENU = [{
    id: 0,
    name: '마이페이지',
    // href: '/mypage/business-state',
    href: '/user',
    depth2: [{
        id: 0,
        name: '사업참여현황',
        href: '/mypage/business-state',
        depth3: [{
            id: 0,
            name: '공모사업',
            href: '/mypage/business-state'
        }, {
            id: 1,
            name: '강의확인서',
            href: '/mypage/business-state/confirmation'
        }]
    },{
        id: 1,
        name: '사업신청내역',
        href: '/mypage/bizapply',
        depth3: null
    }, {
        id: 2,
        name: '강사참여현황',
        href: '/mypage/instructor-state/lecture',
        depth3: [{
            id: 0,
            name: '강사모집',
            href: '/mypage/instructor-state/recruit'
        }, {
            id: 1,
            name: '강의현황',
            href: '/mypage/instructor-state/lecture'
        }, {
            id: 2,
            name: '강사지원문의',
            href: '/mypage/instructor-state/qna'
        }]
    }, {
        id: 3,
        name: '교육/수료현황',
        href: '/mypage/education-state',
        depth3: [{
            id: 0,
            name: '신청중인 과정',
            href: '/mypage/education-state/apply'
        }, {
            id: 1,
            name: '학습중인 과정',
            href: '/mypage/education-state'
        }, {
            id: 2,
            name: '학습종료 과정',
            href: '/mypage/education-state/result'
        }]
    }, {
        id: 4,
        name: '권한신청',
        href: '/user/auth-manage',
        depth3: null
    }, {
        id: 5,
        name: '수업지도안 작성',
        href: '/mypage/class-guide',
        depth3: null
    }, {
        id: 6,
        name: '개인정보관리',
        href: '/user',
        depth3: null
    }]
}, {
    id: 1,
    name: '교육신청',
    href: '/education/schedule',
    depth2: [{
        id: 0,
        name: '교육일정',
        href: '/education/schedule',
        depth3: null
    }, {
        id: 1,
        name: '화상/집합교육',
        href: '/education/application/lecture',
        depth3: null
    }, {
        id: 2,
        name: '이러닝교육',
        href: '/education/application/e-learning',
        depth3: null
    }],
}, {
    id: 2,
    name: '공모/자격',
    href: '/business/pbanc',
    depth2: [{
        id: 0,
        name: '공모사업',
        href: '/business/pbanc',
        depth3: [{
            id: 0,
            name: '공모사업',
            href: '/business/pbanc'
        }, {
            id: 1,
            name: '강사모집',
            href: '/business/instructor'
        }]
    }, {
        id: 1,
        name: '미디어교육사',
        href: '/business/qualification',
        depth3: null
    }]
}, {
    id: 3,
    name: '참여/소통',
    href: '/communication/edu-suggestion',
    depth2: [{
        id: 0,
        name: '교육주제제안',
        href: '/communication/edu-suggestion',
        depth3: null
        // [{
        //     id: 0,
        //     name: '교육주제제안',
        //     href: '/communication/edu-suggestion'
        // }, {
        //     id: 1,
        //     name: '언론인',
        //     href: '/communication/edu-suggestion/journalist'
        // }, {
        //     id: 2,
        //     name: '시민',
        //     href: '/communication/edu-suggestion/public'
        // }]
    }, {
        id: 1,
        name: '교육후기방',
        href: '/communication/review',
        depth3: null
    }, {
        id: 2,
        name: '자료실',
        href: '/communication/archive/class-guide',
        depth3: [{
            id: 0,
            name: '수업지도안',
            href: '/communication/archive/class-guide'
        }, {
            id: 1,
            name: '교재자료',
            href: '/communication/archive/edu-data'
        }, {
            id: 2,
            // name: '연구/통계',
            name: '연구/간행물',
            href: '/communication/archive/publicing'
            // href: '/communication/archive/research-data'
        }, {
            id: 3,
            name: '사업결과물',
            href: '/communication/archive/result'
        }, {
            // id: 3,
            id: 4,
            name: '영상자료',
            href: '/communication/archive/media'
        }, {
            id: 5,
            name: '기타자료',
            href: '/communication/archive/etc-data'
        }]
    }, {
        id: 3,
        name: '뉴스알고',
        href: '/communication/link/news',
        depth3: null
    }, {
        id: 4,
        name: 'e-NIE',
        href: '/communication/link/e-nie',
        depth3: null
    }, {
        id: 5,
        name: '이벤트/설문',
        href: '/communication/event?size=8',
        depth3: null
    }]
}, {
    id: 4,
    name: '고객센터',
    href: '/service-center/notice',
    depth2: [{
        id: 0,
        name: '공지사항',
        href: '/service-center/notice',
        depth3: null
    }, {
        id: 1,
        name: '자주하는 질문',
        href: '/service-center/top-qna',
        depth3: null
    }, {
        id: 2,
        name: '1:1 문의',
        href: '/service-center/my-qna',
        depth3: null
    }, {
        id: 3,
        name: '원격지원서비스',
        href: '/service-center/remote',
        depth3: null
    }]
}, {
    id: 5,
    name: '연수원 소개',
    href: '/edu-center/introduce',
    depth2: [{
        id: 0,
        name: '미디어교육원 소개',
        href: '/edu-center/introduce',
        depth3: null
    }, {
        id: 1,
        name: '사업소개',
        href: '/edu-center/introduce/introduce',
        depth3: null
    }, {
        id: 2,
        name: '행사소개',
        href: '/edu-center/press',
        depth3: null
    }
    // , {
    //     id: 3,
    //     name: '교육장 사용신청',
    //     href: '/edu-center/apply',
    //     depth3: null
    // }
    ]
}, {
    id: 6,
    name: '약관 및 방침',
    href: '/terms/privacy-policy',
    depth2: [{
        id: 0,
        name: '개인정보 처리 방침',
        href: '/terms/privacy-policy',
        depth3: null
    }, {
        id: 1,
        name: '이용약관',
        href: '/terms/of-use',
        depth3: null
    }]
}];


// d1 = 1뎁스, d2 = 2뎁스, d3 = 3뎁스
function navigation(d1, d2, d3){
    const D3CON = MENU[d1].depth2[d2].depth3 !== null;

    // 네비게이션 랜더링
    const NAV = document.querySelector('.navigation');
    const NAVRANDER = document.createElement('div');
    NAVRANDER.setAttribute('class','ly_grid d-flex');

    // 홈 버튼
    const HOMEBTN = document.createElement('a');
    HOMEBTN.setAttribute('class', 'home');
    HOMEBTN.setAttribute('href', '/');

    const HOMEBTN_TXT = document.createElement('span');
    HOMEBTN_TXT.innerText = "홈";
    HOMEBTN.appendChild(HOMEBTN_TXT);

    NAVRANDER.appendChild(HOMEBTN);

    // 1depth
    const DEPTH1 = document.createElement('ul');
    DEPTH1.setAttribute('class', 'depth_1');

    const DEPTH1LIST = document.createElement('li');
    const SELECTDEPTH1 = document.createElement('a');
    SELECTDEPTH1.innerText = MENU[d1].name;
    SELECTDEPTH1.setAttribute('href', 'javascript: void(0)'); //tab key 이동시 focus 되도록 하기 위해 추가(웹 접근성)
    DEPTH1LIST.appendChild(SELECTDEPTH1)

    const MENU1WRAP = document.createElement('ul');
    MENU1WRAP.setAttribute('class', 'depth_1_box');

    for(let i=0; i<MENU.length; i++){
        const MENU1 = document.createElement('li');
        if(i === d1){
            MENU1.setAttribute('class', 'active');
        }
        const MENU1ITEM = document.createElement('a');
        MENU1ITEM.setAttribute('href', MENU[i].href);
        MENU1ITEM.innerText = MENU[i].name;
        MENU1.appendChild(MENU1ITEM)
        MENU1WRAP.appendChild(MENU1);
    }

    DEPTH1LIST.appendChild(MENU1WRAP);
    DEPTH1.appendChild(DEPTH1LIST)

    NAVRANDER.appendChild(DEPTH1)

    // 2depth
    const DEPTH2 = document.createElement('ul');
    DEPTH2.setAttribute('class', 'depth_2');

    const DEPTH2LIST = document.createElement('li');
    const SELECTDEPTH2 = document.createElement('a');
    SELECTDEPTH2.innerText = MENU[d1].depth2[d2].name;
    SELECTDEPTH2.setAttribute('href', 'javascript: void(0)'); //tab key 이동시 focus 되도록 하기 위해 추가(웹 접근성)
    DEPTH2LIST.appendChild(SELECTDEPTH2)

    const MENU2WRAP = document.createElement('ul');
    MENU2WRAP.setAttribute('class', 'depth_2_box');

    for(let i=0; i<MENU[d1].depth2.length; i++){
        const MENU2 = document.createElement('li');
        if(i === d2){
            MENU2.setAttribute('class', 'active');
        }
        const MENU2ITEM = document.createElement('a');
        MENU2ITEM.setAttribute('href', MENU[d1].depth2[i].href);
        MENU2ITEM.innerText = MENU[d1].depth2[i].name;
        MENU2.appendChild(MENU2ITEM)
        MENU2WRAP.appendChild(MENU2);
    }

    DEPTH2LIST.appendChild(MENU2WRAP);
    DEPTH2.appendChild(DEPTH2LIST)

    NAVRANDER.appendChild(DEPTH2)

    let DEPTH3, DEPTH3LIST, SELECTDEPTH3, MENU3WRAP, menu3visible;


    // 3depth
    if(D3CON){
        // const DEPTH3 = document.createElement('ul');
        DEPTH3 = document.createElement('ul');
        DEPTH3.setAttribute('class', 'depth_3');

        // const DEPTH3LIST = document.createElement('li');
        // const SELECTDEPTH3 = document.createElement('a');
        DEPTH3LIST = document.createElement('li');
        SELECTDEPTH3 = document.createElement('a');
        SELECTDEPTH3.innerText = MENU[d1].depth2[d2].depth3[d3].name;
        SELECTDEPTH3.setAttribute('href', 'javascript: void(0)'); //tab key 이동시 focus 되도록 하기 위해 추가(웹 접근성)
        DEPTH3LIST.appendChild(SELECTDEPTH3)

        MENU3WRAP = document.createElement('ul');
        MENU3WRAP.setAttribute('class', 'depth_3_box');

        for(let i=0; i<MENU[d1].depth2[d2].depth3.length; i++){
            const MENU3 = document.createElement('li');
            if(i === d3){
                MENU3.setAttribute('class', 'active');
            }
            const MENU3ITEM = document.createElement('a');
            MENU3ITEM.setAttribute('href', MENU[d1].depth2[d2].depth3[i].href);
            MENU3ITEM.innerText = MENU[d1].depth2[d2].depth3[i].name;
            MENU3.appendChild(MENU3ITEM)
            MENU3WRAP.appendChild(MENU3);
        }

        DEPTH3LIST.appendChild(MENU3WRAP);
        DEPTH3.appendChild(DEPTH3LIST)

        NAVRANDER.appendChild(DEPTH3)
    }

    // 네비게이션 랜더링
    NAV.appendChild(NAVRANDER)

    let menu1visible = false;
    let menu2visible = false;
    if(D3CON){
        menu3visible = false;
    }

    // 이벤트 추가
    document.addEventListener('click',(e) => {
        if(e.target === SELECTDEPTH1){
            if(menu1visible){
                MENU1WRAP.setAttribute('style', 'display: none');
                menu1visible = false;
            } else {
                MENU1WRAP.setAttribute('style', 'display: block');
                menu1visible = true;
            }
            MENU2WRAP.setAttribute('style', 'display: none');
            menu2visible = false;
            if(D3CON){
                MENU3WRAP.setAttribute('style', 'display: none');
                menu3visible = false;
            }
        } else if(e.target === SELECTDEPTH2){
            if(menu2visible){
                MENU2WRAP.setAttribute('style', 'display: none');
                menu2visible = false;
            } else {
                MENU2WRAP.setAttribute('style', 'display: block');
                menu2visible = true;
            }
            MENU1WRAP.setAttribute('style', 'display: none');
            menu1visible = false;
            if(D3CON){
                menu3visible = false;
                MENU3WRAP.setAttribute('style', 'display: none');
            }
        } else if(D3CON){
            if(e.target === SELECTDEPTH3){
                if(menu3visible){
                    MENU3WRAP.setAttribute('style', 'display: none');
                    menu3visible = false;
                } else {
                    MENU3WRAP.setAttribute('style', 'display: block');
                    menu3visible = true;
                }
                MENU1WRAP.setAttribute('style', 'display: none');
                MENU2WRAP.setAttribute('style', 'display: none');
                menu1visible = false;
                menu2visible = false;
            } else {
                MENU1WRAP.setAttribute('style', 'display: none');
                MENU2WRAP.setAttribute('style', 'display: none');
                menu1visible = false;
                menu2visible = false;
                MENU3WRAP.setAttribute('style', 'display: none');
                menu3visible = false;

            }
        } else {
            MENU1WRAP.setAttribute('style', 'display: none');
            MENU2WRAP.setAttribute('style', 'display: none');
            menu1visible = false;
            menu2visible = false;
        }
    })

    // tab key event
    $(document).keyup(function (e) {
        if (e.keyCode === 9 && !e.shiftKey || e.keyCode === 9 && e.shiftKey) {
            if (e.target == HOMEBTN) {
                MENU1WRAP.setAttribute('style', 'display: none');
                MENU2WRAP.setAttribute('style', 'display: none');
                menu1visible = false;
                menu2visible = false;
                if (D3CON) {
                    MENU3WRAP.setAttribute('style', 'display: none');
                    menu3visible = false;
                }
            } else if (e.target === SELECTDEPTH1) {
                MENU1WRAP.setAttribute('style', 'display: block');
                menu1visible = true;
                MENU2WRAP.setAttribute('style', 'display: none');
                menu2visible = false;
                if (D3CON) {
                    MENU3WRAP.setAttribute('style', 'display: none');
                    menu3visible = false;
                }
            } else if (e.target === SELECTDEPTH2) {
                MENU2WRAP.setAttribute('style', 'display: block');
                menu2visible = true;
                MENU1WRAP.setAttribute('style', 'display: none');
                menu1visible = false;
                if (D3CON) {
                    menu3visible = false;
                    MENU3WRAP.setAttribute('style', 'display: none');
                }
            } else if (D3CON) {
                if (e.target === SELECTDEPTH3) {
                    MENU3WRAP.setAttribute('style', 'display: block');
                    menu3visible = true;
                    MENU1WRAP.setAttribute('style', 'display: none');
                    MENU2WRAP.setAttribute('style', 'display: none');
                    menu1visible = false;
                    menu2visible = false;
                }
            }
        } 
    });

    $(document).keydown(function (e) {
        if (!D3CON) {
            let allLi = MENU2WRAP.querySelectorAll('li');
            let lastLi = allLi[allLi.length - 1];
            if (e.target == lastLi.children[0]) {
                MENU1WRAP.setAttribute('style', 'display: none');
                MENU2WRAP.setAttribute('style', 'display: none');
                menu1visible = false;
                menu2visible = false;
            }
        } else if (D3CON) {
            let allLi = MENU3WRAP.querySelectorAll('li');
            let lastLi = allLi[allLi.length - 1];
            if (e.target == lastLi.children[0]) {
                MENU1WRAP.setAttribute('style', 'display: none');
                MENU2WRAP.setAttribute('style', 'display: none');
                menu1visible = false;
                menu2visible = false;
                MENU3WRAP.setAttribute('style', 'display: none');
                menu3visible = false;
            }
        }
    });
}


