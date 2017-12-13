<?php
  require_once('dbConnect.php');

  $sql = "SELECT * FROM test";
  $result = mysqli_query($conn, $sql);
  $row = mysqli_fetch_assoc($result);

  echo $row['led'];


 ?>
