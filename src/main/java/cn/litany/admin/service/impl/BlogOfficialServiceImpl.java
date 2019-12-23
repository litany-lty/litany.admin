package cn.litany.admin.service.impl;

import cn.litany.admin.dto.blog.BlogManager;
import cn.litany.admin.service.BlogOfficialService;
import org.springframework.stereotype.Service;

/**
 * @author Litany
 */
@Service
public class BlogOfficialServiceImpl extends BlogBaseServiceImpl implements BlogOfficialService {


    @Override
    public boolean publishBlog(BlogManager bm) {
        String sshKeyPath = configUtil.getSshKeyPath(bm.getUsername());
        gitUtil.pull(bm.getUsername(), sshKeyPath);
        return gitUtil.push(bm.getUsername(), sshKeyPath);

    }


    @Override
    public boolean copyToDraft(BlogManager bm) {
        return super.copyAndMoveFile(bm, configUtil.getUserDraftPath(bm.getUsername()));
    }

    @Override
    public boolean deleteFile(BlogManager bm) {
        super.moveFile(bm, configUtil.getUserDraftPath(bm.getUsername()));
        return publishBlog(bm);
    }

}
