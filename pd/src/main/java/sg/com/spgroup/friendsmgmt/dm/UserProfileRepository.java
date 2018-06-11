package sg.com.spgroup.friendsmgmt.dm;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import sg.com.spgroup.friendsmgmt.pd.UserProfile;

/**
 * Handles all queries with regards to user profile
 * 
 * @author alvin
 */
public interface UserProfileRepository extends AbstractIdentifiableRepository<UserProfile>
{
    /**
     * Find user profile by email address
     * 
     * @param emailId
     *            Email address
     * @return UserProfile
     */
    UserProfile findByEmailId( final String emailId );

    /**
     * Lists registered email addresses
     * 
     * @return List of email address
     */
    @Query( "SELECT p.emailId FROM UserProfile p ORDER BY p.emailId DESC" )
    List<String> findAllEmailList();
}
