package sg.com.spgroup.friendsmgmt.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sg.com.spgroup.friendsmgmt.dm.FriendBlockRepository;
import sg.com.spgroup.friendsmgmt.dm.FriendConnectionRepository;
import sg.com.spgroup.friendsmgmt.dm.FriendSubscriberRepository;
import sg.com.spgroup.friendsmgmt.dm.UserProfileRepository;
import sg.com.spgroup.friendsmgmt.exception.ApplicationException;
import sg.com.spgroup.friendsmgmt.exception.EmailNotFoundException;
import sg.com.spgroup.friendsmgmt.exception.FriendConnectionAlreadyExists;
import sg.com.spgroup.friendsmgmt.exception.GeneralApplicationException;
import sg.com.spgroup.friendsmgmt.exception.UserBlockedException;
import sg.com.spgroup.friendsmgmt.exception.UserProfileAlreadyExists;
import sg.com.spgroup.friendsmgmt.model.FriendsMgmtResponse;
import sg.com.spgroup.friendsmgmt.pd.FriendBlock;
import sg.com.spgroup.friendsmgmt.pd.FriendConnection;
import sg.com.spgroup.friendsmgmt.pd.FriendSubscriber;
import sg.com.spgroup.friendsmgmt.pd.UserProfile;
import sg.com.spgroup.friendsmgmt.services.FriendManagementService;

/**
 * Implementation class of FriendManagementService.java
 * 
 * @author alvin
 */
public class FriendManagementServiceImpl implements FriendManagementService
{
    private static final Logger LOGGER = LoggerFactory
            .getLogger( FriendManagementServiceImpl.class );

    @Autowired
    UserProfileRepository userRepo;

    @Autowired
    FriendConnectionRepository connectionRepo;

    @Autowired
    FriendSubscriberRepository subscriberRepo;

    @Autowired
    FriendBlockRepository blockRepo;

    public FriendManagementServiceImpl()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * Persists new user profile to database
     */
    @Override
    public boolean createUserProfile( final String emailId, final String fullname )
            throws ApplicationException
    {
        UserProfile profile = userRepo.findByEmailId( emailId );

        if ( profile != null )
        {
            throw new UserProfileAlreadyExists( emailId );
        }

        UserProfile newUserProfile = new UserProfile( emailId, fullname );

        try
        {
            userRepo.save( newUserProfile );
        }
        catch ( Exception e )
        {
            LOGGER.error( e.getMessage() );
            throw new GeneralApplicationException(
                    String.format( "There was an error creating profile for %s", emailId ) );
        }

        return true;
    }

    /**
     * Returns the list of all registered email address
     */
    @Override
    public FriendsMgmtResponse getAllEmailList()
    {
        // TODO Auto-generated method stub
        FriendsMgmtResponse response = new FriendsMgmtResponse();
        List<String> emailList = userRepo.findAllEmailList();

        if ( emailList != null )
        {
            response.getEmailList().addAll( emailList );
        }
        return response;
    }

