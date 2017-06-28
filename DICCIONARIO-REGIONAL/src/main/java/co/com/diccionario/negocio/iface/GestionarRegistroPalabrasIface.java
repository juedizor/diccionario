package co.com.diccionario.negocio.iface;

import co.com.diccionario.dto.ParametrosRegistroTermino;
import co.com.diccionario.dto.SinonimosDTO;

public interface GestionarRegistroPalabrasIface {

	public SinonimosDTO actualizarSinonimos(SinonimosDTO sinonimosDTO);

	public SinonimosDTO actualizarCalificacionSinonimos(SinonimosDTO sinonimosDTO);

	public SinonimosDTO actualizarCalificacionOraciones(SinonimosDTO sinonimosDTO);

	public void registrarSinonimosPalabrasCompleta(ParametrosRegistroTermino params);

	public SinonimosDTO actualizarOraciones(SinonimosDTO sinonimosDTO);

}
