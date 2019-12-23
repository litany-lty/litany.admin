package cn.litany.admin.service.impl;

import cn.litany.admin.dto.blog.BlogManager;
import cn.litany.admin.dto.blog.Top;
import cn.litany.admin.service.BlogBaseService;
import cn.litany.admin.util.ConfigUtil;
import cn.litany.admin.util.GitUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

import static cn.litany.admin.constant.BlogConstant.*;

/**
 * @author Litany
 */
public class BlogBaseServiceImpl implements BlogBaseService {



    @Autowired
    protected ConfigUtil configUtil;

    @Autowired
    protected GitUtil gitUtil;


    @Override
    public boolean createBlogFile(BlogManager bm) {
        return writeToFile(bm);
    }

    @Override
    public boolean updateBlogFile(BlogManager bm) {
        if (!bm.isDraft()) {
            String userDraftPath = configUtil.getUserDraftPath(bm.getUsername());
            copyAndMoveFile(bm, userDraftPath);
            bm.setPath(userDraftPath);
        }
        return writeToFile(bm);
    }

    @Override
    public boolean deleteBlogFile(BlogManager bm) {
        return new File(getFilePath(bm)).delete();
    }

    @Override
    public boolean moveFile(BlogManager bm, String filePath) {
        try {
            String srcPath = getFilePath(bm);
            bm.setPath(filePath);
            String destinationPath = getFilePath(bm);
            File destFile = new File(destinationPath);
            if (destFile.exists() && destFile.isFile()) {
                destFile.delete();
            }
            FileUtils.moveFile(new File(srcPath), destFile);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String getFilePath(BlogManager bm) {
        String path = bm.getPath();
        String newPath = ConfigUtil.getPath(path);
        if (new File(newPath).isFile()) {
            return newPath;
        }
        Top top = bm.getBlog().getTop();
        return newPath + (top.getDate()) + (AND) + (top.getTitle()) + (MARKDOWN_TYPE);
    }

    private boolean writeToFile(BlogManager bm) {
        String filePath = getFilePath(bm);
        try {
            FileUtils.write(new File(filePath), bm.getBlogContext(), ENCODING, false);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean copyAndMoveFile(BlogManager bm, String filePath) {
        String srcPath = getFilePath(bm);
        bm.setPath(filePath);
        String destinationPath = getFilePath(bm);
        File file = new File(destinationPath);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        try {
            FileUtils.copyFile(new File(srcPath), new File(destinationPath));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
