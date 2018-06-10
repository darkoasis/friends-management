package sg.com.spgroup.friendsmgmt.api;

import java.io.FileOutputStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;

import sg.com.spgroup.friendsmgmt.dm.UserProfileRepository;
import sg.com.spgroup.friendsmgmt.pd.UserProfile;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
@ContextConfiguration( classes = { TestConfig.class } )
@ActiveProfiles( "test" )
@TestExecutionListeners( { //
        DependencyInjectionTestExecutionListener.class, //
        DirtiesContextTestExecutionListener.class, //
        TransactionDbUnitTestExecutionListener.class } )
/**
 * <code>Dump<code> is used to create persistence test data
 */
@Ignore
public class Dump
{
    public static final String FLAT_XML_OUTPUT_PATH = "src/test/resources/META-INF/dbtest/";

    public static final String EMAILID = "johndoe@spgroup.com.sg";

    public static final String NAME = "John Doe";

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    protected IDatabaseConnection idc;

    @Autowired
    protected UserProfileRepository strepo;

    @Test
    @Transactional
    @Commit
    public void testCreateEmpty() throws Exception
    {
        ITableFilter flt = new DatabaseSequenceFilter( idc );
        IDataSet ids = idc.createDataSet();
        IDataSet all = new FilteredDataSet( flt, ids );
        FlatXmlDataSet.write( all, new FileOutputStream( FLAT_XML_OUTPUT_PATH + "empty.xml" ) );
    }

    @Test
    @Transactional
    @Commit
    public void testCreateExpessApplicaton() throws Exception
    {
        UserProfile sample = new UserProfile( EMAILID, NAME );
        strepo.save( sample );

        em.flush();

        ITableFilter flt = new DatabaseSequenceFilter( idc );
        IDataSet ids = idc.createDataSet();
        IDataSet all = new FilteredDataSet( flt, ids );
        FlatXmlDataSet.write( all,
                new FileOutputStream( FLAT_XML_OUTPUT_PATH + "projectname-app.xml" ) );
    }
}
