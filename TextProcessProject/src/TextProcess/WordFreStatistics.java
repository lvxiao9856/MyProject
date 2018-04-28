package TextProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import divideWords.NlpirMethod;
import fileutil.FileOperateUtils;
import net.sf.json.JSONObject;


public class WordFreStatistics {
	/**
	 * 分词
	 * @param path需要分词的单文件路径
	 * @return  分词之后的字符串
	 */
	public String textpart(String path)
	{
		NlpirMethod.Nlpir_init();
		String sSrc=FileOperateUtils.getFileContent(path);
//		System.out.println(sSrc);
		List<String> stopwords=new ArrayList<String>();
		stopwords.add("[\\pP\\p{Punct}]");
		stopwords.add("[^\u4E00-\u9FA5]+");
		stopwords.add("(●|的|是|及|或|元|万|了|在|对|来源|发布|和|关于|有关|等)");
		Iterator<String> stopworditerator=stopwords.iterator();
		while(stopworditerator.hasNext())
		{
			String stopword=stopworditerator.next();
			sSrc = sSrc.replaceAll(stopword, "");
		}
		String data=NlpirMethod.NLPIR_ParagraphProcess(sSrc, 1);
		data=data.replaceAll("\\s[\u4E00-\u9FA5]\\/[\\w]+", "");
//		System.out.println(data);
		return data;
	}
	/**
	 * 但文本词频统计
	 * @param partdata  经分词之后的字符串
	 * @return  词名，词性，词频，词重
	 */
	public List<JSONObject> onefileStatistics(String partdata)
	{
		Map<String,JSONObject> wordst=new HashMap<String,JSONObject>();
		String[] words=partdata.split(" ");
		int sum=words.length;
		for(int i=0;i<words.length;i++)
		{
			JSONObject a=new JSONObject();
			String[] wordp=words[i].split("/");
			if(!wordst.keySet().contains(wordp[0]))
			{
				a.put("Wordp", wordp[1]);
				a.put("WordNum", 1);
				a.put("Wordf",(float)1/sum);
				wordst.put(wordp[0],a);
			}
			else
			{
				int aa=wordst.get(wordp[0]).getInt("WordNum");
				wordst.get(wordp[0]).put("WordNum", aa+1);
				wordst.get(wordp[0]).put("Wordf", (float)(aa+1)/sum);
			}
		}
		return sort(wordst);
	}
	
	/**
	 * map<String,JSONObject>  排序
	 * @param aa  待排序的map
	 * @return  排序之后的list
	 */
	 public List<JSONObject> sort(Map<String,JSONObject> aa)
	    {
//		    List<Map<String,Integer>> w=new ArrayList<Map<String,Integer>>();
		 List<JSONObject> w=new ArrayList<JSONObject>();
		    
		    List<Map.Entry<String,JSONObject>> list=new ArrayList<Map.Entry<String,JSONObject>>(aa.entrySet());
	    	Collections.sort(list,new Comparator<Map.Entry<String,JSONObject>>()
	    			 {
	    				 public int compare(Map.Entry<String,JSONObject> o1,Map.Entry<String,JSONObject> o2)
	    				 {
	    					 return (o2.getValue().getInt("WordNum")-o1.getValue().getInt("WordNum"));
	    				 }
	    			 });
	    	ListIterator<Map.Entry<String,JSONObject>> e=list.listIterator();
	    	int id=1;
	    	while(e.hasNext())
	    	{
	    		JSONObject b=new JSONObject();
	    		Map.Entry<String,JSONObject> m=e.next();
	    		b.put("WordName",m.getKey());
	    		b.put("Wordp",m.getValue().getString("Wordp"));
	    		b.put("WordNum",m.getValue().getInt("WordNum"));
	    		b.put("Wordf",m.getValue().getDouble("Wordf"));
	    		w.add(b);
	    		id++;
	    	}
	    	return w;  
	    }
	 
	 public void ClassifyByTime(String folderpath)
	 {
		String pathtotal="./contentdata/时间序列/上海/total.txt";
		String path2010="./contentdata/时间序列/上海/2010.txt";
		String path2011="./contentdata/时间序列/上海/2011.txt";
		String path2012="./contentdata/时间序列/上海/2012.txt";
		String path2013="./contentdata/时间序列/上海/2013.txt";
		String path2014="./contentdata/时间序列/上海/2014.txt";
		 File file=new File(folderpath);
		 File[] files=file.listFiles();
		 for(int i=0;i<files.length;i++)
		 {
			 String filename=files[i].getName();
			 String textpath=files[i].getAbsolutePath();
			 String textcontent=FileOperateUtils.getFileContent(textpath);
			 String regex="[^\u4E00-\u9FA5]";
			 textcontent=textcontent.replaceAll(regex, "");
			 FileOperateUtils.writeFile(textcontent, pathtotal);
			 String yeartime=filename.substring(0,4);
			 System.out.println(yeartime);
			 switch (yeartime)
			 {
			 case "2014": {FileOperateUtils.writeFile(textcontent, path2014);break;}
			 case "2013": {FileOperateUtils.writeFile(textcontent, path2013);break;}
			 case "2012": {FileOperateUtils.writeFile(textcontent, path2012);break;}
			 case "2011": {FileOperateUtils.writeFile(textcontent, path2011);break;}
			 default: FileOperateUtils.writeFile(textcontent, path2010);
			 }
		 }
	 }
	 
	public static void main(String[] args) throws Exception
	{
		WordFreStatistics aa=new WordFreStatistics();
////		aa.ClassifyByTime("./contentdata/上海");
//		String path="./contentdata/时间序列/上海";
//		File file=new File(path);
//		File[] files=file.listFiles();
//		for(File a:files)
//		{
//			
//			String name=a.getName();
//			String absupath=a.getAbsolutePath();
//			String data=aa.textpart(absupath);
//			System.out.println("正在处理"+name+".....");
//			List<JSONObject> word=aa.onefileStatistics(data);
//			Iterator<JSONObject> d=word.listIterator();
//			while(d.hasNext())
//			{
//				JSONObject one=d.next();
//				FileOperateUtils.writeFile(one.getString("WordName")+"	"+one.getString("WordNum")+"	"+one.getString("Wordp")+"	"+one.getString("Wordf"), "./中间结果/上海/"+name);
//			}
//			System.out.println(name+"的词频统计如下："+word);
//		}
		String path="./test/药品.txt";
		String data=aa.textpart(path);
		List<JSONObject> word=aa.onefileStatistics(data);
		Iterator<JSONObject> d=word.listIterator();
		while(d.hasNext())
		{
			JSONObject one=d.next();
			FileOperateUtils.writeFile(one.getString("WordName")+"	"+one.getString("WordNum")+"	"+one.getString("Wordp")+"	"+one.getString("Wordf"), "./test/药品stat.txt");
		}
		System.out.println("词频统计如下："+word);
	}
}
