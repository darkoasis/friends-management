package sg.com.spgroup.friendsmgmt.exception;

/**
 * Returned when a blocked user tries make a connection with the requestor
 * 
 * @author alvin
 */
@SuppressWarnings( "serial" )
public class UserBlockedException extends ApplicationException
{
    /**
     * Parametarized message format
     */
    private static final String msgFormat = "User %s has blocked %s.";

    public UserBlockedException( final String profileEmailId, final String subscriberEmailId )
    {
        super( String.format( msgFormat, profileEmailId, subscriberEmailId ) );
    }
}
