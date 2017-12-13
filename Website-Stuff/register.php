<?php
  require_once('dbConnect.php');

  if($_POST['name'] != "" && $_POST['email'] != "" && $_POST['username'] != "" && $_POST['password'] != ""){
    $name = $_POST['name'];
    $email = $_POST['email'];
    $username = $_POST['username'];
    $password = $_POST['password'];

    $sql = "INSERT INTO users (name, email, username, password)
            VALUES ('".$name."', '".$email."', '".$username."', '".$password."')";

    $sql1 = "INSERT INTO account (email, balance)
            VALUES ('".$email."', 0)";

    if(mysqli_query($conn, $sql) && mysqli_query($conn, $sql1) ){
      echo "Your account is successfully created";
      mysqli_close($conn);
    }
    else {
      echo "Failed to create new account. Please try again.";
      mysqli_close($conn);
    }
  }
  else {
    echo "Please refill all the forms first.";
  }
?>
