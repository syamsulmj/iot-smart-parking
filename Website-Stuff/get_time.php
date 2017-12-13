<?php
  require_once('dbConnect.php');

  if($_GET['grid'] != "") {
    $grid = $_GET['grid'];

    $parking_table = "SELECT * FROM parking_time WHERE grid = '".$grid."' ";

    $result = mysqli_query($conn, $parking_table);

    if($result) {
      $row = mysqli_fetch_assoc($result);

      if($row['time_status'] == "booked" || $row['time_status'] == "parked") {
        echo $row['date_end'];
      }

      elseif ($row['time_status'] == "expired") {
        echo "N/A";
      }
    }


  }
  else {
    echo "none";
  }
?>
