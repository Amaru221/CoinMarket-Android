<?php

/**
********************************************************
*  	@file ConexionCoin.php
*  	@brief Clase de ejemplo para conectar PHP con MySQL
* 	@author Antonio Garcia Delgado
* 	@version 1.0
********************************************************
*/

define("DB_HOST", 		"localhost");
define("DB_DATABASE", 	"id5356514_dbfavoritos");
define("DB_USER", 		"id5356514_admin");
define("DB_PASSWORD", 	"admin");
 
class ConexionEjemplo 
{ 
	private $db;	// Base de datos

	/**
	* Description: Constructor de la clase
	* Conecta automáticamente con la base de datos
	*/
	function __construct() 
	{
        $db = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD)
        	or die('No se pudo conectar: ' . mysql_error());
        mysql_select_db(DB_DATABASE);
	}

	/**
	* Description: Destructor de la clase
	* Cierra la conexión automáticamente con la base de datos
	*/
    function __destruct() 
    {
		mysql_close();
    }

    /**
	 * Descripción: Obtiene una persona
	 * @param DNI DNI de la persona
	 * @return Datos de la persona en concreto
	*/
	public function obtenerFavoritos($token)
	{
		$sql = sprintf("SELECT * FROM favortio WHERE token ='%s'", mysql_real_escape_string($token));
		//echo $sql;
		//echo "<br>";
		$resultado = mysql_query($sql);
		$datos = array();
		
		while ($fila = mysql_fetch_assoc($resultado)) 
		{
			$datos[] = array('token' => utf8_encode($fila['token']), 'moneda' => utf8_encode($fila['moneda']));
		}
		
		mysql_free_result($resultado);
		return $datos;
	}


	/**
	 * Descripción: Inserta una nueva moneda
	 * 
	 * @param token del usuario
	 * @param moneda favorita del usuario
	 * @param telefono Teléfono del usuario
	 * 
	 * @return Devuelve 'ok' si se ha insertado correctamente la moneda y 'no ok' en caso contrario
	*/
	public function insertarFavorito($token, $moneda)
	{	
		$sqlinsert = sprintf("INSERT INTO favortio (token, moneda) VALUES ('%s', '%s')", mysql_real_escape_string($token), mysql_real_escape_string($moneda));
		if (mysql_query($sqlinsert))
		{
			$devolver = "ok";
		}
		else
		{
			$devolver = "no ok";
		}
		echo $devolver;
		
		return $devolver;
	}


	public function eliminarFavorito($token, $moneda)
	{	
		$sqldelete = sprintf("DELETE FROM favortio WHERE token = '%s' and moneda = '%s'", mysql_real_escape_string($token), mysql_real_escape_string($moneda));
		if (mysql_query($sqldelete))
		{
			$devolver = "ok";
		}
		else
		{
			$devolver = "no ok";
		}
		echo $devolver;
		
		return $devolver;
	}

	public function fechaUnix()
	{
		$fecha = date_create();
		$datos = array();
		$unix = date_timestamp_get($fecha);
		$datos[]=array('fecha' => $unix);
		return $datos;
	}

}