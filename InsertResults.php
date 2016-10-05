<?php
$con=mysqli_connect("mysql4.000webhost.com","a7713750_vasavi","geetha@123","a7713750_SahiApp");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 
$ChildId = $_GET['ChildId'];
$Date = $_GET['Date'];
$Score_new = $_GET['Score'];

$result1 = mysqli_query($con,"SELECT * FROM DiagnosisResults WHERE ChildId ='$ChildId' and Date = '$Date' and Score != '0'");

if($result1->num_rows > 0)
{
     $row = mysqli_fetch_assoc($result1);  
     if($row['Score'] < $Score_new)
     {
         $result2 = mysqli_query($con,"UPDATE DiagnosisResults SET Score=$Score_new WHERE ChildId='$ChildId' and Date='$Date' and Score != '0'"); 
         if($result2 > 0){
             echo '{"query_result":"UPDATED"}';
         }       
     }
}
else
{
    $result = mysqli_query($con,"INSERT INTO DiagnosisResults (ChildId, Date, Score) VALUES ('$ChildId', '$Date', '$Score_new')");
    if($result == true) {
        echo '{"query_result":"SUCCESS"}';
    }
    else{
        echo '{"query_result":"FAILURE"}';
    }
}
 
mysqli_close($con);
?>		