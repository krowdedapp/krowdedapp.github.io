<?php

$link = mysqli_connect('localhost', 'roland', 'mariaAntonia'); 

if (!$link) { 
  $output = 'Unable to connect to the database server.'; 
  include 'output.html.php'; 
  exit(); 
} 

if (!mysqli_set_charset($link, 'utf8')) { 
  $output = 'Unable to set database connection encoding.'; 
  include 'output.html.php'; 
  exit(); 
} 

if (!mysqli_select_db($link, 'fightnight')) { 
  $output = 'Unable to locate the fighter records.'; 
  include 'output.html.php'; 
  exit(); 
} 

/* Take this out!
$output = 'Database connection established.'; 

include 'output.html.php'; 
*/

	/* Preparation */
//	$stmt = $link->prepare("SELECT age,year,house,blood,dob,boggart,patronus FROM quills WHERE first_name = ? AND last_name=?");
    $stmt = $link->prepare("SELECT fighter.name,class,title,series.name FROM righter LEFT JOIN work ON appears_in=work.id LEFT JOIN series ON part_of=series.id;");

    /* Binding */
//    $stmt->bind_param("ss",$first_name,$last_name);

    /* Execution */
    $stmt->execute();
    $stmt->store_result();
    $stmt->bind_result($character,$class,$debuted_in,$series);
	$stmt->fetch();

	$num_rows = $stmt->num_rows;	
?>

