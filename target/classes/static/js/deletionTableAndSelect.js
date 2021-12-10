



//$(document).ready(function(){

$(function() {

        $('#car').chosen({
            width: '50%',
            no_results_text: 'Совпадений не найдено',
            placeholder_text_multiple: 'Выберите машину'
        });
        $('#client').chosen({
            width: '50%',
            no_results_text: 'Совпадений не найдено',
            placeholder_text_multiple: 'Выберите клиента'
        });

            console.log($("#log").css("width"));
            $("#reg").css({
            'width': ($("#log").css("width") + 'px')
            });
            $("#reg").css({
            'height': ($("#log").css("height") + 'px')
            });

         $("#car").change(function() {
             var cars = [];
             var tableExist = false;
             $.each($("#car option:selected"), function(){
                cars.push($(this).val());
             });

             let container = document.getElementById("container1");

             while (container.hasChildNodes()) {
                container.removeChild(container.lastChild);
             }

             for (let i = 0; i < number.length; i++) {
                if (cars.includes(number[i]["carId"].toString())) {
                     tableExist = true;
                     break;
                }

             }

             if (tableExist) {

                var cringe = "<table class='table table-bordered'> <tr> <td>Id</td> <td>Model</td> <td>CarReg</td> <td>Mark</td> <td>Status</td> </tr>";
                var head = $("#head");

                for (let i = 0; i < number.length; i++) {
                    if (cars.includes(number[i]["carId"].toString())) {
                        var str = "<tr>" + "<td>" + number[i]["carId"] + "</td>" +
                         "<td>" + number[i]["carMark"] + "</td>" +
                         "<td>" + number[i]["carModel"] + "</td>" +
                         "<td>" + number[i]["carRegistrationNumber"] + "</td>" +
                         "<td>" + number[i]["carStatus"] + "</td>"  + "</tr>"
                         cringe += str;
                    }
                }
                cringe += "</table>";
                container.innerHTML += cringe;
             }
         });

         $("#client").change(function() {
             var clients = [];
             var tableExist = false;
             $.each($("#client option:selected"), function(){
                clients.push($(this).val());
             });

             let container = document.getElementById("container2");

             while (container.hasChildNodes()) {
                 container.removeChild(container.lastChild);
             }

             for (let i = 0; i < number2.length; i++) {
                 if (clients.includes(number2[i]["clientID"].toString())) {
                     tableExist = true;
                     break;
                 }
             }


             if (tableExist) {
                 var cringe = "<table class='table table-bordered' > <tr> <td>Id</td> <td>Full name</td> <td>Passport</td> <td>phoneNumber</td> <td>email</td> </tr>";

                 for (let i = 0; i < number2.length; i++) {
                     if (clients.includes(number2[i]["clientID"].toString())) {
                         var str = "<tr>" + "<td>" + number2[i]["clientID"] + "</td>" +
                          "<td>" + number2[i]["fullName"] + "</td>" +
                          "<td>" + number2[i]["passport"] + "</td>" +
                          "<td>" + number2[i]["phoneNumber"] + "</td>" +
                          "<td>" + number2[i]["email"] + "</td>"  + "</tr>"
                          cringe += str;
                     }

                 }
                 cringe += "</table>";
                 container.innerHTML += cringe;
             }

         });

});