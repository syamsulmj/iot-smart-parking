<?php
  require_once("dbConnect.php");
  date_default_timezone_set("Asia/Kuala_Lumpur");

  $response = array();

  if($_POST['change'] != "" && $_POST['email'] != "" && $_POST['grid'] != "" && $_POST['bookStatus'] != "" && $_POST['hoursParking'] != "") {
    $change = $_POST['change'];
    $grid = $_POST['grid'];
    $bookStatus = $_POST['bookStatus'];
    $hoursParking = $_POST['hoursParking'];
    $email = $_POST['email'];

    $sql = "SELECT * FROM parking_status WHERE grid = '".$grid."'";
    $result = mysqli_query($conn, $sql);

    if($result) {
      $row = mysqli_fetch_assoc($result);

      if($bookStatus == "booking") {
        $date_end_booking = date("M d, Y H:i:s", strtotime("+10 minutes"));
        $updateBookedTimeStatus = "UPDATE parking_time SET time_status = 'booked', date_end = '".$date_end_booking."' WHERE grid = '".$grid."' ";
        $changeLedColor = "UPDATE parking_status SET available = 'false', booked = 'true', not_paid = 'false',
                           paid = 'false', booked_for = '".$email."', led_color = '".$change."'
                           WHERE grid = '".$grid."' ";

        if(mysqli_query($conn, $updateBookedTimeStatus) && mysqli_query($conn, $changeLedColor)) {
          $response["response"] = "success";
          $response["message"] = "You have successfully booked for this parking for 10 minutes!";
          echo json_encode($response);
        }
        else {
          $response["response"] = "failed";
          $response["message"] = "6Something went wrong. Please try again.";
          echo json_encode($response);
        }

      }

      elseif ($bookStatus == "paying") {
        $date_end_parking = date("M d, Y H:i:s", strtotime("+".$hoursParking." hours"));
        $updatePayingTimeStatus = "UPDATE parking_time SET time_status = 'parked', hours_parking = ".$hoursParking.", date_end = '".$date_end_parking."' WHERE grid = '".$grid."'";
        $changeLedColor = "UPDATE parking_status SET available = 'false', booked = 'false', not_paid = 'false',
                           paid = 'true', booked_for = '', led_color = '".$change."'
                           WHERE grid = '".$grid."'";

        $totalCost = $hoursParking * 0.40;
        $getBalance = "SELECT * FROM account WHERE email='".$email."' ";
        $balanceResult = mysqli_query($conn, $getBalance);

        if($balanceResult) {
          $balanceResultRow = mysqli_fetch_assoc($balanceResult);

          $availableBalance = $balanceResultRow['balance'];
          $finalBalance = $availableBalance - $totalCost;

          if($finalBalance > 0) {
            $updateNewBalance = "UPDATE account SET balance = ".$finalBalance." WHERE email='".$email."' ";


          }
          else {
            $response["response"] = "failed";
            $response["message"] = "Not enough balance! Please topup first!";
            echo json_encode($response);
          }


        }
        else {
          $response["response"] = "failed";
          $response["message"] = "7Something went wrong. Please try again.";
          echo json_encode($response);
        }

        if(mysqli_query($conn, $updatePayingTimeStatus) && mysqli_query($conn, $changeLedColor)) {
          $response["response"] = "success";
          $response["message"] = "You have successfully payed for this parking!";
          echo json_encode($response);
        }
        else {
          $response["response"] = "failed";
          $response["message"] = "5Something went wrong. Please try again.";
          echo json_encode($response);
        }
      }

      elseif ($bookStatus == "paying_booking") {
        $date_end_parking = date("M d, Y H:i:s", strtotime("+".$hoursParking." hours"));
        $changeLedColor = "UPDATE parking_status SET available = 'false', booked = 'false', not_paid = 'false',
                           paid = 'true', booked_for = '', led_color = '".$change."'
                           WHERE grid = '".$grid."'";
        $updatePayingTimeStatus = "UPDATE parking_time SET time_status = 'parked', hours_parking = ".$hoursParking.", date_end = '".$date_end_parking."' WHERE grid = '".$grid."'";

        $checkingBooking = "SELECT * FROM parking_status WHERE booked_for='".$email."' ";

        if(mysqli_num_rows(mysqli_query($conn, $checkingBooking)) > 0) {
          // Calculation
          $totalCost = $hoursParking * 0.40;
          $getBalance = "SELECT * FROM account WHERE email='".$email."' ";
          $balanceResult = mysqli_query($conn, $getBalance);

          if($balanceResult) {
            $balanceResultRow = mysqli_fetch_assoc($balanceResult);

            $availableBalance = $balanceResultRow['balance'];
            $finalBalance = $availableBalance - $totalCost;

            if($finalBalance > 0) {
              $updateNewBalance = "UPDATE account SET balance = ".$finalBalance." WHERE email='".$email."' ";

              if(mysqli_query($conn, $changeLedColor) && mysqli_query($conn, $updatePayingTimeStatus) && mysqli_query($conn, $updateNewBalance)) {
                $response["response"] = "success";
                $response["message"] = "You have successfully payed for this parking!";
                echo json_encode($response);
              }
              else {
                $response["response"] = "failed";
                $response["message"] = "2Something went wrong. Please try again.";
                echo json_encode($response);
              }
            }
            else {
              $response["response"] = "failed";
              $response["message"] = "Not enough balance! Please topup first!";
              echo json_encode($response);
            }
          }
          else {
            $response["response"] = "failed";
            $response["message"] = "7Something went wrong. Please try again.";
            echo json_encode($response);
          }
          // End of calculation
        }
        else {
          $response["response"] = "failed";
          $response["message"] = "This parking was not booked for you!";
          echo json_encode($response);
        } // else for checking booked for whom?
      }

      else {
        $response["response"] = "failed";
        $response["message"] = "3Something went wrong. Please try again.";
        echo json_encode($response);
      }

    }

    else {
      $response["response"] = "failed";
      $response["message"] = "4Something went wrong. Please try again.";
      echo json_encode($response);
    }
  }
  elseif($_POST['change'] == "blue" && $_POST['grid'] != "" && $_POST['email'] != "" && $_POST['bookStatus'] != "" && $_POST['hoursParking'] == "") {

    $response["response"] = "failed";
    $response["message"] = "Please fill in the hours you wanna park first!";
    echo json_encode($response);

  }
  else {
    $response["response"] = "failed";
    $response["message"] = "1Something went wrong. Please try again.";
    echo json_encode($response);
  }

?>
