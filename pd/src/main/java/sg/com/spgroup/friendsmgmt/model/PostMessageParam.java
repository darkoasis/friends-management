package sg.com.spgroup.friendsmgmt.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class PostMessageParam {
    @NotBlank
    @Email
    private String sender;

    @NotBlank
    private String text;

    public PostMessageParam() {
    }

    public PostMessageParam(final String sender, final String text) {
	this();
	this.sender = sender;
	this.text = text;
    }

    public String getSender() {
	return sender;
    }

    public void setSender(final String sender) {
	this.sender = sender;
    }

    public String getText() {
	return text;
    }

    public void setText(final String text) {
	this.text = text;
    }
}
