package com.electoral.dashboard_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal para el servicio de Dashboard Electoral SITE.
 * Servicio de lectura que proporciona datos simulados basados en roles de usuario.
 * 
 * @author Sistema Electoral Transparente Electrónico (SITE) v2.1
 */
@SpringBootApplication
public class PrincipalAplicacionSitio {

	/**
	 * Punto de entrada de la aplicación Spring Boot.
	 * 
	 * @param args argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(PrincipalAplicacionSitio.class, args);
	}
}
