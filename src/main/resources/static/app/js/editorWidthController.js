function editorWidthController(){
    // 에디터를 통해서 만들어진 이미지와 표가 제공하는 width값을 넘는다면 크기가 조절

    const contWrap = document.querySelector('.bl_boardView_cont');
    const contImg = document.querySelectorAll('.bl_boardView_cont img');
    let contImgArr = [];
    contImg.forEach(item => {
        const tempObj = {
            width: item.clientWidth,
            height: item.clientHeight
        }
        contImgArr.push(tempObj);
    })
    const contTable = document.querySelectorAll('.bl_boardView_cont table');
    let contTableArr = [];
    contTable.forEach(item => {
        const tempObj = {
            width: item.clientWidth,
            // height: item.clientHeight
        }
        contTableArr.push(tempObj);
    })

    resizeWidth();

    function resizeWidth(){
        for(let i=0; i<contImg.length; i++){
            if(contWrap.clientWidth < contImg[i].clientWidth){
                contImg[i].style.width = '100%'
                contImg[i].style.height = 'auto'
            } else {
                contImg[i].style.width = contImgArr[i].width+'px';
                contImg[i].style.height = contImgArr[i].height+'px';
            }
        }
        for(let i=0; i<contTable.length; i++){
            if(contWrap.clientWidth < contTable[i].clientWidth){
                contTable[i].style.width = '100%';
            } else {
                contTable[i].style.width = contTableArr[i].width+'px';
            }
        }
    }

    window.addEventListener("resize", function() {
        resizeWidth();
    })
}