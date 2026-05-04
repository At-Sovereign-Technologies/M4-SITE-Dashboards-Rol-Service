package com.electoral.dashboard_service.servicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.electoral.dashboard_service.excepcion.ExcepcionRolNoAutorizado;
import com.electoral.dashboard_service.fabrica.FabricaDatosSimulados;

/**
 * Servicio de Dashboard que encamina las solicitudes a los datos simulados correspondientes
 * según el rol del usuario extraído del encabezado X-Mock-Role.
 * Utiliza expresión switch de Java 21 para un enrutamiento limpio y eficiente.
 * 
 * @author SITE Dashboard Service
 */
@Service
@RequiredArgsConstructor
public class ServicioDashboard {

	private static final Logger log = LoggerFactory.getLogger(ServicioDashboard.class);

	private final FabricaDatosSimulados fabricaDatos;

	/**
	 * Obtiene el resumen del dashboard según el rol del usuario.
	 * Los roles válidos son: CANDIDATO, TESTIGO, AUDITOR, DELEGADO_CNE, FISCALIA.
	 * 
	 * @param rol el rol del usuario extraído del encabezado X-Mock-Role
	 * @return objeto DTO específico del rol con datos simulados
	 * @throws ExcepcionRolNoAutorizado si el rol es nulo o no está en la lista de roles válidos
	 */
	public Object obtenerResumenSegunRol(String rol) {
		if (rol == null || rol.isBlank()) {
			log.warn("ACCESO_DENEGADO: Rol requerido pero no proporcionado");
			throw new ExcepcionRolNoAutorizado("Encabezado X-Mock-Role requerido");
		}

		String rolNormalizado = rol.trim().toUpperCase();
		log.info("ACCESO_DASHBOARD: rol={}", rolNormalizado);

		return switch (rolNormalizado) {
			case "CANDIDATO" -> {
				log.debug("GENERANDO_PANEL: Candidato");
				yield fabricaDatos.generarPanelCandidato();
			}
			case "TESTIGO" -> {
				log.debug("GENERANDO_PANEL: Testigo");
				yield fabricaDatos.generarPanelTestigo();
			}
			case "AUDITOR" -> {
				log.debug("GENERANDO_PANEL: Auditor");
				yield fabricaDatos.generarPanelAuditor();
			}
			case "DELEGADO_CNE" -> {
				log.debug("GENERANDO_PANEL: Delegado CNE");
				yield fabricaDatos.generarPanelDelegadoCNE();
			}
			case "FISCALIA" -> {
				log.debug("GENERANDO_PANEL: Fiscalía");
				yield fabricaDatos.generarPanelFiscalia();
			}
			default -> {
				log.warn("ACCESO_DENEGADO: Rol inválido - {}", rolNormalizado);
				throw new ExcepcionRolNoAutorizado("Rol inválido: " + rolNormalizado + ". Roles válidos: CANDIDATO, TESTIGO, AUDITOR, DELEGADO_CNE, FISCALIA");
			}
		};
	}
}
