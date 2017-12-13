<?php
  require_once('dbConnect.php');

  $sql = "UPDATE saya SET nama = 'bad', id = 'girl' WHERE nama = 'good' ";

  if(mysqli_query($conn, $sql)){
    echo "Success";
  }
  else {
    echo "DOkleh";
  }

?>
