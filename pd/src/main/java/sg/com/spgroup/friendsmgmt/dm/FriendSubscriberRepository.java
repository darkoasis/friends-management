package sg.com.spgroup.friendsmgmt.dm;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import sg.com.spgroup.friendsmgmt.pd.FriendSubscriber;

/**
 * Handles all queries for subscriber
 * 
 * @author alvin
 */
public interface FriendSubscriberRepository extends AbstractIdentifiableRepository<FriendSubscriber>
{
    /**
     * Find friend subscriber by profile UUID and subscriberUUID
     * 
     * @param profileUUID
     *            UUID
     * @param subscriberUUID
     *            UUID
     * @return FriendSubscriber
     */
    FriendSubscriber findByProfileUUIDAndSubscriberUUID( final UUID profileUUID,
                                                         final UUID subscriberUUID );

    /**
     * Find registered email address of feed subscribers
     * 
     * @param uuid
     *            UUID
     * @return List of email adress
     */
    @Query( "SELECT u.emailId FROM FriendSubscriber s LEFT JOIN UserProfile u ON s.subscriberUUID = u.id WHERE s.profileUUID = ?1" )
    List<String> findListOfSubscribers( final UUID uuid );
}
