package sg.com.spgroup.friendsmgmt.exception;

/**
 * Returned for general error message (e.g. parameter not valid)
 * 
 * @author alvin
 */
@SuppressWarnings( "serial" )
public class GeneralApplicationException extends ApplicationException
{
    public GeneralApplicationException( final String message )
    {
        super( message );
    }
}
