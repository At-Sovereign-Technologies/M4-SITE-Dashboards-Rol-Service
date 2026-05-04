package com.electoral.dashboard_service.dto;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

/**
 * DTO del panel de control para candidatos.
 * Proporciona información sobre votos propios, cobertura de mesas y estado de candidatura.
 * 
 * @author SITE Dashboard Service
 */
@Data
@Builder
public class PanelCandidatoDTO {

	/**
	 * Conteo de votos propios por número de mesa.
	 * Clave: identificador de mesa (e.g., "Mesa 1 - Puesto Central")
	 * Valor: cantidad de votos recibidos
	 */
	private Map<String, Integer> votosPropiosPorMesa;

	/**
	 * Porcentaje de mesas que han reportado resultados (0-100).
	 */
	private Double porcentajeMesasReportadas;

	/**
	 * Estado actual de la candidatura (e.g., "ACEPTADA - ACTIVA").
	 */
	private String estadoCandidatura;

	/**
	 * Indicador de si hay alertas o reclamaciones activas pendientes de resolver.
	 */
	private Boolean alertasReclamacionesActivas;
}
