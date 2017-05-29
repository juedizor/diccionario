package co.com.diccionario.rest.exception;

public class NotFoundException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msgError;

	public NotFoundException() {
		super();
	}

	public NotFoundException(String msgError) {
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
