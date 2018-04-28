package HelpAlgri;

import java.io.*;
import java.util.*;
 public class ListSort{
//按照文件大小排序  
public static void orderByLength(String fliePath) {
 List< File> files = Arrays.asList(new File(fliePath).listFiles());
 Collections.sort(files, new Comparator< File>() {
     public int compare(File f1, File f2) {
	long diff = f1.length() - f2.length();
	if (diff > 0)
	  return 1;
	else if (diff == 0)
	  return 0;
	else
	  return -1;
     }
    public boolean equals(Object obj) {
	return true;
    }
   });
   for (File f : files) {
      if(f.isDirectory()) continue;
      System.out.println(f.getName()+":"+f.length());
   }
}
//按照文件名称排序
public static void orderByName(String fliePath) {
  List<File> files = Arrays.asList(new File(fliePath).listFiles());
  Collections.sort(files, new Comparator< File>() {
   @Override
   public int compare(File o1, File o2) {
	if (o1.isDirectory() && o2.isFile())
          return -1;
	if (o1.isFile() && o2.isDirectory())
          return 1;
	return o1.getName().compareTo(o2.getName());
   }
  });
   for (File f : files) {
     System.out.println(f.getName());
    }
  }
//按日期排序
public static void orderByDate(File[] flies) {
 //  File file = new File(fliePath);
 //  File[] fs = file.listFiles();
   Arrays.sort(flies,new Comparator< File>(){
     public int compare(File f1, File f2) {
	long diff = f1.lastModified() - f2.lastModified();
	if (diff > 0)
	  return 1;
	else if (diff == 0)
	  return 0;
	else
	  return -1;
     }
     public boolean equals(Object obj) {
	return true;
     }
		
     });
     for (int i =0;i< flies.length; i++) {
	System.out.println(flies[i].getName());
//	System.out.println(new Date(fs[i].lastModified()));
      }
  }
   public static void main(String args[]){
      // orderByLength("c:/java");
      //orderByName("htmldata");
      //orderByDate("htmldata");
      }
}

