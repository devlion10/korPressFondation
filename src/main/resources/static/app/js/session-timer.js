var timer = 120*60-1;

function authTimer() {
    var minutes;
    var seconds;

    var timeCheck = setInterval(function () {
        minutes = parseInt(timer / 60,10);
        seconds = parseInt(timer % 60,10);
        minutes = minutes <10? "0"+ minutes : minutes + "";
        seconds = seconds <10? "0"+ seconds : seconds + "";
        $ ("#session_time").text(minutes + ":"+ seconds);
        $ ("#session_time_m").text(minutes + ":"+ seconds);
        timer -= 1;

        if (timer < -1) {
            timer = 0;
            $ ("#session_time").text("00:00");
            $ ("#session_time_m").text("00:00");
            clearInterval(timeCheck);
            if (confirm("로그인 시간이 만료되어 로그아웃 됩니다. 재로그인 팝업을 여시겠습니까?")) {
                // location.href="/logout";
                alert("!로그인 후 팝업창을 닫아주시기 바랍니다!")
                window.open("/login", "_blank", "width=500, height=700")
            } else {
                location.href="/logout";
            }
        }
    },1000);
}