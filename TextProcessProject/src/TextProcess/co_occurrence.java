package TextProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import divideWords.NlpirMethod;

import fileutil.FileOperateUtils;

//judge whether a text contain the two words or not
public class co_occurrence {
	public static boolean is_contain(String text_path,String word1,String word2){
		String text=FileOperateUtils.getFileContent(text_path);
		if(text.contains(word1) && text.contains(word2)){
			return true;
		}
		else
			return false;
	}
	
	public static int calc_sum(String folder,String word1, String word2){
		int sum=0;
		File file=new File(folder);
		File[] files=file.listFiles();
		for(File a:files){
			String text_path=a.getAbsolutePath();
			if(is_contain(text_path,word1,word2)){
				sum++;
			}
			else{
				continue;
			}
		}
		return sum;
	}
	
	public static void main(String[] args){
		String year="2015";
		String total_path="./corpus/beijing/total/"+year+"-bj-total.txt";
		String corpus_path="./corpus/beijing/"+year;
		int sum=0;
		Map<String,List<String>> word_occurlist=new HashMap<String,List<String>>();
		NlpirMethod.Nlpir_init();
		String keyword=NlpirMethod.NLPIR_GetKeyWords(FileOperateUtils.getFileContent(total_path),30,false);
		String[] keywords=keyword.split("#");
		for(int i=0;i<keywords.length;i++){
			List<String> occurlist=new ArrayList<String>();
			for(int j=0;j<keywords.length;j++){
				sum=calc_sum(corpus_path,keywords[i],keywords[j]);
				if(sum!=0){
					String word_sum=keywords[j]+"_"+sum;
					occurlist.add(word_sum);
				}
			}
			word_occurlist.put(keywords[i], occurlist);
		}
		Iterator<String> iterator=word_occurlist.keySet().iterator();
		while(iterator.hasNext()){
			String word=iterator.next();
			List<String> list=word_occurlist.get(word);
			System.out.println(word+"	"+list);
			FileOperateUtils.writeFile(word+"	"+list,"./models/result/"+year+"co-occurence.txt");
		}
	}
}
