package com.electoral.dashboard_service.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing) para la API.
 * Permite que clientes frontend de diferentes orígenes accedan a los endpoints del Dashboard Service.
 * 
 * @author SITE Dashboard Service
 */
@Configuration
public class ConfiguracionCORS implements WebMvcConfigurer {

	/**
	 * Configura las políticas CORS globales para toda la aplicación.
	 * Permite acceso desde cualquier origen a todos los endpoints con método GET.
	 * En producción, se debe especificar un whitelist de orígenes permitidos.
	 * 
	 * @param registro el registro de mapeos CORS
	 */
	@Override
	public void addCorsMappings(CorsRegistry registro) {
		registro.addMapping("/api/**")
				.allowedOrigins("*") // En producción: especificar orígenes concretos
				.allowedMethods("GET", "OPTIONS")
				.allowedHeaders("Content-Type", "X-Mock-Role")
				.allowCredentials(false)
				.maxAge(3600); // Una hora de cacheo de configuración preflight
	}
}
