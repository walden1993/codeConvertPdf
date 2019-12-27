package com.pdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 文件处理 待完善
 */
public class FileUtils {

	// 被处理的文件绝对路径
	static String fromFile = "H:\\code\\qrcode\\action";
	
	static String toFile = "H:\\code\\qrcode\\to\\";
	
	// 读取被处理文件的读取编码
	static String fromFileCharset = "UTF-8";
	// 处理后文件的编码格式
	static String toFileCharset = "UTF-8";
	// 被处理文件的路径（不包括文件名称）
	static String fromFilePathWithOutFile = fromFile;
	// 被处理文件的文件名称（不包括路径）
	static String fromFileWithOutFilePath = fromFile.substring(fromFile.lastIndexOf("\\") + 1, fromFile.length());

	public static void main(String args[]) {
		
		File file = new File(toFile);
		
		if(!file.exists()) {
			file.mkdir();
			file.setWritable(true);
			file.setReadable(true);
			file.setExecutable(true);
		}
		
		FileUtils.excute(fromFile);
		System.out.println(fromFileWithOutFilePath);
	}

	public static void excute(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			//如果是文件
			if (file.isFile()) {
				System.out.println("---文件存在正在处理---");
				String tempString = null;
				String writeString = null;
				InputStreamReader isReader = null;
				FileInputStream fiStream = null;
				BufferedReader bReader = null;
				try {

					fiStream = new FileInputStream(filePath);
					isReader = new InputStreamReader(fiStream, fromFileCharset);
					bReader = new BufferedReader(isReader);

					String toFilePathWithFileName = toFile+"\\allCode.txt";
					System.out.println(toFilePathWithFileName);
					File wFile = new File(toFilePathWithFileName);
					wFile.createNewFile();
					OutputStreamWriter os = null;
					FileOutputStream fos = null;
					fos = new FileOutputStream(toFilePathWithFileName);
					os = new OutputStreamWriter(fos, toFileCharset);
					while ((tempString = bReader.readLine()) != null) {
						writeString = tempString;
						// 处理方式
						writeString = delHangHouZhuShi(writeString);
						writeString = replaceShuangyinhaoToDanyinhao(writeString);
						writeString = replaceDanyinhaoToShuangyinhao(writeString);
						writeString = delKongHang(writeString);
						if (!writeString.equals("")) {
							os.write(writeString);
							os.write("\n");
						}

					}
					if (os != null) {
						try {
							os.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						os = null;
					}
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						fos = null;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

				}

			}
			//文件夹
			if(file.isDirectory()) {
				File[] fileList = file.listFiles();
				String toFilePathWithFileName = toFile + "\\AllCode.txt";
				File wFile = new File(toFilePathWithFileName);
				if(!wFile.exists()) {
					try {
						wFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("文件创建异常");
					}
				}
				FileOutputStream fos = null;
				OutputStreamWriter os = null;
				try {
					fos = new FileOutputStream(wFile);
					os = new OutputStreamWriter(fos, toFileCharset);
				} catch (Exception e) {
					e.printStackTrace();
				}
				InputStreamReader isReader = null;
				FileInputStream fiStream = null;
				BufferedReader bReader = null;
				for (int i = 0; i < fileList.length; i++) {
					File tempFile = fileList[i];
					if(tempFile.isDirectory()) continue;
					try {
						fiStream = new FileInputStream(tempFile);
						isReader = new InputStreamReader(fiStream);
						bReader = new BufferedReader(isReader);
						
						String tempString = null;
						String writeString = null;
						while ((tempString = bReader.readLine()) != null) {
							writeString = tempString;
							// 处理方式
							writeString = delHangHouZhuShi(writeString);
							writeString = replaceShuangyinhaoToDanyinhao(writeString);
							writeString = replaceDanyinhaoToShuangyinhao(writeString);
							writeString = delKongHang(writeString);
							if (!writeString.equals("")) {
								os.write(writeString);
								os.write("\n");
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				try {
					os.flush();
					os.close();
					fos.flush();
					fos.close();
					System.out.println("文件处理完毕");
				} catch (Exception e) {
				}
			}
			
		} else {
			System.out.println("---文件不存在！");
		}

	}

	/**
	 * 去掉空行
	 */
	public static String delKongHang(String str) {
		if (str.trim() != null && !str.trim().equals("")) {
			return str;
		} else {
			return "";
		}
	}

	/**
	 * 去掉行后注释 //和/*和<!--
	 */
	public static String delHangHouZhuShi(String str) {
		if (str.contains("/*")) {
			return str.substring(0, str.indexOf("/*"));
		} else if (str.contains("//") && !str.contains("://")) {
			return str.substring(0, str.indexOf("//"));
		} else if (str.contains("<!--")) {
			return str.substring(0, str.indexOf("<!--"));
		} else if (str.contains("*")) {
			return str.substring(0, str.indexOf("*"));
		} else {
			return str;
		}
	}

	/**
	 * 双引号换单引号
	 */
	public static String replaceShuangyinhaoToDanyinhao(String str) {
		return str.replaceAll("\"", "\'");
	}

	/**
	 * 单引号换双引号
	 */
	public static String replaceDanyinhaoToShuangyinhao(String str) {
		return str.replaceAll("\'", "\"");
	}
}
