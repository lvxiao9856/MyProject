package HtmlCrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


import HtmlCrawler.LJHtmLibrary.CLibraryHP;
import HelpAlgri.ListSort;
public class HtmlContent {
	
	private static Logger logger = Logger.getLogger(HtmlContent.class.getName());
    static String argu="";
	public static boolean HP_init() {
		logger.debug("��ʼ����ʼ");
		boolean init_HP = CLibraryHP.Instance.HPR_Init(argu);
		if (!init_HP) {
			System.err.println("��ʼ��ʧ�ܣ�");
			logger.debug("Html��ʼ��ʧ��");
		}
		return init_HP;
	}
	//boolean HPR_ParseFile(String sHtmlFilename)
	public static boolean HP_PF(String HFname){
		return CLibraryHP.Instance.HPR_ParseFile(HFname);
	}
	//boolean HPR_ParseBuffer(String sHtmlBuffer, int nLen);
	public static boolean HP_PB(String HBname,int n){
		return CLibraryHP.Instance.HPR_ParseBuffer(HBname,n);
	}
	//String HPR_GetContent();
	public static String HP_GC(){
		return CLibraryHP.Instance.HPR_GetContent();
	}
	//void HPR_Exit();
	public static void DS_Exit() {
		CLibraryHP.Instance.HPR_Exit();
		logger.debug("�˳�");
	}
	// String HPR_GetLastErrMsg();
	public static String HP_GLEM(){
		return CLibraryHP.Instance.HPR_GetLastErrMsg();
	}
	/**
	 * ���ı�����ȡʱ��
	 * @param content
	 * @return
	 */
	public String TiTime(String content)
	{
		String time=null;
		String time1="1";
		String regx="����ʱ�䣺[\\d]{4}\\-[\\d]+\\-[\\d]+";
		Pattern pattern=Pattern.compile(regx);
		Matcher matcher=pattern.matcher(content);
		while(matcher.find())
		{
			time=matcher.group();
			if(!time.isEmpty())
			{
				String regx1="[\\d]{4}\\-[\\d]+\\-[\\d]+";
				Pattern pattern1=Pattern.compile(regx1);
				Matcher aa1=pattern1.matcher(time);
				while(aa1.find())
				{
					time1=aa1.group();
					System.out.println(time1);
				}
			}		
		}
		return time1;
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args)
	{
		HP_init();
		String b = null;
		String time= null;
		String contentpath="contentdata/����ס��";//��ҳ���Ĵ���ļ���
		HtmlContent hc=new HtmlContent();
		File[] files=new File("htmldata/beijing").listFiles();
		ListSort.orderByDate(files);
		for(int i=1;i <= files.length;i++)
		{
			HP_PF(files[i-1].getPath());
			b=HP_GC();
			if(!b.isEmpty())
			{
				time=hc.TiTime(b);
				if(!time.equals("1"))
				{
					HtmlParser.writecontent(contentpath,b,time);
				}	
			}	
		}
		DS_Exit();
		}
}
