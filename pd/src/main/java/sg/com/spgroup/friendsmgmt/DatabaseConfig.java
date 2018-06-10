package sg.com.spgroup.friendsmgmt;

import java.security.InvalidParameterException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.cfg.AvailableSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Profile("!test")
@Configuration
@EnableJpaAuditing
public class DatabaseConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);

    @Autowired
    private Environment env;

    /**
     * Hibernate properties
     */
    private static final String[] HIBERNATE_PROP_NAMES = { AvailableSettings.DIALECT,
	    AvailableSettings.IMPLICIT_NAMING_STRATEGY, AvailableSettings.HBM2DDL_AUTO, AvailableSettings.FORMAT_SQL,
	    AvailableSettings.SHOW_SQL, AvailableSettings.GENERATE_STATISTICS, AvailableSettings.USE_QUERY_CACHE,
	    AvailableSettings.USE_SECOND_LEVEL_CACHE, AvailableSettings.CACHE_REGION_FACTORY };

    // --------------------------------------------------------------------------------------------

    private static Properties getJpaProperties(Environment env, String... requiredNames) {
	Properties props = new Properties();
	for (String name : requiredNames) {
	    String value = env.getRequiredProperty(name);
	    LOGGER.info("Found property:'{}' value:'{}'", name, value);
	    props.put(name, value);
	}
	return props;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
	// Get the JDBC username and password.
	String usr = env.getProperty("spring.datasource.username");
	String pwd = env.getProperty("spring.datasource.password");

	if (StringUtils.isBlank(usr) || StringUtils.isBlank(pwd)) {
	    throw new InvalidParameterException("no db user or password provided");
	}

	DataSourceBuilder factory = DataSourceBuilder.create().username(usr).password(pwd);

	DataSource ds = factory.build();

	// For debugging.
	LOGGER.info("DataSource class is {}", ds.getClass().getName());

	return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
	HibernateJpaVendorAdapter vend = new HibernateJpaVendorAdapter();
	vend.setGenerateDdl(true);

	String dialect = env.getRequiredProperty("hibernate.dialect");
	if (dialect.contains("PostgreSQL")) {
	    vend.setDatabase(Database.POSTGRESQL);
	}

	bean.setDataSource(dataSource());
	bean.setJpaVendorAdapter(vend);
	bean.setPackagesToScan("sg.com.spgroup.friendsmgmt");
	bean.setPersistenceUnitName("friendsmgmt-pd");
	bean.setJpaProperties(getJpaProperties(env, HIBERNATE_PROP_NAMES));

	bean.afterPropertiesSet();
	return bean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
	return new JpaTransactionManager(entityManagerFactory().getObject());
    }

}
