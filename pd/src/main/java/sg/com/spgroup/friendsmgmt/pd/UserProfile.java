package sg.com.spgroup.friendsmgmt.pd;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class UserProfile extends AbstractAuditableEntity {
	/** Email Address **/
	@Column(unique = true)
	private String emailId;

	/** Full Name **/
	private String fullName;

	public UserProfile() {
	}

	public UserProfile(final String emailId, final String fullName) {
		this();
		this.emailId = emailId;
		this.fullName = fullName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(final String emailId) {
		this.emailId = emailId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}
}
