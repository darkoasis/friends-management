package sg.com.spgroup.friendsmgmt.exception;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public abstract class ApplicationException extends Exception {
    private String message;
    private Map<String, String> errorMessage;

    public ApplicationException(final String message) {
	this.message = message;
	// TODO Auto-generated constructor stub
    }

    @Override
    public String getMessage() {
	// TODO Auto-generated method stub
	return this.message;
    }

    public Map<String, String> getErrorMessage() {
	errorMessage = new HashMap<>();
	errorMessage.put("error", message);
	return errorMessage;
    }
}
