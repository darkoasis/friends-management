package sg.com.spgroup.friendsmgmt.pd;

import java.util.UUID;

import javax.persistence.Entity;

@Entity
public class FriendConnection extends AbstractAuditableEntity {
    private UUID connectionUUID1;
    private UUID connectionUUID2;

    public FriendConnection() {
	// TODO Auto-generated constructor stub
    }

    public FriendConnection(final UUID connectionUUID1, final UUID connectionUUID2) {
	this();
	this.connectionUUID1 = connectionUUID1;
	this.connectionUUID2 = connectionUUID2;
    }

    public UUID getConnectionUUID1() {
	return connectionUUID1;
    }

    public void setConnectionUUID1(final UUID connectionUUID1) {
	this.connectionUUID1 = connectionUUID1;
    }

    public UUID getConnectionUUID2() {
	return connectionUUID2;
    }

    public void setConnectionUUID2(final UUID connectionUUID2) {
	this.connectionUUID2 = connectionUUID2;
    }
}
