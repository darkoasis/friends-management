package sg.com.spgroup.friendsmgmt;

import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableSpringConfigured
@Import(DBConfigTest.class)
@ComponentScan(basePackages = { "sg.com.spgroup.friendsmgmt" })
public class TestConfig {
    @Bean
    public Validator validator() {
	return new LocalValidatorFactoryBean();
    }
}
