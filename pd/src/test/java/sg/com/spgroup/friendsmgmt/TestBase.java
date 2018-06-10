package sg.com.spgroup.friendsmgmt;

import static org.junit.Assert.assertTrue;

import javax.validation.Validator;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
@ContextConfiguration( classes = { TestConfig.class } )
@ActiveProfiles( "test" )
@Ignore
public class TestBase
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    protected Validator validator;

    @Test
    public void testSample()
    {
        assertTrue( true );
    }

}
