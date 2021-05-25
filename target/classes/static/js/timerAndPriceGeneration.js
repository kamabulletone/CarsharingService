

$(document).ready(function(){

        var minutesLabel = document.getElementById("minutes");
        var secondsLabel = document.getElementById("seconds");
        var cost = $("#cost");

        if (sessionStorage.length !== 0 ) {
            console.log(sessionStorage.totalSeconds);
        } else {
          sessionStorage.totalSeconds = 0;
          sessionStorage.totalMinutes = 0;
          var now = new Date();
          sessionStorage.currDate = now.getTime();

        }

        var totalSeconds = sessionStorage.totalSeconds;
        var a = setInterval(setTime, 1000);

        function setTime()
        {
            let newDate = new Date();
            sessionStorage.totalSeconds = Math.ceil(Math.abs((newDate.getTime() - sessionStorage.currDate ) / 1000));
            secondsLabel.innerHTML = pad(sessionStorage.totalSeconds%60);
            minutesLabel.innerHTML = pad(parseInt(sessionStorage.totalSeconds/60));
            sessionStorage.totalMinutes = parseInt(totalSeconds/60);
            cost.attr("value", Number(parseFloat(sessionStorage.totalSeconds) * 0.2).toFixed(1));
        }

        function pad(val)
        {
            var valString = val + "";
            if(valString.length < 2)
            {
                return "0" + valString;
            }
            else
            {
                return valString;
            }
        }

        $("#stop" ).click(function() {

          clearInterval(a);
          sessionStorage.clear();
        });

        $("#submit" ).click(function() {


          sessionStorage.visitCount = 1;
          clearInterval(a);
          sessionStorage.clear();
        });

        $("#submit").submit(function(e) {

            e.preventDefault();

        });

    });