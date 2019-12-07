package cn.litany.admin.service.impl;

import cn.litany.admin.dto.blog.BlogManager;
import cn.litany.admin.service.BlogBaseService;
import cn.litany.admin.service.BlogOfficialService;
import cn.litany.admin.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Litany
 */
public class BlogOfficialServiceImpl implements BlogOfficialService {

    @Autowired
    BlogBaseService blogBaseService;

    @Override
    public boolean publishBlog(BlogManager bm) {
        return false;
    }

    @Override
    public boolean copyToDraft(BlogManager bm) {
        return blogBaseService.copyAndMoveFile(bm, ConfigUtil.getUserDraftPath(bm.getUsername()));
    }

    @Override
    public boolean deleteFile(BlogManager bm) {
        return blogBaseService.moveFile(bm,ConfigUtil.getUserDraftPath(bm.getUsername()));
    }
}
