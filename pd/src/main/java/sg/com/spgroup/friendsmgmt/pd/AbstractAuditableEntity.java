package sg.com.spgroup.friendsmgmt.pd;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import sg.com.spgroup.friendsmgmt.annotations.JsonDateFormat;

/**
 * Extends from this class and it will add 2 fields(createdDate,lastModifiedDate
 * ) for auditing purpose.
 * 
 * <pre>
 *    Example: extends from this class if you want audit the activities.
 *
 *    public class Registration extends AbstractAuditableEntity {
 *
 *    }
 * </pre>
 * 
 * @author ursbrbalaji
 */

@MappedSuperclass
@EntityListeners( { AuditingEntityListener.class } )
public abstract class AbstractAuditableEntity extends AbstractIdentifiableEntity
{
    /**
     * The created date for audit
     */
    @Column( nullable = false, updatable = false )
    @CreatedDate
    @JsonDateFormat
    private Date createdDate = new Date();

    /**
     * The last modified date for audit
     */
    @Column( nullable = false )
    @LastModifiedDate
    @JsonDateFormat
    private Date lastModifiedDate = new Date();

    /**
     * Gets the created date.
     * 
     * @return the the created date
     */
    public Date getCreatedDate()
    {
        return this.createdDate == null ? null : new Date( createdDate.getTime() );
    }

    /**
     * Set the created date
     * 
     * @param date
     *            the value to set
     */
    public void setCreatedDate( final Date date )
    {
        this.createdDate = date == null ? null : new Date( date.getTime() );
    }

    /**
     * Gets the last modified date
     * 
     * @return the last modified date
     */
    public Date getLastModifiedDate()
    {
        return this.lastModifiedDate == null ? null : new Date( lastModifiedDate.getTime() );
    }

    /**
     * Set the last modified date
     * 
     * @param lastDate
     *            the value to set
     */
    public void setLastModifiedDate( final Date lastDate )
    {
        this.lastModifiedDate = ( lastDate == null ) ? null : new Date( lastDate.getTime() );
    }
}
