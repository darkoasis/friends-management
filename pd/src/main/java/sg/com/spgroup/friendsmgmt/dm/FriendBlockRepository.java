package sg.com.spgroup.friendsmgmt.dm;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import sg.com.spgroup.friendsmgmt.pd.FriendBlock;

public interface FriendBlockRepository extends AbstractIdentifiableRepository<FriendBlock> {
    FriendBlock findByProfileUUIDAndBlockedUUID(final UUID profileUUID, final UUID blockedUUID);

    @Query("SELECT u.emailId FROM FriendBlock f LEFT JOIN UserProfile u ON f.blockedUUID = u.id WHERE f.profileUUID = ?1")
    List<String> findBlockedFriendsByUUID(UUID id);
}
