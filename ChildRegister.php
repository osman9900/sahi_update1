<?php
$con=mysqli_connect("mysql4.000webhost.com","a7713750_vasavi","geetha@123","a7713750_SahiApp");
if (mysqli_connect_errno($con))
{
   echo '{"query_result":"ERROR"}';
}
 
$ChildId = $_GET['ChildId'];
$ChildName = $_GET['ChildName'];
$ChildAge = $_GET['ChildAge'];
$DoctorName = $_GET['DoctorName'];
$DoctorHosp = $_GET['DoctorHosp'];
$Date = $_GET['Date'];
$Score = $_GET['Score'];
 
$result1 = mysqli_query($con,"INSERT INTO DiagnosisResults (ChildId, Date, Score) 
          VALUES ('$ChildId', '$Date', '$Score')");


$result2 = mysqli_query($con,"INSERT INTO ChildDetails (ChildId, ChildName, ChildAge, DoctorName, DoctorHosp) 
          VALUES ('$ChildId', '$ChildName', '$ChildAge', '$DoctorName', '$DoctorHosp')");
 
if($result1 == true && $result2 == true) {
    echo '{"query_result":"SUCCESS"}';
}
else{
    echo '{"query_result":"FAILURE"}';
}
mysqli_close($con);
?>