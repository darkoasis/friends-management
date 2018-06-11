package sg.com.spgroup.friendsmgmt.exception;

/**
 * Returned for errors when email address is not found in the UserProfile table.
 * 
 * @author alvin
 */
@SuppressWarnings( "serial" )
public class EmailNotFoundException extends ApplicationException
{
    /**
     * Parametarized message format
     */
    private static final String msgFormat = "User with %s emailId is not found";

    public EmailNotFoundException( final String emailId )
    {
        super( String.format( msgFormat, emailId ) );
    }
}
