<?php
  require_once('dbConnect.php');

  $response = array();

  if($_POST['topup'] != "" && $_POST['email'] != "") {
    $topup = $_POST['topup'];
    $email = $_POST['email'];

    $sql = "SELECT * FROM account WHERE email='".$email."' ";

    $result = mysqli_query($conn, $sql);

    if($result) {
      $row = mysqli_fetch_assoc($result);

      $new_balance = $topup + $row['balance'];

      $updateNewBalance = "UPDATE account SET balance=".$new_balance." WHERE email='".$email."' ";

      if(mysqli_query($conn, $updateNewBalance)) {
        $response["response"] = "success";
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
  }
  else {
    $response["response"] = "failed";
    echo json_encode($response);
  }
?>
