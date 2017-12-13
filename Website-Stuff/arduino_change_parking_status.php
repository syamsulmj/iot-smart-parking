<?php
  require_once('dbConnect.php');

  // http://intelligenceparkingsystem.000webhostapp.com/arduino_change_parking_status.php?parking1=1&parking2=0...


  if($_GET['parking1'] != "" && $_GET['parking2'] != "" && $_GET['parking3'] != "" && $_GET['parking4'] != "") {

    $parking[0] = $_GET['parking1'];
    $parking[1] = $_GET['parking2'];
    $parking[2] = $_GET['parking3'];
    $parking[3] = $_GET['parking4'];

    $status1 = ($_GET['parking1'] == 1) ? "red" : "green";
    $status2 = ($_GET['parking2'] == 1) ? "red" : "green";
    $status3 = ($_GET['parking3'] == 1) ? "red" : "green";
    $status4 = ($_GET['parking4'] == 1) ? "red" : "green";

    $grid[0] = "1-1";
    $grid[1] = "1-2";
    $grid[2] = "2-1";
    $grid[3] = "2-2";

    $checkQueryGrid1 = "SELECT * FROM parking_status WHERE grid='1-1'";
    $checkQueryGrid2 = "SELECT * FROM parking_status WHERE grid='1-2'";
    $checkQueryGrid3 = "SELECT * FROM parking_status WHERE grid='2-1'";
    $checkQueryGrid4 = "SELECT * FROM parking_status WHERE grid='2-2'";

    $checkResult1 = mysqli_query($conn, $checkQueryGrid1);
    $checkResult2 = mysqli_query($conn, $checkQueryGrid2);
    $checkResult3 = mysqli_query($conn, $checkQueryGrid3);
    $checkResult4 = mysqli_query($conn, $checkQueryGrid4);

    if($checkResult1 && $checkResult2 && $checkResult3 && $checkResult4){
      $ledStatus1 = mysqli_fetch_assoc($checkResult1);
      $ledStatus2 = mysqli_fetch_assoc($checkResult2);
      $ledStatus3 = mysqli_fetch_assoc($checkResult3);
      $ledStatus4 = mysqli_fetch_assoc($checkResult4);

      echo "   ";
      echo $ledStatus1['led_color'];
      echo " and ";
      echo $status1;
      echo "   ";
      echo $ledStatus2['led_color'];
      echo " and ";
      echo $status2;
      echo "   ";
      echo $ledStatus3['led_color'];
      echo " and ";
      echo $status3;
      echo "   ";
      echo $ledStatus4['led_color'];
      echo " and ";
      echo $status4;
      echo "   ";

      if($ledStatus1['led_color'] == $status1 && $ledStatus2['led_color'] == $status2 && $ledStatus3['led_color'] == $status3 && $ledStatus4['led_color'] == $status4) {
        echo "We don't update the same things";
      }
      else {
        for($i=0; $i<4; $i++) {

          if($parking[$i] == 1){
            $checkParking = "SELECT * FROM parking_status WHERE grid = '".$grid[$i]."'";
            $queryResult = mysqli_query($conn, $checkParking);

            if($queryResult) {
              $checkingRow = mysqli_fetch_assoc($queryResult);

              if($checkingRow['led_color'] != "yellow" || $checkingRow['led_color'] != "blue") {
                $sql = "UPDATE parking_status SET available = 'false', booked = 'false', not_paid = 'true', paid = 'false', led_color = 'red' WHERE grid = '".$grid[$i]."'";
              }
              else {
                $sql = "none"
              }
            }

          }
          if ($parking[$i] == 0) {

            $checkParking = "SELECT * FROM parking_status WHERE grid = '".$grid[$i]."'";
            $queryResult = mysqli_query($conn, $checkParking);

            if($queryResult) {
              $checkingRow = mysqli_fetch_assoc($queryResult);

              if($checkingRow['led_color'] != "yellow") {
                $sql = "UPDATE parking_status SET available = 'true', booked = 'false', not_paid = 'false', paid = 'false', led_color = 'green' WHERE grid = '".$grid[$i]."'";
              }
              else {
                $sql = "none"
              }
            }
          }

          if(mysqli_query($conn, $sql)) {
            echo "Updated";
          }
          else {
            echo "Something went wrong brah...";
          }
        }
      }
    }
  }
  else {
    echo "Nothing here but us chickens...";
  }
?>
