package sg.com.spgroup.friendsmgmt.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UserActionParam {
    @NotBlank
    @Email
    private String requestor;

    @NotBlank
    @Email
    private String target;

    public UserActionParam() {
    }

    public UserActionParam(final String requestor, final String target) {
	this();
	this.requestor = requestor;
	this.target = target;
    }

    public String getRequestor() {
	return requestor;
    }

    public void setRequestor(final String requestor) {
	this.requestor = requestor;
    }

    public String getTarget() {
	return target;
    }

    public void setTarget(final String target) {
	this.target = target;
    }
}
