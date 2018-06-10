package sg.com.spgroup.friendsmgmt.exception;

@SuppressWarnings("serial")
public class UserBlockedException extends ApplicationException {
    private static final String msgFormat = "User %s has blocked %s.";

    public UserBlockedException(final String profileEmailId, final String subscriberEmailId) {
	super(String.format(msgFormat, profileEmailId, subscriberEmailId));
    }

}
