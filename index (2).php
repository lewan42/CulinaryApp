<?php

require_once 'db_config.php';

include('simplehtmldom_1_5/simple_html_dom.php');


$link = mysqli_connect($host, $user, $password, $database)
or die("Ошибка " . mysqli_error($link));

// try {
//     $link = mysqli_connect($host, $user, $password, $database);
   
// } catch (mysqli_sql_exception $e) {
//     print "Error!: " . $e->getMessage() . "<br/>";
//     die();
// }

$host = 'localhost'; // адрес сервера 
$db = 'id7219699_appculinary'; // имя базы данных
$user = 'id7219699_qwerty'; // имя пользователя
$pass = 'qwerty777'; // пароль
    
    $charset = 'utf8';

    $dsn = "mysql:host=$host;dbname=$db;charset=$charset";
    $opt = [
        PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
        PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
        PDO::ATTR_EMULATE_PREPARES   => false,
    ];
    //$pdo = new PDO($dsn, $user, $pass, $opt);
    
    try {
    $pdo = new PDO($dsn, $user, $pass, $opt);
    $res['status2'] = "Connection success";
} catch (PDOException $e) {
    $res['status2'] = "Connection fail";
    die('Подключение не удалось: ' . $e->getMessage());
}



if ($_REQUEST['proc'] == "logIn") {

     $sql = "SELECT EXISTS (SELECT * FROM clients WHERE login = :login and password = :password)";
     $stmt = $pdo->prepare($sql);

    $stmt->bindValue(':login',  $_REQUEST['login']);
    $stmt->bindValue(':password',  $_REQUEST['password']);
    $stmt->execute();

    if ($stmt->fetchColumn()){
        $res['status'] = "yes";
    } else $res['status'] = "Неверный логин или пароль!";
    
    $pdo = null;
    $dsn = null;

    
} else if ($_REQUEST['proc'] == "createAccount") {

      try{
    
    $sql = "INSERT INTO `clients` (`login`,`password`,`email`) VALUES (?, ?, ?)";
     $stmt = $pdo->prepare($sql);

   // $stmt->bindValue(':login',  $_REQUEST['newLogin']);
   /// $stmt->bindValue(':password',  $_REQUEST['newPassword']);
   // $stmt->bindValue(':email',  $_REQUEST['newEmail']);
    
    $stmt->execute([$_REQUEST['newLogin'], $_REQUEST['newPassword'], $_REQUEST['newEmail']]);

    
  
        //$stmt->execute();
        $res['status'] = "yes";
    }
    catch(PDOException $e)
    {
        $res['status'] = "no";
    }
    
    $pdo = null;
    $dsn = null;


} else if ($_REQUEST['proc'] == "selection") {

    $products = $_REQUEST['sql'];
    $productsAll = $_REQUEST['sqlAll'];
    $countParam = $_REQUEST['countParam'];

    //echo $countParam."<br>";
    
    $productsAll = str_replace("P", "%", $productsAll);
    
    $products = str_replace("P", "%", $products);
    
    //echo $products."<br>";

    if ($countParam > 0) {
	    
        $param = "";
        
        $ar1 = [];
        $ar2 = [];

        for ($i = 0; $i < $countParam; $i++) {
            
            $_REQUEST['param' . $i] = str_replace("P", "%", $_REQUEST['param' . $i]);
            
            //echo $_REQUEST['param' . $i]."</br>";
            
            if($i == 0)
            {
                 $param = "SELECT DISTINCT table2.id from table2 WHERE " . $_REQUEST['param' . $i];
                 
                 $result1 = mysqli_query($link, $param) or die("Ошибка " . mysqli_error($link));
                 
                 while ($resId = mysqli_fetch_array($result1)) {
                    $ar1[] = $resId['id'];
                 }
                 //echo print_r($ar1)."<br>";
            }
            else
            {
                $param = "SELECT DISTINCT table2.id from table2 WHERE " . $_REQUEST['param' . $i];
                $result1 = mysqli_query($link, $param) or die("Ошибка " . mysqli_error($link));
                 
                 $ar2 = [];
                while ($resId = mysqli_fetch_array($result1)) {
                    $ar2[] = $resId['id'];
                }
                
                $ar1 = array_intersect($ar1, $ar2);
                
                //echo print_r($ar1)."<br>";
            }
            
            
            
            // if ($countParam - 1 != $i)
            //     $param .= "SELECT DISTINCT table2.id from table2 WHERE " . $_REQUEST['param' . $i] . " in ";
            // else $param .= "SELECT DISTINCT table2.id from table2 WHERE " . $_REQUEST['param' . $i];
        }
        
        //echo print_r($ar1)."<br>";

        // $param .= " GROUP by table2.id";
        
        // echo $param."<br>";

        $productsAll = str_replace("+", " ", $productsAll);
        $products = str_replace("+", " ", $products);
        
        $sqlAll = "SELECT DISTINCT table2.id from table2 WHERE table2.id NOT IN (SELECT table2.id from table2 WHERE table2.name NOT IN (" . $productsAll . "))";
        
        $sql = "SELECT DISTINCT table2.id from table2 WHERE table2.id NOT IN (SELECT table2.id from table2 WHERE table2.name NOT IN (" . $products . "))";

        //$param .= $sql;

        //$result1 = mysqli_query($link, $param) or die("Ошибка " . mysqli_error($link));
        $result2 = mysqli_query($link, $sqlAll) or die("Ошибка " . mysqli_error($link));
        $result3 = mysqli_query($link, $sql) or die("Ошибка " . mysqli_error($link));

        $arr1 = $ar1;
        $arr2 = [];
        $arr3 = [];
        $arrAll = [];


        // while ($resId = mysqli_fetch_array($result1)) {
        //     $arr1[] = $resId['id'];
        // }
        
        while ($resId = mysqli_fetch_array($result2)) {
            $arr2[] = $resId['id'];
            $arrAll[] = $resId['id'];
        }
        
        while ($resId = mysqli_fetch_array($result3)) {
            $arr3[] = $resId['id'];
        }


        $intersect = array_intersect($arr1, $arr2); //пересечение ограничений и всех продуктов
        
        $intersect = array_merge($intersect,$arr3); //объединение верхнего и продуктов без ограничений

        $clearZapros = array_diff($arr2, $intersect); // продукты с недостающим количеством



        $plusProdZapros = "SELECT id,name from table2 WHERE name NOT IN (" . $products . ") GROUP BY id HAVING COUNT(id) <= 1";
        
        $resultPlusProd = mysqli_query($link, $plusProdZapros) or die("Ошибка " . mysqli_error($link));


        $res = [];
        $valueArr = [];
        $valueArrName = [];
        
        while ($val = mysqli_fetch_array($resultPlusProd)) {
            $valueArr[] = $val['id'];
            $valueArrName[] = $val['name'];
        }
    
        $valueArr = array_diff($valueArr, $clearZapros);
        
         $num = 0;
        
        foreach ($valueArr as &$value){   
            $query = "SELECT * from table1 where table1.id=" . $value;
            $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));

            if ($result) {
                $row = mysqli_fetch_row($result); // количество полученных строк

                $tmp1['id'] = $row[0];
                $tmp2['name'] = $row[1];
                $tmp3['serv'] = $row[2];
                $tmp4['time'] = $row[3];
                $tmp4['descr'] = $row[4];
                $tmp5['img'] = $row[5];
                $tmp6['flag'] = "Не хватает продукта";
                $num = $num+1;

                $tmp = [$tmp1['id'], $tmp2['name'], $tmp3['serv'], $tmp4['time'], $tmp4['descr'], $tmp5['img'], $tmp6['flag']];

                $res[] = $tmp;
            }
        }
        
        foreach ($clearZapros as &$value) {
            $query = "SELECT * from table1 where table1.id=" . $value;
            $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));

            if ($result) {
                $row = mysqli_fetch_row($result); // количество полученных строк

                $tmp1['id'] = $row[0];
                $tmp2['name'] = $row[1];
                $tmp3['serv'] = $row[2];
                $tmp4['time'] = $row[3];
                $tmp4['descr'] = $row[4];
                $tmp5['img'] = $row[5];
                $tmp6['flag'] = "Не достает количества у продукта";

                $tmp = [$tmp1['id'], $tmp2['name'], $tmp3['serv'], $tmp4['time'], $tmp4['descr'], $tmp5['img'], $tmp6['flag']];

                $res[] = $tmp;
            }
        }
        
        foreach ($intersect as &$value) {
            $query = "SELECT * from table1 where table1.id=" . $value;
            $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));

            if ($result) {
                $row = mysqli_fetch_row($result); // количество полученных строк

                $tmp1['id'] = $row[0];
                $tmp2['name'] = $row[1];
                $tmp3['serv'] = $row[2];
                $tmp4['time'] = $row[3];
                $tmp4['descr'] = $row[4];
                $tmp5['img'] = $row[5];
                $tmp6['flag'] = "";

                $tmp = [$tmp1['id'], $tmp2['name'], $tmp3['serv'], $tmp4['time'], $tmp4['descr'], $tmp5['img'], $tmp6['flag']];

                $res[] = $tmp;
            }
        }
        
        


    } else {
        $products = str_replace("+", " ", $products);
        $param = "SELECT DISTINCT table2.id from table2 WHERE table2.id NOT IN (SELECT table2.id from table2 WHERE table2.name NOT IN (" . $products . "))";
        
        //echo $param."<br>";

        $query = $param;

        $resultAll = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));
        $count = 3;
        
        
        //ы$clearZapros = array_diff($arr2, $intersect); // продукты с недостающим количеством


        $res = [];
        while ($resId = mysqli_fetch_array($resultAll)) {

            $query = "SELECT * from table1 where table1.id=" . $resId['id'];
            $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));

            if ($result) {
                $row = mysqli_fetch_row($result); // количество полученных строк

                $tmp1['id'] = $row[0];
                $tmp2['name'] = $row[1];
                $tmp3['serv'] = $row[2];
                $tmp4['time'] = $row[3];
                $tmp4['descr'] = $row[4];
                $tmp5['img'] = $row[5];
                $tmp6['flag'] = "";

                $tmp = [$tmp1['id'], $tmp2['name'], $tmp3['serv'], $tmp4['time'], $tmp4['descr'], $tmp5['img'], $tmp6['flag']];

                $res[] = $tmp;
            }

        }
        
        $valueArr = [];
        $valueArrName = [];
        
        $plusProdZapros = "SELECT id, name from table2 WHERE name NOT IN (" . $products . ") GROUP BY id HAVING COUNT(id) <= 1";
        $resultPlusProd = mysqli_query($link, $plusProdZapros) or die("Ошибка " . mysqli_error($link));
        
        while ($val = mysqli_fetch_array($resultPlusProd)) {
            $valueArr[] = $val['id'];
            $valueArrName[] = $val['name'];
        }
        
        $num = 0;
        
        foreach ($valueArr as &$value){   
            $query = "SELECT * from table1 where table1.id=" . $value;
            $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));

            if ($result) {
                $row = mysqli_fetch_row($result); // количество полученных строк

                $tmp1['id'] = $row[0];
                $tmp2['name'] = $row[1];
                $tmp3['serv'] = $row[2];
                $tmp4['time'] = $row[3];
                $tmp4['descr'] = $row[4];
                $tmp5['img'] = $row[5];
                $tmp6['flag'] = $valueArrName[$num];
                $num = $num+1;

                $tmp = [$tmp1['id'], $tmp2['name'], $tmp3['serv'], $tmp4['time'], $tmp4['descr'], $tmp5['img'], $tmp6['flag']];

                $res[] = $tmp;
            }
        }

    }


} else if ($_REQUEST['proc'] == "added") {

    $query = "SELECT name, type FROM table2 GROUP BY name";
    //SELECT name,type FROM table2 GROUP BY name

    $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));
    // $count = 0;
    $res = [];
    while ($result2 = mysqli_fetch_array($result)) {
        // $srt = $count."q";

        $tmp1['name'] = $result2[0];
        $tmp2['type'] = $result2[1];

        $tmp = [$tmp1['name'], $tmp2['type']];

        $res[] = $tmp;

        //$res[$srt] = $result2['name'];
        //if($count == 15) break;
        // $count++;
    }

    //$res["size"] = $count;

}else if ($_REQUEST['proc'] == "idRecipe") {

    $query = "SELECT * from table2 where id=".$_REQUEST['id'];
    //SELECT name,type FROM table2 GROUP BY name

    $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));
    // $count = 0;
    $res = [];
    
    
    while ($result2 = mysqli_fetch_array($result)) {

        // $srt = $count."q";
        
        //echo $result2[0]." ".$result2[1]." ".$result2[2]." ".$result2[3]."<br>";

        $tmp1['name'] = $result2[1];
        $tmp1['count'] = $result2[2];
        $tmp2['type'] = $result2[3];

        $tmp = [$tmp1['name'],$tmp1['count'], $tmp2['type']];

        $res[] = $tmp;
    }

}