    /**
     * Creates a friend connection between two email adress
     * 
     * <pre>
     * - throws EmailNotFoundException if email address is not registered
     * - throws FriendConnectionAlreadyExists if connection is already established
     * - throws UserBlockedException if user is blocked
     * </pre>
     */
    @Override
    public boolean createFriendConnection( final String fromEmailId, final String toEmailId )
            throws ApplicationException
    {
        UserProfile fromProfile = userRepo.findByEmailId( fromEmailId );

        if ( fromProfile == null )
        {
            throw new EmailNotFoundException( fromEmailId );
        }

        UserProfile toProfile = userRepo.findByEmailId( toEmailId );

        if ( toProfile == null )
        {
            throw new EmailNotFoundException( toEmailId );
        }

        FriendConnection friendConnection = connectionRepo
                .findByConnectionUUID1AndConnectionUUID2( fromProfile.getId(), toProfile.getId() );

        if ( friendConnection != null )
        {
            throw new FriendConnectionAlreadyExists( fromEmailId, toEmailId );
        }
        else
        {
            friendConnection = connectionRepo.findByConnectionUUID1AndConnectionUUID2(
                    toProfile.getId(), fromProfile.getId() );

            if ( friendConnection != null )
            {
                throw new FriendConnectionAlreadyExists( fromEmailId, toEmailId );
            }
        }

        FriendBlock block = blockRepo.findByProfileUUIDAndBlockedUUID( fromProfile.getId(),
                toProfile.getId() );

        if ( block != null )
        {
            throw new UserBlockedException( fromEmailId, toEmailId );
        }

        FriendConnection newFriendConnection = new FriendConnection( fromProfile.getId(),
                toProfile.getId() );

        try
        {
            connectionRepo.save( newFriendConnection );
        }
        catch ( Exception e )
        {
            LOGGER.error( e.getMessage() );
            throw new GeneralApplicationException(
                    String.format( "There was an error making friend connection between %s and %s",
                            fromEmailId, toEmailId ) );
        }

        return true;
    }

    /**
     * Returns the list of email adresses that the parameter is in connection
     * with
     */
    @Override
    public FriendsMgmtResponse getFriendConnections( final String emailId )
            throws ApplicationException
    {
        FriendsMgmtResponse response = new FriendsMgmtResponse();

        UserProfile userProfile = userRepo.findByEmailId( emailId );

        if ( userProfile == null )
        {
            throw new EmailNotFoundException( emailId );
        }

        response.setStatus( true );
        response.setFriends( findListOfFriends( userProfile ) );
        response.setCount( response.getFriends().size() );

        return response;
    }

    /**
     * Creates new friend subscription to news feeds
     */
    @Override
    public boolean createFriendSubscription( final String profileEmailId,
                                             final String subscriberEmailId )
            throws ApplicationException
    {

        UserProfile profile = userRepo.findByEmailId( profileEmailId );

        if ( profile == null )
        {
            throw new EmailNotFoundException( profileEmailId );
        }

        UserProfile subscriber = userRepo.findByEmailId( subscriberEmailId );

        if ( subscriber == null )
        {
            throw new EmailNotFoundException( subscriberEmailId );
        }

        FriendSubscriber newSubscription = new FriendSubscriber( profile.getId(),
                subscriber.getId() );

        try
        {
            subscriberRepo.save( newSubscription );
        }
        catch ( Exception e )
        {
            LOGGER.error( e.getMessage() );
            throw new GeneralApplicationException(
                    String.format( "There was an error making subscription from %s to %s",
                            profileEmailId, subscriberEmailId ) );
        }
        return true;
    }

    /**
     * Returns common friend email address between two emailIds
     */
    @Override
    public FriendsMgmtResponse getCommonFriendConnections( final String fromEmailId,
                                                           final String withEmailId )
            throws ApplicationException
    {
        FriendsMgmtResponse response = new FriendsMgmtResponse();

        UserProfile fromProfile = userRepo.findByEmailId( fromEmailId );

        if ( fromProfile == null )
        {
            throw new EmailNotFoundException( fromEmailId );
        }

        UserProfile withProfile = userRepo.findByEmailId( withEmailId );

        if ( withProfile == null )
        {
            throw new EmailNotFoundException( withEmailId );
        }
        response.setStatus( true );

        List<String> fromProfileFriendsList = findListOfFriends( fromProfile );
        List<String> withProfileFriendsList = findListOfFriends( withProfile );
        List<String> commonFriendsList = new ArrayList<>();

        for ( String fromProfileFriends : fromProfileFriendsList )
        {
            for ( String withProfileFriends : withProfileFriendsList )
            {
                if ( fromProfileFriends.equals( withProfileFriends ) )
                {
                    commonFriendsList.add( fromProfileFriends );
                }
            }
        }

        response.setFriends( commonFriendsList );

        response.setCount( response.getFriends().size() );
        return null;
    }

