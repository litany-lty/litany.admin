package cn.litany.admin.service;

import cn.litany.admin.dto.blog.BlogManager;

/**
 * @author Litany
 * 这里是页面交互调用的服务
 */
public interface BlogManagerService {

    /**
     * 编辑功能
     * */
    BlogManager edit(BlogManager blogManager);

    /**
     * 修改功能
     * */
    BlogManager modify(BlogManager blogManager);

    /**
     * 发布功能
     * */
    BlogManager publish(BlogManager blogManager);

    /**
     * 转至草稿箱
     * */
    BlogManager move(BlogManager blogManager);
    /**
     * 根据情况删除
     * */
    Boolean delete(BlogManager blogManager);
}
