//package com.mine.util;
//
//import org.mozilla.universalchardet.UniversalDetector;
//import org.primefaces.model.UploadedFile;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipInputStream;
//import java.util.zip.ZipOutputStream;
//
//public class FileHelper {
//
//	static final Logger _log = LoggerFactory.getLogger(FileHelper.class);
//
//	private static final int BUFFER_SIZE = 4096;
//
//	/**
//	 * Su dung cho primefaces.
//	 */
//	public static String uploadFile(String folderStore,
//                                    UploadedFile fileUpload, String fileName) {
//		if (fileUpload == null)
//			return "FALSE";
//
//		OutputStream outputStream;
//		try {
//			outputStream = getOutputStream(folderStore, fileName);
//			outputStream.write(fileUpload.getContents());
//			outputStream.close();
//		} catch (Exception e) {
//			_log.info(e.getMessage());
//			return "FALSE";
//		}
//
//		return "SUCCESS";
//	}
//
//	/**
//	 * Get output stream
//	 */
//	public static OutputStream getOutputStream(String folderStore,
//			String fileName) {
//		OutputStream outputStream = null;
//		try {
//			File fileToCreate = new File(folderStore);
//			if (!fileToCreate.exists()) {
//				_log.info("run mkdir: " + folderStore);
//				fileToCreate.mkdirs();
//			}
//
//			fileToCreate = new File(folderStore, fileName);
//			outputStream = new FileOutputStream(fileToCreate);
//		} catch (Exception e) {
//			_log.error(e.getMessage());
//		}
//
//		return outputStream;
//	}
//
//	/**
//	 * Remove file
//	 */
//	public static Boolean removeFile(String folderStore) {
//		try {
//			File fileToRemove = new File(folderStore);
//			if (fileToRemove.exists()) {
//				_log.info("run remove: " + folderStore);
//				if (fileToRemove.delete()) {
//					_log.info("remove file success");
//				}
//			}
//		} catch (Exception e) {
//			_log.error(e.getMessage());
//		}
//		return false;
//	}
//
//	public static String unzip(ZipInputStream zipIn, String... destDirectories)
//			throws IOException {
//		String fileName = "";
//		for (String dir : destDirectories) {
//			File destDir = new File(dir);
//			if (!destDir.exists()) {
//				destDir.mkdir();
//			}
//		}
//		ZipEntry entry = zipIn.getNextEntry();
//		List[] lstFileToZip = { new ArrayList<>(), new ArrayList<>(),
//				new ArrayList<>() };
//		// iterates over entries in the zip file
//		while (entry != null) {
//			String destDirectory = destDirectories[2];
//			if (entry.getName().endsWith(".mp3")
//					|| entry.getName().endsWith(".wma")) {
//				destDirectory = destDirectories[0];
//				lstFileToZip[0].add(destDirectory + File.separator
//						+ entry.getName());
//			} else if (entry.getName().toLowerCase().endsWith(".png")
//					|| entry.getName().toLowerCase().endsWith(".jpg")
//					|| entry.getName().toLowerCase().endsWith(".gif")) {
//				destDirectory = destDirectories[1];
//				lstFileToZip[1].add(destDirectory + File.separator
//						+ entry.getName());
//			} else if (entry.getName().toLowerCase().endsWith(".mp4")
//					|| entry.getName().toLowerCase().endsWith(".mpg")
//					|| entry.getName().toLowerCase().endsWith(".mov")) {
//				destDirectory = destDirectories[2];
//				lstFileToZip[2].add(destDirectory + File.separator
//						+ entry.getName());
//			}
//			String filePath = destDirectory + File.separator + entry.getName();
//			if (!entry.isDirectory()) {
//				// if the entry is a file, extracts it
//				if (entry.getName().toLowerCase().endsWith(".htm")
//						|| entry.getName().toLowerCase().endsWith(".html")) {
//					fileName = entry.getName();
//				}
//				extractFile(zipIn, filePath);
//				if (entry.getName().toLowerCase().endsWith(".htm")
//						|| entry.getName().toLowerCase().endsWith(".html")) {
//					optimizeFile(filePath);
//				}
//			} else {
//				// if the entry is a directory, make the directory
//				File dir = new File(filePath);
//				dir.mkdir();
//			}
//			zipIn.closeEntry();
//			entry = zipIn.getNextEntry();
//		}
//		zipIn.close();
//
//		// Nen lai toan bo thu muc data
//		for (int i = 0; i < lstFileToZip.length; i++) {
//			if (lstFileToZip[i].size() > 0) {
//				if (i == 0) {
//					zipFolder(destDirectories[i] + File.separator
//							+ Config.AUDIO_FILE_ZIP, destDirectories[i]);
//				} else if (i == 1) {
//					zipFolder(destDirectories[i] + File.separator
//							+ Config.IMAGE_FILE_ZIP, destDirectories[i]);
//				} else if (i == 2) {
//					zipFolder(destDirectories[i] + File.separator
//							+ Config.MEDIA_FILE_ZIP, destDirectories[i]);
//				}
//			}
//		}
//
//		return fileName;
//	}
//
//	/**
//	 * Zip it
//	 *
//	 * @param zipFile
//	 *            output ZIP file location
//	 */
//	public static void zipFolder(String zipFilePath, String folderPath) {
//
//		byte[] buffer = new byte[1024];
//
//		try {
//			File folder = new File(folderPath);
//			if (folder.isDirectory()) {
//				FileOutputStream fos = new FileOutputStream(zipFilePath);
//				ZipOutputStream zos = new ZipOutputStream(fos);
//				_log.info("Output to Zip : " + zipFilePath);
//
//				try {
//					for (File fileToZip : folder.listFiles()) {
//						if (!fileToZip.getPath().equals(zipFilePath)) {
//							_log.info("File Added : "
//									+ fileToZip.getName());
//							FileInputStream in = new FileInputStream(fileToZip);
//							ZipEntry ze = new ZipEntry(fileToZip.getName());
//							zos.putNextEntry(ze);
//
//							int len;
//							while ((len = in.read(buffer)) > 0) {
//								zos.write(buffer, 0, len);
//							}
//
//							in.close();
//						}
//					}
//				} catch (Exception ex) {
//					_log.error(ex.getMessage());
//				} finally {
//					zos.closeEntry();
//					// remember close it
//					zos.close();
//					_log.info("Done");
//				}
//
//			}
//
//		} catch (IOException ex) {
//			_log.error(ex.getMessage());
//		} finally {
//
//		}
//	}
//
//	public static void optimizeFile(String filePath) {
//		// TODO Auto-generated method stub
//
//		BufferedWriter bw = null;
//		BufferedReader br = null;
//
//		try {
//			String data = "";
//			String encoding = getEncoding(filePath);
//			if (encoding != null)
//				br = new BufferedReader(new InputStreamReader(
//						new FileInputStream(filePath), encoding));
//			else
//				br = new BufferedReader(new InputStreamReader(
//						new FileInputStream(filePath)));
//			String line;
//			boolean isContTag = false;
//			while ((line = br.readLine()) != null) {
//				if (isContTag) {
//					line = line.replaceAll("width:\\d{1,4}pt", "");
//					isContTag = false;
//				}
//				if (line.trim().startsWith("<table")) {
//					line = line.replaceAll("width=\\d{1,4}", "width=100%");
//					isContTag = true;
//				}
//				data += line + "\n";
//			}
//			br.close();
//			if (encoding != null)
//				bw = new BufferedWriter(new OutputStreamWriter(
//						new FileOutputStream(filePath), encoding));
//			else
//				bw = new BufferedWriter(new OutputStreamWriter(
//						new FileOutputStream(filePath)));
//			bw.write(data);
//			bw.flush();
//			bw.close();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} finally {
//
//		}
//	}
//
//	public static String getEncoding(String fileName) {
//		String encoding = null;
//		try {
//			byte[] buf = new byte[4096];
//			FileInputStream fis = new FileInputStream(fileName);
//
//			// (1)
//			UniversalDetector detector = new UniversalDetector(null);
//
//			// (2)
//			int nread;
//			while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
//				detector.handleData(buf, 0, nread);
//			}
//			// (3)
//			detector.dataEnd();
//
//			// (4)
//			encoding = detector.getDetectedCharset();
//			if (encoding != null) {
//				System.out.println("Detected encoding = " + encoding);
//
//			} else {
//				System.out.println("No encoding detected.");
//			}
//
//			// (5)
//			detector.reset();
//			fis.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return encoding;
//
//	}
//
//	public static void main(String[] args) {
//		String filePath = "C:/Users/anhdt8.mineGROUP/Desktop/Vdict/anh up phan mem/anh/anh";
//		// FileHelper.getEncoding(filePath);
//		File folder = new File(filePath);
//		for(File file:folder.listFiles()) {
//			System.out.println(file.getName());
//		}
//	}
//
//	/**
//	 * Extracts a zip entry (file entry)
//	 *
//	 * @param zipIn
//	 * @param filePath
//	 * @throws IOException
//	 */
//	public static void extractFile(ZipInputStream zipIn, String filePath)
//			throws IOException {
//		BufferedOutputStream bos = new BufferedOutputStream(
//				new FileOutputStream(filePath));
//		byte[] bytesIn = new byte[BUFFER_SIZE];
//		int read = 0;
//		while ((read = zipIn.read(bytesIn)) != -1) {
//			bos.write(bytesIn, 0, read);
//		}
//		bos.close();
//	}
//}
