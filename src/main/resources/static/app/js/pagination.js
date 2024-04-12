function pagination(pageInfo, writeBtn, btnText, apply=false, func){

    // 글작성에만 아이콘 붙고 나머지느 상관 없음

    const LOCATION = window.location;
    const nowLink = LOCATION.origin + LOCATION.pathname; // 전체 경로, http:// ~ /my-qna
    const nowParam = window.location.search;
    const param = new URLSearchParams(nowParam);

    let tempParam=new URLSearchParams(nowParam);
    tempParam.delete("size");
    tempParam.delete("page");
    let anotherString = tempParam.toString();
    const SIZE = param.get('size');

    // 페이징과 관련없는 파라미터의 유지를 위한 부분
    let anotherParam = '';
    anotherString && (anotherParam += `&${anotherString}`);
    SIZE && (anotherParam += `&size=${SIZE}`);

    // 페이징 관련
    const maxPage = pageInfo.totalPages; // 최대 페이지
    const blockPage = 10; // 한 블럭의 페이지

    // 페이지 블록 구하기
    const currentBlock = parseInt(pageInfo.page / blockPage) + 1;
    const totalBlock = parseInt(pageInfo.totalPages / blockPage) + 1;
    const startBlock = (currentBlock-1) * blockPage + 1;
    const endBlock = ((currentBlock * blockPage)) > maxPage ? maxPage : currentBlock * blockPage;

    // 페이지 숫자 버튼 랜더링
    const bl_paginate = document.querySelector('.bl_paginate');

    const bl_paginate_more = document.createElement('div');
    bl_paginate_more.setAttribute('class','bl_paginate_more');
    const bl_pageNums = document.createElement('div');
    bl_pageNums.setAttribute('class', 'bl_pageNums');

    // 맨 앞으로 버튼
    let firstItem = document.createElement('span');
    firstItem.setAttribute('class', 'bl_paginate_first bl_paginate_btn');
    pageInfo.first && firstItem.classList.add('disabled');
    firstItem.innerHTML = '<img src="/assets/images/icons/page_first.png" alt="첫 페이지"/>'
    bl_pageNums.appendChild(firstItem)

    // 앞 블록 버튼
    let prevItem = document.createElement('span');
    prevItem.setAttribute('class', 'bl_paginate_prev bl_paginate_btn')
    currentBlock === 1 && prevItem.classList.add('disabled');
    prevItem.innerHTML = '<img src="/assets/images/icons/page_prev.png" alt="이전 페이지"/>'
    bl_pageNums.appendChild(prevItem)

    // 페이지 랜더링
    for(let i = startBlock; i<endBlock+1; i++){
        let pageItem = document.createElement('a');
        pageItem.innerText = i;
        if(func == null){
            pageItem.setAttribute('href', `${nowLink}?page=${i-1}${anotherParam}`);
        } else {
            //pageItem.setAttribute('onclick',`${func}(${i-1})`);
            pageItem.addEventListener("click", function(e){
                e.preventDefault();
                func(i-1);
            });
        }
        (i-1) === pageInfo.page ? pageItem.setAttribute('class', 'is_active') : '';
        bl_pageNums.appendChild(pageItem)
    }

    // 뒷 블록 버튼
    let nextItem = document.createElement('span');
    nextItem.setAttribute('class', 'bl_paginate_next bl_paginate_btn')
    currentBlock === totalBlock && nextItem.classList.add('disabled');
    nextItem.innerHTML = '<img src="/assets/images/icons/page_next.png" alt="다음 페이지"/>'
    bl_pageNums.appendChild(nextItem)

    // 마지막 버튼
    let lastItem = document.createElement('span');
    lastItem.setAttribute('class', 'bl_paginate_last bl_paginate_btn')
    pageInfo.last && lastItem.classList.add('disabled');
    lastItem.innerHTML = '<img src="/assets/images/icons/page_last.png" alt="마지막 페이지"/>'
    bl_pageNums.appendChild(lastItem)

    bl_paginate_more.appendChild(bl_pageNums);
    bl_paginate.appendChild(bl_paginate_more);

    if(writeBtn){
        const writeBtn = document.createElement('div')
        writeBtn.setAttribute('class', 'bl_paginate_rgt')
        let writeBtnInner;

        if(apply){
            writeBtnInner = document.createElement('div');
            writeBtnInner.setAttribute('class', 'btn_type1 btn_blue');
            writeBtnInner.setAttribute('onclick', `aply()`);
        } else {
            writeBtnInner = document.createElement('a');
            writeBtnInner.setAttribute('class', 'btn_type1 btn_icon btn_write');
            writeBtnInner.setAttribute('href', `${nowLink}/write`);
        }

        writeBtnInner.innerText=btnText;
        writeBtn.appendChild(writeBtnInner);
        bl_paginate.appendChild(writeBtn);
    }

    // 페이징 숫자 버튼을 제외한 버튼
    const firstBtn = document.querySelector(".bl_paginate .bl_paginate_first");
    const prevBtn = document.querySelector(".bl_paginate .bl_paginate_prev");
    const nextBtn = document.querySelector(".bl_paginate .bl_paginate_next");
    const lastBtn = document.querySelector(".bl_paginate .bl_paginate_last");

    let ___prevPage = (startBlock -1) - blockPage;
    let ___nextPage = (startBlock -1) + blockPage;
    let ___maxPage = maxPage -1;


    if(func == null){
        firstBtn.addEventListener("click", () => {
            location.href = `${nowLink}?page=0${anotherParam}`;
            // callback && typeof callback == "function" && callback({ startBlock, blockPage});
        })
        prevBtn.addEventListener("click", () => {
            location.href = `${nowLink}?page=${(startBlock-1) - blockPage}${anotherParam}`
            // callback && typeof callback == "function" && callback();
        })
        nextBtn.addEventListener("click", () => {
            location.href = `${nowLink}?page=${(startBlock-1) + blockPage}${anotherParam}`
            // callback && typeof callback == "function" && callback();
        })
        lastBtn.addEventListener("click", () => {
            location.href = `${nowLink}?page=${maxPage-1}${anotherParam}`;
            // callback && typeof callback == "function" && callback();
        })
    } else {
        firstBtn.addEventListener("click", function(e){
            e.preventDefault();
            func(0);
        });
        prevBtn.addEventListener("click", function(e){
            e.preventDefault();
            func(___prevPage);
        });
        nextBtn.addEventListener("click", function(e){
            e.preventDefault();
            func(___nextPage);
        });
        lastBtn.addEventListener("click", function(e){
            e.preventDefault();
            func(___maxPage);
        });
    }
}