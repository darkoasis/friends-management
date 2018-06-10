package sg.com.spgroup.friendsmgmt.model;

import java.util.ArrayList;
import java.util.List;

public class Friends {
    List<String> friends;

    public Friends() {
	super();
	// TODO Auto-generated constructor stub
    }

    public List<String> getFriends() {
	if (friends == null) {
	    friends = new ArrayList<>();
	}
	return friends;
    }

    public void setFriends(List<String> friendsList) {
	this.friends = friendsList;
    }

}
