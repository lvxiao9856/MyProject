package divideWords;

import org.apache.log4j.Logger;
import org.junit.Test;

import fileutil.FileOperateUtils;

public class NlpirTest {
	Logger logger=Logger.getLogger(NlpirTest.class.getName());
    
	@Test
	/**
	 * 分词测试
	 */
    public void testParticiple(){
    	NlpirMethod.Nlpir_init();
    	System.out.println("aaaaaaaaa");
    	String sSrc=FileOperateUtils.getFileContent("./contentdata/北京/20130330.txt");
    	logger.debug("文章内容为---->"+sSrc);
    	String data=NlpirMethod.NLPIR_ParagraphProcess(sSrc, 0);
//    	FileOperateUtils.writeFile(data, "E:\\灵玖\\国防科技大学\\国防科大测试语料\\听out.txt");
    	logger.debug(data);
    }
//	@Test
	public void testKeyWord(){
		NlpirMethod.Nlpir_init();
    	//String sSrc=FileOperateUtils.getFileContent("");
		String sSrc=FileOperateUtils.getFileContent("./contentdata/北京/20130330.txt");
    	logger.debug("文章内容为---->"+sSrc);
    	String data=NlpirMethod.NLPIR_GetKeyWords(sSrc,10,false);
    	//FileOperateUtils.writeFile(data, "out.txt");
    	logger.debug(data);
	}
	@Test
	public void testNewWord(){
		NlpirMethod.Nlpir_init();
    	String sSrc=FileOperateUtils.getFileContent("./contentdata/北京/20130330.txt");
    	logger.debug("文章内容为---->"+sSrc);
    	String data=NlpirMethod.NLPIR_GetNewWords(sSrc,10,true);
//    	FileOperateUtils.writeFile(data, "E:\\灵玖\\国防科技大学\\国防科大测试语料\\听out.txt");
    	logger.debug(data);
	}
	@Test
	public void testAddUserWord(){
		NlpirMethod.Nlpir_init();
		int i=NlpirMethod.NLPIR_AddUserWord("国防");
		if(1==i){
			logger.debug("用户词添加成功");
		}else{
			logger.debug("用户词添加失败");
		}
	}
	@Test
	public void testImportUserDict(){
		
		NlpirMethod.Nlpir_init();
    	int addDictNum=NlpirMethod.NLPIR_ImportUserDict("userdictutf8.txt");
    	logger.debug("添加用户自定义词个数为："+addDictNum);

       }
	@Test
	public void testFileNewWord(){
		NlpirMethod.Nlpir_init();
    	String data=NlpirMethod.NLPIR_GetFileNewWords("./contentdata/北京/20130330.txt");
//    	FileOperateUtils.writeFile(data, "E:\\灵玖\\国防科技大学\\国防科大测试语料\\听out.txt");
    	logger.debug(data);
	}

	public static void main(String[] args) throws Exception{
		NlpirTest model = new NlpirTest();
//		model.testParticiple();
//		model.testKeyWord();
		model. testKeyWord();
	}
}
