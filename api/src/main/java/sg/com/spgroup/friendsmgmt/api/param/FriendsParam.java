package sg.com.spgroup.friendsmgmt.api.param;

import java.util.List;

public class FriendsParam
{
    private List<String> friends;

    public FriendsParam()
    {
    }

    public List<String> getFriends()
    {
        return friends;
    }

    public void setFriends( List<String> friends )
    {
        this.friends = friends;
    }
}
