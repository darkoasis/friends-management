package sg.com.spgroup.friendsmgmt.api.doc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sg.com.spgroup.friendsmgmt.api.AbstractControllerTest;
@Ignore
@AutoConfigureRestDocs( outputDir = "build/asciidoc/snippets" )
public class Swagger2MarkupTest extends AbstractControllerTest
{

    private static final Logger logger = LoggerFactory.getLogger( Swagger2MarkupTest.class );

    @Autowired
    private WebApplicationContext wctx;

    private MockMvc mock;

    @Before
    public void setup()
    {
        this.mock = MockMvcBuilders.webAppContextSetup( this.wctx ).build();
    }

    @Test
    public void createSpringfoxSwaggerJson() throws Exception
    {

        String outputDir = System.getProperty( "io.springfox.staticdocs.outputDir" );
        logger.info( "swagger outputDir {}", outputDir );

        MvcResult mvcResult = this.mock
                .perform( get( "/v2/api-docs" ).accept( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() ).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        String swaggerJson = response.getContentAsString();

        Files.createDirectories( Paths.get( outputDir ) );

        try( BufferedWriter writer = Files.newBufferedWriter(
                Paths.get( outputDir, "swagger.json" ), StandardCharsets.UTF_8 ) )
        {
            writer.write( swaggerJson );
        }
    }
}
