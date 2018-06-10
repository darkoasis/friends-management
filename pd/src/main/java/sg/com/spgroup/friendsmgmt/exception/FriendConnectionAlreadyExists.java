package sg.com.spgroup.friendsmgmt.exception;

@SuppressWarnings("serial")
public class FriendConnectionAlreadyExists extends ApplicationException {
	private static final String msgFormat = "Friend connection already exists between %s and %s";

	public FriendConnectionAlreadyExists(final String fromEmailId, final String toEmailId) {
		super(String.format(msgFormat, fromEmailId, toEmailId));
	}
}
