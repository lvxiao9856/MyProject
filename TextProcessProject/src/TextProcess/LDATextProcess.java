package TextProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import divideWords.NlpirMethod;
import fileutil.FileOperateUtils;

public class LDATextProcess {
	public void TextNto1(String path)
	{
		String outpath="./models/casestudy-en/2010-bj.txt";
		int i=0;
		File file=new File(path);
		File[] files=file.listFiles();
		System.out.println(files.length);
		FileOperateUtils.writeFile(files.length+"",outpath);
		
		for(File a:files)
		{
			WordFreStatistics aa=new WordFreStatistics();
			String apath=a.getAbsolutePath();
			String str=FileOperateUtils.getFileContent(apath);
			if(!str.isEmpty()) 
			{
				str=str.replaceAll("[^\u4E00-\u9FA5]+","");
				str=str.replaceAll("\\s+","");
				String data=NlpirMethod.NLPIR_ParagraphProcess(str, 0);
				List<String> stopwords=new ArrayList<String>();
				stopwords.add("[\\pP\\p{Punct}]");
//				stopwords.add("[^\u4E00-\u9FA5]+");
				stopwords.add("(●|的|是|及|或|元|万|了|在|对|来源|发布|和|关于|" +
						"有关|等|本|公司|董事会|集团|有限公司|基金|北京市|记者|通知|北京|表示|部分|开始|目前" +
						"|机构|单位|我们|年月|这个|部门|合同|相关|专项|进行|市|区|年|号|时间|浏览|次数)");
				Iterator<String> stopworditerator=stopwords.iterator();
				while(stopworditerator.hasNext())
				{
					String stopword=stopworditerator.next();
					data = data.replaceAll(stopword+" ", "");
				}
				
				System.out.println(data);
				data=data.replaceAll("\\s[\u4E00-\u9FA5]\\s", " ").replaceAll("\\s[\u4E00-\u9FA5]\\s", " ").replaceAll("\\s[\u4E00-\u9FA5]\\s", " ");
			
				FileOperateUtils.writeFile(data,outpath);
				i++;
			}
		}
		NlpirMethod.NLPIR_EXIT();
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		NlpirMethod.Nlpir_init();
		LDATextProcess aa=new LDATextProcess();
		//aa.TextNto1("./contentdata/北京住建");
/*		String text=FileOperateUtils.getFileContent("./中间结果/时间序列/total.txt");
		String newwords=NlpirMethod.NLPIR_GetNewWords(text,1000,false);
		String[] wordsstr=newwords.split("#");
		for(int i=0;i<wordsstr.length;i++)
		{
			NlpirMethod.NLPIR_AddUserWord(wordsstr[i]);
		}
		System.out.println(newwords);*/
		aa.TextNto1("./corpus/beijing/2010");
/*		String test="ssdfkjk3040390http://www.baidu.com/ 中 国 中国 尽快交罚款的676878  发布时间：2014-10-30";
		String rule="[\\d]{4}\\-[\\d]+\\-[\\d]+";
		String rule1="[\\d]{4}\\-[\\d]+\\-[\\d]+";
		Pattern aaa=Pattern.compile(rule);
		Boolean as=aaa.matches(rule, test);
		System.out.println(as);
		Matcher matcher=aaa.matcher(test);
		String bb = null;
		while(matcher.find())
		{
			bb=matcher.group();
			System.out.println(bb);
		}
		Pattern aaa1=Pattern.compile(rule1);
		Matcher matcher1=aaa1.matcher(bb);
		while(matcher1.find())
		{
			String bb1=matcher1.group();
			System.out.println(bb1);
		}*/
		
/*		String aaa=" [\u4E00-\u9FA5] ";
		Pattern pattern = Pattern.compile(aaa);
		Matcher matcher = pattern.matcher(test);
		while(matcher.find())
		{
			int i=0;
			String bb=matcher.group();
			String cc=test.replaceAll(aaa,"");
			System.out.println(cc);
			System.out.println(bb);

		}
		Pattern pattern1 = Pattern.compile("[, |]+");
		String[] strs = pattern1.split("Java Hello World  Java,Hello,,World|Sun");
		for (int i=0;i<strs.length;i++) {
		    System.out.println(strs[i]);
		}
		//System.out.println(matcher.matches());
*/	}
}
