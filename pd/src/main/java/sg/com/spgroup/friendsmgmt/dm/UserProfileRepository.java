package sg.com.spgroup.friendsmgmt.dm;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import sg.com.spgroup.friendsmgmt.pd.UserProfile;

public interface UserProfileRepository extends AbstractIdentifiableRepository<UserProfile> {
	UserProfile findByEmailId(final String emailId);
	
	@Query("SELECT p.emailId FROM UserProfile p ORDER BY p.emailId DESC")
	List<String> findAllEmailList();
}
