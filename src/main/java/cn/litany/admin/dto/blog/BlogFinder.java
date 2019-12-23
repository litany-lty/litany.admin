package cn.litany.admin.dto.blog;
/**
 * 通过该对象与前端交互需要寻找具体blog
 * @author Litany
 * @date 2019/12/19 17:06
 **/
public class BlogFinder {
    private String date;
    private String region;
    private String key;
    private Blog blog;
    private String blogPath;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public String getBlogPath() {
        return blogPath;
    }

    public void setBlogPath(String blogPath) {
        this.blogPath = blogPath;
    }
}
