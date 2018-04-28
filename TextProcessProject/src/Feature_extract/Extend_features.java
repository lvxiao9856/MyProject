package Feature_extract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

import pagerank.Node;
import pagerank.Word;
import divideWords.NlpirMethod;
import HelpAlgri.Word_leftright;

public class Extend_features {
	static List<String> unionword=new ArrayList<String>();
	//计算左右邻集合
	public static List<Word_leftright> left_right_set(List<String> candidate_words,String path){
		List<Word_leftright> wordinfo_list=new ArrayList<Word_leftright>();
			candidate_words.stream().forEach(w-> {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(new File(path)));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Map<String,Integer> b_left=new HashMap<String,Integer>();
				Map<String,Integer> b_right=new HashMap<String,Integer>();
			try {
				String line=null;
				while((line=reader.readLine())!=null){
					if(!line.contains(w) || line.split(" ").length<2){
						continue;
					}
					else{
						List<String> aa=Arrays.asList(line.split(" "));
						int index=aa.indexOf(w);
						if(index==0){
							int index_right=index+1;
							String right=aa.get(index_right);
							if(b_right.keySet().contains(right)){
								b_right.put(right, (b_right.get(right)+1));
							}
							else if(!right.equals("")){
								b_right.put(right, 1);
							}
						}
						else if(index==(aa.size()-1)){
							int index_left=index-1;
							String left=aa.get(index_left);
							if(b_left.keySet().contains(left)){
								b_left.put(left, (b_left.get(left)+1));
							}
							else if(!left.equals("")){
								b_left.put(left, 1);
							}
						}
						else if(0 < index && index <(aa.size()-1)){
							int index_left=index-1;
							int index_right=index+1;
							String left=aa.get(index_left);
							String right=aa.get(index_right);
							if(b_left.keySet().contains(left)){
								b_left.put(left, (b_left.get(left)+1));
							}
							else if(!left.equals("")){
								b_left.put(left, 1);
							}
							if(b_right.keySet().contains(right)){
								b_right.put(right, (b_right.get(right)+1));
							}
							else if(!right.equals("")){ 
								b_right.put(right, 1);
							}
						}
						
					}
				}
				reader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			Word_leftright wordlr=new Word_leftright();
			Map<String,Map<String,Integer>> left_right=new HashMap<String,Map<String,Integer>>();
			left_right.put("left", b_left);
			left_right.put("right", b_right);
			wordlr.setword(w);
			wordlr.setleft_right(left_right); 
			wordinfo_list.add(wordlr); 
			});
		
		wordinfo_list.stream().forEach(n->System.out.println(n.getword()+"\t"+n.getleft_right()));
		return wordinfo_list;
	}
	//扩展特征词
	public static List<String> unionwords(List<Word_leftright> wordinfo_list,double thres,int i){
		List<String> unionwords=new ArrayList<String>();

		wordinfo_list.stream().forEach(n -> {String word=n.getword();
		Map<String,Map<String,Integer>> left_right=n.getleft_right();
		List<String> unionword0 = new ArrayList<String>();
		List<String> unionword1 = new ArrayList<String>();
		List<Map<String,Integer>> h =new ArrayList<Map<String,Integer>>();
		Iterator<String> iter=left_right.keySet().iterator();
		while(iter.hasNext()){
			String flag=iter.next();
			if(flag.equals("left")){
				h.add(0, left_right.get(flag));
			}
			if(flag.equals("right")){
				h.add(1, left_right.get(flag));
			}
		}
//		System.out.println(h);
//		Iterator<Map<String,Integer>> iter1=h.iterator(); 
		String flag="left";	
//		while(iter1.hasNext()){	
			
			if(flag.equals("left")){
				int sum=0;//左邻集合总数
				int left_i=0;//左邻集合数
				Map<String,Integer> info=h.get(0);
				Iterator<String> iter2=info.keySet().iterator();
				while(iter2.hasNext()){
					String wordname=iter2.next();
					sum+=info.get(wordname);
					left_i++;
				}
				Iterator<String> iter3=info.keySet().iterator();
				while(iter3.hasNext()){
					String wordname=iter3.next();
					double rate=info.get(wordname)/(double)sum;
					if(rate > thres && sum>1 && (wordname+word).length()<=7){
						unionword0.add(wordname+word);
					}else{
						unionword0.add(word);
					}
				}
			
			}
			flag="right";
			
			if(flag.equals("right")){
				int sum=0;//右邻集合总数
				int right_i=0;//右邻集合数
				Map<String,Integer> info=h.get(1);
				Iterator<String> iter11=info.keySet().iterator();
				while(iter11.hasNext()){
					String wordname=iter11.next();
					sum+=info.get(wordname);
					right_i++;
				}
				Iterator<String> iter2=info.keySet().iterator();
				while(iter2.hasNext()){
					String wordname=iter2.next();
					if(info.get(wordname)/(double)sum > thres && sum>1){
						Iterator<String> k=unionword0.iterator();
						while(k.hasNext()){
							String wordk=k.next();
							String ff=wordk+wordname;
							if(ff.length()<=7){
								unionword1.add(ff);
							}else{
								unionword1.add(wordk);
							}							
						}
					}
//					unionword1=unionword0;
				}
			}
			if(unionword1.isEmpty()){
				unionword1=unionword0;
			}
//		}
		if(i==1){
			unionword1.stream().filter(o->!o.equals(word) && !unionwords.contains(o)).forEach(u-> unionwords.add(u));		
		}else{
			unionword1.stream().filter(o->!unionwords.contains(o)).forEach(u-> unionwords.add(u));
		}
		unionword0.clear();
		unionword1.clear();
		h.clear();
		}); 
		
		System.out.println(unionwords);
//		String path="";
//		List<Word_leftright> word_lr=left_right_set(unionwords,path);
		return unionwords;
	}
	//将原始词典按照扩展后的特征词进行合并
	public static String union_dic(List<String> unionwords,String original,String generate_dic){
		NlpirMethod.Nlpir_init();
//		String generate_dic="./corpus/beijing/total/seg/2014-bj-total-seg-union.txt";		
		unionwords.stream().forEach(n->NlpirMethod.NLPIR_AddUserWord(n));
		NlpirMethod.NLPIR_FileProcess(original, generate_dic,0);
//		new File(original_dic).delete();
//		new File(generate_dic).renameTo(new File(original_dic));
//		NlpirMethod.NLPIR_EXIT();
		return generate_dic;
	}
	//递归扩展
	public static List<String> recursion(List<String> candidate_words,int i){
//		List<String> unionword=new ArrayList<String>();
		double thres=run.thres;
//		String path="./corpus/beijing/total/2014-bj-total-seg.txt";
		String path=run.segpath;
//		String original="./corpus/beijing/total/line/2014-bj-total-line.txt";
		String original=run.paths;
//		String generate_dic="./corpus/beijing/total/seg/2014-bj-total-seg-union.txt";
		String generate_dic=run.generate_dic;
		if(i<3){
			if(i>1){
				path=generate_dic;
			}
			List<Word_leftright> word_lr=left_right_set(candidate_words,path);
			candidate_words=unionwords(word_lr,thres,i);
			unionword=candidate_words;
/*			if(i==2){
				unionword=candidate_words;
			}*/
			union_dic(candidate_words,original,generate_dic);
			i++;
			recursion(candidate_words,i);	
		}
//		System.out.println("............"+i+candidate_words);
		return unionword;
	}
	//计算最终词频
	public static List<Word> wordfre(List<String> words){
		List<Word> wordfre=new ArrayList<Word>();
		
//		String nowdic="./corpus/beijing/total/line/2014-bj-total-line.txt";
		String nowdic=run.paths;
		words.stream().forEach(n->{
			try {
				BufferedReader reader=new BufferedReader(new FileReader(new File(nowdic)));
				Word node=new Word();
				int i=0;
				String line=null;
				while((line=reader.readLine())!=null){
					if(line.contains(n)){												
					    i++;
					}
				}
				node.setword(n);
				node.setnum(i);
				wordfre.add(node);
				reader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println("排序前：");
		wordfre.stream().forEach(m->System.out.println(m.getword()+"\t"+m.getnum()));
		Collections.sort(wordfre, new Comparator<Word>(){
			 public int compare(Word t1, Word t2){
			 return (t2.getnum()-t1.getnum());
			 }
			});
/*		List<Integer> transactionsIds = transactions.parallelStream().
				 filter(t -> t.getType() == Transaction.GROCERY).
				 sorted(comparing(Transaction::getValue).reversed()).
				 map(Transaction::getId).
				 collect(toList());*/
/*		List<String> wordss=wordfre.parallelStream().
				filter(t->t.getword()!=null).
				sorted(comparing(Word::getnum).reversed()).
				collect(toList());*/
		System.out.println("--------------------------"+"\n"+"排序后：");
		wordfre.stream().forEach(m->{System.out.println(m.getword()+m.getnum());});
		return wordfre;
	}
}
