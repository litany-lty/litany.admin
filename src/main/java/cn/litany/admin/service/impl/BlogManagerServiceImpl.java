package cn.litany.admin.service.impl;

import cn.litany.admin.dto.blog.BlogManager;
import cn.litany.admin.service.BlogDraftService;
import cn.litany.admin.service.BlogManagerService;
import cn.litany.admin.service.BlogOfficialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Litany
 */
@Service
public class BlogManagerServiceImpl implements BlogManagerService {

    @Autowired
    private BlogDraftService blogDraftService;

    @Autowired
    private BlogOfficialService blogOfficialService;

    @Override
    public BlogManager edit(BlogManager blogManager) {

        blogDraftService.createDraft(blogManager);
        return blogManager;
    }

    @Override
    public BlogManager modify(BlogManager blogManager) {
        if (!blogManager.isDraft()){
            blogOfficialService.copyToDraft(blogManager);
        }
        blogDraftService.updateDraft(blogManager);
        return blogManager;
    }

    @Override
    public BlogManager publish(BlogManager blogManager) {
        if (blogManager.isDraft()) {
            blogDraftService.moveToOfficial(blogManager);
        }
        blogOfficialService.publishBlog(blogManager);
        return blogManager;
    }

    @Override
    public BlogManager move(BlogManager blogManager) {
        if (blogManager.isDraft()) {
            blogDraftService.moveToOfficial(blogManager);
        } else {
            blogOfficialService.copyToDraft(blogManager);
        }
        return blogManager;
    }

    @Override
    public Boolean delete(BlogManager blogManager) {
        if (blogManager.isDraft()) {
            return blogDraftService.deleteDraft(blogManager);
        } else {
          return  blogOfficialService.deleteFile(blogManager);
        }
    }
}
