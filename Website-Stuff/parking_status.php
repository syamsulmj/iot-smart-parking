<?php
  require_once('dbConnect.php');

  $grid11 = $_POST['grid11'];
  $grid12 = $_POST['grid12'];
  $grid21 = $_POST['grid21'];
  $grid22 = $_POST['grid22'];
  $response = array();

  if($grid11 != "" && $grid12 != "" && $grid21 != "" && $grid22 != ""){
    $sql1 = "SELECT * FROM parking_status WHERE grid = '".$grid11."' ";
    $sql2 = "SELECT * FROM parking_status WHERE grid = '".$grid12."' ";
    $sql3 = "SELECT * FROM parking_status WHERE grid = '".$grid21."' ";
    $sql4 = "SELECT * FROM parking_status WHERE grid = '".$grid22."' ";

    $result1 = mysqli_query($conn, $sql1);
    $result2 = mysqli_query($conn, $sql2);
    $result3 = mysqli_query($conn, $sql3);
    $result4 = mysqli_query($conn, $sql4);

    if($result1 && $result2 && $result3 && $result4){
      $row1 = mysqli_fetch_assoc($result1);
      $row2 = mysqli_fetch_assoc($result2);
      $row3 = mysqli_fetch_assoc($result3);
      $row4 = mysqli_fetch_assoc($result4);

      $response["grid11"] = $row1['led_color'];
      $response["grid12"] = $row2['led_color'];
      $response["grid21"] = $row3['led_color'];
      $response["grid22"] = $row4['led_color'];

      echo json_encode($response);
    }
  }
  else {
    echo "Nothing's here but us chickens...";
  }

?>
