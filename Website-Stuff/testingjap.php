<!-- <!DOCTYPE HTML>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
<style>
p {
  text-align: center;
  font-size: 60px;
}
</style>
</head>
<body>

<p id="demo"></p>

<script>

// var test = "<?php echo $_GET['name']; ?>";
// document.getElementById("demo").innerHTML = test;
function passVal() {
    window.location = "testjer.php";
}

// Set the date we're counting down to
var countDownDate = new Date("<?php include('testinglagi.php') ?>").getTime();

// Update the count down every 1 second
var x = setInterval(function() {

    // Get todays date and time
    var now = new Date().getTime();

    // Find the distance between now an the count down date
    var distance = countDownDate - now;

    // Time calculations for days, hours, minutes and seconds
    //var days = Math.floor(distance / (1000 * 60 * 60 * 24));
    var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((distance % (1000 * 60)) / 1000);

    // Output the result in an element with id="demo"
    document.getElementById("demo").innerHTML = hours + "h "
    + minutes + "m " + seconds + "s ";

    // If the count down is over, write some text
    if (distance < 0) {
        clearInterval(x);
        document.getElementById("demo").innerHTML = "EXPIRED";
        //passVal();
    }
}, 3000);
</script>

<!-- </br>
<p id="demo2"></p>

<script>
var count = 0;
var y = setInterval(function() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            myObj = JSON.parse(this.responseText);
            document.getElementById("demo2").innerHTML = myObj.name + myObj.age + myObj.city + count;
        }
    };
    xmlhttp.open("GET", "testinglagi.php", true);
    xmlhttp.send();

    if(count == 15) {
      clearInterval(y);
    }

    count++;

}, 2000);

</script> -->


</body>
</html> -->
