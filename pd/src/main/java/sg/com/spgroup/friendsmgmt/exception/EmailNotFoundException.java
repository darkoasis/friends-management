package sg.com.spgroup.friendsmgmt.exception;

@SuppressWarnings("serial")
public class EmailNotFoundException extends ApplicationException {
    private static final String msgFormat = "User with %s emailId is not found";

    public EmailNotFoundException(final String emailId) {
	super(String.format(msgFormat, emailId));
    }

}
