package sg.com.spgroup.friendsmgmt.api.util;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Catch the any un-handled <code>Exception</code> for security
 */
@ControllerAdvice
public class GenericExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionHandler.class);

    /**
     * Handle the validation errors
     * 
     * @param ex
     *            the validation errors
     * @param resp
     *            the response servlet object
     */
    /*
     * @ExceptionHandler(value = {ConstraintViolationException.class}) public void
     * handleValidationFailure(HttpServletResponse resp,
     * ConstraintViolationException ex) { /// LOGGER.error( "{}", ex ); for
     * (ConstraintViolation<?> cve : ex.getConstraintViolations()) {
     * LOGGER.error("---result: {} - {}", cve.getPropertyPath(), cve.getMessage());
     * for (Object param : cve.getExecutableParameters()) {
     * LOGGER.error("---param: {}", (param != null ? param.toString() : "null")); }
     * }
     * 
     * resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); }
     */
    /**
     * Handle the parameter binding errors
     * 
     * @param resp
     *            the response servlet object
     * @param ex
     *            the parameter binding errors
     */
    @ExceptionHandler(value = { ServletRequestBindingException.class })
    public void handleBindFailure(HttpServletResponse resp, ServletRequestBindingException ex) {
	LOGGER.error("{}", ex);
	resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Handle the exception for parameter validation
     * 
     * @param r
     *            the <code>HttpServletResponse</code> object
     * @param e
     *            the validation errors
     */
    /*
     * @ExceptionHandler(value = MethodArgumentNotValidException.class) public void
     * handleMethodArgumentNotValidException(HttpServletResponse r,
     * MethodArgumentNotValidException e) { LOGGER.info("{}", e);
     * r.setStatus(HttpServletResponse.SC_BAD_REQUEST); }
     */

    /**
     * Handle generic uncaught exception
     * 
     * @param resp
     *            the <code>HttpServletResponse</code> object
     * @param e
     *            the unknown exceptions
     */
    @ExceptionHandler(value = Exception.class)
    public void handleException(HttpServletResponse resp, Exception e) {
	LOGGER.error("{}", e);
	resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
