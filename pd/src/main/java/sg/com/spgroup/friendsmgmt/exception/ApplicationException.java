package sg.com.spgroup.friendsmgmt.exception;

import sg.com.spgroup.friendsmgmt.model.ErrorMessage;

/**
 * Custom exception to provide front-end notification
 * 
 * @author alvin
 */
@SuppressWarnings( "serial" )
public abstract class ApplicationException extends Exception
{
    private String       message;
    private ErrorMessage errorMessage;

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
    public ErrorMessage getErrorMessage()
    {
        errorMessage = new ErrorMessage( message );
        return errorMessage;
    }
}