else if ($_REQUEST['proc'] == "sales") {

    $query = "SELECT * from sales where caterogy='".$_REQUEST['nameProduct']."'";
    $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));
    $res = [];
    while ($result2 = mysqli_fetch_array($result)) {

        $tmp1['name'] = $result2[1];
        $tmp1['caterogy'] = $result2[2];
        $tmp1['shop'] = $result2[3];
        $tmp1['price'] = $result2[4];
        $tmp = [$tmp1['name'],$tmp1['caterogy'], $tmp1['shop'],$tmp1['price']];
        $res[] = $tmp;
    }

}

else if ($_REQUEST['proc'] == "userRecipe") {
	
    $query = "SELECT * from table1 where id in ".$_REQUEST['ids'];
    $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));
    $res = [];
    
    while ($row = mysqli_fetch_array($result)) {

        $tmp1['id'] = $row[0];
        $tmp1['name'] = $row[1];
        $tmp1['serv'] = $row[2];
        $tmp1['time'] = $row[3];
        $tmp1['descr'] = $row[4];
        $tmp1['img'] = $row[5];

        $tmp = [$tmp1['id'], $tmp1['name'], $tmp1['serv'], $tmp1['time'], $tmp1['descr'], $tmp1['img']];

        $res[] = $tmp;
    }

}

