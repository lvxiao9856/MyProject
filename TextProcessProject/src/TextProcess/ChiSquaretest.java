package TextProcess;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChiSquaretest {
	int A=1;  // 包含某词且属于某类别的文档数=1
	int B;  //包含某词且不属于某类别的文档数
	int C=0;  //不包含某词且属于某类别的文档数=0
	int D;  //不包含某词且不属于某类别的文档数

	public double caculate_chi(Map<Integer,List<String>> tw){
		double chi=0.0;
		Map<String,Map<String,Double>> s=new HashMap<String,Map<String,Double>>();
		int num=tw.size();
		Iterator<Integer> iterator=tw.keySet().iterator();
		while(iterator.hasNext()){
			int k=iterator.next();
			String label=String.valueOf(k);
			List<String> wordslist=tw.get(k);
			Iterator<String> listiterator=wordslist.iterator();
			Map<String,Double> a=new HashMap<String,Double>();			
			while(listiterator.hasNext()){
				String word=listiterator.next();	
				int sum1=0,sum2=0;
				for(int i=0;i<num;i++){
					if(tw.get(i).contains(word)) sum1+=1;
					else sum2+=1;
				}
				B=sum1-A;
				D=sum2-C;
				chi=Math.pow((A*D-B*C), 2)/((A+B)*(C+D));				
				a.put(word, chi);
			}
			s.put(label, a);
		}
		Iterator<String> aa=s.keySet().iterator();
		while(aa.hasNext()){
			String label=aa.next();
			System.out.println(label);
			Map<String,Double> dd=s.get(label);
			Iterator<String> ff=dd.keySet().iterator();
			while(ff.hasNext()){
				String word=ff.next();
				double zhi=dd.get(word);
				System.out.println(word+"  "+zhi);
			}
		}
		return chi;
	}
}
