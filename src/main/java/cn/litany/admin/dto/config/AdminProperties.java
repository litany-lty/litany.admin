package cn.litany.admin.dto.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Litany
 */
@ConfigurationProperties(prefix = "spring.admin")
@ConditionalOnProperty(prefix = "spring.admin", name = "enabled", matchIfMissing = true)
@Component
public class AdminProperties {
    private String username;
    private String githubSshKey;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGithubSshKey() {
        return githubSshKey;
    }

    public void setGithubSshKey(String githubSshKey) {
        this.githubSshKey = githubSshKey;
    }
}
