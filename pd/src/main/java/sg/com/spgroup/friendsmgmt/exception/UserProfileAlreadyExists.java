package sg.com.spgroup.friendsmgmt.exception;

/**
 * Returned when an existing email address is already registered
 * 
 * @author alvin
 */
@SuppressWarnings( "serial" )
public class UserProfileAlreadyExists extends ApplicationException
{
    /**
     * Parametarized message format
     */
    private static final String msgFormat = "User Profile with %s email address already exists.";

    public UserProfileAlreadyExists( final String emailId )
    {
        super( String.format( msgFormat, emailId ) );
    }
}
