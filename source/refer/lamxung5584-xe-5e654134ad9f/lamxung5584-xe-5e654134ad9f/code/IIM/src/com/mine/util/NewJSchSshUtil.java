package com.mine.util;

import com.jcraft.jsch.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author quanns2
 */
public class NewJSchSshUtil implements Serializable {
	private static Logger logger = LogManager.getLogger(NewJSchSshUtil.class);
	private static final long serialVersionUID = 1L;
	private String host;
	private int port;
	private String username;
	private String password;
	private String prompt = "$";
	private BufferedInputStream dataIn;
	private BufferedOutputStream dataOut;
	private Session session;
	private Channel channel;
	private StringBuilder log = new StringBuilder();
	private boolean isConnect = false;
	private boolean isSaveLog = true;
	private boolean isDebug;
	private String ctrlC="\u0003";

	public NewJSchSshUtil(String host, int port, String usename, final String password, String promt) {
		this.host = host;
		this.port = port;
		this.username = usename;
		this.password=password;
		/*this.prompt = promt;
		try {
			JSch shell = new JSch();
			// get a new session
			session = shell.getSession(username, this.host, this.port);
			// set user password and connect to a channel
			session.setUserInfo(new SSHUserInfo(password, true));
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("PreferredAuthentications", "privatekey,keyboard-interactive,password");
			session.setConfig(config);
			session.setTimeout(2 * 60 * 1000);

		} catch (Exception e) {
			logger.info(e, e);
		}*/
	}

	public boolean connect() {


		this.session=null;
		try {
			JSch shell = new JSch();
			// get a new session
			session = shell.getSession(username, this.host, this.port);
			// set user password and connect to a channel
			session.setUserInfo(new SSHUserInfo(password, true));
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("PreferredAuthentications", "privatekey,keyboard-interactive,password");
			session.setConfig(config);
			session.setTimeout(2 * 60 * 1000);

		} catch (Exception e) {
			logger.info(e, e);
		}


		try {

			this.session.connect();
			this.channel = session.openChannel("shell");
			((ChannelShell) channel).setPtyType("dumb");
			((ChannelShell) channel).setPtySize(4096, 24, 640, 480);
			this.channel.connect();
			this.dataIn = new BufferedInputStream(channel.getInputStream());
			this.dataOut = new BufferedOutputStream(channel.getOutputStream());
			// this.waitAndOut(this.prompt, 2000);// ch? d?u nh�c l?nh xu?t hi?n
			// trong 2s

			// Get prompt
			OutputStream out = new ByteArrayOutputStream();
			String sbuff = null;
			try {
				channel.setOutputStream(out);
				PrintStream shellStream = new PrintStream(this.channel.getOutputStream());
				shellStream.print("\r");
				shellStream.flush();
//				long start = new Date().getTime();
				/*while (new Date().getTime() < start + 15000) {
					sbuff = out.toString();
				}*/
				Thread.sleep(5000);

				sbuff = out.toString();
				if (sbuff != null && sbuff.length() > 3)
					prompt = sbuff.substring(sbuff.length() - 3).trim();
				logger.error("prompt: " + prompt);
			} catch (Exception e) {
				logger.info(e, e);
				;
			}

			isConnect = true;
			logger.info("Connected Server: " + host);
		} catch (Exception e) {
			logger.info(e, e);
			;
			isConnect = false;
		}

		return isConnect;
	}

	public List<String> subLogToList(String log) {
		List<String> list = new ArrayList<String>();
		try {
			list = Arrays.asList(log.split("\n"));
		} catch (Exception e) {
                    logger.error(e.getMessage(), e);
			// TODO: handle exception
		}
		return list;
	}

	public void logAppendLine(String log) {
		this.log.append("\r\n" + log);
	}

	/**
	 * @param command
	 * @param timeOut
	 * @return {@link Result}: Status and result of command </br>
	 * @author huynx6
	 *
	 */
	public String sendLineWithTimeOutNew(String command, long timeOut, List<String> otherPrompt) {
		try {
			if (!this.getSession().isConnected())
				this.connect();
		} catch (Exception e1) {
			logger.info(e1, e1);
			;
		}
		if (timeOut < 10000)
			timeOut = 10000;
		OutputStream out = new ByteArrayOutputStream();
		Result result = new Result();
		try {
			channel.setOutputStream(out);
			PrintStream shellStream = new PrintStream(this.channel.getOutputStream());
			shellStream.print(command + "\r");
			shellStream.flush();


			boolean check=true;
			long start = new Date().getTime();
			long maxWaitingTime = start+6000*1000;
			long endTime= start+ timeOut;
			int maxSentCtrl=10;

			/*while (new Date().getTime() < start + timeOut) {*/
			while (check) {
				String sbuff = out.toString();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					logger.info(e, e);
				}
				if (sbuff.trim().endsWith(this.prompt)) {

					try {
						Thread.sleep(2000); // sleep 2s
					}catch(Exception e){
                                        logger.error(e.getMessage(),e);
                                        }

//					if(sbuff.length()==out.toString().length()){ //neu khong con nhan dc ky tu nao
						result.setSuccessSent(true);
//						check=false;
						break;
//					}
				}
				if (otherPrompt != null) {
					boolean isBreak = false;
					for (String prompt : otherPrompt) {
						if (sbuff.trim().endsWith(prompt)) {
							result.setSuccessSent(true);
							isBreak = true;
//							check=false;
							break;
						}
					}
					if (isBreak)
						break;
				}

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					logger.info(e, e);
				}
				if (sbuff.length() < out.toString().length()){
					endTime += 20000;
				}else{
					if( new Date().getTime() >  endTime ){

						logger.error("Send ctrlC");
						shellStream.print(ctrlC+"\r");
						shellStream.flush();
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							logger.info(e, e);
						}

					}
				}

