package TextProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import summary.SummaryMethod;
import divideWords.NlpirMethod;
import fileutil.FileOperateUtils;

public class Help {
	public static void main(String args[]){
//		String path="G:/Myeclipse/newsCollection/data/beijing";
//		File file=new File(path);
//		change(file);
//		deletefile(file);
/*		Stream.of(new int[]{1, 2, 3}).forEach(System.out::println);
		Integer[] sixNums = {1, 2, 3, 4, 5, 6};
		Integer[] evens =Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);*/
/*		String aa="及水电费，都很费劲的。说东风水库！山东矿机？";
		List<String> bb=new ArrayList<String>();
		bb=Arrays.asList(aa.split("(，|。|！|？)"));
		bb.stream().forEach(System.out::println);*/
		NlpirMethod.Nlpir_init();
		sentence_seg("./corpus/shanghai/2010","./corpus/shanghai/total/line/2010-sh-total-line.txt","./corpus/shanghai/total/seg/2010-sh-total-seg.txt");
//		uniontext("./corpus/shanghai/2010","./corpus/shanghai/total/2010-sh-total.txt");
//		String test="jigjfg经快捷快递大哥  大 哥 即可 发 的  季 8765";
//		System.out.println(test.replaceAll("\\s[\u4E00-\u9FA5]\\s", " ").replaceAll("\\s[\u4E00-\u9FA5]\\s", " "));
//		NlpirMethod.Nlpir_init();
//		SummaryMethod.Summary_init();
//		summary_gen("corpus/beijing/2013","corpus/beijing/2013_summary");
//		keyword_gen("G:/Myeclipse/newsCollection/data/beijing/2015","corpus/beijing/2015_keyword");
//		SummaryMethod.DS_Exit();
//		NlpirMethod.NLPIR_EXIT();
	}
 
	public static void keyword_gen(String path,String outpath){
		File file=new File(path);
		File[] files=file.listFiles();
		try{
			for(int i=0;i<files.length;i++){
				String spath=files[i].getAbsolutePath();
				System.out.println(spath);
				String str=FileOperateUtils.getFileContent(spath);
				str=str.replaceAll("[^\u4E00-\u9FA5]", "");
				System.out.println(str);				
				String data=NlpirMethod.NLPIR_GetKeyWords(str,40,false);
				System.out.println(data);
				data=data.replaceAll("#", " ");
				FileOperateUtils.writeFile(data,outpath+"/"+files[i].getName());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void summary_gen(String path,String outpath){

		File file=new File(path);
		File[] files=file.listFiles();
		KeyWordSummary s=new KeyWordSummary();
		try{
			for(int i=0;i<files.length;i++){
				String spath=files[i].getAbsolutePath();
				System.out.println(spath);
				String sSrc=FileOperateUtils.getFileContent(spath);
				System.out.println(sSrc);
				String data=SummaryMethod.DS_SingleDoc(sSrc);
				System.out.println(data);
				FileOperateUtils.writeFile(data,outpath+"/"+files[i].getName());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void uniontext(String path,String outpath){
		File file=new File(path);
		File[] files=file.listFiles();
//		FileOperateUtils.writeFile(files.length+"",outpath);
		for(int i=0;i<files.length;i++){
			String spath=files[i].getAbsolutePath();
			String str=FileOperateUtils.getFileContent(spath);
			str=str.replaceAll("[^\u4E00-\u9FA5]+","");
			FileOperateUtils.writeFile(str,outpath);
		}
	}
	public static void sentence_seg(String path,String outpath,String out){
		File file=new File(path);
		File[] files=file.listFiles();
		Stream.of(files).forEach(n-> {String str=FileOperateUtils.getFileContent(n.getAbsolutePath());
		Stream.of(str.split("(，|。|！|；|？)")).forEach(w->{String data=w.replaceAll("([^\u4E00-\u9FA5]+|的|是|及|或|元|万|了|在|对|来源|发布|和|关于|有关|等)", "");
		FileOperateUtils.writeFile(data, outpath);});
		}); 
		NlpirMethod.NLPIR_FileProcess(outpath, out,0); 
//		new File(outpath).delete();
	}
	public static void deletefile(File a){
		File[] files=a.listFiles();
		for(int i=0;i<files.length;i++){
			if(files[i].isDirectory()){
				deletefile(files[i]);
			}
			else{
				if(files[i].isFile()){
					try {
						String str=FileOperateUtils.getFileContent(files[i].getCanonicalPath());
						if(str.isEmpty()){
							files[i].delete();
						}
//						String filename=files[i].getName();
//						if(!filename.contains("北京") || !filename.contains("房地产")){
//							files[i].delete();
//						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}
		}
	}
	public static void change(File a){
		
		File[] files=a.listFiles();
		for(int i=0;i<files.length;i++){
			if(files[i].isDirectory()){
				change(files[i]);
			}
			else{
				String newname=files[i].getName()+".txt";
				File name=new File(a.getAbsolutePath()+"/"+newname);
				files[i].renameTo(name);
			}
		}
	}
	
}
