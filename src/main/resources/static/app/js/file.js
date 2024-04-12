// file 업로드 파일명 출력
window.addEventListener('load', function () {
    let bl_uploadGrid2 = document.querySelectorAll('.bl_uploadGrid2');
    let fileWrap = document.querySelectorAll('.js_upload');

    if (bl_uploadGrid2.length > 0) {
        fileWrap = bl_uploadGrid2;
    }
    if (fileWrap.length > 0) {
        fileWrap.forEach((item, idx) => {
            let fileTarget = item.querySelector('.js_file');
            let fileDelBtn = item.querySelector('.bl_uploadGrid2_del');
            if( fileTarget )fileTarget.addEventListener('change', fileUpload);
            if( fileDelBtn ) fileDelBtn.addEventListener('click', fileDelete);

            function fileUpload() {
                let fileName;
                if(fileTarget.files.length==0){
                    fileName = "";
                }else if (fileTarget.files.length < 2) {
                    fileName = fileTarget.files[0].name;
                } else { 
                    fileName = '파일 ' + fileTarget.files.length + '개';
                }
                let uploadName = item.querySelector('.js_path');
                uploadName.value = fileName;
            }

            function fileDelete() {
                if (fileTarget.files[0] == undefined) { 
                    return false;
                }
                let uploadName = item.querySelector('.js_path');
                uploadName.value = '';
            }
        })
    }
})


// file size
function fileTotalSize(fileArray){
    let totalSize = 0;
    for(let i=0; i<fileArray.length; i++){
        totalSize += fileArray[i].size;
    }
    return formatBytes(totalSize);
}

function formatBytes(bytes, decimals = 2) {
    if (bytes === 0) return '0 Bytes';

    const k = 1024;
    const dm = decimals < 0 ? 0 : decimals;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

    const i = Math.floor(Math.log(bytes) / Math.log(k));

    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
}