package com.electoral.dashboard_service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * DTO del panel de control para la Fiscalía Electoral.
 * Proporciona información sobre incidentes georreferenciados, anomalías de tráfico y casos de fraude.
 * 
 * @author SITE Dashboard Service
 */
@Data
@Builder
public class PanelFiscaliaDTO {

	/**
	 * Mapa de incidentes georreferenciados detectados en el territorio electoral.
	 * Cada incidente incluye coordenadas, severidad e información del tipo de incidente.
	 */
	private List<IncidenteGeorreferenciado> mapaGeorreferenciado;

	/**
	 * Cantidad total de anomalías de tráfico de red detectadas en el sistema.
	 */
	private Integer alertasAnomaliasTrafico;

	/**
	 * Lista de casos de fraude en investigación con identificadores únicos.
	 */
	private List<String> casosFraudeInvestigacion;

	/**
	 * Clase interna que representa un incidente georreferenciado con información de ubicación y severidad.
	 */
	@Data
	@Builder
	public static class IncidenteGeorreferenciado {

		/**
		 * Coordenadas geográficas en formato "latitud, longitud" (e.g., "4.6097, -74.0817" para Bogotá).
		 */
		private String coordenadas;

		/**
		 * Nivel de severidad del incidente (e.g., "CRITICA", "ALTA", "MEDIA", "BAJA").
		 */
		private String severidad;

		/**
		 * Descripción del incidente (e.g., "Intento sistemático de voto doble").
		 */
		private String incidente;
	}
}
