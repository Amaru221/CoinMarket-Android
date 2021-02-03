<?php
	$token = $_REQUEST['token'];
	require_once 'ConexionCoin.php';
	$conexion = new ConexionEjemplo();

	$datos = $conexion->obtenerFavoritos($token);
	echo json_encode($datos);
?>