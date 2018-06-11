package sg.com.spgroup.friendsmgmt.services;

import sg.com.spgroup.friendsmgmt.exception.ApplicationException;
import sg.com.spgroup.friendsmgmt.model.FriendsMgmtResponse;

/**
 * Inteface for friend management
 * 
 * @author alvin
 */
public interface FriendManagementService
{
    boolean createUserProfile( String emailId, String fullname ) throws ApplicationException;

    FriendsMgmtResponse getAllEmailList();

    boolean createFriendConnection( String fromEmailId, String toEmailId )
            throws ApplicationException;

    FriendsMgmtResponse getFriendConnections( String emailId ) throws ApplicationException;

    boolean createFriendSubscription( String profileEmailId, String subscriberEmailId )
            throws ApplicationException;

    // FriendsMgmtResponse getListOfSubscribers(String emailId) throws
    // ApplicationException;
    FriendsMgmtResponse getCommonFriendConnections( String fromEmailId, String withEmailId )
            throws ApplicationException;

    boolean blockFriendConnection( String profileEmailId, String blockedEmailId )
            throws ApplicationException;

    FriendsMgmtResponse postUpdate( String profileEmailId, String text )
            throws ApplicationException;
}
