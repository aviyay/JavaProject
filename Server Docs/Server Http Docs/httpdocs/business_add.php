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
$name = $_REQUEST["name"];
$phone = $_REQUEST["phone"];
$email = $_REQUEST["email"];
$link= $_REQUEST["link"];
$country = $_REQUEST["country"];
$city = $_REQUEST["city"];
$street = $_REQUEST["street"];
$sql = "INSERT INTO `bnet_data`.`business_table` (`_id`,
`name`, `phone`, `email`, `link`,`country`,`city`,`street`,`changed`) VALUES (NULL,
\"$name\", \"$phone\", \"$email\", \"$link\", \"$country\", \"$city\", \"$street\",1)";
if ($conn->query($sql) === TRUE) {
 $last_id = $conn->insert_id;
        echo $last_id;
} else {
 echo "Error: " . $sql . "<br>" . $conn->error;
}
$conn->close();
?>