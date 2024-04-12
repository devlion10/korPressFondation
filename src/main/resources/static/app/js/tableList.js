window.onload = function () {
    let addBtn = document.querySelectorAll(".btn_add");
    addBtn.forEach((el) => {
        el.addEventListener('click', function (e) {
            // node 선택
            let cloneDiv = e.target.nextElementSibling.lastElementChild;
            let firstDiv = e.target.nextElementSibling.firstElementChild
    
            // 노드 복사하기 (deep copy)
            let newDiv = cloneDiv.cloneNode(true);
    
            // 테이블 맨 첫번째 리스트 클론할 때 th 클레스 삭제
            if (cloneDiv == firstDiv) {
                let thList = newDiv.querySelectorAll('.table_cont > div:first-child');
                thList.forEach((th) => {
                    th.classList.add('hp_md_show');
                });
            }
    
            // 복사한 노드 붙여넣기
            cloneDiv.parentElement.append(newDiv);
        })
    });

    // export function removeList(delBtn) {
    const removeList = (delBtn) => {
        let table = delBtn.parentElement.parentElement.parentElement.parentElement;
        let btnList = table.querySelectorAll('.btn_del');
    
        // 테이블 맨 첫번째 리스트 삭제할 때 다음 리스트 th 클레스 추가
        let firstList = table.querySelector('.table_row:first-child');
        if (delBtn == firstList.querySelector('.btn_del')) {
            let nextList = delBtn.parentElement.parentElement.parentElement.nextElementSibling;
            if (nextList != null) {
                let thList = nextList.querySelectorAll('.table_cont > div:first-child');
                thList.forEach((th) => {
                    th.classList.remove('hp_md_show');
                });
            }
        }
    
        if (btnList.length == 1 && !confirm('현재 수업시간표가 1개입니다. 정말로 삭제하시겠습니까?')) {
            return;
        }
    
        // 클릭한 리스트 삭제
        delBtn.parentElement.parentElement.parentElement.remove();
    }
}