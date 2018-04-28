package TextProcess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import divideWords.NlpirMethod;
import fileutil.FileOperateUtils;

public class NewWordDiff {
//	@Test

	/**
	 * �ҵ������ַ������鲻ͬ���ַ���
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
		System.out.println("ǰ����ͬ�㣺	"+FilterNewWords(strsame));
		System.out.println("���߲�ͬ�㣺	"+FilterNewWords(strdif));
		/*		Iterator<String> a=str.iterator();
		while(a.hasNext())
		{
			System.out.println(a.next());
		}*/
		return strdif;
	}
	/**
	 * ��ȡ�´�
	 * @param filepath1
	 * @param filepath2
	 */
	public List<String> DifNewWord(String filepath1,String filepath2)
	{
		NlpirMethod.Nlpir_init();
		String text1=FileOperateUtils.getFileContent(filepath1);
		String text2=FileOperateUtils.getFileContent(filepath2);
		String newword1=NlpirMethod.NLPIR_GetNewWords(text1,20,true);
		System.out.println(filepath1.replaceAll("[^\\d]","")+"��newword:	"+newword1);
		String newword2=NlpirMethod.NLPIR_GetNewWords(text2,20,true);
		System.out.println(filepath2.replaceAll("[^\\d]","")+"��newword:	"+newword2);
		String[] str1=newword1.split("#");
		String[] str2=newword2.split("#");		
	   	
	   	return strdif(str1,str2);
	}
	/**
	 * �����´�
	 * @param words
	 * @return
	 */
	public List<String> FilterNewWords(List<String> words)
	{
		List<String> finalwords=new ArrayList<String>();
		for(int i=0;i<words.size();i++)
		{
			if(words.get(i).contains("ס��")|| words.get(i).contains("��ͥ") || words.get(i).contains("��")||words.get(i).contains("����"))
			{
				if(words.get(i).contains("��ҵ")|| words.get(i).contains("����")|| words.get(i).contains("��"))
					continue;
				else
					finalwords.add(words.get(i));
			}
		}
//		System.out.println("��ҵ��أ�	"+finalwords);
		return finalwords;
	}
	
	public static void main(String[] args)
	{
		NewWordDiff aa=new NewWordDiff();
		String filepath1="./�м���/ʱ������/2013.txt";
		String filepath2="./�м���/ʱ������/2014.txt";
		System.out.println(filepath1.replaceAll("[^\\d]","")+"---"+filepath2.replaceAll("[^\\d]","")+"�´ʷ��ַ���");
		System.out.println("=====================");
		aa.DifNewWord(filepath1, filepath2);
		NlpirMethod.NLPIR_EXIT();
	}
}
