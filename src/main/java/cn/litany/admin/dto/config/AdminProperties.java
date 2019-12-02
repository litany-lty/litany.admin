package cn.litany.admin.dto.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * @author Litany
 */
@ConfigurationProperties(prefix = "spring.admin")
public class AdminProperties {
    private String blogOfficialFilePath;
    @PostConstruct
    public void init()
    {
        System.out.println(blogOfficialFilePath);
    }

    public String getBlogOfficialFilePath() {
        return blogOfficialFilePath;
    }

    public void setBlogOfficialFilePath(String blogOfficialFilePath) {
        this.blogOfficialFilePath = blogOfficialFilePath;
    }
}
