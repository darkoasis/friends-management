package sg.com.spgroup.friendsmgmt.api.param;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * API Parameter that converts from below JSON format:
 * 
 * <pre>
 * {
 *  "emailId" : "johndoe@spgroup.com.sg"
 *  "fullname" : "John Doe"
 * }
 * </pre>
 * 
 * @author alvin
 */
public class UserProfileParam
{
    @NotBlank
    @Email( message = "Invalid email address" )
    private String emailId;
    private String fullname;

    public UserProfileParam()
    {
    }

    public UserProfileParam( final String emailId, final String fullname )
    {
        this();
        this.emailId = emailId;
        this.fullname = fullname;
    }

    public String getEmailId()
    {
        return emailId;
    }

    public void setEmailId( String emailId )
    {
        this.emailId = emailId;
    }

    public String getFullname()
    {
        return fullname;
    }

    public void setFullname( String fullname )
    {
        this.fullname = fullname;
    }
}
