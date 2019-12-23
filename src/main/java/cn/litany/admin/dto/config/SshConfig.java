package cn.litany.admin.dto.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.util.FS;

import java.io.File;

/**
 * @author Litany
 */
public class SshConfig  {
    private static String USER_HOME = System.getProperty("user.home");
    private final static String SEPARATOR = System.getProperty("file.separator");

    public static TransportConfigCallback getSsh(String sshKeyPath){
        return new TransportConfigCallback() {
            @Override
            public void configure(Transport transport) {
                if (transport instanceof SshTransport) {
                    SshTransport sshTransport = (SshTransport) transport;
                    sshTransport.setSshSessionFactory(new Ssh(sshKeyPath));
                }
            }
        };
    }

    private static class Ssh extends JschConfigSessionFactory {

        private String sshKeyFilePath;

        public Ssh(String sshKeyFilePath) {
            this.sshKeyFilePath = sshKeyFilePath;
        }

        @Override
        protected JSch createDefaultJSch(FS fs ) throws JSchException {
            JSch defaultJSch = super.createDefaultJSch( fs );
            defaultJSch.addIdentity( sshKeyFilePath );
            return defaultJSch;
        }

        protected JSch createDefaultJSch(final OpenSshConfig.Host hc, FS fs) throws JSchException {
            JSch jsch = new JSch();
            jsch.removeAllIdentity();
            new File(sshKeyFilePath).setReadable(true);
            jsch.addIdentity(sshKeyFilePath);
//            jsch.setKnownHosts(USER_HOME+SEPARATOR+".ssh"+SEPARATOR+"known_hosts");

//  todo    jsch.setKnownHosts("C:\\Users\\wb-lty594074\\.ssh\\known_hosts");
            return jsch;
        }

        @Override
        protected void configure(OpenSshConfig.Host hc, Session session) {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

        }

        public String getSshKeyFilePath() {
            return sshKeyFilePath;
        }

        public void setSshKeyFilePath(String sshKeyFilePath) {
            this.sshKeyFilePath = sshKeyFilePath;
        }
    }
}
