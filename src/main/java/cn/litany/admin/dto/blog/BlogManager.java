package cn.litany.admin.dto.blog;


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
}
