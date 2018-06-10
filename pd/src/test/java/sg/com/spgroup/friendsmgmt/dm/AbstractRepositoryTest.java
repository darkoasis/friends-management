package sg.com.spgroup.friendsmgmt.dm;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dbunit.database.IDatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;

import sg.com.spgroup.friendsmgmt.TestBase;

@TestExecutionListeners( { //
        DependencyInjectionTestExecutionListener.class, //
        DirtiesContextTestExecutionListener.class, //
        TransactionDbUnitTestExecutionListener.class } )
public abstract class AbstractRepositoryTest extends TestBase
{

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    protected IDatabaseConnection idc;

}
