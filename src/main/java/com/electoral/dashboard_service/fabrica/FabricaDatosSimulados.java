package com.electoral.dashboard_service.fabrica;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.electoral.dashboard_service.dto.PanelAuditorDTO;
import com.electoral.dashboard_service.dto.PanelAuditorDTO.ActaDigital;
import com.electoral.dashboard_service.dto.PanelCandidatoDTO;
import com.electoral.dashboard_service.dto.PanelDelegadoCNEDTO;
import com.electoral.dashboard_service.dto.PanelFiscaliaDTO;
import com.electoral.dashboard_service.dto.PanelFiscaliaDTO.IncidenteGeorreferenciado;
import com.electoral.dashboard_service.dto.PanelTestigoDTO;
import com.electoral.dashboard_service.dto.PanelTestigoDTO.MesaEstado;

/**
 * Fábrica de datos simulados para los paneles de control del Dashboard Electoral.
 * Genera datos realistas de prueba basados en el contexto electoral colombiano.
 * Esta clase proporciona métodos estáticos para crear DTOs completamente poblados
 * con datos típicos del proceso electoral SITE.
 * 
 * @author SITE Dashboard Service
 */
@Component
public class FabricaDatosSimulados {

	/**
	 * Genera el panel de control simulado para un candidato.
	 * Incluye votos por mesa, cobertura reportada y estado de candidatura.
	 * 
	 * @return PanelCandidatoDTO con datos simulados
	 */
	public PanelCandidatoDTO generarPanelCandidato() {
		Map<String, Integer> votosPorMesa = new HashMap<>();
		votosPorMesa.put("Mesa 1 - Puesto Central", 450);
		votosPorMesa.put("Mesa 2 - Puesto Sur", 320);
		votosPorMesa.put("Mesa 3 - Puesto Norte", 510);
		votosPorMesa.put("Mesa 4 - Puesto Occidente", 280);
		votosPorMesa.put("Mesa 5 - Puesto Oriente", 395);

		return PanelCandidatoDTO.builder()
				.votosPropiosPorMesa(votosPorMesa)
				.porcentajeMesasReportadas(85.5)
				.estadoCandidatura("ACEPTADA - ACTIVA")
				.alertasReclamacionesActivas(false)
				.build();
	}

	/**
	 * Genera el panel de control simulado para un testigo electoral.
	 * Incluye mesas bajo cobertura y conteo de votos del partido.
	 * 
	 * @return PanelTestigoDTO con datos simulados
	 */
	public PanelTestigoDTO generarPanelTestigo() {
		List<MesaEstado> mesasCobertura = Arrays.asList(
				MesaEstado.builder().mesa("Mesa 14").estado("ESCRUTINIO FINALIZADO").build(),
				MesaEstado.builder().mesa("Mesa 15").estado("ACTA GENERADA").build(),
				MesaEstado.builder().mesa("Mesa 16").estado("ACTA FIRMADA").build(),
				MesaEstado.builder().mesa("Mesa 17").estado("ESCRUTINIO FINALIZADO").build()
		);

		return PanelTestigoDTO.builder()
				.mesasBajoCobertura(mesasCobertura)
				.conteoVotosPartido(1450)
				.build();
	}

	/**
	 * Genera el panel de control simulado para un auditor.
	 * Incluye actas digitales con hashes y alertas de fraude.
	 * 
	 * @return PanelAuditorDTO con datos simulados
	 */
	public PanelAuditorDTO generarPanelAuditor() {
		List<ActaDigital> actasDigitales = Arrays.asList(
				ActaDigital.builder()
						.id("E14-9982")
						.hashSha256("a2b4c6d8e0f1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b1c2d3e4f5a6b")
						.estado("FIRMADA_INMUTABLE")
						.build(),
				ActaDigital.builder()
						.id("E15-9983")
						.hashSha256("b3c5d7e9f0a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2e3f4a5b6")
						.estado("FIRMADA_INMUTABLE")
						.build(),
				ActaDigital.builder()
						.id("E16-9984")
						.hashSha256("c4d6e8f0a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2e3f4a5b6c7")
						.estado("EN_REVISION")
						.build()
		);

		List<String> alertasFraud = Arrays.asList(
				"Alerta FRA-001: Anomalía estadística en Puesto Norte",
				"Alerta FRA-002: Intento de acceso remoto no autorizado"
		);

		return PanelAuditorDTO.builder()
				.actasDigitales(actasDigitales)
				.alertasFraudeFRA(alertasFraud)
				.build();
	}

	/**
	 * Genera el panel de control simulado para un delegado de la CNE.
	 * Incluye resultados consolidados y alertas de inconsistencias.
	 * 
	 * @return PanelDelegadoCNEDTO con datos simulados
	 */
	public PanelDelegadoCNEDTO generarPanelDelegadoCNE() {
		Map<String, Integer> resultados = new HashMap<>();
		resultados.put("Candidato A", 5400000);
		resultados.put("Candidato B", 4900000);
		resultados.put("Candidato C", 3200000);
		resultados.put("Voto en Blanco", 250000);
		resultados.put("Voto Nulo", 150000);

		List<String> alertas = Arrays.asList(
				"GIDD-099: Discrepancia Doble Verdad en Mesa 4",
				"GIDD-100: Diferencia de actas en Puesto Este",
				"GIDD-101: Recuento de votos fuera de rango esperado"
		);

		return PanelDelegadoCNEDTO.builder()
				.resultadosConsolidados(resultados)
				.mesasActasPendientes(14)
				.alertasInconsistencias(alertas)
				.build();
	}

	/**
	 * Genera el panel de control simulado para la Fiscalía Electoral.
	 * Incluye incidentes georreferenciados, anomalías de tráfico y casos de fraude.
	 * 
	 * @return PanelFiscaliaDTO con datos simulados
	 */
	public PanelFiscaliaDTO generarPanelFiscalia() {
		List<IncidenteGeorreferenciado> incidentes = Arrays.asList(
				IncidenteGeorreferenciado.builder()
						.coordenadas("4.6097, -74.0817")
						.severidad("CRITICA")
						.incidente("Intento sistemático de voto doble")
						.build(),
				IncidenteGeorreferenciado.builder()
						.coordenadas("4.7110, -74.0721")
						.severidad("ALTA")
						.incidente("Acceso no autorizado a máquina de votación")
						.build(),
				IncidenteGeorreferenciado.builder()
						.coordenadas("4.6726, -74.0481")
						.severidad("MEDIA")
						.incidente("Interferencia de señal de comunicación")
						.build(),
				IncidenteGeorreferenciado.builder()
						.coordenadas("4.5527, -74.0359")
						.severidad("BAJA")
						.incidente("Retraso en reporte de actas")
						.build()
		);

		List<String> casosFraude = Arrays.asList(
				"CASO-FRA-992: Suplantación biométrica detectada",
				"CASO-FRA-993: Manipulación de actas en tránsito",
				"CASO-FRA-994: Votante múltiple identificado"
		);

		return PanelFiscaliaDTO.builder()
				.mapaGeorreferenciado(incidentes)
				.alertasAnomaliasTrafico(12)
				.casosFraudeInvestigacion(casosFraude)
				.build();
	}
}
