package co.com.diccionario.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ GeneralErrorException.class })
	public ResponseEntity<String> getGeneralException(GeneralErrorException e) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(e.getMsgError(), HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}

	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<String> notFoundRquest(NotFoundException e) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(e.getMsgError(), HttpStatus.NOT_FOUND);
		return responseEntity;
}

}
