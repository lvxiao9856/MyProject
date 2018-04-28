package HelpAlgri;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word_leftright {
	
	private String word;
	private Map<String,Map<String,Integer>> left_right=new HashMap<String,Map<String,Integer>>();
	
	public String getword(){
		return word;
	}
	
	public void setword(String word){
		this.word=word;
	}
	
	public Map<String,Map<String,Integer>> getleft_right(){
		return left_right;
	}
	
	public void setleft_right(Map<String,Map<String,Integer>> left_right){
		this.left_right=left_right;
	}
	
}
