package sg.com.spgroup.friendsmgmt.model;

/**
 * API Views based on FriendMgmtResponse.java
 * 
 * @author alvin
 */
public interface Views
{
    /**
     * Returns JSON format
     * 
     * <pre>
     *  {
     *      "status" : true
     *  }
     * </pre>
     * 
     * @author alvin
     */
    public static interface Status
    {
    }

    /**
     * Returns JSON format
     * 
     * <pre>
     *  {
     *      "friends" : ["johndoe@spgroup.com.sg", "therock@spgroup.com.sg"]
     *  }
     * </pre>
     * 
     * @author alvin
     */
    public static interface Friends
    {
    }

    /**
     * Returns JSON format
     * 
     * <pre>
     *  {
     *      "count" : 0
     *  }
     * </pre>
     * 
     * @author alvin
     */
    public static interface Count
    {
    }

    /**
     * Returns JSON format
     * 
     * <pre>
     *  {
     *      "recepients" : ["johndoe@spgroup.com.sg", "therock@spgroup.com.sg"]
     *  }
     * </pre>
     * 
     * @author alvin
     */
    public static interface Recepients
    {
    }

    /**
     * Returns JSON format
     * 
     * <pre>
     *  {
     *      "emailList" : ["johndoe@spgroup.com.sg", "therock@spgroup.com.sg"]
     *  }
     * </pre>
     * 
     * @author alvin
     */
    public static interface EmailList
    {
    }

    /**
     * Returns JSON format
     * 
     * <pre>
     *  {
     *      "status" : true,
     *      "friends" : ["johndoe@spgroup.com.sg", "therock@spgroup.com.sg"],
     *      "count" : 2
     *  }
     * </pre>
     * 
     * @author alvin
     */
    public static interface FriendsList extends Status, Friends, Count
    {

    }

    /**
     * Returns JSON format
     * 
     * <pre>
     *  {
     *      "status" : true,
     *      "recepients" : ["johndoe@spgroup.com.sg", "therock@spgroup.com.sg"],
     *  }
     * </pre>
     * 
     * @author alvin
     */
    public static interface UpdateList extends Status, Recepients
    {

    }
}