else if ($_REQUEST['proc'] == "addRecipe") {
    
    $query = "SELECT COUNT(*) FROM table1";
    $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));
    $row = mysqli_fetch_row($result);
    
    //echo $row[0]."<br>";
    
    $id = $row[0]+55;
    
    //echo $id."<br>";
	
    $query = "INSERT INTO `table1` (`id`, `name`, `time`, `servings`, `description`, `image`) VALUES ($id, '".$_REQUEST['nameRecipe']."', '5', '30', '".$_REQUEST['decrRecipe']."', 'rr450')";
    
    //echo $query."<br>"; 
    $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));
    //$row = mysqli_fetch_row($result);
    
    if ($result) {
        $res['statusRecipeAdded'] = "yes";
        $res['id'] = $id;
        
    } else $res['statusRecipeAdded'] = "error added";
    
    //$query = "INSERT INTO `table2` (`id`, `name`, `count`, `type`) VALUES ('1', 'n1', 'c1', 't1'), ('1', 'n2', 'c2', 't2')";
    $query ="INSERT INTO `table2` (`id`, `name`, `count`, `type`) VALUES ";
    
    for($i = 0; $i < $_REQUEST['countP']; $i++)
    {
        $str = "('$id', '".$_REQUEST['nameIngr'.$i]."', '".$_REQUEST['countIngr'.$i]."', '".$_REQUEST['typeIngr'.$i]."'),";
        $query .= $str;
    }
    
   $query = mb_substr($query, 0, -1);
    
    $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));
    
    if ($result) {
        $res['statusProductsAdded'] = "yes";
        $res['id'] = $id;
        
    } else $res['statusProductsAdded'] = "error added";
}


