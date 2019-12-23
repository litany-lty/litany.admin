package cn.litany.admin.service.impl;

import cn.litany.admin.dto.blog.Blog;
import cn.litany.admin.dto.blog.Top;
import cn.litany.admin.service.BlogReader;
import cn.litany.admin.util.BlogUtil;
import cn.litany.admin.util.ConfigUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.litany.admin.constant.BlogConstant.*;

/**
 * 博客获取服务类
 *
 * @author Litany
 */
@Service("blogReader")
public class BlogReaderImpl implements BlogReader {

    @Autowired
    private ConfigUtil configUtil;

    @Override
    public List<Blog> getDraftBlog(String username) {
        String userDraftPath = configUtil.getUserDraftPath(username);
        return BlogUtil.getBlogListByDirPath(userDraftPath);
    }

    @Override
    public List<Blog> getOfficialBlog(String username) {
        String userOfficialPath = configUtil.getUserOfficialPath(username);
        return BlogUtil.getBlogListByDirPath(userOfficialPath);
    }

    @Override
    public List<Blog> getAllBlog(String username) {
        List<Blog> blogAll = getDraftBlog(username);
        blogAll.addAll(getOfficialBlog(username));
        return blogAll;
    }

    @Override
    public List<Blog> getDraftBlogByKey(String username, String key) {
        List<Blog> draftBlog = getDraftBlog(username);
        return findInBlogListByKey(draftBlog, key);
    }

    @Override
    public List<Blog> getOfficialBlogByKey(String username, String key) {
        List<Blog> officialBlog = getOfficialBlog(username);
        return findInBlogListByKey(officialBlog, key);
    }

    @Override
    public List<Blog> getAllBlogByKey(String username, String key) {
        List<Blog> allBlog = getAllBlog(username);
        return findInBlogListByKey(allBlog, key);
    }

    @Override
    public Map<String, List<Blog>> getDraftBlogGroupByDate(String username) {
        List<Blog> draftBlog = getDraftBlog(username);
        return BlogUtil.findInBlogListByDate(draftBlog);
    }

    @Override
    public Map<String, List<Blog>> getOfficialBlogGroupByDate(String username) {
        List<Blog> officialBlog = getOfficialBlog(username);
        return BlogUtil.findInBlogListByDate(officialBlog);
    }

    @Override
    public Map<String, List<Blog>> getAllBlogGroupByDate(String username) {
        List<Blog> allBlog = getAllBlog(username);
        return BlogUtil.findInBlogListByDate(allBlog);
    }

    @Override
    public Blog getBlogByName(String username, String blogName, String field) {
        List<Blog> blogList = null;
        if (StringUtils.isNotBlank(field) && StringUtils.equalsIgnoreCase(field, OFFICIAL)) {
            blogList = getOfficialBlog(username);
        } else if (StringUtils.isNotBlank(field) && StringUtils.equalsIgnoreCase(field, DRAFT)) {
            blogList = getDraftBlog(username);
        }else if (StringUtils.isNotBlank(field)&&StringUtils.equalsIgnoreCase(field,ALL)){
            blogList=getAllBlog(username);
        }
        if (blogList == null) {
            return null;
        }
        for (Blog blog : blogList) {
            if (blog.getTitle().equalsIgnoreCase(blogName)) {
                return blog;
            }
        }
        return null;
    }

    @Override
    public List<Blog> findNewBlogList(String username,String region) {
        Map<String, List<Blog>> blogGroupByDate=new HashMap<>(10);
        if (StringUtils.isBlank(region)|| OFFICIAL.equalsIgnoreCase(region)){

            blogGroupByDate= getOfficialBlogGroupByDate(username);
        }else if (DRAFT.equalsIgnoreCase(region)){
            blogGroupByDate= getDraftBlogGroupByDate(username);
        }
        return BlogUtil.getNewBlogListByNum(blogGroupByDate,10);
    }


    private List<Blog> findInBlogListByKey(List<Blog> blogList, String key) {
        List<Blog> blogKeyList = new ArrayList<Blog>();
        for (Blog blog : blogList) {
            Top top = blog.getTop();
            String title = top.getTitle();
            String subtitle = top.getSubtitle();
            String tags = top.getTags();

            if (StringUtils.contains(title, key) || StringUtils.contains(subtitle, key) || StringUtils.contains(tags, key)) {
                blogKeyList.add(blog);
            }
        }
        return blogKeyList;
    }


}
