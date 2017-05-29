package co.com.diccionario.rest.exception;

public class GeneralErrorException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msgError;

	public GeneralErrorException() {
		super();
	}

	public GeneralErrorException(String msgError) {
		super(msgError);
		this.msgError = msgError;
	}

	public String getMsgError() {
		return msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

}