/*
 * else if($_REQUEST['proc'] == "parse") {


    $query="SELECT DISTINCT table2.name FROM table2";
    //SELECT name,type FROM table2 GROUP BY name

    $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));
    $count = 0;
    while ($result2 = mysqli_fetch_array($result)) {

        $srt = $count."q";
        $res[$srt] = $result2['name'];
        //if($count == 15) break;
        $count++;
    }

    $res["size"] = $count;



}


// setcookie("_ga","GA1.2.1412451640.1541493703");
// setcookie("_gid","GA1.2.24325606.1541493703");
// //setcookie("_git",".edadeal.ru","Tue, 30 Oct 2018 19:10:17 GMT", ,"false","Unset");
// setcookie("_ym_uid","1541493703961634153");
// setcookie("_ym_d","1541493703");
// setcookie("_ym_visorc_34675050","w");
// setcookie("_ym_isad","2");

// setcookie("_ym_uid","1541493703961634153");
// setcookie("_ym_visorc_34675050","w");

//require_once 'phpQuery2/phpQuery/phpQuery.php';

require_once 'db_config.php';

include('simplehtmldom_1_5/simple_html_dom.php');
// $html = new simple_html_dom();

// $html = file_get_html('https://edadeal.ru/', false, null, 0);
//  foreach($html->find('div') as $element)
// echo $element . '<br>';
// $html = file_get_contents('https://www.google.com/');
// $b = str_get_html($object);
// +++++
//   $html = file_get_html( "https://edadeal.ru/perm/offers", false, null, 0 );
// echo $html;
// $html = file_get_html('https://www.edadeal.ru/', false, null, 0);
// $dom = str_get_html(file_get_contents('https://www.edadeal.ru/'));
// echo $dom;
// $html = file_get_html('https://edadeal.ru/', false, null, 0);
//  foreach($html->find('div') as $element)
// echo $element . '<br>';
// $url = "https://edadeal.ru/";
// $curl = curl_init($url);
// curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
// $page = curl_exec($curl);
// $html = file_get_html('https://kedem.ru/recipe/salads/', false, null, 0);
// $site = 'https://kedem.ru/recipe/salads/';
// $page = 1;
//   $data = file_get_html('https://kedem.ru/recipe/salads/'.$page.'/', false, null, 0);


if ($_REQUEST['proc'] == "logIn") {
    $login = $_REQUEST['login'];
    $password = $_REQUEST['password'];
    $query = "SELECT EXISTS (SELECT * FROM clients WHERE login = '" . $login . "' and password = '" . $password . "')";
    $result = mysqli_query($link, $query) or die("Ошибка " . mysqli_error($link));

    $row = mysqli_fetch_row($result);
    if ($row[0] > 0) {
        $res['status'] = "yes";
    } else $res['status'] = "Неверный логин или пароль!";


 */

echo json_encode($res, JSON_UNESCAPED_UNICODE);
?>


