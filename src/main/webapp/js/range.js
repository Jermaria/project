
document.getElementById("custom-slider").addEventListener("input", function(event){
  let value = event.target.value;
  document.getElementById("current-value").innerText = value;
  document.getElementById("current-value").style.left =
  `${value*7.9}%`;
});
