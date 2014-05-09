<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
</head>
<?php

	mysql_connect("localhost","root","1234");
	mysql_select_db("testJson");
	$result=mysql_query("select * from foods");
	while($row=mysql_fetch_array($result,MYSQL_ASSOC)) 
	$output[]=$row;

	print "{\"foods\": ";
	print(json_encode($output));
	print "}";
	
	mysql_free_result($result);
	mysql_close();

?>
<body>
</body>
</html>