    /**
     * Returns the list of friends from FriendConnection table
     * 
     * @param profile
     * @return
     */
    private List<String> findListOfFriends( final UserProfile profile )
    {
        List<String> friendsList = new ArrayList<>();

        List<String> friendsWith = connectionRepo
                .findFriendListByConnectionUUID1( profile.getId() );

        if ( friendsWith != null )
        {
            friendsList.addAll( friendsWith );
        }

        List<String> friendsOf = connectionRepo.findFriendListByConnectionUUID2( profile.getId() );

        if ( friendsOf != null )
        {
            friendsList.addAll( friendsOf );
        }

        return friendsList;
    }

    /**
     * Block a friend connection, blocked friend will not be able to receive
     * updates (if they are already connected as friends) or cannot create new
     * connection with the requestor email
     */
    @Override
    public boolean blockFriendConnection( final String profileEmailId, final String blockedEmailId )
            throws ApplicationException
    {
        // TODO Auto-generated method stub

        UserProfile profile = userRepo.findByEmailId( profileEmailId );

        if ( profile == null )
        {
            throw new EmailNotFoundException( profileEmailId );
        }

        UserProfile blocked = userRepo.findByEmailId( blockedEmailId );

        if ( blocked == null )
        {
            throw new EmailNotFoundException( blockedEmailId );
        }

        FriendBlock newBlock = new FriendBlock( profile.getId(), blocked.getId() );

        try
        {
            blockRepo.save( newBlock );
        }
        catch ( Exception e )
        {
            LOGGER.error( e.getMessage() );
            throw new GeneralApplicationException(
                    String.format( "There was an error blocking subscription from %s to %s",
                            profileEmailId, blockedEmailId ) );
        }
        return true;
    }

    /**
     * Returns the list of email addresses which will be able to read posted
     * updates
     */
    @Override
    public FriendsMgmtResponse postUpdate( final String profileEmailId, final String text )
            throws ApplicationException
    {
        // TODO Auto-generated method stub
        List<String> updateList = new ArrayList<>();
        FriendsMgmtResponse response = new FriendsMgmtResponse();

        UserProfile profile = userRepo.findByEmailId( profileEmailId );

        if ( profile == null )
        {
            throw new EmailNotFoundException( profileEmailId );
        }
        List<String> friendList = findListOfFriends( profile );
        updateList.addAll( friendList );

        List<String> subscriberList = subscriberRepo.findListOfSubscribers( profile.getId() );
        if ( subscriberList != null )
        {
            for ( String subscriber : subscriberList )
            {
                if ( !friendList.contains( subscriber ) )
                {
                    updateList.add( subscriber );
                }
            }
        }

        List<String> emailIdsInMsg = extractEmailFromText( text );

        for ( String mentioned : emailIdsInMsg )
        {
            if ( !updateList.contains( mentioned ) )
            {
                updateList.add( mentioned );
            }
        }

        List<String> blockedList = blockRepo.findBlockedFriendsByUUID( profile.getId() );

        if ( blockedList != null )
        {
            updateList.removeAll( blockedList );
        }

        response.setStatus( true );
        response.setRecepients( updateList );

        return response;
    }

    /**
     * Returns the list of email addresses extracted from the message
     * 
     * @param msg
     * @return
     */
    private List<String> extractEmailFromText( final String msg )
    {
        List<String> emailIdList = new ArrayList<>();

        Pattern pattern = Pattern.compile( "[a-zA-Z0-9._%+-]*@[a-zA-Z0-9._%+-]*" );
        Matcher matcher = pattern.matcher( msg );
        if ( matcher.find() )
        {
            for ( int i = 0; i < matcher.groupCount(); i++ )
            {
                String emailId = matcher.group( i ).toLowerCase();
                UserProfile profile = userRepo.findByEmailId( emailId );
                if ( profile != null )
                {
                    emailIdList.add( emailId );
                }
            }
        }

        return emailIdList;
    }
}
