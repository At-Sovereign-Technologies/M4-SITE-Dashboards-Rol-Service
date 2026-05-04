package com.electoral.dashboard_service.excepcion;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * DTO de respuesta para errores. Estructura estándar para todas las respuestas de error en el servicio.
 * Incluye información de contexto del error como timestamp, código de estado HTTP, mensaje y ruta.
 * 
 * @author SITE Dashboard Service
 */
@Data
@Builder
public class ExcepcionRespuestaError {

	/**
	 * Marca de tiempo cuando ocurrió el error.
	 */
	private LocalDateTime timestamp;

	/**
	 * Código de estado HTTP (e.g., 403, 500).
	 */
	private Integer estado;

	/**
	 * Tipo de error (e.g., "ACCESO_DENEGADO", "ERROR_INTERNO").
	 */
	private String error;

	/**
	 * Mensaje descriptivo del error.
	 */
	private String mensaje;

	/**
	 * Ruta HTTP que generó el error.
	 */
	private String ruta;
}
