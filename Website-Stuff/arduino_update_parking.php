<!DOCTYPE HTML>
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
    <p id="demo1"></p>
    <script>

    function passVal() {
      var grid = "<?php echo $_GET['grid']; ?>";
      var goTo = "update_parking_based_on_time.php?grid=" + grid;
      window.location = goTo;
    }

    function checkingTime() {
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
              passVal();
          }
      }, 1000);
    }

    // Set the date we're counting down to
    var getTime = "<?php include('get_time.php'); ?>";
    var countDownDate = new Date(getTime).getTime();
    document.getElementById("demo1").innerHTML = getTime;

    if(countDownDate != "N/A") {
      checkingTime();
    }
    </script>



  </body>
</html>
