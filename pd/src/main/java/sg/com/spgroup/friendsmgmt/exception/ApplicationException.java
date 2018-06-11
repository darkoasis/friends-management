package sg.com.spgroup.friendsmgmt.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom exception to provide front-end notification
 * 
 * @author alvin
 */
@SuppressWarnings( "serial" )
public abstract class ApplicationException extends Exception
{
    private String              message;
    private Map<String, String> errorMessage;

    /**
     * Initializes this exception by taking the error message
     * 
     * @param message
     *            formatted custom error message
     */
    public ApplicationException( final String message )
    {
        this.message = message;
        // TODO Auto-generated constructor stub
    }

    /**
     * Overrides the superclass getMessage() and returns the constructor
     * received message
     */
    @Override
    public String getMessage()
    {
        // TODO Auto-generated method stub
        return this.message;
    }

    /**
     * Custom error message for JSON response
     * 
     * @return Hashmap of error message
     */
    public Map<String, String> getErrorMessage()
    {
        errorMessage = new HashMap<>();
        errorMessage.put( "error", message );
        return errorMessage;
    }
}
