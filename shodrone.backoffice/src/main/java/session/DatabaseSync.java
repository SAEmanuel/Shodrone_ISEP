package session;

import com.jcraft.jsch.*;
import java.io.*;

public class DatabaseSync {
    private static final String USER = "root";
    private static final String HOST = "vs789.dei.isep.ipp.pt";
    private static final int PORT = 22;
    private static final String PASSWORD = "RomeuZouXu@@";
    private static final String REMOTE_BASE_DIR = "/root/db";

    private static final String LOCAL_DIR_PATH = "./db";

    public static void sync() {
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp sftpChannel = null;

        try {
            session = jsch.getSession(USER, HOST, PORT);
            session.setPassword(PASSWORD);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();

            createRemoteDir(sftpChannel, REMOTE_BASE_DIR);
            uploadDirectory(sftpChannel, LOCAL_DIR_PATH, REMOTE_BASE_DIR);

            System.out.println("Diretório sincronizado com sucesso no servidor!");
        } catch (Exception e) {
            System.err.println("Erro ao sincronizar: " + e.getMessage());
        } finally {
            if (sftpChannel != null) sftpChannel.exit();
            if (session != null) session.disconnect();
        }
    }

    private static void createRemoteDir(ChannelSftp sftp, String remoteDir) throws SftpException {
        try { sftp.stat(remoteDir); }
        catch (SftpException e) { if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) sftp.mkdir(remoteDir); }
    }

    private static void uploadDirectory(ChannelSftp sftp, String localPath, String remotePath) throws SftpException {
        File localDir = new File(localPath);
        File[] files = localDir.listFiles();
        if (files == null) return;
        for (File file : files) {
            String remoteFilePath = remotePath + "/" + file.getName();
            if (file.isDirectory()) {
                createRemoteDir(sftp, remoteFilePath);
                uploadDirectory(sftp, file.getAbsolutePath(), remoteFilePath);
            } else {
                try (FileInputStream fis = new FileInputStream(file)) {
                    sftp.put(fis, remoteFilePath, ChannelSftp.OVERWRITE);
                    //System.out.println("Enviado: " + file.getAbsolutePath() + " -> " + remoteFilePath);
                } catch (Exception e) {
                    System.err.println("Falha ao enviar " + file.getName() + ": " + e.getMessage());
                }
            }
        }
    }
}
