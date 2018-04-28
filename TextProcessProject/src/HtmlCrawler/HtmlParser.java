package HtmlCrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HtmlParser {
	static BufferedReader reader;
	public static String getHtmlContent(URL url, String encode) {
		StringBuffer contentBuffer = new StringBuffer();
		int responseCode = -1;
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");// IE�����������
			con.setConnectTimeout(60000);
			con.setReadTimeout(60000);
			// �����ҳ������Ϣ��
			responseCode = con.getResponseCode();
			if (responseCode == -1) {
				System.out.println(url.toString() + " : connection is failure...");
				con.disconnect();
				return null;
			}
			if (responseCode >= 400) // ����ʧ��
			{
				System.out.println("����ʧ��:get response code: " + responseCode);
				con.disconnect();
				return null;
			}

			InputStream inStr = con.getInputStream();
			InputStreamReader istreamReader = new InputStreamReader(inStr, encode);
			BufferedReader buffStr = new BufferedReader(istreamReader);

			String str = null;
			while ((str = buffStr.readLine()) != null)
				contentBuffer.append(str);
			inStr.close();
		} catch (IOException e) {
			e.printStackTrace();
			contentBuffer = null;
			System.out.println("error: " + url.toString());
		} finally {
			con.disconnect();
		}
		return contentBuffer.toString();
	}

	public static String getHtmlContent(String url, String encode) {
		if (!url.toLowerCase().startsWith("http://")) {
			//url = "http://" + url;
		}
		try {
			URL rUrl = new URL(url);
			return getHtmlContent(rUrl, encode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * ���ļ���д��htmlԴ�ļ�
	 * @param htmlpath �ļ�������
	 * @param yuanma  Ҫд���htmlԴ����
	 * @param i   ������д���Ŀ���ı��ļ�
	 */
	public static void write(String htmlpath ,String yuanma,int i){
		try {
			File folder=new File(htmlpath);
			if(!folder.exists()){
				folder.mkdirs();
			}
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream("./"+htmlpath+"/"+i+".html"), "UTF-8");
			   BufferedWriter writer = new BufferedWriter(write);
			   writer.write(yuanma);
			   write.flush();
			   writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ����ȡ����ҳ���Ĵ�ŵ�ָ�����ļ���
	 * @param htmlpath
	 * @param yuanma
	 * @param i
	 */
	public static void writecontent(String contentpath ,String yuanma,String i){
		try {
			File folder=new File(contentpath);
			if(!folder.exists()){
				folder.mkdir();
			}
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream("./"+contentpath+"/"+i+".txt"), "UTF-8");
			   BufferedWriter writer = new BufferedWriter(write);
			   writer.write(yuanma);
			   write.flush();
			   writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡURL�ļ�
	 * @param path
	 */
	public static void read(String path){
		try{
			  File file=new File(path);
			  if(file.isFile() && file.exists()){
				  InputStreamReader read=new InputStreamReader(new FileInputStream(file));
				  reader=new BufferedReader(read);
//				  reader.close();
			  }
			  else{
				  System.out.println("�Ҳ���ָ�����ļ���");
			  }
		  }catch(Exception e){
			  System.out.println("��ȡ�ļ����ݳ���");
			  e.printStackTrace();
		  }
	}
	/**
	 * ��searchlog�л�ȡhttp
	 * @param path   
	 */
	public static void acquirehttp(String Path){
		String Path1="shanghaihtml.txt";
		File file1=new File(Path1);		
		try{
			 // String Path="searchlog.txt";
			  File file=new File(Path);
			  if(file.isFile() && file.exists()){
				  InputStreamReader read=new InputStreamReader(new FileInputStream(file));
				  BufferedReader reader=new BufferedReader(read);
				  BufferedWriter  writer=new BufferedWriter(new FileWriter(file1));
				  String line=null;
				  while((line=reader.readLine())!=null){
					 // if(line.substring(line.length()-4).equals("html")){
					//	  line=line.substring(15);
						  System.out.println(line);
						  writer.write(line+"\n");
					  //}
				  }
				  reader.close();
				  writer.flush();
				  writer.close();
			  }else{
				  System.out.println("�Ҳ���ָ�����ļ���");
			  }
		  }catch(Exception e){
			  System.out.println("��ȡ�ļ����ݳ���");
			  e.printStackTrace();
		  } 
	} 
	public static void main(String argsp[]) throws IOException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		String starttime=df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		acquirehttp("url2010sh.txt");
		//System.out.println("12");
		read("shanghaihtml.txt");
		String line=null;
		String htmlpath="htmldata/shanghai";
		int i=1;
		try{
		while((line=reader.readLine())!=null){
				  System.out.println(line);
				  String text=getHtmlContent(line,"GBK");
				  if(!text.equals(""))
				      write(htmlpath,text,i);
				  i++;
		  }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		String endtime=df.format(new Date());
		System.out.println("��ʼʱ�䣺"+starttime);
		System.out.println("����ʱ�䣺"+endtime);
		reader.close();
	}
}