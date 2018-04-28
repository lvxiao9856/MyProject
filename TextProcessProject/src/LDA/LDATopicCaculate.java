package LDA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LDATopicCaculate {
	//1、计算话题之间平均相似度
	//2、计算话题演化相似度，形成演化路径
	/**
	 * 从LDA形成的模型中读取各个话题，形成一个Map<String,List<String>>
	 * @param path
	 * @return
	 */
	public Map<String,List<String>> readTopic(String path)
	{
		Map<String,List<String>> topics=new HashMap<String,List<String>>();
		List<String> words = null;
		try {
			BufferedReader reader=new BufferedReader(new FileReader("./models/casestudy-en/model-final.twords"));
			String line,topic = null;
			try {
				while((line=reader.readLine())!=null)
				{	
					if(line.contains("Topic"))
					{	
						topics.put(topic, words);
						topic=line.trim();
						words=new ArrayList<String>();
					}
					else{
						words.add(line);
					}
				}
				topics.put(topic, words);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return topics;
	}
	/**
	 * caculate similarity each other by Map<String,List<String>>
	 * @param topics
	 * @return
	 */
	public Map<String,Double> caculatesimilar(Map<String,List<String>> topics)
	{
		Map<String,Double> amongsimilar=new HashMap<String,Double>();
		
		return amongsimilar;
	}
	/**
	 * caculate average similarity of certain tipic
	 * @param amongsimilar
	 * @return
	 */
	public double caculateavgsimilar(Map<String,Double> amongsimilar)
	{
		double avgsimilar=0.0;
		return avgsimilar;
	}
	/**
	 * caculate the max similarity of the current topic with the next year's topics
	 * @param a
	 * @param b
	 * @return
	 */
	public Map<String,Double> maxsimilar(Map<String,List<String>> a,Map<String,List<String>> b)
	{
		Map<String,Double> max=new HashMap<String,Double>();
		return max;
	}
	/**
	 * generate the evalution path according to the similarity
	 * @param list
	 * @return
	 */
	public List<List<String>> evaluatepath( Map<String,List<String>>...list)
	{
		List<List<String>> path=new ArrayList<List<String>>();
		return path;
 	}
}
