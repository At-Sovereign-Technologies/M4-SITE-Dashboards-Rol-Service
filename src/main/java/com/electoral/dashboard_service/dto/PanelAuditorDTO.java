package com.electoral.dashboard_service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * DTO del panel de control para auditores.
 * Proporciona información sobre actas digitales y alertas de fraude detectadas.
 * 
 * @author SITE Dashboard Service
 */
@Data
@Builder
public class PanelAuditorDTO {

	/**
	 * Lista de actas digitales procesadas con su información de integridad.
	 */
	private List<ActaDigital> actasDigitales;

	/**
	 * Lista de alertas de fraude generadas por el módulo de Fraude por Regresión Auxiliar (FRA).
	 */
	private List<String> alertasFraudeFRA;

	/**
	 * Clase interna que representa una acta digital con su información de integridad criptográfica.
	 */
	@Data
	@Builder
	public static class ActaDigital {

		/**
		 * Identificador único del acta (e.g., "E14-9982").
		 */
		private String id;

		/**
		 * Hash SHA-256 para verificación de integridad.
		 */
		private String hashSha256;

		/**
		 * Estado del acta (e.g., "FIRMADA_INMUTABLE", "EN_REVISION", "RECHAZADA").
		 */
		private String estado;
	}
}
