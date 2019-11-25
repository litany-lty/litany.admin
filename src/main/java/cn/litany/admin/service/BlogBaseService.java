package cn.litany.admin.service;


import cn.litany.admin.dto.BlogManager;

/**
 * @author Litany
 * 博客增删改查的基本服务
 */
public interface BlogBaseService {

    /**
     * 用于生成博客所需的.md文件
     * @param  bm  Blog管理员
     * @return 执行结果
     * */
    boolean createBlogFile(BlogManager bm);

    /**
     * 用于修改已有的.md文件
     * @param  bm  Blog管理员
     * @return 执行结果
     * */
    boolean updateBlogFile(BlogManager bm);


    /**
     * 用于物理删除已有的.md文件
     * @param  bm  Blog管理员
     * @return 执行结果
     * */
    boolean deleteBlogFile(BlogManager bm);

    /**
     * 用于移动.md文件到新路径
     * @param  bm  Blog管理员 存储着需要被移动文件的信息
     * @param filePath 需要被移动到的目标路径
     * @return 执行结果
     * */
    boolean moveFile(BlogManager bm,String filePath);

    /**
     * 用于复制文件，再移动.md文件到新路径
     * @param  bm  Blog管理员 存储着需要被复制移动文件的信息
     * @param filePath 需要被移动到的目标路径
     * @return 执行结果
     * */
    boolean copyAndMoveFile(BlogManager bm,String filePath);

}
