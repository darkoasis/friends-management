package sg.com.spgroup.friendsmgmt.api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({ DBConfigTest.class })
public class TestConfig {

}
