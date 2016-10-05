<?php
 
$con=mysqli_connect("mysql4.000webhost.com","a7713750_vasavi","geetha@123","a7713750_SahiApp");
 
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 
$ChildId = $_GET['ChildId'];
$Date = $_GET['Date'];
 
$result = mysqli_query($con,"SELECT * FROM DiagnosisResults WHERE ChildId = '$ChildId' and Score = '0'");

$row = mysqli_fetch_assoc($result);

if ($row['Date'] == $Date) 
{
    echo '{"query_result":"EQUAL"}';
} 
else 
{
    $result1 = mysqli_query($con,"UPDATE DiagnosisResults SET Date='$Date' WHERE ChildId = '$ChildId' and Score = '0'");
    if($result1 > 0)
    {
        echo '{"query_result":"UPDATED"}';
    }
    else
        echo '{"query_result":"FAILED"}';
}
 
mysqli_close($con);
 
?>			