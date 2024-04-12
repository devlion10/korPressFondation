function CV_checkIdPattern(str){
    var pattern1 = /[0-9]/; // 숫자
    var pattern2 = /[a-zA-Z]/; // 문자
    var pattern3 = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자

    var numtextyn = (pattern1.test(str) || pattern2.test(str));
    if(!numtextyn || pattern3.test(str) || str.length > 13 || str.length < 5) {
        alert("아이디는 6~12자리 문자 또는 숫자로 구성하여야 합니다.");
        return false;
    } else {
        return true;
    }
}

function CV_checkPasswordPattern(str) {
    var pattern1 = /[0-9]/; // 숫자
    var pattern2 = /[a-zA-Z]/; // 문자
    var pattern3 = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자
    if(!pattern1.test(str) || !pattern2.test(str) || !pattern3.test(str) || str.length < 7) {
        alert("비밀번호는 8자리 이상 문자, 숫자, 특수문자로 구성하여야 합니다.");
        return false;
    } else {
        return true;
    }
}