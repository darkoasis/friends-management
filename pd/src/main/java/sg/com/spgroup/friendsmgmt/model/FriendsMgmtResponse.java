package sg.com.spgroup.friendsmgmt.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public class FriendsMgmtResponse {
	@JsonView(Views.Status.class)
	private boolean status;

	@JsonView(Views.EmailList.class)
	private List<String> emailList;
	
	@JsonView(Views.Friends.class)
	private List<String> friends;
	
	@JsonView(Views.Recepients.class)
	private List<String> recepients;
	
	@JsonView(Views.Count.class)
	private int count;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(final boolean status) {
		this.status = status;
	}

	public List<String> getEmailList() {
		if(emailList == null) {
			emailList = new ArrayList<String>();
		}
		return emailList;
	}

	public void setEmailList(final List<String> emailList) {
		this.emailList = emailList;
	}

	public List<String> getFriends() {
		if(friends == null) {
			friends = new ArrayList<String>();
		}
		return friends;
	}

	public void setFriends(final List<String> friends) {
		this.friends = friends;
	}

	public List<String> getRecepients() {
		if(recepients == null) {
			recepients = new ArrayList<String>();
		}
		return recepients;
	}

	public void setRecepients(final List<String> recepients) {
		this.recepients = recepients;
	}

	public int getCount() {
		return count;
	}

	public void setCount(final int count) {
		this.count = count;
	}	
}
