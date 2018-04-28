package summary;

import org.apache.log4j.Logger;

import summary.SummaryLibrary.CLibrarySM;

public class SummaryMethod {
	private static Logger logger = Logger.getLogger(SummaryMethod.class
			.getName());
	private static final int GBK_CODE = 0;// 默认支持GBK编码
	private static final int UTF8_CODE = 1;// UTF8编码
	private static final int BIG5_CODE = 2;// BIG5编码
	private static final int GBK_FANTI_CODE = 3;// GBK编码，里面包含繁体字
	static String argu = "";

	public static boolean Summary_init() {
		logger.debug("初始化开始");
		boolean init_summary = CLibrarySM.Instance.DS_Init(argu, 1);
		if (!init_summary) {
			System.err.println("初始化失败！");
			logger.debug("摘要初始化失败");
		}
		return init_summary;
	}

	/**
	 * 封装为三个参数的摘要提取方法
	 * 
	 * @param sText
	 *            :document content（将要提取摘要的文本）
	 * @param fSumRate
	 *            ：the percentage of the document’s summary.（文章摘要的百分比）
	 * @param iSumLen
	 *            ：document summary of the limited length。（文章摘要的长度）
	 * @return:Return summary string. Otherwise return NULL.(返回摘要内容）
	 */
	public static String DS_SingleDoc(String sText, float fSumRate, int iSumLen) {
		String nativeSummary = "";
		nativeSummary = CLibrarySM.Instance.DS_SingleDoc(sText, fSumRate, iSumLen);
		return nativeSummary;
	}

	/**
	 * 封装为一个参数的摘要提取方法
	 * 
	 * @param sText将要提取摘要的文本
	 * @return 文本摘要
	 */
	public static String DS_SingleDoc(String sText) {

		int len = sText.length();
		String nativeSummary = "";
		if (len < 100) {// 少于100字全部提取
			nativeSummary = CLibrarySM.Instance.DS_SingleDoc(sText, 0.0f, len);
		} else if (len >= 100 && len < 1000) {// 100和200之间提取100
			nativeSummary = CLibrarySM.Instance.DS_SingleDoc(sText, 0.00f, 100);
		} else if (len >= 1000 && len < 2000) {// 100和200之间提取100
			nativeSummary = CLibrarySM.Instance.DS_SingleDoc(sText, 0.00f, 200);
		} else if (len >= 2000) {// 1000以上提取200
			nativeSummary = CLibrarySM.Instance.DS_SingleDoc(sText, 0.00f, 300);
		}
		return nativeSummary;
	}

	public static void DS_Exit() {
		CLibrarySM.Instance.DS_Exit();
		logger.debug("摘要退出");
	}

	public static String DS_GetLastErrMsg() {
		return CLibrarySM.Instance.DS_GetLastErrMsg();

	}

}
