window.addEventListener('load', function () {
    $(".download_ajax").each((index, item)=>{
        $(item).bind('click', function (e){
            e.preventDefault();
            let url = $(this).attr('href');
            $.ajax({
                type:"get", //http method
                url: url,
                success:function(data){
                    window.location.href=url;
                },
                error: function(e) {
                    if(e && e.responseJSON && e.responseJSON.resultMessage){
                        alert(e.responseJSON.resultMessage);
                    }else{
                        alert('다운로드하는 과정에서 오류가 발생했습니다.');
                    }
                }
            })
        })
    })
})