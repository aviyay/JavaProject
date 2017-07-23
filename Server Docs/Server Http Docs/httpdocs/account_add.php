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
$username = $_REQUEST["username"];
$password =$_REQUEST["password"];
$sql = "INSERT INTO `bnet_data`.`account_table` (`username`,
`password`) VALUES (\"$username\",\"$password\")";
if ($conn->query($sql) === TRUE) {
 echo "Success";
} else {
 echo "Error: " . $sql . "<br>" . $conn->error;
}
$conn->close();
?>