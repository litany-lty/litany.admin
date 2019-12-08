package cn.litany.admin.util;

import cn.litany.admin.dto.config.SshConfig;
import org.apache.commons.lang.time.DateFormatUtils;
import org.aspectj.util.FileUtil;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;

import java.io.File;
import java.util.Date;

/**
 * @author Litany
 */
public class GitUtil {

    public static Boolean cloneRepository(String username, String keyPath) {

        CloneCommand cloneCommand = Git.cloneRepository();
        File directory = new File(ConfigUtil.getUserOfficialPath(username));
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

    public static Boolean pull(String username, String keyPath) {
        try {
            Git git = Git.open(new File(ConfigUtil.getUserOfficialPath(username)));
            PullCommand pull = git.pull();
            pull.setTransportConfigCallback(SshConfig.getSsh(keyPath));
            pull.call();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static Boolean push(String username, String keyPath) {
        try {
            File dir = new File(ConfigUtil.getUserOfficialPath(username));
            Git git = Git.open(dir);
            git.add().addFilepattern(".").call();
            git.commit().setMessage(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:ss:mm") +" ["+username+ "] Auto update").call();
            PushCommand push = git.push();
            push.setTransportConfigCallback(SshConfig.getSsh(keyPath));
            push.call();
            return true;

        } catch (Exception e) {
            return false;
        }

    }
}



