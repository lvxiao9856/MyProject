package Feature_extract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pagerank.Node;
import pagerank.UndirectWeightedGraph;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import tagger.JiebaTagger;
import tagger.Term;
import net.sf.json.JSONObject;
import HelpAlgri.Word_leftright;
import TextProcess.WordFreStatistics;
import divideWords.NlpirMethod;
import fileutil.FileOperateUtils;

public class Candidate_features {
	/**
	 * 分词
	 * @param path需要分词的单文件路径
	 * @return  分词之后的字符串
	 */
	public static List<Term> textpart(String path)
	{
		NlpirMethod.Nlpir_init();
		String sSrc=FileOperateUtils.getFileContent(path);
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
		List<Term> words=new ArrayList<Term>();
		
		List<String> word_pos=Arrays.asList(data.split(" "));
//		word_pos.stream().forEach(System.out::println);
		word_pos.stream().forEach(a -> {Term t=new Term();t.setText(a.split("/")[0]);t.setPos(a.split("/")[1]);words.add(t);});
//		words.stream().forEach(n -> System.out.println(n.getText()+"\t"+n.getPos())); 
//		data=data.replaceAll("\\s[\u4E00-\u9FA5]\\/[\\w]+", "");
		return words;
	}
	public static String filter(String text){
		String result = null;
		String regex="\\s[\u4E00-\u9FA5]+\\/(n|v|vn|a|vi)";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(text);
		while(matcher.find()){
			String word=matcher.group();
			result=result+" "+word;
		}
		return result;
	}
    public static List<Node> candidate(List<Term> terms,int topN) throws Exception {

        Set<String> filt = Sets.newHashSet();
        filt.add("n");
        filt.add("v");
        filt.add("vn");
        filt.add("vi");
        filt.add("a");

        // 1.build text graph

        // 1.1 get tokens
//        List<Term> terms = new JiebaTagger().seg(text);
//        terms.stream().forEach(w -> System.out.println(w.getText()+"\t"+w.getPos()));
        // 1.2 filt pos

		Term[] filtTerms = Lists.newArrayList(terms.stream().filter(t -> {
           return filt.contains(t.getPos());
		}).iterator()).toArray(new Term[0]);

        // 1.3 build graph
        CounterMap cm = new CounterMap();

        int span = 5;

        for (int i = 0; i < filtTerms.length; ++i ) {
            for (int j = i + 1; j < i + span && j < filtTerms.length; ++j) {
                cm.incr(filtTerms[i].getText() + ":" + filtTerms[j].getText());
            }
        }
        // TODO we can do more optimization as follow: 1) pos 2) stopwords 3) rational span
        UndirectWeightedGraph uwg = new UndirectWeightedGraph();
        for (String pair : cm.countAll().keySet()) {
//			System.out.println(pair + "\t" + cm.get(pair));
            String[] segs = Lists.newArrayList(Splitter.on(":").split(pair)).toArray(new String[0]);
            uwg.addNode(segs[0], segs[1], cm.get(pair));
        }
        // 2. rank
        uwg.rank();
        List<Node> list = uwg.topk(topN);
        list.stream().forEach(w -> System.out.println(w.label + "\t" + w.rank));
        return list;
    }
    public static List<String> match_dic(List<Node> words){
//    	String dic_path="./dic.txt";
    	String dic_path=run.dic_path;
    	List<String> dic=new ArrayList<String>();
    	List<String> matched_word=new ArrayList<String>();
    	try {
			BufferedReader reader=new BufferedReader(new FileReader(new File(dic_path)));
			String line=null;
			try {
				while((line=reader.readLine())!=null){
					dic.add(line.trim());
				}
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	System.out.println(dic);
    	words.stream().filter(n->dic.contains(n.label)).forEach(w->matched_word.add(w.label));
    	System.out.println(matched_word);
    	return matched_word;
    }
}
