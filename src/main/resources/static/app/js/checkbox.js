function checkBox(){
    const all_check = document.getElementById("all_check");
    const checkItem = document.querySelectorAll(".chk_box.item_chk input");

    let checkList = [];

    // 전체 선택 해제
    all_check.addEventListener('click',function (){
        checkList = [];
        if(this.checked){
            // 체크
            checkItem.forEach((item) => {
                checkList.push(item.id)
                item.checked = true;
            })
        } else {
            // 체크 해제
            checkItem.forEach((item) => {
                item.checked = false;
            })
        }
    })

    // 개별 아이템 선택
    checkItem.forEach((item) => {
        item.addEventListener('click', function (){
            // 체크
            if(this.checked){
                checkList.push(this.id);
                checkList.length === checkItem.length && (all_check.checked = true);
            // 체크 해제
            } else {
                checkList = checkList.filter((el) => el !== this.id)
                all_check.checked = false;
            }
        })
    })
}