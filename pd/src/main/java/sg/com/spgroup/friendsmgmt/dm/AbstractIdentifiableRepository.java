package sg.com.spgroup.friendsmgmt.dm;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import sg.com.spgroup.friendsmgmt.pd.AbstractIdentifiableEntity;

@NoRepositoryBean
public interface AbstractIdentifiableRepository<T extends AbstractIdentifiableEntity>
        extends CrudRepository<T, UUID>
{

}
