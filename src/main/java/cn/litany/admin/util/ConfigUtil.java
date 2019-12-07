package cn.litany.admin.util;

import cn.litany.admin.dto.config.AdminProperties;
import cn.litany.admin.dto.config.BlogProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Litany
 */
@Component
public class ConfigUtil {
    @Autowired
    private static BlogProperties blogProperties;
    @Autowired
    private static AdminProperties adminProperties;

    private static String userHome = System.getProperty("user.home");

    private static String separator = System.getProperty("file.separator");

    private static String USER_NAME="<USER_NAME>";

    private static String Blank="";


    @PostConstruct
    public void init() {

        initAdminConfig();
        initBlogConfig();

    }

    private void initAdminConfig() {
        String username = adminProperties.getUsername();
        if (StringUtils.isBlank(username)) {
            adminProperties.setUsername("BlogAdmin");
        }
        //todo 初始化时检验配置的gitHub ssh 是否可链接，否则抛出异常
    }

    private void initBlogConfig() {
        String filePath = blogProperties.getFilePath();
        if (StringUtils.isBlank(filePath)) {
            blogProperties.setFilePath(userHome+separator+"blog");
        }else {
            blogProperties.setFilePath(filePath+separator+"blog");
        }
        String officialFilePath = blogProperties.getOfficialFilePath();
        if (StringUtils.isBlank(officialFilePath)) {
            blogProperties.setOfficialFilePath(blogProperties.getFilePath() + separator + USER_NAME);
        }

        String draftFilePath = blogProperties.getDraftFilePath();
        if (StringUtils.isBlank(draftFilePath)) {
            String defaultDraftName = "BlogDraft";
            blogProperties.setDraftFilePath(blogProperties.getOfficialFilePath() + separator + defaultDraftName);
        }

    }

    public static String getFilePath(){
        return blogProperties.getFilePath();
    }

    /**
     * 获取管理员的路径（配置文件中的username即为管理员，仅有一个,永久存在）
     */
    public static String getAdminPath() {
        return getPath(blogProperties.getFilePath()+separator+USER_NAME,Blank);
    }
    public static String getAdminOfficialPath() {
        return getPath(blogProperties.getOfficialFilePath(),Blank);
    }
    public static String getAdminDraftPath() {
        return getPath(blogProperties.getDraftFilePath(),Blank);
    }



    /**
     * 获取用户路径（通过注册，存入数据库用户表的用户），30分钟自动移除
     */
    public static String getUserPath(String userName) {
        return getPath(blogProperties.getFilePath()+separator+USER_NAME,userName);
    }
    public static String getUserOfficialPath(String userName) {
        return getPath(blogProperties.getOfficialFilePath(),userName);
    }
    public static String getUserDraftPath(String userName) {
        return getPath(blogProperties.getDraftFilePath(),userName);
    }


    private static String getPath(String path ,String userName){
        if (StringUtils.isBlank(userName)){
            return path.replace(USER_NAME,adminProperties.getUsername());
        }else {
            return path.replace(USER_NAME,userName);
        }
    }

}
