package sg.com.spgroup.friendsmgmt.api.doc;

import static springfox.documentation.builders.PathSelectors.ant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.base.Predicates;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@Import( BeanValidatorPluginsConfiguration.class )
public class SwaggerConfig
{
    @SuppressWarnings( "unchecked" )
    @Bean
    public Docket restApi()
    {
        return new Docket( DocumentationType.SWAGGER_2 ).useDefaultResponseMessages( false )
                .apiInfo( apiInfo() ).select()
                .paths( Predicates.and( ant( "/**" ), Predicates.not( ant( "/error" ) ),
                        Predicates.not( ant( "/management/**" ) ),
                        Predicates.not( ant( "/management*" ) ) ) )
                .build();
    }

    private ApiInfo apiInfo()
    {
        String version = System.getProperty( "sg.com.spgroup.friendsmgmt.api.version" );
        return new ApiInfoBuilder().title( "Friend Management REST APIs" )
                .description( "REST APIs for Friend Management." )
                .contact( new Contact( "Alvin Flores", "http://www.domain.com",
                        "alvinjavierflores@gmail.com" ) )
                .license( "Open" ).licenseUrl( "http://www.domain.com" ).version( version ).build();
    }
}
