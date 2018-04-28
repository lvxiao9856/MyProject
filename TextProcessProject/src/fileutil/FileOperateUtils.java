package fileutil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import divideWords.NlpirMethod;

import TextProcess.WordFreStatistics;

public class FileOperateUtils {
	
	/**
	 * @description：得到目录下的所有文件的路径
	 * @param dir
	 *            :要分析的文件夹的路径
	 * @return：文件夹中所有文件的绝对路径集合
	 * @throws Exception
	 * @author tanshuguo
	 */
	// 
	static ArrayList allFilesPath = new ArrayList();

	public static ArrayList getAllFilesPath(File dir) {

		if (!dir.isDirectory()) {
			String filePath = dir.getAbsolutePath();
			System.out.println(filePath);
			allFilesPath.add(filePath);
		} else {
			File[] fs = dir.listFiles();
			for (int i = 0; i < fs.length; i++) {

				if (fs[i].isDirectory()) {
					try {
						getAllFilesPath(fs[i]);
					} catch (Exception e) {
					}
				} else {
					String filePath = fs[i].getAbsolutePath();
					System.out.println(filePath);
					allFilesPath.add(filePath);
				}
			}
		}
		System.out.println("Utils.getAllFilesPath-文件个数---->" + allFilesPath.size());
		return allFilesPath;
	}

	/**
	 * @description：得到文件内容
	 * @param filePath
	 *            :要读取的文件路径
	 * @return 返回文件内容
	 * @author tanshuguo
	 */
	public static String getFileContent(String filePath) {
		StringBuffer sb = new StringBuffer();
		InputStreamReader isr = null;
		BufferedReader bufferedReader = null;
		// String fileContent="";
		try {
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				isr = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				bufferedReader = new BufferedReader(isr);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
				//	System.out.println("判断文件是否存在");
				//	System.out.println(lineTxt);
					sb.append(lineTxt);
				}

				isr.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		} finally {
			try {
				if (isr != null) {
					isr.close();
					isr = null;
				}
				if (bufferedReader != null) {
					bufferedReader.close();
					bufferedReader = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//System.out.println("--->" + sb.toString());
		// System.out.println("---->"+);
		return sb.toString();
	}

	/**
	 * @decription:把data写入targetFilePath中
	 * @param data
	 *            ：要写入的内容，采用编码为：utf-8
	 * @param targetFilePath
	 *            ：要写入到的文件路径
	 * @author tanshuguo
	 */
	public static void writeFile(String data, String targetFilePath) {
		OutputStreamWriter osw = null;
		BufferedWriter output = null;
		FileOutputStream fos=null;
		String encoding = "UTF-8";
//		String encoding = "gbk";
//		String encoding = "gb2312";

		try {
			File file = new File(targetFilePath);
			if (file.exists()) {
//				System.out.println("Utils.writeFile--文件存在，追加内容");
                fos=new FileOutputStream(file,true);
				osw = new OutputStreamWriter(fos,
						encoding);// 考虑到编码格式
				output = new BufferedWriter(osw);
				output.write(data+ "\r\n");// 
			} else {
				System.out.println("Utils.writeFile--文件不存在--已创建");
				File parentOfFile = file.getParentFile();
				if (!parentOfFile.exists()) {
					parentOfFile.mkdirs();
					System.out.println("Utils--writeFile--存储文件父路径-->" + parentOfFile.getPath());

				}
				// file.mkdirs();
				file.createNewFile();// 不存在则创建
				fos=new FileOutputStream(file, true);
				osw = new OutputStreamWriter(fos,
						encoding);// 考虑到编码格式
				output = new BufferedWriter(osw);
				output.write(data+ "\r\n");// 
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
		
				if (output != null) {
					output.close();
					output = null;
				}
				if (osw != null) {
					osw.close();
					osw = null;
				}
				if (fos != null) {
					fos.close();
					fos = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @decription:把data写入targetFilePath中
	 * @param data
	 *            ：要写入的内容，采用编码为：utf-8
	 * @param targetFilePath
	 *            ：要写入到的文件路径
	 * @param encoding:写文件时要采用的编码格式
	 */
	public static void writeFile(String data, String targetFilePath,String encoding) {
		OutputStreamWriter osw = null;
		BufferedWriter output = null;
		FileOutputStream fos=null;
		//String encoding = "utf-8";
//		String encoding = "gbk";
//		String encoding = "gb2312";

		try {
			File file = new File(targetFilePath);
			if (file.exists()) {
				System.out.println("Utils.writeFile--文件存在，追加内容");
                fos=new FileOutputStream(file, true);
				osw = new OutputStreamWriter(fos,
						encoding);// 考虑到编码格式
				output = new BufferedWriter(osw);
				output.write(data + "\r\n");
			} else {
				System.out.println("Utils.writeFile--文件不存在--已创建");
				File parentOfFile = file.getParentFile();
				if (!parentOfFile.exists()) {
					parentOfFile.mkdirs();
					System.out.println("Utils--writeFile--存储文件父路径-->" + parentOfFile.getPath());

				}
				// file.mkdirs();
				file.createNewFile();// 不存在则创建
				fos=new FileOutputStream(file, true);
				osw = new OutputStreamWriter(fos,
						encoding);// 考虑到编码格式
				output = new BufferedWriter(osw);
				output.write(data + "\r\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
		
				if (output != null) {
					output.close();
					output = null;
				}
				if (osw != null) {
					osw.close();
					osw = null;
				}
				if (fos != null) {
					fos.close();
					fos = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		FileOperateUtils aa=new FileOperateUtils();
//		aa.TextNto1("./contentdata/北京住建");
		String aaa="\\s[\u4E00-\u9FA5]\\/[\\w]+\\s";
		String text="zhongguiu    高n峰将恢复环境   兜兜风    的/ule     电放费……《》";
		String a = text.replaceAll(aaa, "");
		System.out.println(a+"\r"+"dfdf");
}

}