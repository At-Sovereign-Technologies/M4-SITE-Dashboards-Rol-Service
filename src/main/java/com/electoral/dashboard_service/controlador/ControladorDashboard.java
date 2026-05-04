package com.electoral.dashboard_service.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import com.electoral.dashboard_service.excepcion.ExcepcionRolNoAutorizado;
import com.electoral.dashboard_service.excepcion.ExcepcionRespuestaError;
import com.electoral.dashboard_service.servicio.ServicioDashboard;

/**
 * Controlador REST para el endpoint del Dashboard Electoral.
 * Expone un único endpoint que proporciona datos simulados según el rol del usuario
 * especificado en el encabezado HTTP X-Mock-Role.
 * 
 * @author SITE Dashboard Service
 */
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard Electoral", description = "Endpoints del panel de control electoral SITE v2.1")
public class ControladorDashboard {

	private static final Logger log = LoggerFactory.getLogger(ControladorDashboard.class);

	private final ServicioDashboard servicioDashboard;

	/**
	 * Obtiene un resumen del dashboard personalizado según el rol del usuario.
	 * El cliente debe proporcionar el rol a través del encabezado X-Mock-Role.
	 * 
	 * Roles válidos:
	 * - CANDIDATO: Ver votos por mesa, cobertura de reportes
	 * - TESTIGO: Ver mesas bajo cobertura y conteo de votos del partido
	 * - AUDITOR: Ver actas digitales y alertas de fraude
	 * - DELEGADO_CNE: Ver resultados consolidados e inconsistencias
	 * - FISCALIA: Ver incidentes georreferenciados y casos de fraude
	 * 
	 * @param rolHeader el rol del usuario (desde encabezado X-Mock-Role)
	 * @return ResponseEntity con el panel de datos específico del rol
	 * @throws ExcepcionRolNoAutorizado si el rol es nulo, vacío o no válido
	 */
	@GetMapping("/resumen")
	@Operation(
			summary = "Obtener resumen del dashboard",
			description = "Retorna un objeto JSON con datos del dashboard específicos del rol del usuario. " +
					"El cliente debe proporcionar el rol mediante el encabezado X-Mock-Role."
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Resumen obtenido exitosamente. El tipo de respuesta depende del rol.",
					content = {
							@Content(
									mediaType = "application/json",
									schema = @Schema(type = "object"),
									examples = {
											@ExampleObject(
													name = "Candidato",
													value = "{ \"votosPropiosPorMesa\": {\"Mesa 1 - Puesto Central\": 450}, \"porcentajeMesasReportadas\": 85.5, \"estadoCandidatura\": \"ACEPTADA - ACTIVA\", \"alertasReclamacionesActivas\": false }"
											),
											@ExampleObject(
													name = "Testigo",
													value = "{ \"mesasBajoCobertura\": [{\"mesa\": \"Mesa 14\", \"estado\": \"ESCRUTINIO FINALIZADO\"}], \"conteoVotosPartido\": 1450 }"
											)
									}
							)
					}
			),
			@ApiResponse(
					responseCode = "403",
					description = "Acceso denegado. El encabezado X-Mock-Role es requerido, está vacío o el rol es inválido.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ExcepcionRespuestaError.class),
							examples = @ExampleObject(
									value = "{ \"timestamp\": \"2026-05-04T10:30:00\", \"estado\": 403, \"error\": \"ACCESO_DENEGADO\", \"mensaje\": \"Encabezado X-Mock-Role requerido\", \"ruta\": \"/api/v1/dashboard/resumen\" }"
							)
					)
			),
			@ApiResponse(
					responseCode = "500",
					description = "Error interno del servidor.",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ExcepcionRespuestaError.class)
					)
			)
	})
	public ResponseEntity<?> obtenerResumen(
			@Parameter(
					name = "X-Mock-Role",
					description = "Rol del usuario. Valores válidos: CANDIDATO, TESTIGO, AUDITOR, DELEGADO_CNE, FISCALIA",
					required = true,
					example = "CANDIDATO"
			)
			@RequestHeader(value = "X-Mock-Role", required = false) String rolHeader) {

		if (rolHeader == null || rolHeader.isBlank()) {
			log.warn("SOLICITUD_INVALIDA: Encabezado X-Mock-Role faltante");
			throw new ExcepcionRolNoAutorizado("Encabezado X-Mock-Role requerido");
		}

		Object resumen = servicioDashboard.obtenerResumenSegunRol(rolHeader);
		log.debug("RESUMEN_GENERADO: rol={}", rolHeader.toUpperCase());

		return ResponseEntity.ok(resumen);
	}
}
