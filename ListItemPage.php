<?php
 
$con=mysqli_connect("mysql4.000webhost.com","a7713750_vasavi","geetha@123","a7713750_SahiApp");
 
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 
$ChildId = $_GET['ChildId'];
 
$result = mysqli_query($con,"SELECT * FROM DiagnosisResults WHERE ChildId = '$ChildId'");
$rows = array();
if ($result->num_rows > 0) 
{
    while($row = $result->fetch_assoc()) 
    {
		$rows[] = $row;
    }
    print json_encode($rows);
} 
else 
{
    echo '{"query_result":"FAILURE"}';
}
 
mysqli_close($con);
 
?>