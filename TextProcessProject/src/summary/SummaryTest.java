package summary;

import org.apache.log4j.Logger;
import org.junit.Test;

import fileutil.FileOperateUtils;

public class SummaryTest {
	private static Logger logger = Logger.getLogger(SummaryTest.class.getName());
	@Test
	/**
	 * 分词测试
	 */
    public void testSummary(){
		System.out.println(System.getProperty("user.dir"));
		SummaryMethod.Summary_init();
    	String sSrc=FileOperateUtils.getFileContent("./contentdata/北京/20130330.txt");
        logger.debug("文本内容为："+sSrc);
    	String data=SummaryMethod.DS_SingleDoc(sSrc);
    	//FileOperateUtils.writeFile(data, "G:\\eclipse-workspace\\TitleExtract\\14个省份建“五人小组” 听取巡视工作报告机制1.txt");
    	logger.debug("摘要内容为："+data);
    }
}
