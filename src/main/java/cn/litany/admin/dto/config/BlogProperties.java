package cn.litany.admin.dto.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * @author Litany
 */
@ConfigurationProperties(prefix = "spring.blog")
@ConditionalOnProperty(prefix ="spring.blog" ,name = "enabled", matchIfMissing = true)
public class BlogProperties {
    private String filePath;
    private String officialFilePath;
    private String draftFilePath;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getOfficialFilePath() {
        return officialFilePath;
    }

    public void setOfficialFilePath(String officialFilePath) {
        this.officialFilePath = officialFilePath;
    }

    public String getDraftFilePath() {
        return draftFilePath;
    }

    public void setDraftFilePath(String draftFilePath) {
        this.draftFilePath = draftFilePath;
    }
}
