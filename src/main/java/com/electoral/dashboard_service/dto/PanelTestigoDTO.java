package com.electoral.dashboard_service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * DTO del panel de control para testigos electorales.
 * Proporciona información sobre mesas bajo cobertura de testigos y conteo de votos.
 * 
 * @author SITE Dashboard Service
 */
@Data
@Builder
public class PanelTestigoDTO {

	/**
	 * Lista de mesas bajo la cobertura de este testigo con su estado actual.
	 */
	private List<MesaEstado> mesasBajoCobertura;

	/**
	 * Conteo acumulado de votos del partido representado por este testigo.
	 */
	private Integer conteoVotosPartido;

	/**
	 * Clase interna que representa el estado de una mesa electoral.
	 */
	@Data
	@Builder
	public static class MesaEstado {

		/**
		 * Identificador de la mesa electoral (e.g., "Mesa 14").
		 */
		private String mesa;

		/**
		 * Estado actual de la mesa (e.g., "ESCRUTINIO FINALIZADO", "ACTA GENERADA").
		 */
		private String estado;
	}
}
