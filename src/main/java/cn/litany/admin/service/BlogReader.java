package cn.litany.admin.service;

import cn.litany.admin.dto.blog.Blog;

import java.util.List;
import java.util.Map;

/**
 * 用于获取草稿箱和正式区博客文件的服务
 *
 * @author Litany
 */
public interface BlogReader {

    /**
     * 获取所有草稿箱中的博客
     *
     * @param username 用户名
     * @return 返回博客集合
     */
    List<Blog> getDraftBlog(String username);

    /**
     * 获取所有正式区中的博客
     *
     * @param username 用户名
     * @return 返回博客集合
     */
    List<Blog> getOfficialBlog(String username);

    /**
     * 获取所有的博客
     *
     * @param username 用户名
     * @return 返回博客集合
     */
    List<Blog> getAllBlog(String username);

    /**
     * 通过关键字获取所有草稿箱中的博客
     *
     * @param username 用户名
     * @param key      关键字
     * @return 返回博客集合
     */
    List<Blog> getDraftBlogByKey(String username, String key);

    /**
     * 通过关键字获取所有正式区中的博客
     *
     * @param username 用户名
     * @param key      关键字
     * @return 返回博客集合
     */
    List<Blog> getOfficialBlogByKey(String username, String key);

    /**
     * 通过关键字获取所有的博客
     *
     * @param username 用户名
     * @param key      关键字
     * @return 返回博客集合
     */
    List<Blog> getAllBlogByKey(String username, String key);

    /**
     * 获取所有草稿箱中的博客，以yyyy-MM分组
     *
     * @param username 用户名
     * @return 返回博客集合 key date ,value Blog List
     */
    Map<String, List<Blog>> getDraftBlogGroupByDate(String username);

    /**
     * 获取所有正式区中的博客，以yyyy-MM分组
     *
     * @param username 用户名
     * @return 返回博客集合 key date ,value Blog List
     */
    Map<String, List<Blog>> getOfficialBlogGroupByDate(String username);


    /**
     * 获取所有的博客，以yyyy-MM分组
     *
     * @param username 用户名
     * @return 返回博客集合 key date ,value Blog List
     */
    Map<String, List<Blog>> getAllBlogGroupByDate(String username);

    /**
     * 通过博客名和区域获取博客
     *
     * @param username 用户名
     * @param blogName 博客名称
     * @param field    "draft" 草稿区 "official"正式区
     * @return 返回博客对象
     */
    Blog getBlogByName(String username, String blogName, String field);

    /**
     * 用于页面加载默认的几篇博客
     * @param username 用户名
     * @param region 范围
     * @return: 返回所有最新的10篇博客
     */
    List<Blog> findNewBlogList(String username,String region);

}
