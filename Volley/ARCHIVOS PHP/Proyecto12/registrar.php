<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
$username = $_POST['username'];
$password = $_POST['password'];
$email = $_POST['email'];
require_once('conexion.php');
$sql = "INSERT INTO usuario (username,password,email) VALUES ('$username','$password','$email')";
if(mysqli_query($con,$sql)){
echo "Se registró correctamente";
}else{
echo "No se pudó registrar";
}
}else{
echo 'error';
}

?>