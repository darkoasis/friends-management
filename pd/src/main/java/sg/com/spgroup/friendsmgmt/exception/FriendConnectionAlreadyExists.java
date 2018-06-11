package sg.com.spgroup.friendsmgmt.exception;

/**
 * Returned when there is already a friend connection relationship in our table
 * 
 * @author alvin
 */
@SuppressWarnings( "serial" )
public class FriendConnectionAlreadyExists extends ApplicationException
{
    /**
     * Parametarized message format
     */
    private static final String msgFormat = "Friend connection already exists between %s and %s";

    public FriendConnectionAlreadyExists( final String fromEmailId, final String toEmailId )
    {
        super( String.format( msgFormat, fromEmailId, toEmailId ) );
    }
}
