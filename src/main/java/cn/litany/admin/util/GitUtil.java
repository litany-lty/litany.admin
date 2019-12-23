package cn.litany.admin.util;

import cn.litany.admin.dto.config.SshConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.aspectj.util.FileUtil;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

/**
 * @author Litany
 */
@Component
public class GitUtil {

    @Autowired
    private ConfigUtil configUtil;

    public Boolean cloneRepository(String username, String keyPath) {
        CloneCommand cloneCommand = Git.cloneRepository();
        File directory = new File(configUtil.getUserOfficialPath(username));
        if (directory.exists()) {
            FileUtil.deleteContents(directory);
        }

        cloneCommand.setDirectory(directory);
        cloneCommand.setURI("git@github.com:" + username + "/" + username + ".github.io.git");
        cloneCommand.setTransportConfigCallback(SshConfig.getSsh(keyPath));
        try {
            cloneCommand.call();
            return true;
        } catch (GitAPIException e) {
            return false;
        }

    }

    public Boolean pull(String username, String keyPath) {
        try {
            Git git = Git.open(new File(configUtil.getUserPath(username)));
            PullCommand pull = git.pull();
            pull.setRemote("origin");
            pull.setTransportConfigCallback(SshConfig.getSsh(keyPath));
            pull.call();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Boolean push(String username, String keyPath) {
        if (StringUtils.isBlank(username)) {
            username = configUtil.getUsername();
        }
        try {
            File dir = new File(configUtil.getUserPath(username));
            Git git = Git.open(dir);
            git.add().addFilepattern(".").call();
            git.commit().setAll(true).setMessage(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:ss:mm") + " [" + username + "] Auto update").call();
            PushCommand push = git.push();
            TransportConfigCallback ssh = SshConfig.getSsh(keyPath);
            push.setTransportConfigCallback(ssh);
            push.call();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}



