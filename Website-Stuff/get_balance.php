<?php
  require_once('dbConnect.php');

  $response = array();

  if($_POST['email'] != "") {
    $email = $_POST['email'];

    $sql = "SELECT * FROM account WHERE email = '".$email."' ";

    $result = mysqli_query($conn, $sql);

    if($result) {
      $row = mysqli_fetch_assoc($result);

      $response["response"] = "success";
      $response["balance"] = $row['balance'];

      echo json_encode($response);
    }
    else {
      $response["response"] = "failed";
      echo json_encode($response);
    }
  }
  else {
    $response["response"] = "failed";
    echo json_encode($response);
  }
?>
