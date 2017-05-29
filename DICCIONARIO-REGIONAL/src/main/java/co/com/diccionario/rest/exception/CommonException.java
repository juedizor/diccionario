package co.com.diccionario.rest.exception;

public class CommonException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msgError;

	public CommonException() {
		super();
	}

	public CommonException(String msgError) {
		super(msgError);
		this.msgError = msgError;
	}

	/**
	 * @return the msgError
	 */
	public String getMsgError() {
		return msgError;
	}

	/**
	 * @param msgError
	 *            the msgError to set
	 */
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

}
