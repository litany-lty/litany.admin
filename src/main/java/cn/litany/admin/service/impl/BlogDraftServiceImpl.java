package cn.litany.admin.service.impl;

import cn.litany.admin.dto.blog.BlogManager;
import cn.litany.admin.service.BlogDraftService;
import org.springframework.stereotype.Service;

/**
 * @author Litany
 */
@Service
public class BlogDraftServiceImpl extends BlogBaseServiceImpl implements BlogDraftService {


    @Override
    public boolean createDraft(BlogManager bm) {
        bm.setPath(configUtil.getUserDraftPath(bm.getUsername()));
        return super.createBlogFile(bm);
    }

    @Override
    public boolean updateDraft(BlogManager bm) {
        if (!bm.isDraft()){
            String userDraftPath = configUtil.getUserDraftPath(bm.getUsername());
            copyAndMoveFile(bm, userDraftPath);
            bm.setPath(userDraftPath);
        }
        return super.updateBlogFile(bm);
    }

    @Override
    public boolean deleteDraft(BlogManager bm) {
        return super.deleteBlogFile(bm);
    }

    @Override
    public boolean moveToOfficial(BlogManager bm) {
        bm.setPath(configUtil.getUserDraftPath(bm.getUsername()));
        return super.moveFile(bm,configUtil.getUserOfficialPath(bm.getUsername()));
    }
}
