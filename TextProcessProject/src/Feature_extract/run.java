package Feature_extract;

import java.util.List;

import pagerank.Node;
import tagger.Term;

public class run {
	static double thres=0.2;//左右邻阈值
	static double nbthres=-7;//贝叶斯筛选阈值
	static String year="2010";//研究年份
	static int top=500;                 //textrank   top N
	static String area="sh";          //地区
	static String area1="shanghai";     //地区
//	static String path="./corpus/beijing/total/2014-bj-total.txt";
	static String path="./corpus/"+area1+"/total/"+year+"-"+area+"-total.txt";
//	static String segpath="./corpus/beijing/total/seg/2014-bj-total-seg.txt";
	static String segpath="./corpus/"+area1+"/total/seg/"+year+"-"+area+"-total-seg.txt";
//	static String paths="./corpus/beijing/total/line/2014-bj-total-line.txt";
	static String paths="./corpus/"+area1+"/total/line/"+year+"-"+area+"-total-line.txt";
//	static String generate_dic="./corpus/beijing/total/seg/2014-bj-total-seg-union.txt";
	static String generate_dic="./corpus/"+area1+"/total/seg/"+year+"-"+area+"-total-seg-union.txt";
	static String dic_path="./dic.txt";
	public static void main(String[] args) throws Exception
	{
		List<Term> data=Candidate_features.textpart(path);
		List<Node> aa=Candidate_features.candidate(data,top);//topN
		List<String> ss=Candidate_features.match_dic(aa);
		List<String> gg=Extend_features.recursion(ss,1);//迭代开始值
		List<String> unitword=Sift_features.unitnum(gg);
		List<String> bayes=Sift_features.bayes(unitword,nbthres);//概率阈值
		System.out.println(bayes);
		Extend_features.wordfre(bayes);
		
	}
}
