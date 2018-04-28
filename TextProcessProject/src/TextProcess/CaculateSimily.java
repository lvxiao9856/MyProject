package TextProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import org.junit.Test;

public class CaculateSimily<E> {
	/**
	 * 计算两个向量相似度cos()
	 * @param key1
	 * @param key2
	 * @return
	 */
	public double Simily(List<String> key1,List<String> key2)
	{
		double similar=0.0;
		Vector<Map.Entry<String,Double>> vector1=ListToVector(key1);
		Vector<Map.Entry<String,Double>> vector2=ListToVector(key2);
		Vector<String> unionvector=VectorUnion(vector1,vector2);
		Vector<Double> vector11=VectorToVector(vector1,unionvector);
		Vector<Double> vector22=VectorToVector(vector2,unionvector);
		double fenmu=VectorMultiply(vector11,vector22);
		double fenzi=VectorMod(vector11)*VectorMod(vector22);
		similar=(double)fenmu/fenzi;
		System.out.println("相似度为：	"+similar);
		return similar;
	}
	/**
	 * 将列表转换为向量
	 * @param key
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "null" })
	public Vector<Entry<String, Double>> ListToVector(List<String> key)
	{
		Vector<Map.Entry<String,Double>> vector=new Vector<Map.Entry<String,Double>>();
		Iterator<String> iterator=key.iterator();
		while(iterator.hasNext())
		{
			Map<String, Double> map =new HashMap<String,Double>();
			String keywords=iterator.next();
			String[] words=keywords.split("/");
			map.put(words[0], Double.parseDouble(words[2]));
			vector.addAll(map.entrySet());
		}
		return vector;
	}
	/**
	 * 将两个向量合并成一个向量
	 * @param vector1
	 * @param vector2
	 * @return
	 */
	public Vector<String> VectorUnion(Vector<Map.Entry<String,Double>> vector1,Vector<Map.Entry<String,Double>> vector2)
	{
		int i,j;
		Vector<String> unionvector=new Vector<String>();
		for(i=0;i<vector1.size();i++)
		{
			for(j=0;j<vector2.size();j++)
			{
				if(vector2.get(j).getKey().equals(vector1.get(i).getKey()))
				{
					break;
				}
			}
			if(j==vector2.size())
			{
				unionvector.add(vector1.get(i).getKey());
			}
		}
		for(j=0;j<vector2.size();j++)
		{
			unionvector.add(vector2.get(j).getKey());
		}
		return unionvector;
	}
	/**
	 * 将向量转化为符合计算相似度的向量格式
	 * @param vector1
	 * @param vector2
	 * @return
	 */
	public Vector<Double> VectorToVector(Vector<Map.Entry<String,Double>> tovector,Vector<String> unionvector)
	{
		int i,j;
		Vector<Double> vector=new Vector<Double>();
		for(i=0;i<unionvector.size();i++)
		{
			for(j=0;j<tovector.size();j++)
			{
				if(unionvector.get(i).equals(tovector.get(j).getKey()))
				{
					vector.add(tovector.get(j).getValue());
					break;
				}	
			}
			if(j==tovector.size())
			{
				vector.add(0.0);
			}
		}
		return vector;
	}

	/**
	 * 两个向量相乘
	 * @param a
	 * @param b
	 * @return
	 */
	public double VectorMultiply(Vector<Double> a,Vector<Double> b)
	{
		double result=0.0;
		for(int i=0;i<a.size();i++)
		{
			result+=a.get(i)*b.get(i);
		}
		return result;
	}
	/**
	 * 求向量的模
	 * @param vector
	 * @return
	 */
	public double VectorMod(Vector<Double> vector)
	{
		double result=0.0;
		for(int i=0;i<vector.size();i++)
		{
			result+=Math.pow(vector.get(i),2);
		}
		result=Math.sqrt(result);
		return result;
	}
	public List<String> strtolist(String[] str)
	{
		List<String> list=new ArrayList<String>();
		for(int i=0;i<str.length;i++)
		{
			list.add(str[i]);
		}
		return list;
	}
}
