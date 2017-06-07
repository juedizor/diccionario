package co.com.diccionario.client.catalogos;

import java.util.List;

import co.com.diccionario.dto.ParamsBusquedaPalabraDTO;
import co.com.diccionario.dto.SinonimosDTO;
import co.com.diccionario.utils.ParamsBundle;

public class GestionarPalabrasServiceClient {

	private static GestionarPalabrasServiceClient INSTANCE;
	private static String HOST_END_POINT;
	private static final String HOST = "host_end_point";

	private GestionarPalabrasServiceClient() {
		// TODO Auto-generated constructor stub
	}

	public static GestionarPalabrasServiceClient getInstance() throws Exception {
		if (INSTANCE == null) {
			INSTANCE = new GestionarPalabrasServiceClient();
			ParamsBundle.getInstance().getEtiquetasParamsClient(ParamsBundle.SERVICE_CLIENT_DICCIONARIO);
			HOST_END_POINT = ParamsBundle.getInstance().getMapParamsServiceClientPatios().get(HOST);
		}
		return INSTANCE;
	}
	
	public List<SinonimosDTO> obtenerSinonimos(ParamsBusquedaPalabraDTO paramsBusqueda) throws Exception{
		
	}
	
	

}
