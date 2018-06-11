package sg.com.spgroup.friendsmgmt.dm;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import sg.com.spgroup.friendsmgmt.pd.FriendConnection;

/**
 * Handles all queries with regards to friend connection
 * 
 * @author alvin
 */
public interface FriendConnectionRepository extends AbstractIdentifiableRepository<FriendConnection>
{
    /**
     * Find requestor initiated connections
     * 
     * @param connectionUUID1
     *            UUID
     * @return FriendConnection
     **/
    List<FriendConnection> findByConnectionUUID1( final UUID connectionUUID1 );

    /**
     * Find friend initiated connections
     * 
     * @param connectionUUID2
     *            UUID
     * @return FriendConnection
     */
    List<FriendConnection> findByConnectionUUID2( final UUID connectionUUID2 );

    /**
     * Find friend connection given both UUID
     * 
     * @param connectionUUID1
     *            UUID
     * @param connectionUUID2
     *            UUID
     * @return FriendConnection
     */
    FriendConnection findByConnectionUUID1AndConnectionUUID2( final UUID connectionUUID1,
                                                              final UUID connectionUUID2 );

    /**
     * Find registered email address the requestor initiated for connection
     * 
     * @param connectionUUID1
     *            UUID
     * @return List of email adress
     */
    @Query( "SELECT u.emailId FROM FriendConnection f LEFT JOIN UserProfile u ON f.connectionUUID2 = u.id WHERE f.connectionUUID1 = ?1" )
    List<String> findFriendListByConnectionUUID1( final UUID connectionUUID1 );

    /**
     * Find registered email address of friend initiated connections
     * 
     * @param connectionUUID2
     *            UUID
     * @return List of email adress
     */
    @Query( "SELECT u.emailId FROM FriendConnection f LEFT JOIN UserProfile u ON f.connectionUUID1 = u.id WHERE f.connectionUUID2 = ?1" )
    List<String> findFriendListByConnectionUUID2( final UUID connectionUUID2 );
}
