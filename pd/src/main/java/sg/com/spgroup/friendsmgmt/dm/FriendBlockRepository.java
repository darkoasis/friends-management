package sg.com.spgroup.friendsmgmt.dm;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import sg.com.spgroup.friendsmgmt.pd.FriendBlock;

/**
 * Handles all queries regarding to friend blocked connection
 * 
 * @author alvin
 */
public interface FriendBlockRepository extends AbstractIdentifiableRepository<FriendBlock>
{
    /**
     * Find blocked profile by requestor profile UUID and blocked profile UUID
     * 
     * @param profileUUID
     *            UUID
     * @param blockedUUID
     *            UUID
     * @return FriendBlock
     */
    FriendBlock findByProfileUUIDAndBlockedUUID( final UUID profileUUID, final UUID blockedUUID );

    /**
     * Lists blocked email adresses of a given profile UUID
     * 
     * @param id
     *            UUID
     * @return List of email adress
     */
    @Query( "SELECT u.emailId FROM FriendBlock f LEFT JOIN UserProfile u ON f.blockedUUID = u.id WHERE f.profileUUID = ?1" )
    List<String> findBlockedFriendsByUUID( UUID id );
}
