package sg.com.spgroup.friendsmgmt.dm;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import sg.com.spgroup.friendsmgmt.pd.FriendConnection;

public interface FriendConnectionRepository extends AbstractIdentifiableRepository<FriendConnection> {

    List<FriendConnection> findByConnectionUUID1(final UUID connectionUUID1);

    List<FriendConnection> findByConnectionUUID2(final UUID connectionUUID2);

    FriendConnection findByConnectionUUID1AndConnectionUUID2(final UUID connectionUUID1, final UUID connectionUUID2);

    @Query("SELECT u.emailId FROM FriendConnection f LEFT JOIN UserProfile u ON f.connectionUUID2 = u.id WHERE f.connectionUUID1 = ?1")
    List<String> findFriendListByConnectionUUID1(final UUID connectionUUID1);

    @Query("SELECT u.emailId FROM FriendConnection f LEFT JOIN UserProfile u ON f.connectionUUID1 = u.id WHERE f.connectionUUID2 = ?1")
    List<String> findFriendListByConnectionUUID2(final UUID connectionUUID2);
}
