package sg.com.spgroup.friendsmgmt.api;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;

@Configuration
@EnableJpaRepositories( basePackages = "sg.com.spgroup.friendsmgmt" )
@EnableTransactionManagement
@EnableSpringConfigured
public class DBConfigTest implements TransactionManagementConfigurer
{
    private static final String[] HIBERNATE_PROP_NAMES = { "hibernate.dialect",
            "hibernate.format_sql", "hibernate.hbm2ddl.auto", "hibernate.implicit_naming_strategy",
            "hibernate.show_sql" };

    @Autowired
    private Environment env;

    @Autowired
    private ApplicationContext ctx;

    @Bean( name = "dbUnitDatabaseConfig" )
    public DatabaseConfigBean dbUnitDatabaseConfig()
    {
        DatabaseConfigBean config = new DatabaseConfigBean();
        String dialect = env.getRequiredProperty( "hibernate.dialect" );
        if ( dialect.contains( "PostgreSQL" ) )
        {
            config.setDatatypeFactory( new PostgresqlDataTypeFactory() );
        }
        else if ( dialect.contains( "H2" ) )
        {
            config.setDatatypeFactory( new H2DataTypeFactory() );
        }
        return config;
    }

    @Bean( name = "dbUnitDatabaseConnection" )
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection()
    {
        DatabaseDataSourceConnectionFactoryBean con = new DatabaseDataSourceConnectionFactoryBean(
                dataSource() );
        con.setDatabaseConfig( dbUnitDatabaseConfig() );
        return con;
    }

    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dm = new DriverManagerDataSource();
        dm.setDriverClassName( env.getRequiredProperty( "spring.datasource.driver-class-name" ) );
        dm.setUrl( env.getRequiredProperty( "spring.datasource.url" ) );
        dm.setUsername( env.getRequiredProperty( "spring.datasource.username" ) );
        dm.setPassword( env.getRequiredProperty( "spring.datasource.password" ) );

        // THIS IS IMPORTANT!!! Without this DbUnit and JPA won't run in the
        // same transaction, and things will go wrong. Badly.
        DataSource ds = new TransactionAwareDataSourceProxy( dm );
        return ds;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory()
    {
        System.out.println( "SimpleConfig::entityManagerFactory() start" );

        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter vend = new HibernateJpaVendorAdapter();
        vend.setGenerateDdl( true );

        String dialect = env.getRequiredProperty( "hibernate.dialect" );
        if ( dialect.contains( "PostgreSQL" ) )
        {
            vend.setDatabase( Database.POSTGRESQL );
        }
        else if ( dialect.contains( "H2" ) )
        {
            vend.setDatabase( Database.H2 );
        }

        bean.setDataSource( dataSource() );
        bean.setJpaVendorAdapter( vend );
        bean.setPackagesToScan( "sg.com.spgroup.friendsmgmt" );
        bean.setPersistenceUnitName( "projectname-pd" );
        bean.setJpaProperties( getRequired( HIBERNATE_PROP_NAMES ) );
        bean.afterPropertiesSet();

        EntityManagerFactory emf = bean.getObject();
        System.out.println( "SimpleConfig::entityManagerFactory() end: " + emf );
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager()
    {

        EntityManagerFactory emf = ctx.getBean( EntityManagerFactory.class );
        PlatformTransactionManager tx = new JpaTransactionManager( emf );
        System.out.println( "SimpleConfig::transactionManager() " + tx );
        return tx;
    }

    private Properties getRequired( String... names )
    {
        Properties props = new Properties();
        for ( String name : names )
        {
            props.put( name, env.getRequiredProperty( name ) );
        }

        props.put( "hibernate.cache.use_second_level_cache", "false" );
        props.put( "hibernate.cache.use_query_cache", "false" );
        props.put( "hibernate.cache.region.factory_class",
                "org.hibernate.cache.ehcache.EhCacheRegionFactory" );

        return props;
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager()
    {
        PlatformTransactionManager tx = ctx.getBean( PlatformTransactionManager.class );
        System.out.println( "SimpleConfig::annotationDrivenTransactionManager() " + tx );
        return tx;
    }

}
