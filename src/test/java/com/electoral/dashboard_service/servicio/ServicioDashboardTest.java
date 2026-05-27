package com.electoral.dashboard_service.servicio;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.electoral.dashboard_service.dto.PanelCandidatoDTO;
import com.electoral.dashboard_service.excepcion.ExcepcionRolNoAutorizado;
import com.electoral.dashboard_service.fabrica.FabricaDatosSimulados;
import org.junit.jupiter.api.Test;

class ServicioDashboardTest {

    @Test
    void debeRetornarPanelDeCandidatoParaRolNormalizado() {
        FabricaDatosSimulados fabricaDatos = mock(FabricaDatosSimulados.class);
        ServicioDashboard servicio = new ServicioDashboard(fabricaDatos);

        PanelCandidatoDTO panel = PanelCandidatoDTO.builder()
                .estadoCandidatura("ACEPTADA - ACTIVA")
                .build();

        when(fabricaDatos.generarPanelCandidato()).thenReturn(panel);

        Object resultado = servicio.obtenerResumenSegunRol(" candidato ");

        assertSame(panel, resultado);
        verify(fabricaDatos).generarPanelCandidato();
    }

    @Test
    void debeRechazarRolVacio() {
        ServicioDashboard servicio = new ServicioDashboard(mock(FabricaDatosSimulados.class));

        assertThrows(ExcepcionRolNoAutorizado.class, () -> servicio.obtenerResumenSegunRol("   "));
    }
}