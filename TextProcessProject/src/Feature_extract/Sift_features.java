package Feature_extract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Sift_features {
	static double delta=0.000001;
	static String dic_path=run.dic_path;
//	static String path="./corpus/beijing/total/2014-bj-total-seg.txt";
	static String path=run.segpath;
	static List<String> dic=new ArrayList<String>();
	//语义单元数
	public static List<String> unitnum(List<String> words){
		List<String> featurewords=new ArrayList<String>();
		words.stream().forEach(w->{
			char[] chars=w.toCharArray();
			int i=0;
			int en=0;
			int di=0;
			for(char c:chars){
				if((int)c>=65 && (int)c<=122){
					en=1;continue;
				}
				else if((int)c>=48 && (int)c<=57){
					di=1;continue;
				}
				else{
					i++;
				}
			}
			int num=i+en+di;
			if(num<=6 && num>=2){
				featurewords.add(w);
			}
		});
		return featurewords;
	}
	//贝叶斯筛选
	public static List<String> bayes(List<String> words,double a){
		List<String> featurewords=new ArrayList<String>();
		double p_s=p_s();
		words.stream().forEach(w->{
			int len=w.length();
			double pro=(calculate(w)+Math.log(p_s))/len;
			DecimalFormat df = new DecimalFormat(".00");
			String strpro=df.format(pro);
			pro=Double.parseDouble(strpro);
			if(pro>a){
				featurewords.add(w);
			}
			System.out.println(w+"   "+pro);
		});
		return finalwords(featurewords);
	}
	//重复筛选
	public static List<String> finalwords(List<String> words){
		List<String> finalwords=new ArrayList<String>();
		words.stream().forEach(w->{
				Iterator<String> iter=words.iterator();
				while(iter.hasNext()){
					String word=iter.next();
					if(word.contains(w) && !word.equals(w)){
						finalwords.add(w); 
						break;
					}
				}
		});
		words.removeAll(finalwords);
		return words;
	}
	//计算p(w/s)
	public static double calculate(String featureword){
		double pro=0.0;		
		String[] chars=featureword.split("");
		for(String c:chars){
			int num=0;
			int numall=0;
			try {
				BufferedReader reader=new BufferedReader(new FileReader(new File(dic_path)));
				String line=null;
				while((line=reader.readLine())!=null){
					line=line.trim();
					if(line.contains(c)){
						num++;
					}
					numall++;
				}
				reader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pro+=Math.log((double)(num+delta)/(numall+1));
		}
		return pro;
	}
	//计算p(s)
	public static double p_s(){
		double pro=0.0;
		int num=0;
		int numall=0;

		try {
			BufferedReader reader=new BufferedReader(new FileReader(new File(dic_path)));
			String line=null;
			while((line=reader.readLine())!=null){
				dic.add(line.trim());
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			BufferedReader reader=new BufferedReader(new FileReader(new File(path)));
			String line=null;
			while((line=reader.readLine())!=null){
				String[] words=line.split(" ");
				for(String s:words){
					if(dic.contains(s)){
						num++;
					}
					numall++;
				}
			}
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		pro=(double)num/numall;
		return pro;
	}
}