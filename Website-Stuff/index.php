<!DOCTYPE html>
<?php
  require_once('dbConnect.php');
  $sql = "SELECT * FROM test";
  $result = mysqli_query($conn, $sql);
  $row = mysqli_fetch_assoc($result);
  $change;
?>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>Pipi Baik</title>
  </head>
  <body>
    <div>
      <p>
        <form action="" method="POST">
          <button name="change" style="width: 50px; height: 20px;">
            <?php
              if($row['led'] == "off") {
                echo "Turn ON";
                $change = "on";
              }
              else {
                echo "Turn OFF";
                $change = "off";
              }
            ?>
          </button>
        </form>
      </p>
    </div>
  </body>
  <?php
    if(isset($_POST["change"])) {
      $update = "UPDATE test SET led = '".$change."'";
      if(mysqli_query($conn, $update)){
        mysqli_close($conn);
        echo "<script language='javascript'>";
        echo "alert('CHANGE!'); window.location='index.php';";
        echo "</script>";
      }
    }
  ?>
</html>
