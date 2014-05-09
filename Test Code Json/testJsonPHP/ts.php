<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
</head>
<?php
$con=mysqli_connect("localhost","root","1234","testJson");
// Check connection
if (mysqli_connect_errno()) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$result = mysqli_query($con,"SELECT * FROM foods");

while($row = mysqli_fetch_array($result)) {
  echo $row['food_id'] . " " . $row['food_name'];
  echo "<br>";
}

mysqli_close($con);
?>
<body>
</body>
</html>