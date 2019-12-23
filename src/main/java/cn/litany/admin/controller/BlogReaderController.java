package cn.litany.admin.controller;

import cn.litany.admin.dto.blog.Blog;
import cn.litany.admin.service.BlogReader;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static cn.litany.admin.constant.BlogConstant.*;

/**
 * @author Litany
 */
@RestController
public class BlogReaderController {

    @Qualifier("nameReader")
    @Autowired
    private BlogReader blogReader;


    @RequestMapping("/draft")
    public List<Blog> getDraftBlog(String username) {
        return blogReader.getDraftBlog(username);
    }

    @RequestMapping("/official")
    public List<Blog> getOfficialBlog(String username) {
        return blogReader.getOfficialBlog(username);
    }

    @RequestMapping("/dateBlog")
    public Map<String, List<Blog>> getBlogDateGroup(HttpServletRequest request,String region) {
        String username = (String) request.getSession().getAttribute("username");
        Map<String, List<Blog>> dateBlogMap = null;
        if (ALL.equalsIgnoreCase(region)){
            dateBlogMap= blogReader.getAllBlogGroupByDate(username);
        }else if (OFFICIAL.equalsIgnoreCase(region)){
            dateBlogMap= blogReader.getOfficialBlogGroupByDate(username);
        }else if (DRAFT.equalsIgnoreCase(region)){
            dateBlogMap=blogReader.getDraftBlogGroupByDate(username);
        }

        return dateBlogMap;
    }


    @RequestMapping("/findBlog")
    public Blog findOfficialBlog(String username,String blogName,String region) {
        if (StringUtils.isBlank(region)){
            region=OFFICIAL;
        }
        return blogReader.getBlogByName(username,blogName,region);
    }

    @RequestMapping("/findDarBlog")
    public Blog findDraftBlog(String username,String blogName) {
        return blogReader.getBlogByName(username,blogName,DRAFT);
    }

    @RequestMapping("/newest")
    public List<Blog> getNewBlogList(String username,String region) {
        return blogReader.findNewBlogList(username,region);
    }


}
