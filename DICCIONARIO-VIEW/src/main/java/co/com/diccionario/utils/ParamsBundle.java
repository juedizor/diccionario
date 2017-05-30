package co.com.diccionario.utils;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ParamsBundle {

	private static ParamsBundle mensajesBundle;
	private ResourceBundle resourceBundle;
	public static final String MSG = "Mensajes";
	public static final String SERVICE_CLIENT_DICCIONARIO = "params_service_client";
	private Map<String, String> mapMensajes;
	private Map<String, String> mapParamsServiceClientPatios;

	private ParamsBundle() {

	}

	public static ParamsBundle getInstance() {
		if (mensajesBundle == null) {
			mensajesBundle = new ParamsBundle();
		}
		return mensajesBundle;
	}

	private void setProperties(String pathProperties) throws Exception {
		try {
			resourceBundle = ResourceBundle.getBundle(pathProperties);
		} catch (Exception e) {
			throw e;
		}
	}

	private ResourceBundle getProperites(String pathProperties) throws Exception {
		setProperties(pathProperties);
		return resourceBundle;
	}

	public Map<String, String> getEtiquetas(String pathProperties) throws Exception {
		getProperites(pathProperties);
		Enumeration<String> enums = resourceBundle.getKeys();
		String key;
		mapMensajes = new LinkedHashMap<>();
		while (enums.hasMoreElements()) {
			key = enums.nextElement();
			mapMensajes.put(key, resourceBundle.getString(key));
		}
		return mapMensajes;
	}

	public Map<String, String> getEtiquetasParamsClient(String pathProperties) throws Exception {
		if (mapParamsServiceClientPatios != null && !mapParamsServiceClientPatios.isEmpty()) {
			return mapParamsServiceClientPatios;
		}

		getProperites(pathProperties);
		Enumeration<String> enums = resourceBundle.getKeys();
		String key;
		mapParamsServiceClientPatios = new LinkedHashMap<>();
		while (enums.hasMoreElements()) {
			key = enums.nextElement();
			mapParamsServiceClientPatios.put(key, resourceBundle.getString(key));
		}
		return mapParamsServiceClientPatios;
	}

	/**
	 * @return the map
	 */
	public Map<String, String> getMapMensajes() {
		return mapMensajes;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMapMensajes(Map<String, String> map) {
		this.mapMensajes = map;
	}

	/**
	 * @return the mapParamsServiceClientPatios
	 */
	public Map<String, String> getMapParamsServiceClientPatios() {
		return mapParamsServiceClientPatios;
	}

	/**
	 * @param mapParamsServiceClientPatios
	 *            the mapParamsServiceClientPatios to set
	 */
	public void setMapParamsServiceClientPatios(Map<String, String> mapParamsServiceClientPatios) {
		this.mapParamsServiceClientPatios = mapParamsServiceClientPatios;
	}

}
