package sg.com.spgroup.friendsmgmt.dm;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import sg.com.spgroup.friendsmgmt.pd.FriendSubscriber;

public interface FriendSubscriberRepository extends AbstractIdentifiableRepository<FriendSubscriber> {
    FriendSubscriber findByProfileUUIDAndSubscriberUUID(final UUID profileUUID, final UUID subsciberUUID);

    @Query("SELECT u.emailId FROM FriendSubscriber s LEFT JOIN UserProfile u ON s.subscriberUUID = u.id WHERE s.profileUUID = ?1")
    List<String> findListOfSubscribers(final UUID uuid);
}
