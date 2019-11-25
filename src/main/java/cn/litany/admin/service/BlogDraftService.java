package cn.litany.admin.service;


import cn.litany.admin.dto.BlogManager;

/**
 * 草稿箱功能
 * @author Litany
 */
public interface BlogDraftService {

    /**
     *创建草稿
     * @param bm 博客管理对象
     * @return  返回执行结果
     * */
    boolean createDraft(BlogManager bm);
    /**
     *修改草稿
     * @param bm 博客管理对象
     * @return  返回执行结果
     * */
    boolean updateDraft(BlogManager bm);

    /**
     * 物理删除草稿
     * @param bm 博客管理对象
     * @return  返回执行结果
     * */
    boolean deleteDraft(BlogManager bm);

    /**
     * 移动至正式文件区（草稿升级为正式版本）
     * @param bm 博客管理对象
     * @return 执行结果
     * */
    boolean moveToOfficial(BlogManager bm);

}
