package com.pdf;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import cn.hutool.core.io.FileUtil;

/**
 * @author Bill Tu(tujiyue/iwtxokhtd) Jun 6, 2011[4:10:35 PM]
 * 生成PDF
 */
public class PDFUtils {
	private final static String RESULT_FILE = "";//默认替换后缀为PDF
	private final static String FILE_PATH = "H:\\code\\qrcode\\to\\AllCode.txt";//需要转换的文件路径
	private final static String YEMEI = "兴华融扫码管理系统V1.0";//页眉
	private final static String COMPANY = "深圳市兴华融网络科技股份有限公司";//页尾公司名称；

	public static void main(String[] args) throws Exception {
		createPDF();
	}
	
	public static String getResultPath() throws Exception {
		String resultPath = null;
		if(null==RESULT_FILE || "".equals(RESULT_FILE)) {
			if(null==FILE_PATH || "".equals(FILE_PATH)) {
				throw new Exception("请设置要转换成PDF的文件路径");
			}
			
			resultPath = FILE_PATH;
			resultPath = resultPath.substring(0, resultPath.lastIndexOf("."))+".pdf";
		}else {
			resultPath = RESULT_FILE;
		}
		return resultPath;
	}
	

	public static void createPDF() throws Exception {

		// 1.新建document对象
		Document document = new Document();

		// 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
		// 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(getResultPath()));
		//华融基金代销系统V1.0源代码
		writer.setPageEvent(new PDFBuilder(YEMEI,COMPANY));
        //设置分页页眉页脚字体
        final BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
        final Font fontDetail = new Font(bf, 9, Font.NORMAL);// 数据体字体
        
		// 3.打开文档
		document.open();

		BufferedReader reader = FileUtil.getReader(FILE_PATH, Charset.defaultCharset());

		List orderedList = new List(List.ORDERED);
		reader.lines().forEach(line -> {
			line = line.replaceAll("\t", "    ");
			ListItem item = new ListItem(line);
			item.setFont(fontDetail);
			orderedList.add(item);
		});
		
		// 4.添加一个内容段落
		document.add(orderedList);

		// 5.关闭文档
		document.close();

	}

	public static void updatePDF() throws MalformedURLException, IOException, DocumentException {
		// 读取pdf文件
		PdfReader pdfReader = new PdfReader(RESULT_FILE);

		// 修改器
		PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("H:/code/test10.pdf"));

		pdfStamper.close();
	}

	public static void excute(String fromFile) {
		// TODO Auto-generated method stub
		
	}

}
