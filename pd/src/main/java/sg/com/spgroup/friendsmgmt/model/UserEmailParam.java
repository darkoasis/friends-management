package sg.com.spgroup.friendsmgmt.model;

import org.hibernate.validator.constraints.Email;

public class UserEmailParam {
    @Email
    private String email;

    public UserEmailParam() {
    }

    public UserEmailParam(final String emailId) {
	this();
	this.email = emailId;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String emailId) {
	this.email = emailId;
    }
}
