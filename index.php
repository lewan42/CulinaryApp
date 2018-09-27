
<?php
require_once 'db_config.php';

      $link = mysqli_connect($host, $user, $password, $database) 
    or die("Ошибка " . mysqli_error($link));
    
      if($_REQUEST['proc'] == "logIn") {
        $login = $_REQUEST['login']; 
        $password = $_REQUEST['password']; 
        $query="SELECT EXISTS (SELECT * FROM clients WHERE login = '".$login."' and password = '".$password."')";
        $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link)); 
        
        $row = mysqli_fetch_row($result);
        if ($row[0] > 0)
        {
          $res[Eee] = "yes";
        }
        else $res[Eee] = "no";
    }
   
//$query ="SELECT * FROM test";
//$result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link)); 
//if($result)
//{
// $res[Aaa] = $_REQUEST['name'];

//  $res[Eee] = "secseeeee";
//}
//else echo "nnnnn!"; -->
echo (json_encode($res));
      ?>
