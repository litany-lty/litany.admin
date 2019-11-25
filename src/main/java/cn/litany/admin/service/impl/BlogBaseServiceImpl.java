package cn.litany.admin.service.impl;

import cn.litany.admin.dto.Blog;
import cn.litany.admin.dto.BlogManager;
import cn.litany.admin.dto.Top;
import cn.litany.admin.service.BlogBaseService;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @author Litany
 */
@Service
public class BlogBaseServiceImpl implements BlogBaseService {

    private final static String SEP_A = "/";
    private final static String SEP_B = "//";
    private final static String SEP_C = "\\\\";
    private final static String SEP_D = "\\";
    private final static String SEPARATOR = "file.separator";
    private final static String AND = "-";
    private final static String MARKDOWN_TYPE = ".md";
    private final static String ENCODING = "UTF-8";

    @Override
    public boolean createBlogFile(BlogManager bm) {
        return writeToFile(bm);
    }

    @Override
    public boolean updateBlogFile(BlogManager bm) {
        File file = new File(bm.getPath());
        if (file.exists()){
            file.delete();
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
            FileUtils.moveFile(new File(srcPath),new File(destinationPath));
            return true;
        } catch (IOException e) {
           return false;
        }
    }

    private String getFilePath(BlogManager bm) {
        String path = bm.getPath();
        String[] routes = new String[0];
        if (path.contains(SEP_A)) {
            routes = path.split(SEP_A);
        } else if (path.contains(SEP_B)) {
            routes = path.split(SEP_B);
        } else if (path.contains(SEP_D)) {
            routes = path.split(SEP_C);
        }
        StringBuilder newPath = new StringBuilder();
        for (String route : routes) {
            newPath.append(route).append(System.getProperty(SEPARATOR));
        }
        Top top = bm.getBlog().getTop();
        newPath.append(top.getDate()).append(AND).append(top.getTitle()).append(MARKDOWN_TYPE);
        return newPath.toString();

    }

    private boolean writeToFile(BlogManager bm) {
        String filePath = getFilePath(bm);
        try {
            FileUtils.write(new File(filePath), bm.getBlogContext(),ENCODING , false);
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
        try {
            FileUtils.copyFile(new File(srcPath),new File(destinationPath));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
