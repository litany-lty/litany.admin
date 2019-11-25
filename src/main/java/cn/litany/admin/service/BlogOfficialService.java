package cn.litany.admin.service;

import cn.litany.admin.dto.BlogManager;
/**
 * 处理正式文件区的文件（正式版本的文件）
 * @author Litany
 */
public interface BlogOfficialService {

    /**
     * 将修改后或完成的博客进行发布
     * @param bm 博客管理员
     * @return 执行结果
     * */
    boolean publishBlog(BlogManager bm);

    /**
     * 将正式区的文件拷贝至草稿区
     * @param bm 博客管理员
     * @return 执行结果
     * */
    boolean copyToDraft(BlogManager bm);

    /**
     * 将正式区的文件从发布区移除
     * @param bm 博客管理员
     * @return 执行结果
     * */
    boolean deleteFile(BlogManager bm);

}
