package com.electoral.dashboard_service.dto;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

/**
 * DTO del panel de control para delegados de la Consejería Nacional Electoral (CNE).
 * Proporciona información consolidada de resultados y alertas de inconsistencias.
 * 
 * @author SITE Dashboard Service
 */
@Data
@Builder
public class PanelDelegadoCNEDTO {

	/**
	 * Resultados consolidados a nivel nacional por candidato o categoría.
	 * Clave: nombre del candidato o categoría (e.g., "Candidato A", "Voto en Blanco")
	 * Valor: cantidad total de votos
	 */
	private Map<String, Integer> resultadosConsolidados;

	/**
	 * Cantidad de mesas pendientes de reportar actas finales.
	 */
	private Integer mesasActasPendientes;

	/**
	 * Lista de alertas sobre inconsistencias detectadas en los resultados
	 * (e.g., "GIDD-099: Discrepancia Doble Verdad en Mesa 4").
	 */
	private List<String> alertasInconsistencias;
}
