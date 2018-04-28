package summary;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class SummaryLibrary {
	public interface CLibrarySM extends Library {
		// 定义并初始化接口的静态变量
		CLibrarySM Instance = (CLibrarySM) Native.loadLibrary("LJSummary",
				CLibrarySM.class);

		/**
		 * 
		 * @param sLicenseCode
		 *            :Initial Directory Path, where Data directory stored. the
		 *            default value is 0, it indicates the initial directory is
		 *            current working directory
		 *            path(Data目录的父目录，可以直接传入""来表示寻找项目下的Data目录）
		 * @param code_type
		 *            : Encoding of input string, default is GBK_CODE (GBK
		 *            encoding), and it can be set with UTF8_CODE (UTF8
		 *            encoding) and BIG5_CODE (BIG5
		 *            encoding).(设置分词编码，只能处理相应初始编码的文件.
		 *            默认为GBK，0：GBK；1：UTF-8;2:BIG5;3:GBK_FANTI)
		 * @return:Return true if init succeed. Otherwise return false.
		 */
		public boolean DS_Init(String sLicenseCode, int code_type);
        /**
         * 
         * @param sText:document content（将要提取摘要的文本）
         * @param fSumRate：the percentage of the document’s summary.（文章摘要的百分比）
         * @param iSumLen：document summary of the limited length。（文章摘要的长度）
         * @return:Return summary string. Otherwise return NULL.(返回摘要内容）
         */
		public String DS_SingleDoc(String sText, float fSumRate, int iSumLen);

		public void DS_Exit();

		public String DS_GetLastErrMsg();
	}	
}
