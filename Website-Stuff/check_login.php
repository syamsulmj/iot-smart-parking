<?php
  require_once('dbConnect.php');

  $response = array();

  if($_POST['username'] != "" && $_POST['password'] != ""){
    $username = $_POST['username'];
    $password = $_POST['password'];

    $sql = "SELECT * FROM users WHERE username = '".$username."' AND password = '".$password."'";

    if(mysqli_num_rows(mysqli_query($conn, $sql)) > 0){
      $row = mysqli_fetch_assoc(mysqli_query($conn, $sql));

      $response["check"] = "201";
      $response["name"] = $row['name'];
      $response["email"] = $row['email'];
      $response["username"] = $row['username'];
      $response["password"] = $row['password'];

      echo json_encode($response);
    }
    else {
      $response["check"] = "400";
      echo json_encode($response);
    }
  }
  else {
    echo "Please enter both username and password.";
  }
?>
