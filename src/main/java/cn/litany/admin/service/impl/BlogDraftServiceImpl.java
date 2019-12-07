package cn.litany.admin.service.impl;

import cn.litany.admin.dto.blog.Blog;
import cn.litany.admin.dto.blog.BlogManager;
import cn.litany.admin.dto.blog.Top;
import cn.litany.admin.service.BlogBaseService;
import cn.litany.admin.service.BlogDraftService;
import cn.litany.admin.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Litany
 */
@Service
public class BlogDraftServiceImpl implements BlogDraftService {

    @Autowired
    BlogBaseService blogBaseService;


    @Override
    public boolean createDraft(BlogManager bm) {
        bm.setPath(ConfigUtil.getUserDraftPath(bm.getUsername()));
        return blogBaseService.createBlogFile(bm);
    }

    @Override
    public boolean updateDraft(BlogManager bm) {
        bm.setPath(ConfigUtil.getUserDraftPath(bm.getUsername()));
        return blogBaseService.updateBlogFile(bm);
    }

    @Override
    public boolean deleteDraft(BlogManager bm) {
        return blogBaseService.deleteBlogFile(bm);
    }

    @Override
    public boolean moveToOfficial(BlogManager bm) {
        bm.setPath(ConfigUtil.getUserOfficialPath(bm.getUsername()));
        return blogBaseService.moveFile(bm,ConfigUtil.getUserOfficialPath(bm.getUsername()));
    }
}
