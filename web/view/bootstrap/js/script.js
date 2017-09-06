/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function loadDoc() {
    var xhttp;

    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("nbmessagetotalenonlu").innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", "view/output/nb_message_non_lu.jsp", true);
    xhttp.send();
}



window.onload = function () {
    loadDoc();
    setInterval(loadDoc, 1000);
}
