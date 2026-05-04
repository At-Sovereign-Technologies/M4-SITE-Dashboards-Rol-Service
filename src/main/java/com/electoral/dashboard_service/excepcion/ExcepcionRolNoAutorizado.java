package com.electoral.dashboard_service.excepcion;

/**
 * Excepción personalizada lanzada cuando el rol en el encabezado X-Mock-Role es nulo, inválido o no autorizado.
 * Corresponde a un estado HTTP 403 Forbidden.
 * 
 * @author SITE Dashboard Service
 */
public class ExcepcionRolNoAutorizado extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor con mensaje.
	 * 
	 * @param mensaje descripción del error
	 */
	public ExcepcionRolNoAutorizado(String mensaje) {
		super(mensaje);
	}

	/**
	 * Constructor con mensaje y causa.
	 * 
	 * @param mensaje descripción del error
	 * @param causa excepción que causó este error
	 */
	public ExcepcionRolNoAutorizado(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
