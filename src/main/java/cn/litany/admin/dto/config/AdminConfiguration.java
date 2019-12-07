package cn.litany.admin.dto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Litany
 */
@Configuration
@EnableConfigurationProperties({AdminProperties.class,BlogProperties.class})
public class AdminConfiguration{

}
