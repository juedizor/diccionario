package co.com.diccionario.utils;

import java.util.Iterator;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class Utils {

	/**
	 * controla el envio de mensajes a la vista, a traves del contexto de faces
	 * donde se encuentra
	 * 
	 * @param context,
	 *            contexto del faces, managedBean que se este manejando
	 * @param message,
	 *            variable de tipo faceMesagge
	 * @param tipoMensaje,
	 *            variable de tipo Severity, aqui se puede usar
	 *            FaceMessage.<tipo de mensaje>
	 * @param idComponente,
	 *            id del componente de la vista donde se va a mostrar el mensaje
	 * @param detail,
	 *            contenido que se va a mostrar en el mensaje
	 * @param summary,
	 *            titulo de la cabecera del mensaje
	 */
	public static void enviarMensajeVista(FacesContext context, FacesMessage message, Severity tipoMensaje,
			String idComponente, String detail, String summary) {
		message.setSeverity(tipoMensaje);
		message.setDetail(detail);
		message.setSummary(summary);
		context.addMessage(idComponente, message);
	}

	public static String getPrimerValorMap(Map<String, String> map) {
		if (map == null) {
			return "";
		}
		Iterator<String> it = map.keySet().iterator();
		String key = "";
		String value = "";
		while (it.hasNext()) {
			key = "" + it.next();
			value = map.get(key);
			break;
		}

		return value;
	}
	
	public static String foundValueMap(Map<String, String> map, String id) {
		if (map == null) {
			return "";
		}
		Iterator<String> it = map.keySet().iterator();
		String key = "";
		String value = "";
		while (it.hasNext()) {
			key = "" + it.next();
			value = map.get(key);
			if(value.equals(id)){
				return key;
			}
		}

		return value;
	}
	
	public static void removeSessionBean(final String beanName) {
		FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(beanName);
	}

}
