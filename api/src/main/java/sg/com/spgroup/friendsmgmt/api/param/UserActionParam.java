package sg.com.spgroup.friendsmgmt.api.param;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * API Parameter that converts from below JSON format:
 * 
 * <pre>
 * {
 *  "requestor" : "johndoe@spgroup.com.sg"
 *  "target" : "therock@spgroup.com.sg"
 * }
 * </pre>
 * 
 * @author alvin
 */
public class UserActionParam
{
    @NotBlank
    @Email
    private String requestor;

    @NotBlank
    @Email
    private String target;

    public UserActionParam()
    {
    }

    public UserActionParam( final String requestor, final String target )
    {
        this();
        this.requestor = requestor;
        this.target = target;
    }

    public String getRequestor()
    {
        return requestor;
    }

    public void setRequestor( final String requestor )
    {
        this.requestor = requestor;
    }

    public String getTarget()
    {
        return target;
    }

    public void setTarget( final String target )
    {
        this.target = target;
    }
}
