package HelpAlgri;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

	public class SortAll {
		/*
		 * 折半插入排序O(n^2)
		 */
	    private static int[] Sort(int[] arr) {
	        int i, j;
	        //保存中间插入的值
	        int insertNote = 0;
	        //将待排序的数列保存起来
	        int[] array = arr;
	        System.out.println("开始排序：");
	        for (i = 1; i < array.length; i++) {
	            int low = 0;
	            int high = i - 1;
	            insertNote = array[i];
	            //不断的折半
	            while (low <= high) {
	                //找出中间值
	                int mid = (low + high) / 2;
	                //如果大于中间值
	                if (array[i] > array[mid]) {
	                    //在大于中间值的那部分查找
	                    low = mid+1;
	                } else
	                    //在小于中间值的那部分查找
	                    high = mid-1;
	            }
	          //将整体数组向后移
	            for ( j=i; j > low; j--) {
	                array[j] = array[j - 1];
	            }
	         //插入到指定的位置
	            array[low] = insertNote;
	            System.out.println(Arrays.toString(array));
	        }
	        System.out.println("排序之后：");
	        System.out.println(Arrays.toString(array));
	        return array;
	    }
	    /*
	     * 直接插入排序O(n^2)
	     */
	    public static int[] InsertSort(int[] arr){
	    	int[] array=arr;
	    	int i,j,temp;
	    	for(i=1;i<array.length;i++){	    		
	    		if(array[i]<array[i-1]){
	    			temp=array[i];
	    			for(j=i-1;j>=0 && temp<array[j];j--){
	    				array[j+1]=array[j];
	    			}
	    			array[j+1]=temp;
	    		}
	    	}
	        System.out.println("排序之后：");
	        System.out.println(Arrays.toString(array));
	    	return array;
	    }
	    /*
	     * 交换排序之冒泡排序O(n^2)
	     */
	    public static int[] BubbleSort(int[] arr){
	    	int[] array=arr;
	    	int i,j,temp;
	    	for(i=0;i<array.length;i++){
	    		for(j=array.length-1;j>i;j--){
	    			if(array[j]<array[j-1]){
	    				temp=array[j];
	    				array[j]=array[j-1];
	    				array[j-1]=temp;
	    			}
	    		}
	    	}
	    	System.out.println("排序之后：");
		    System.out.println(Arrays.toString(array));
	    	return array;
	    }
	    /*
	     * 交换排序之快速排序O(log2n)
	     */
	    public static int Partition(int[] arr,int low,int high)
	    {
	    	int[] array=arr;
	    	int elem=array[low];
	    	int i=low,j=high;
	    	while(i<j){
	    		while(i<j && array[j]>=elem) j--;
	    		if(i<j) {array[i]=array[j];i++;}
	    		while(i<j && array[i]<=elem) i++;
	    		if(i<j) {array[j]=array[i];j--;}
	    	}
	    	array[j]=elem;
	    	return j;
	    }
	    public static int[] QuickSort(int[] arr,int low,int high){
	    	int[] array=arr;
	    	if(low<high){
	    		int Pivot=Partition(array,low,high);
	    		QuickSort(array,low,Pivot-1);
	    		QuickSort(array,Pivot+1,high);
	    	}
	    	return array;
	    }
	    /*
	     * 归并排序
	     */
	    public static int[] MergeSort(int[] arr,int left,int right){
	    	int[] array=arr;
	    	if(left<right){
	    		int mid=(left+right)/2;
	    		MergeSort(array,left,mid);
	    		MergeSort(array,mid+1,right);
	    		Merge(array,left,mid,right);
	    	}
	    	return array;
	    }
	    public static int[] Merge(int[] arr,int left,int mid,int right){
	    	int[] array=arr;
	    	int[] temp=new int[right-left+1];
	    	int i=left,j=mid+1,k=0;
	    	while(i<=mid && j<=right){
	    		if(array[i]<array[j]) temp[k++]=array[i++];
	    		else temp[k++]=array[j++];
	    	}
	    	while(i<=mid) temp[k++]=array[i++];
	    	while(j<=right) temp[k++]=array[j++];
	    	for(i=0,k=left;k<=right;) array[k++]=temp[i++];
	    	return array;
	    }
	    /*
	     * 简单选择排序
	     */
	    public static int[] SelectSort(int[] arr){
	    	int[] array=arr;
	    	for(int i=0;i<array.length;i++){
	    		int small_loc=i;
	    		for(int j=i+1;j<array.length;j++){
	    			if(array[j]<array[small_loc])
	    				small_loc=j;
	    		}
	    		if(small_loc!=i){
	    			int temp=array[i];
	    			array[i]=array[small_loc];
	    			array[small_loc]=temp;
	    		}
	    	}
	    	System.out.println("排序之后：");
		    System.out.println(Arrays.toString(array));
	    	return array;
	    }
	    /*
	     * 堆排序
	     */
	    public static void HeapSort(int[] arr){
	    	int i;
	    	for(i=arr.length/2;i>=0;i--){//初始建堆
	    		HeapAdjust(arr,i,arr.length-1);
	    	}
	    	for(i=arr.length-1;i>=0;i--){//将当前堆顶元素与堆尾元素互换
	    		int temp=arr[0];
	    		arr[0]=arr[i];
	    		arr[i]=temp;
	    		HeapAdjust(arr,0,i-1);//将剩下的元素重新调整成堆
	    	}
	    }
	    public static void HeapAdjust(int[] arr,int i,int j){
	    	int child=2*i;
	    	int temp=arr[i];//临时存放根节点
	    	while(child<+j){ //沿大儿子向下调整
	    		if(child<j && arr[child+1]>arr[child]) child++;
	    		if(temp>=arr[child]) break;
	    		arr[child/2]=arr[child];
	    		child=2*child;
	    	}
	    	arr[child/2]=temp;
	    }
	    @SuppressWarnings("null")
		public static void CountSort(int[] arr){
	    	int K=100;
	    	int[] array=arr;
	    	int[] C =null;
	    	int[] Rank = null;
	    	for(int i=0;i<=K;i++){
	    		C[i]=0;
	    	}
	    	for(int i=0;i<array.length;i++){
	    		C[array[i]]++;
	    	}
	    	for(int i=0;i<=K;i++){
	    		C[i]=C[i-1];
	    	}
	    	for(int i=array.length-1;i>=0;i--){
	    		Rank[--C[array[i]]]=array[i];
	    	}
	    	System.out.println("排序之后：");
		    System.out.println(Arrays.toString(Rank));
	    }
	    public static void main(String[] args) {
	        Random random = new Random();
	        int[] array = new int[10];
	        for (int i = 0; i < 10; i++) {
	            array[i] = Math.abs(random.nextInt() % 100);
	        }
/*	        Scanner s=new Scanner(System.in);
	        int[] ss=new int[5];
	        for(int i=0;i<5;i++){
	        	ss[i]=s.nextInt();
	        }
	        System.out.println(Arrays.toString(ss));*/
	        System.out.println("排序之前：");
	        System.out.println(Arrays.toString(array));            
//	        SortAll.Sort(array);//折半插入排序
//	        SortAll.InsertSort(array); //直接插入排序
//	        SortAll.BubbleSort(array); //冒泡排序
	        int[] a=SortAll.SelectSort(array);//选择排序
	        System.out.println(Arrays.toString(a));
//	        SortAll.HeapSort(array);//堆排序
//	        SortAll.CountSort(array);//计数排序
//	        System.out.println(Arrays.toString(array));
//	        System.out.println(Arrays.toString(SortAll.QuickSort(array,0,array.length-1)));//快速排序
//	        System.out.println(Arrays.toString(SortAll.MergeSort(array,0,array.length-1)));//归并排序
	    }
	}
