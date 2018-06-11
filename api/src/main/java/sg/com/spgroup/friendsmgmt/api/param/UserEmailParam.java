package sg.com.spgroup.friendsmgmt.api.param;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * API Parameter that converts from below JSON format:
 * 
 * <pre>
 * {
 *  "email" : "johndoe@spgroup.com.sg"
 * }
 * </pre>
 * 
 * @author alvin
 */
public class UserEmailParam
{
    @NotBlank
    @Email
    private String email;

    public UserEmailParam()
    {
    }

    public UserEmailParam( final String emailId )
    {
        this();
        this.email = emailId;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String emailId )
    {
        this.email = emailId;
    }
}
