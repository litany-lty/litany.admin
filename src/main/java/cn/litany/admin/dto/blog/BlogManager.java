package cn.litany.admin.dto.blog;


import cn.litany.admin.util.ConfigUtil;
import org.apache.commons.lang.StringUtils;
import static cn.litany.admin.constant.BlogConstant.*;

/**
 * @author Litany
 */
public class BlogManager {
    private String username;
    private Blog blog;
    private String path;
    private String status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Blog getBlog() {
        return blog;
    }

    public String getBlogContext() {
        return blog.getBlog();
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDraft() {
        String separator = ConfigUtil.getSeparator();
        String[] split = path.split(TM+separator);
        String s = split[split.length - 1];
        return StringUtils.containsIgnoreCase(s,DRAFT);
    }

    public String getFileName(){
        Top top = this.blog.getTop();
        return  (top.getDate()) + AND + (top.getTitle()) + (MARKDOWN_TYPE);
    }
}
