var modal1 = document.getElementsByClassName("sign_in_overlay")[0];
var modal2 = document.getElementsByClassName("sign_up_overlay")[0];
var modal3 = document.getElementsByClassName("show-overlay")[0];

var open1 = document.getElementsByClassName("open_sign_in")[0];
var close1 = document.getElementsByClassName("close_sign_in")[0];

var open2 = document.getElementsByClassName("open_sign_up")[0];
var close2 = document.getElementsByClassName("close_sign_up")[0];

open1.onclick = function() {
  modal1.style.display = "block";
}

open2.onclick = function() {
  modal2.style.display = "block";
}

close1.onclick = function() {
  modal1.style.display = "none";
}

close2.onclick = function() {
  modal2.style.display = "none";
}

window.onclick = function(e) {
  if (e.target == modal1) {
    modal1.style.display = "none";
  }
  if (e.target == modal2) {
    modal2.style.display = "none";
  }
  
}
