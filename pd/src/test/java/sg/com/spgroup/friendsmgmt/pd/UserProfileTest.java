package sg.com.spgroup.friendsmgmt.pd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import sg.com.spgroup.friendsmgmt.dm.AbstractRepositoryTest;
import sg.com.spgroup.friendsmgmt.dm.Dump;
import sg.com.spgroup.friendsmgmt.dm.UserProfileRepository;
import sg.com.spgroup.friendsmgmt.pd.UserProfile;

@Ignore
public class UserProfileTest extends AbstractRepositoryTest
{
    @Autowired
    private UserProfileRepository stRepo;

    @Test
    @Transactional
    @DatabaseSetup( value = "/META-INF/dbtest/empty.xml" )
    @Rollback
    public void testGetEmptyUserProfileValuesWithNoParamExpectSuccess()
    {
        Iterable<UserProfile> allVal = stRepo.findAll();
        List<UserProfile> list = new ArrayList<>();
        allVal.forEach( list::add );
        assertTrue( list.isEmpty() );
    }

    @Test
    @Transactional
    @DatabaseSetup( value = "/META-INF/dbtest/projectname-app.xml" )
    @Rollback
    public void testGetAllUserProfileValuesWithNoParamExpectSuccess()
    {
        Iterable<UserProfile> allVal = stRepo.findAll();
        assertNotNull( allVal );
    }

    @Test
    @Transactional
    @DatabaseSetup( value = "/META-INF/dbtest/projectname-app.xml" )
    @Rollback
    public void testGetJohnFromUserProfileValuesWithNoParamExpectSuccess()
    {
        UserProfile john = stRepo.findByEmailId(Dump.EMAILID );
        assertEquals( Dump.NAME, john.getFullName() );
    }

    @Test
    @Transactional
    @DatabaseSetup( value = "/META-INF/dbtest/projectname-app.xml" )
    @Rollback
    public void testSaveNewUserProfileValuesWithAnnaRogueExpectSuccess()
    {
        UserProfile sample = new UserProfile( "johncena@spgroup.com.sg", "John Cena");
        stRepo.save( sample );

        UserProfile anna = stRepo.findByEmailId( "johncena@spgroup.com.sg" );
        assertEquals( "John Cena", anna.getFullName() );
    }

    @Test
    public void testCreateFullName()
    {
        UserProfile sample = new UserProfile( "mickeymouse@spgroup.com.sg", "Mickey Mouse");
        assertEquals( "Mickey Mouse", sample.getFullName() );
    }

}
