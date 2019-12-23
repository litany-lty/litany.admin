package cn.litany.admin.service.impl;

import cn.litany.admin.dto.blog.Blog;
import cn.litany.admin.service.BlogReader;
import cn.litany.admin.util.BlogUtil;
import cn.litany.admin.util.ConfigUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

import static cn.litany.admin.constant.BlogConstant.*;

/**
 * @author Litany
 */
@Service("nameReader")
public class BlogNameReader implements BlogReader {
    @Autowired
    private ConfigUtil configUtil;

    @Override
    public List<Blog> getDraftBlog(String username) {
        String userDraftPath = configUtil.getUserDraftPath(username);
        return getBlogByDir(userDraftPath);
    }

    @Override
    public List<Blog> getOfficialBlog(String username) {
        String userOfficialPath = configUtil.getUserOfficialPath(username);
        return getBlogByDir(userOfficialPath);
    }

    @Override
    public List<Blog> getAllBlog(String username) {
        List<Blog> blogList = getDraftBlog(username);
        blogList.addAll(getOfficialBlog(username));
        return blogList;
    }

    @Override
    public List<Blog> getDraftBlogByKey(String username, String key) {
        List<Blog> allDraftBlog = getDraftBlog(username);
        return findInBlogByKey(allDraftBlog, key);
    }

    @Override
    public List<Blog> getOfficialBlogByKey(String username, String key) {
        List<Blog> allOfficialBlog = getAllBlog(username);
        return findInBlogByKey(allOfficialBlog, key);
    }

    @Override
    public List<Blog> getAllBlogByKey(String username, String key) {
        List<Blog> allBlog = getAllBlog(username);
        return findInBlogByKey(allBlog, key);
    }

    @Override
    public Map<String, List<Blog>> getDraftBlogGroupByDate(String username) {
        List<Blog> draftBlogList = getDraftBlog(username);
        return BlogUtil.findInBlogListByDate(draftBlogList);
    }

    @Override
    public Map<String, List<Blog>> getOfficialBlogGroupByDate(String username) {
        List<Blog> officialBlogList = getOfficialBlog(username);
        return BlogUtil.findInBlogListByDate(officialBlogList);
    }

    @Override
    public Map<String, List<Blog>> getAllBlogGroupByDate(String username) {
        List<Blog> allBlog = getAllBlog(username);
        return BlogUtil.findInBlogListByDate(allBlog);
    }

    @Override
    public Blog getBlogByName(String username, String blogName, String field) {
        File[] blogList = null;
        if (StringUtils.isNotBlank(field) && StringUtils.equalsIgnoreCase(field, OFFICIAL)) {
            blogList = getBlogFiles(configUtil.getUserOfficialPath(username));
        } else if (StringUtils.isNotBlank(field) && StringUtils.equalsIgnoreCase(field, DRAFT)) {
            blogList = getBlogFiles(configUtil.getUserDraftPath(username));
        } else if (StringUtils.isNotBlank(field) && StringUtils.equalsIgnoreCase(field, ALL)) {
            blogList = getBlogFiles(configUtil.getUserOfficialPath(username));
            File[] draftBlog = getBlogFiles(configUtil.getUserDraftPath(username));
            if (draftBlog != null && blogList != null) {
                List<File> allBlogList = new ArrayList<>(Arrays.asList(draftBlog));
                allBlogList.addAll(Arrays.asList(blogList));
                blogList = allBlogList.toArray(new File[0]);
            }
        }

        if (blogList == null) {
            return null;
        }

        for (File blog : blogList) {
            if (StringUtils.contains(blog.getName().replace(" ", ""), blogName.replace(" ", ""))) {
                return BlogUtil.parseBlogFile(blog);
            }
        }
        return null;
    }

    @Override
    public List<Blog> findNewBlogList(String username, String region) {
        Map<String, List<Blog>> blogGroupByDate = new HashMap<>(10);
        if (StringUtils.isBlank(region) || OFFICIAL.equalsIgnoreCase(region)) {
            blogGroupByDate = getOfficialBlogGroupByDate(username);
        } else if (DRAFT.equalsIgnoreCase(region)) {
            blogGroupByDate = getDraftBlogGroupByDate(username);
        } else if (ALL.equalsIgnoreCase(region)) {
            blogGroupByDate = getDraftBlogGroupByDate(username);
            Map<String, List<Blog>> officialBlogGroupByDate = getOfficialBlogGroupByDate(username);
            mergeBlogMap(blogGroupByDate, officialBlogGroupByDate);
        }
        return BlogUtil.getNewBlogListByNum(blogGroupByDate, 10);
    }

    private void mergeBlogMap(Map<String, List<Blog>> offMap, Map<String, List<Blog>> draftMap) {
        Set<String> draftKey = draftMap.keySet();
        draftKey.forEach((s) -> {
            offMap.forEach((k, v) -> {
                if (offMap.get(s)!=null){
                    v.addAll(draftMap.get(s));
                    offMap.put(k,v);

                }else
                if (offMap.get(s)==null &&draftMap.get(s)!=null){
                    offMap.put(s,draftMap.get(s));
                }
            });
        });
    }

    private List<Blog> findInBlogByKey(List<Blog> blogList, String key) {
        List<Blog> blogKeyList = new ArrayList<>();
        for (Blog blog : blogList) {
            String title = blog.getTitle();
            if (StringUtils.contains(title, key)) {
                blogKeyList.add(blog);
            }
        }
        return blogKeyList;
    }

    private File[] getBlogFiles(String dir) {
        File blogDir = new File(dir);
        if (blogDir.exists() && blogDir.isDirectory()) {
            return blogDir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isFile() && pathname.getName().endsWith(MARKDOWN_TYPE);
                }
            });
        }
        return null;
    }

    private List<Blog> getBlogByDir(String dir) {
        List<Blog> blogList = new ArrayList<>();
        File blogDir = new File(dir);
        if (blogDir.exists() && blogDir.isDirectory()) {
            File[] blogFiles = blogDir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isFile() && pathname.getName().endsWith(MARKDOWN_TYPE);
                }
            });
            if (blogFiles == null) {
                return null;
            }
            for (File blogFile : blogFiles) {
                Blog blog = getBlogByTitle(blogFile.getName());
                blogList.add(blog);
            }
        }
        return blogList;
    }

    private Blog getBlogByTitle(String title) {
        Blog blog = new Blog();
        blog.setTitle(title);
        return blog;
    }


}
