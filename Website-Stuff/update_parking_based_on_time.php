<?php
  require_once('dbConnect.php');
  date_default_timezone_set("Asia/Kuala_Lumpur");

  if($_GET['grid'] != "") {
    $grid = $_GET['grid'];

    $update_parking_time = "UPDATE parking_time SET time_status = 'expired', hours_parking = 0, date_end='N/A' WHERE grid='".$grid."'";
    $update_parking_status = "UPDATE parking_status SET available = 'true', booked = 'false', not_paid='false', paid='false', booked_for='', led_color='green' WHERE grid='".$grid."' ";

    if(mysqli_query($conn, $update_parking_time) && mysqli_query($conn, $update_parking_status)) {
      echo "Updated";
    }
    else {
      echo "Something went wrong";
    }
  }
  else {
    echo "none";
  }
?>
