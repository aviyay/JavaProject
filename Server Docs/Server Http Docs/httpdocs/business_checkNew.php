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
$sql = "SELECT * FROM `business_table` WHERE changed=1";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
 echo "true";
}
else {
 echo "false";
}
$conn->close();
?>