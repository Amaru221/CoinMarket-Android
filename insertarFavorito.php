<?php
	$token = $_REQUEST['token'];
	$moneda = $_REQUEST['moneda'];

	require_once 'ConexionCoin.php';
	$conexion = new ConexionEjemplo();

	$datos = $conexion->insertarFavorito($token, $moneda);
	echo json_encode($datos);
?>