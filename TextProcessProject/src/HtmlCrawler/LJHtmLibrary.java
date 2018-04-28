package HtmlCrawler;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class LJHtmLibrary {
	public interface CLibraryHP extends Library{
		CLibraryHP Instance = (CLibraryHP) Native.loadLibrary("LJHtmlParser",
				CLibraryHP.class);
	    
		//��ʼ��
		public boolean HPR_Init(String s);
		//�˳����ͷ���Դ
		public void HPR_Exit(); 
		//��HTML ���н�����ֻ��ִ�д˺��������HPR_GetContent��
		public boolean HPR_ParseFile(String sHtmlFilename);
		public boolean HPR_ParseBuffer(String sHtmlBuffer, int nLen);
		//��ȡ���ģ�����NULL ʱ��ʾʧ�ܣ�����TE_GetLastErrMsg �ɻ�ô�����ʾ��
		public String HPR_GetContent();
		//��ô�����ʾ��Ϣ
		public String HPR_GetLastErrMsg();
		
	}
	}

