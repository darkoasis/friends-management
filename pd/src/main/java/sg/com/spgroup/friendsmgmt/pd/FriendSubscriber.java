package sg.com.spgroup.friendsmgmt.pd;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table( uniqueConstraints = {
        @UniqueConstraint( columnNames = { "profileUUID", "subscriberUUID" } ) }, indexes = {
                @Index( name = "subscribedByProfileUUID", columnList = "profileUUID" ),
                @Index( name = "bySubscriberUUID", columnList = "subscriberUUID" ),
                @Index( name = "byProfileAndSubscriberUUID", columnList = "profileUUID,subscriberUUID" ) } )
public class FriendSubscriber extends AbstractAuditableEntity
{
    private UUID profileUUID;
    private UUID subscriberUUID;

    public FriendSubscriber()
    {
    }

    public FriendSubscriber( final UUID profileUUID, final UUID subscriberUUID )
    {
        this();
        this.profileUUID = profileUUID;
        this.subscriberUUID = subscriberUUID;
    }

    public UUID getProfileUUID()
    {
        return profileUUID;
    }

    public void setProfileUUID( final UUID profileUUID )
    {
        this.profileUUID = profileUUID;
    }

    public UUID getSubscriberUUID()
    {
        return subscriberUUID;
    }

    public void setSubscriberUUID( final UUID subscriberUUID )
    {
        this.subscriberUUID = subscriberUUID;
    }

}
