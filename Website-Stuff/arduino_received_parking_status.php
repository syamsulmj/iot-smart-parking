<?php
  require_once('dbConnect.php');
  $count = 0;

  $sql = "SELECT * FROM parking_status";

  $result = mysqli_query($conn, $sql);

  if(mysqli_num_rows($result) > 0) {

    while ($row = mysqli_fetch_assoc($result)) {
      if($row['led_color'] == "green") {
        $grid[$count] = 0;
      }
      elseif ($row['led_color'] == "red") {
        $grid[$count] = 1;
      }
      elseif ($row['led_color'] == "yellow") {
        $grid[$count] = 2;
      }
      elseif ($row['led_color'] == "blue") {
        $grid[$count] = 3;
      }
      $count++;
    }

    echo "".$grid[0].",".$grid[1].",".$grid[2].",".$grid[3]."";
  }
  else {
    echo "Nothing...";
  }

?>
