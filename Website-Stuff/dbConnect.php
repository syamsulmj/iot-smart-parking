<?php
  define('HOST', 'localhost'); //Your host name
  define('USER', 'username'); // Your Database Username
  define('PASS', 'password'); // Your Database Password
  define('DB', 'database_name'); // Your Database name


  $conn = mysqli_connect(HOST, USER, PASS, DB) or die('Unable to connect');
  
  // If you intended to use PDO method here's the example
  // try {
  //     $conn = new PDO("mysql:host='HOST';dbname=DB", USER, PASS);
  //     // set the PDO error mode to exception
  //     $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
  //     echo "Connected successfully";
  //     }
  // catch(PDOException $e)
  //     {
  //     echo "Connection failed: " . $e->getMessage();
  //     }

 ?>
