function convertToHtml(data){
    let html = data.replace(/(?:\r\n|\r|\n)/g, '<br />');
    return html;
}