				//neu vuot qua 10 phut thi gui lenh ctrl C
				if( new Date().getTime() >  maxWaitingTime ){

					maxSentCtrl--;
					if(maxSentCtrl<0){
						return null;
					}
					logger.error("Send ctrlC");
					shellStream.print(ctrlC+"\r");
					shellStream.flush();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						logger.info(e, e);
					}

				}
			}

		} catch (IOException e) {
			logger.info(e, e)
			;
		} // printStream for convenience
		if (result.isSuccessSent == null)
			result.setSuccessSent(false);
		String lastoutput = out.toString();
		if (isSaveLog)
			log.append(lastoutput);
		if (isDebug)
			System.out.print(lastoutput);
		result.setResult(lastoutput);

		return this.creatResult(lastoutput, command, this.prompt);
	}

	public void disconnect() {
		try {

			if (dataIn != null)
				dataIn.close();
			if (dataOut != null)
				dataOut.close();
			if (channel != null)
				channel.disconnect();
			if (session != null)
				session.disconnect();
			isConnect = false;
			logger.info("Disconected Server" + host);
		} catch (Exception e) {
			logger.info(e, e);
			;
		}
	}

	public String creatResult(String log, String strBegin, String strEnd) {
		try {

			log = log.trim().replaceAll("\\s*\b", "");
			String result = log.trim().replaceAll("\\x1b[^m]*m", "").replaceAll("[\\x00\\x08\\x0B\\x0C\\x0E-\\x1F]",
					"");
			int beginSub = result.indexOf(strBegin.trim());
			if (beginSub != -1) {
				result = result.substring(beginSub + strBegin.length() + 1);
			}
			// result=result.trim(); // x�a c�c d�ng tr?ng
			int lastLF = result.lastIndexOf("\n");
			if (lastLF != -1) {
				String endLine = result.substring(lastLF);
				if (endLine.contains(strEnd)) {
					result = result.substring(0, lastLF);
				}
			} else { // kh�ng c� d?u zu?ng d�ng

				if (result.contains(strEnd))
					result = "";
			}

			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return log;
		}

	}

	private Session getSession() {
		return session;
	}

	public String getLog() {
		return log.toString();
	}

	public ChannelSftp getSftpChanel() throws JSchException {
		ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
		sftp.connect();
		return sftp;
	}

	public boolean isLive() {
		try {

			return session.isConnected();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	public static void main(String[] args) {

		// JSchSshUtil sshUtil = new JSchSshUtil("10.61.94.83", 22,
		// "huynx","done", null);
		NewJSchSshUtil sshUtil;
		if (args.length == 3) {
			sshUtil = new NewJSchSshUtil(args[0], 22, args[1], args[2], null);
		} else {
			logger.error("Input IP, user, pass");
			sshUtil = new NewJSchSshUtil("10.61.94.97", 22, "oracle", "oracle", "$");
			// return;
		}

		// sshUtil.setDebug(true);
		// JSchSshUtil sshUtil = new JSchSshUtil("10.58.65.191", 22, "hanh45",
		// "K3$ndf#1", "$");
		boolean tryConnect = sshUtil.connect();
		// try {
		// Thread.sleep(20000L);
		// } catch (InterruptedException e1) {
		// logger.info(e1, e1);;
		// }
		if (!tryConnect) {
			return;
		}

		try {

			String cmd = "cd /u01/app/oracle/diag/rdbms/ptpmdb/ptpmdb/trace";
			//Result result = new Result();
			String data = sshUtil.sendLineWithTimeOutNew(cmd, 10000, null);
			logger.error(data);

			//cmd = "top";
			cmd = "ssh longlt@10.61.94.83";
			//result = new Result();
			long begin = new Date().getTime();
			data = sshUtil.sendLineWithTimeOutNew(cmd, 20000, null);
			logger.info(new Date().getTime() - begin);
			logger.error(data);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			sshUtil.disconnect();
		}
	}

	public boolean isConnect() {
		return isConnect;
	}

	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}



}
