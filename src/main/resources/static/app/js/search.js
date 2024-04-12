function searchJson(jsonQuery){
    if(Object.keys(jsonQuery).length > 0){
        let query = Object.entries(jsonQuery).map(e => e.join('=')).join('&');
        return window.location.assign(window.location.pathname+"?"+query)
    }else{
        window.location.assign(window.location.pathname)
    }
}

function searchKeyword(options ={}){
    if(window.location.search!=''){
        let searchParams = new URLSearchParams(window.location.search);
        let size = searchParams.get("size") || options.size;
        if(size){
            $(`[data-list="countSortList"]`).val(size).prop("selected", true);
        }
        searchParams.delete("size");
        searchParams.delete("page");
        for (const [key, value] of searchParams.entries()) {
            if( $(`input:text#${key}`).length>0){
                $(`input:text#${key}`).val(value);
            }else if($(`input:radio[name = ${key}]`).length>0 ){
                $(`input:radio[name = ${key}]:input[value=${value}]`).attr("checked", true);
            }else if($(`select#${key}`).length>0){
                $(`select#${key}`).val(value).prop("selected", true);
            }else if($(`input:checkbox[name =${key}]`).length>0){
                let temp = value.split(",");
                for(const e of temp){
                    $(`input:checkbox[name =${key}]:input[value=${e}]`).attr("checked", true);
                }
            }
        }
    }
}

$(document).on("change",`[data-list="countSortList"]`, function (e){
    let size = $(this).val();
    if(window.location.search==''){
        return window.location.replace(window.location.pathname+`?size=${size}`);
    }else{
        let searchParams = new URLSearchParams(window.location.search);
        searchParams.delete("size");
        return window.location.replace(window.location.pathname+`?size=${size}&`+searchParams.toString())
    }
})


window.addEventListener('load', function () {searchKeyword()})
window.addEventListener('load', function () {
    $(".btnReset").bind('click',function (){
        window.location.assign(window.location.pathname)
    })
})