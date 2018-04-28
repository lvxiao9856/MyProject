package TextProcess;

import java.util.ArrayList;
import java.util.List;

import summary.SummaryMethod;

import divideWords.NlpirMethod;
import fileutil.FileOperateUtils;

public class KeyWordSummary {
	/**
	 * 找到两个字符串数组不同的字符串
	 * @param str1
	 * @param str2
	 */
	@SuppressWarnings("null")
	public List<String> strdif(String[] str1,String[] str2)
	{
		int i,j;
		List<String> strdif=new ArrayList<String>();
		List<String> strsame=new ArrayList<String>();
		for(j=0;j<str2.length;j++)
		{	
			for(i=0;i<str1.length;i++)
			{
				if(str2[j].replaceAll("[^\u4E00-\u9FA5]", "").equals(str1[i].replaceAll("[^\u4E00-\u9FA5]", "")))
				{
					strsame.add(str2[j]);
					break;
				}
			}
			if(i==str1.length)
			{  
				strdif.add(str2[j]);
			}
		 
		}
		System.out.println("前后相同点：	"+FilterKeyWords(strsame)+"\n"+strsame);
		System.out.println("后者不同点：	"+FilterKeyWords(strdif)+"\n"+strdif);
		/*		Iterator<String> a=str.iterator();
		while(a.hasNext())
		{
			System.out.println(a.next());
		}*/
		return strdif;
	}
	/**
	 * 获取新词
	 * @param filepath1
	 * @param filepath2
	 */
	public List<String> DifKeyWord(String filepath1,String filepath2)
	{
		NlpirMethod.Nlpir_init();
		SummaryMethod.Summary_init();
		String keyword1=NlpirMethod.NLPIR_GetKeyWords(FileOperateUtils.getFileContent(filepath1),50,true);
		System.out.println(filepath1.replaceAll("[^\\d]","")+"的keyword:	"+keyword1);
///		System.out.println("摘要：	"+summary(filepath1));
		String keyword2=NlpirMethod.NLPIR_GetKeyWords(FileOperateUtils.getFileContent(filepath2),50,true);	
		System.out.println(filepath2.replaceAll("[^\\d]","")+"的keyword:	"+keyword2);
//		System.out.println("摘要：	"+summary(filepath2));
		String[] str1=keyword1.split("#");
		String[] str2=keyword2.split("#");
		for(int i=0;i<str1.length;i++){
			System.out.print(i+":"+str1[i]+"\t");
		}
		System.out.print("\n");
		for(int i=0;i<str2.length;i++){
			System.out.print(i+":"+str2[i]+"\t");
		}
		CaculateSimily aa=new CaculateSimily();
		List<String> a=aa.strtolist(str1);
		List<String> b=aa.strtolist(str2);
		aa.Simily(a,b);
	   	return strdif(str1,str2);
	}
	public List<String> DifKeyWordtext(String text1,String text2)
	{
		NlpirMethod.Nlpir_init();
		SummaryMethod.Summary_init();
		String keyword1=NlpirMethod.NLPIR_GetKeyWords(text1,50,true);
		String keyword2=NlpirMethod.NLPIR_GetKeyWords(text2,50,true);	
		String[] str1=keyword1.split("#");
		String[] str2=keyword2.split("#");
		CaculateSimily aa=new CaculateSimily();
		List<String> a=aa.strtolist(str1);
		List<String> b=aa.strtolist(str2);
		aa.Simily(a,b);
	   	return strdif(str1,str2);
	}
	/**
	 * 过滤关键词
	 * @param words
	 * @return
	 */
	public List<String> FilterKeyWords(List<String> words)
	{
		List<String> finalwords=new ArrayList<String>();
		for(int i=0;i<words.size();i++)
		{
			if(words.get(i).contains("住房")|| words.get(i).contains("家庭") || words.get(i).contains("租")||words.get(i).contains("保障"))
			{
				if(words.get(i).contains("企业")|| words.get(i).contains("机构")|| words.get(i).contains("部"))
					continue;
				else
					finalwords.add(words.get(i));
			}
		}
		return finalwords;
	}
	
	/**
	 * 获取摘要
	 * @param path
	 * @return
	 */
	public String summary(String path)
	{
		String sSrc=FileOperateUtils.getFileContent(path);
		String data=SummaryMethod.DS_SingleDoc(sSrc);
		return data;
	}
	
	public StringBuffer uniontext(String[] textpath,int n)
	{
		StringBuffer text=new StringBuffer();
		for(int i=0;i<n;i++)
		{
			String texti=FileOperateUtils.getFileContent(textpath[i]);
			text.append(texti);
		}
		return text;
	}
	
	public static void main(String[] args)
	{
		KeyWordSummary a=new KeyWordSummary();
		String[] textpath={"./corpus/beijing/total/2010-bj-total.txt","./corpus/beijing/total/2011-bj-total.txt","./corpus/beijing/total/2012-bj-total.txt",
		           "./corpus/beijing/total/2013-bj-total.txt","./corpus/beijing/total/2014-bj-total.txt","./corpus/beijing/total/2015-bj-total.txt"};
//		String path1="./中间结果/时间序列/total.txt";
//		String path2="./contentdata/时间序列/上海/total.txt";
//		a.DifKeyWord(path2,path1);
		NlpirMethod.Nlpir_init();
		SummaryMethod.Summary_init();
//		System.out.println(a.summary("G:/Myeclipse/TextProcessProject/corpus/beijing/2015/20150107-关于北京丽富房地产开发有限公司等房地产.txt"));
		int n=5;  //变量N决定研究年份stdin
		System.out.println(textpath[n-1].replaceAll("[^\\d]","")+"---"+textpath[n].replaceAll("[^\\d]","")+"关键词分析\n"+"=====================");
		a.DifKeyWord(textpath[n-1],textpath[n]);
		String uniontext=a.uniontext(textpath,n).toString();
		System.out.println("当前与以前所有对比分析：\n===========================");
		a.DifKeyWordtext(uniontext,FileOperateUtils.getFileContent(textpath[n]));
		
		
		SummaryMethod.DS_Exit();
		NlpirMethod.NLPIR_EXIT();
	}
}
