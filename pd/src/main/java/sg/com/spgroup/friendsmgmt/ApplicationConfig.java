package sg.com.spgroup.friendsmgmt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sg.com.spgroup.friendsmgmt.services.FriendManagementService;
import sg.com.spgroup.friendsmgmt.services.impl.FriendManagementServiceImpl;

@Configuration
public class ApplicationConfig
{
    @Bean
    public FriendManagementService friendManagementService()
    {
        return new FriendManagementServiceImpl();
    }
}
