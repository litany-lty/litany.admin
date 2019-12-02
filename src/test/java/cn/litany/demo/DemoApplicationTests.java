package cn.litany.demo;

import cn.litany.admin.AdminApplication;
import cn.litany.admin.dto.blog.Blog;
import cn.litany.admin.dto.blog.BlogManager;
import cn.litany.admin.dto.blog.Top;
import cn.litany.admin.service.BlogDraftService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AdminApplication.class )
class DemoApplicationTests {
    @Autowired
    BlogDraftService blogDraftService;
    @Test
    void contextLoads() {
        BlogManager blogManager = new BlogManager();
        Blog blog = new Blog();
        Top top = new Top();
        top.setDate("2019-11-14");
        top.setTitle("测试");
        blog.setTop(top);
        blogManager.setBlog(blog);
        blogManager.setPath("D:\\e\\xx");
        blogDraftService.createDraft(blogManager);
    }

}
