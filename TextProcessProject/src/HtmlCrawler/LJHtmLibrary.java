package HtmlCrawler;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class LJHtmLibrary {
	public interface CLibraryHP extends Library{
		CLibraryHP Instance = (CLibraryHP) Native.loadLibrary("LJHtmlParser",
				CLibraryHP.class);
	    
		//初始化
		public boolean HPR_Init(String s);
		//退出，释放资源
		public void HPR_Exit(); 
		//对HTML 进行解析（只有执行此函数后才能HPR_GetContent）
		public boolean HPR_ParseFile(String sHtmlFilename);
		public boolean HPR_ParseBuffer(String sHtmlBuffer, int nLen);
		//提取正文，返回NULL 时表示失败（调用TE_GetLastErrMsg 可获得错误提示）
		public String HPR_GetContent();
		//获得错误提示消息
		public String HPR_GetLastErrMsg();
		
	}
	}

