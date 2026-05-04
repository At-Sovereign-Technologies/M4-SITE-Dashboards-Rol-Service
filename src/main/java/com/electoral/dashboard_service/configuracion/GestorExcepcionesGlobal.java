package com.electoral.dashboard_service.configuracion;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.electoral.dashboard_service.excepcion.ExcepcionRespuestaError;
import com.electoral.dashboard_service.excepcion.ExcepcionRolNoAutorizado;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Gestor centralizado de excepciones para toda la aplicación.
 * Captura excepciones personalizadas y excepciones genéricas,
 * transformándolas en respuestas JSON estructuradas con información de contexto.
 * 
 * @author SITE Dashboard Service
 */
@RestControllerAdvice
public class GestorExcepcionesGlobal extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GestorExcepcionesGlobal.class);

	/**
	 * Maneja excepciones de rol no autorizado (acceso denegado).
	 * Retorna HTTP 403 Forbidden con detalles del error.
	 * 
	 * @param excepcion la excepción de rol no autorizado
	 * @param request la solicitud HTTP que generó el error
	 * @return respuesta de error estructurada
	 */
	@ExceptionHandler(ExcepcionRolNoAutorizado.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ExcepcionRespuestaError manejarRolNoAutorizado(
			ExcepcionRolNoAutorizado excepcion,
			HttpServletRequest request) {

		log.error("EXCEPCION_ROL_NO_AUTORIZADO: {}", excepcion.getMessage());

		return ExcepcionRespuestaError.builder()
				.timestamp(LocalDateTime.now())
				.estado(HttpStatus.FORBIDDEN.value())
				.error("ACCESO_DENEGADO")
				.mensaje(excepcion.getMessage())
				.ruta(request.getRequestURI())
				.build();
	}

	/**
	 * Maneja excepciones genéricas no capturadas por otros handlers.
	 * Retorna HTTP 500 Internal Server Error.
	 * 
	 * @param excepcion la excepción no manejada
	 * @param request la solicitud HTTP que generó el error
	 * @return respuesta de error estructurada
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExcepcionRespuestaError manejarExcepcionGeneral(
			Exception excepcion,
			HttpServletRequest request) {

		log.error("ERROR_INTERNO_SERVIDOR", excepcion);

		return ExcepcionRespuestaError.builder()
				.timestamp(LocalDateTime.now())
				.estado(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error("ERROR_INTERNO")
				.mensaje("Se produjo un error interno en el servidor. Contacte al administrador.")
				.ruta(request.getRequestURI())
				.build();
	}
}
