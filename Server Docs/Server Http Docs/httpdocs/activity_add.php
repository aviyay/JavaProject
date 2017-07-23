<?php
header('Content-Type: text/html; charset=utf-8');
$servername = "localhost";
$username = "shy";
$password = "123456";
$dbname = "bnet_data";
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
mysql_query("SET NAMES 'utf8'", $conn);
// Check connection
if ($conn->connect_error) {
 die("Connection failed: " . $conn->connect_error);
}
$description = $_REQUEST["description"];
$country =$_REQUEST["country"];
$price = $_REQUEST["price"];
$start = $_REQUEST["start"];
$end= $_REQUEST["end"];
$type = $_REQUEST["type"];
$business_id = $_REQUEST["business_id"];
$sql = "INSERT INTO `bnet_data`.`activity_table` (`_id`,
`description`,`country`, `price`, `start`, `end`,`type`,`business_id`,`changed`) VALUES (NULL,
\"$description\",\"$country\", \"$price\", \"$start\", \"$end\", \"$type\", \"$business_id\",1)";
if ($conn->query($sql) === TRUE) {
 $last_id = $conn->insert_id;
        echo $last_id;
} else {
 echo "Error: " . $sql . "<br>" . $conn->error;
}
$conn->close();
?>