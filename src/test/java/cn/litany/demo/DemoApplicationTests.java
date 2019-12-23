package cn.litany.demo;

import cn.litany.admin.AdminApplication;
import cn.litany.admin.dto.blog.Blog;
import cn.litany.admin.dto.blog.BlogManager;
import cn.litany.admin.dto.blog.Context;
import cn.litany.admin.dto.blog.Top;
import cn.litany.admin.service.BlogDraftService;
import cn.litany.admin.service.BlogManagerService;
import cn.litany.admin.service.BlogOfficialService;
import cn.litany.admin.util.ConfigUtil;
import cn.litany.admin.util.GitUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AdminApplication.class)
class DemoApplicationTests {
    @Autowired
    BlogDraftService blogDraftService;

    @Autowired
    ConfigUtil configUtil;

    @Autowired
    GitUtil gitUtil;

    @Autowired
    BlogOfficialService blogOfficialService;

    @Autowired
    BlogManagerService blogManagerService;

    @Test
    void gitPull(){
        String username = configUtil.getUsername();
        gitUtil.pull(username,configUtil.getSshKeyPath(username));

    }

    @Test
    void deleteBlog() {
        BlogManager blogManager = new BlogManager();
        Blog blog = new Blog();
        Top top = new Top();
        top.setDate("2019-11-15");
        top.setTitle("测试");
        top.setTags("测试");
        top.setHeaderImg("img/litany-1.jpg");
        top.setAuthor("litany");
        top.setSubtitle("测试");
        blog.setTop(top);
        Context context = new Context();
        context.setContent("xxxooo");
        context.setNote("123");
        blog.setContext(context);
        blogManager.setBlog(blog);
        blogManager.setPath(configUtil.getUserOfficialPath("Litany-lty"));
        blogManagerService.delete(blogManager);


    }

    @Test
    public void createBlog(){
        BlogManager blogManager = new BlogManager();
        Blog blog = new Blog();
        Top top = new Top();
        top.setDate("2019-11-15");
        top.setTitle("测试");
        top.setTags("测试");
        top.setHeaderImg("img/litany-1.jpg");
        top.setAuthor("litany");
        top.setSubtitle("测试");
        blog.setTop(top);
        Context context = new Context();
        context.setContent("xxxooo");
        context.setNote("123");
        blog.setContext(context);
        blogManager.setBlog(blog);
        blogManager.setPath(configUtil.getUserOfficialPath("Litany-lty"));
        BlogManager edit = blogManagerService.edit(blogManager);
        BlogManager move = blogManagerService.move(edit);
        blogManagerService.publish(move);

    }

    @Test
    void testGit() {
        String sshKeyPath = configUtil.getSshKeyPath("Litany-lty");
        System.out.println(sshKeyPath);
        Boolean x = gitUtil.push("Litany-lty", sshKeyPath);
        System.out.println(x);

    }


    @Test
    void testGitClone() {
        String sshKeyPath = configUtil.getSshKeyPath("Litany-lty");
        System.out.println(sshKeyPath);
        Boolean x = gitUtil.cloneRepository("Litany-lty", sshKeyPath);
        System.out.println(x);

    }


}
