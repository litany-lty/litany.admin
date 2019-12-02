package cn.litany.admin.dto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Litany
 */
@Configuration
@EnableConfigurationProperties(AdminProperties.class)
@ConditionalOnProperty(prefix = "spring.admin", name = "enabled", matchIfMissing = true)
public class AdminConfiguration extends BlogConfiguration{

    @Autowired
    private AdminProperties adminProperties;


}
