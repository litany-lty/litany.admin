package cn.litany.admin.util;

import cn.litany.admin.dto.config.AdminProperties;
import cn.litany.admin.dto.config.BlogProperties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static cn.litany.admin.constant.BlogConstant.*;


/**
 * @author Litany
 */
@Component
public class ConfigUtil {

    @Autowired
    private AdminProperties adminProperties;
    @Autowired
    private BlogProperties blogProperties;


    @PostConstruct
    public void init() {

        initAdminConfig();
        initBlogConfig();

    }

    private void initAdminConfig() {
        String username = adminProperties.getUsername();

        if (username == null || StringUtils.isBlank(username)) {
            adminProperties.setUsername(DEFAULT_USERNAME);
        }
        //todo 初始化时检验配置的gitHub ssh 是否可链接，否则抛出异常
    }

    private void initBlogConfig() {
        String filePath = blogProperties.getFilePath();
        if (StringUtils.isBlank(filePath)) {
            blogProperties.setFilePath(USER_HOME + SEPARATOR + BLOG + SEPARATOR + USER_NAME + SEPARATOR + USER_NAME + DEFAULT_GITHUB);
        } else {
            blogProperties.setFilePath(filePath + SEPARATOR + BLOG + SEPARATOR + USER_NAME + SEPARATOR + USER_NAME + DEFAULT_GITHUB);
        }
        String officialFilePath = blogProperties.getOfficialFilePath();
        if (StringUtils.isBlank(officialFilePath)) {
            blogProperties.setOfficialFilePath(blogProperties.getFilePath() + SEPARATOR + DEFAULT_OFFICIAL_NAME);
        }

        String draftFilePath = blogProperties.getDraftFilePath();
        if (StringUtils.isBlank(draftFilePath)) {
            String path = blogProperties.getFilePath();
            String[] split = path.split(TM + SEPARATOR);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < split.length - 1; i++) {
                stringBuilder.append(split[i]).append(SEPARATOR);
            }
            path = stringBuilder.toString();
            blogProperties.setDraftFilePath(path + DEFAULT_DRAFT_NAME);
        }

    }

    public static String getSeparator() {
        return SEPARATOR;
    }

    public String getFilePath() {
        return blogProperties.getFilePath();
    }

    /**
     * 获取管理员的路径（配置文件中的username即为管理员，仅有一个,永久存在）
     */
    public String getAdminPath() {
        return getPath(blogProperties.getFilePath(), BLANK);
    }

    public String getAdminOfficialPath() {
        return getPath(blogProperties.getOfficialFilePath(), BLANK);
    }

    public String getAdminDraftPath() {
        return getPath(blogProperties.getDraftFilePath(), BLANK);
    }


    /**
     * 获取用户路径（通过注册，存入数据库用户表的用户），30分钟自动移除
     */
    public String getUserPath(String userName) {
        if (adminProperties.getUsername().equalsIgnoreCase(userName)) {
            return getPath((getAdminPath()));
        }
        return getPath(blogProperties.getFilePath(), userName);
    }

    public String getUserOfficialPath(String userName) {
        if (adminProperties.getUsername().equalsIgnoreCase(userName)) {
            return getPath((getAdminOfficialPath()));
        }
        return getPath(blogProperties.getOfficialFilePath(), userName);
    }

    public String getUserDraftPath(String userName) {
        if (adminProperties.getUsername().equalsIgnoreCase(userName)) {
            return getPath((getAdminDraftPath()));
        }
        return getPath(blogProperties.getDraftFilePath(), userName);
    }

    public String getSshKeyPath(String username) {


        String userPath = getUserPath(username) + SEPARATOR + SSH_DIR + SEPARATOR + SSH_FILE;
        File file = new File(userPath);
        if (file.exists() && file.isFile()) {
            return userPath;
        } else {
            try {
                if (adminProperties.getGithubSshKey() == null || StringUtils.isBlank(adminProperties.getGithubSshKey())) {
                    return USER_HOME + SEPARATOR + SSH_DIR + SEPARATOR + SSH_FILE;
                }
                FileUtils.write(file, adminProperties.getGithubSshKey(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                try {
                    userPath = userPath + SEPARATOR + BASE + SEPARATOR + SSH_FILE;
                    FileUtils.write(new File(userPath), adminProperties.getGithubSshKey(), StandardCharsets.UTF_8);
                } catch (IOException ex) {
                    return null;
                }
            }
            return userPath;
        }

    }

    public static String getPath(String path) {
        String[] routes = new String[0];
        if (path.contains(SEP_A)) {
            routes = path.split(SEP_A);
        } else if (path.contains(SEP_B)) {
            routes = path.split(SEP_B);
        } else if (path.contains(SEP_D)) {
            routes = path.split(SEP_C);
        }
        StringBuilder newPath = new StringBuilder();
        for (String route : routes) {
            newPath.append(route).append(SEPARATOR);
        }
        return newPath.toString();

    }


    private String getPath(String path, String userName) {
        path = getPath(path);
        if (path.endsWith(SEPARATOR)) {
            path = path.substring(0, path.length() - 1);
        }
        if (StringUtils.isBlank(userName)) {
            return path.replace(USER_NAME, adminProperties.getUsername());
        } else {
            return path.replace(USER_NAME, userName);
        }
    }

    public String getUsername() {
        return adminProperties.getUsername();
    }
}
