package com.mine.util;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author vietnv14
 */
public class SFTPNET {

    private static Logger logger = Logger.getLogger(SFTPNET.class);
    private JSch ssh = new JSch();
    private Session session;
    private ChannelSftp sftp;
    private Channel channel;

    //ok
    public boolean Connect(String ip, String port, String userName, String password) {
        return login(ip, port, userName, password);
    }

    public void disconnect() {
        if (sftp != null) {
            sftp.exit();
        }
        if (session != null) {
            session.disconnect();
        }
    }

//ok
    private boolean login(String ip, String port, String userName, String password) {
        boolean ret = false;
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");

        try {
            int portR = 22;
            if (port != null && !port.isEmpty()) {
                portR = Integer.valueOf(port);
            }
            session = ssh.getSession(userName, ip, portR);
            session.setConfig(config);

            session.setPassword(password);

            session.connect(60000);
            channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            ret = true;
        } catch (JSchException e) {

            logger.error(e.getMessage(), e);
        }
        return ret;
    }
//ok

    public void uploadFile(File f, String directory, String fileName) throws Exception {
        //ssh.mkd(directory);
        createDirectoryTree(sftp, directory);
        if (directory != null) {
            changeWorkingDirectory(sftp, directory);
        }
        if (f.exists() && f.isFile()) {
            InputStream inputStream = new FileInputStream(f);
            sftp.put(inputStream, fileName, ChannelSftp.OVERWRITE);
            inputStream.close();
        }
    }

    public void uploadFile2(InputStream inputStream, String directory, String fileName) throws Exception {
        //ssh.mkd(directory);
        createDirectoryTree(sftp, directory);
        if (directory != null) {
            changeWorkingDirectory(sftp, directory);
        }
        sftp.put(inputStream, fileName, ChannelSftp.OVERWRITE);
    }
//ok

    public void deleteFile(String filePath) throws Exception {
        sftp.rm(filePath);
    }
//ok

    public boolean exists(String folder, String fileName) throws Exception {

        List<String> lstFileNames = new ArrayList<String>();
        Vector filelist = new Vector();
        try {

            if (folder != null && folder.length() > 0) {
                filelist = sftp.ls(folder);
            } else {
                filelist = sftp.ls("");
            }
            for (int i = 0; i < filelist.size(); i++) {
                LsEntry entry = (LsEntry) filelist.get(i);
                if (!entry.getAttrs().isDir()) {
                    lstFileNames.add(entry.getFilename());
                }
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }
        if ( lstFileNames.size() < 1) {
            return false;
        } else {
            for (String file : lstFileNames) {
                if (file.equalsIgnoreCase(fileName)) {
                    return true;
                }
            }
            return false;
        }
    }

    //ok
    public InputStream getFileStream(String folder, String fileName) throws Exception {

        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(sftp.get(folder + "/" + fileName));

            return inputStream;
        } catch (SftpException e) {

            logger.error("get " + folder + "/" + fileName + " fail(" + e.getMessage() + ")\n");
            throw e;
        }
    }

    public void createFolder(String folder) {
        File f = new File(folder);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    public InputStream writeFileLocal(String fileLocal, String fileName, InputStream inputStream) throws Exception {
        try {
            createFolder(fileLocal);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            OutputStream out = new FileOutputStream(new File(fileLocal + "/" + fileName), false);
            InputStream ip = new ByteArrayInputStream(baos.toByteArray());
            int read;
            byte[] bytes = new byte[1024];
            while ((read = ip.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            ip.close();
            ip = new ByteArrayInputStream(baos.toByteArray());
            baos.close();
            return ip;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void createDirectoryTree(ChannelSftp client, String dirTree) throws IOException {
        boolean dirExists = true;

        //tokenize the string and attempt to change into each directory level.  If you cannot, then start creating.
        String[] directories = dirTree.split("/");
        String path = "";
        for (String dir : directories) {
            if (!dir.isEmpty()) {
                if (dirExists) {
                    path += "/" + dir;
                    dirExists = changeWorkingDirectory(client, path);
                }
                if (!dirExists) {
                    if (!makeDirectory(client, dir)) {
                        throw new IOException("Unable to create remote directory '" + dir);
                    }
                    if (!changeWorkingDirectory(client, dir)) {
                        throw new IOException("Unable to change into newly created remote directory '" + dir);
                    }
                }
            }
            System.out.println("dir : " + dir);

        }
    }

    private boolean changeWorkingDirectory(ChannelSftp client, String dir) {
        try {
            client.cd(dir);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return false;
        }
    }

    private boolean makeDirectory(ChannelSftp client, String dir) {
        try {
            client.mkdir(dir);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return false;
        }
    }

}
