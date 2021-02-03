<?php
	require_once 'ConexionCoin.php';
	$conexion = new ConexionEjemplo();

	$datos = $conexion->fechaUnix();
	echo json_encode($datos);
?>

