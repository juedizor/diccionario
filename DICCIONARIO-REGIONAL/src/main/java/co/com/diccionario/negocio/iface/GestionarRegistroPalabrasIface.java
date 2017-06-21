package co.com.diccionario.negocio.iface;

import co.com.diccionario.dto.ParametrosRegistroTermino;
import co.com.diccionario.dto.SinonimosDTO;

public interface GestionarRegistroPalabrasIface {
	
	
	
	public SinonimosDTO actualizarSinonimos (SinonimosDTO sinonimosDTO); 
	public SinonimosDTO actualizarCalificacion(SinonimosDTO sinonimosDTO);
	public void registrarSinonimosPalabrasCompleta(ParametrosRegistroTermino params);

}
