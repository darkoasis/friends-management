package sg.com.spgroup.friendsmgmt.pd;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table( uniqueConstraints = {
        @UniqueConstraint( columnNames = { "profileUUID", "blockedUUID" } ) }, indexes = {
                @Index( name = "byProfileUUID", columnList = "profileUUID" ) } )
public class FriendBlock extends AbstractAuditableEntity
{
    private UUID profileUUID;
    private UUID blockedUUID;

    public FriendBlock()
    {
    }

    public FriendBlock( final UUID profileUUID, final UUID blokedUUID )
    {
        this();
        this.profileUUID = profileUUID;
        this.blockedUUID = blokedUUID;
    }

    public UUID getProfileUUID()
    {
        return profileUUID;
    }

    public void setProfileUUID( final UUID profileUUID )
    {
        this.profileUUID = profileUUID;
    }

    public UUID getBlockedUUID()
    {
        return blockedUUID;
    }

    public void setBlockedUUID( final UUID blockedUUID )
    {
        this.blockedUUID = blockedUUID;
    }

}
