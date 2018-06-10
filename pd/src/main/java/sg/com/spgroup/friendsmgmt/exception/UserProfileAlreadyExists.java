package sg.com.spgroup.friendsmgmt.exception;

@SuppressWarnings("serial")
public class UserProfileAlreadyExists extends ApplicationException {
	private static final String msgFormat = "User Profile with %s email address already exists.";

	public UserProfileAlreadyExists(final String emailId) {
		super(String.format(msgFormat, emailId));
	}
}
