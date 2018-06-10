package sg.com.spgroup.friendsmgmt.model;

public class Error {
	private String error;

	public Error() {
	}

	public Error(final String error) {
		this();
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(final String error) {
		this.error = error;
	}
}
