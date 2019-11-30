package com.javacodegeeks.enterprise.lucene.search;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.javacodegeeks.enterprise.lucene.index.marking;
public class map {
	public static double[]precision;
	public static String[]queryTerm;
	public static float precision_value;
	public map(float[]boost){
		float[][] queryResult = new float[5][50];
		queryTerm=new String[5];
		queryTerm[0]="human";
		queryTerm[1]="water";
		queryTerm[2]="system";
		queryTerm[3]="expression";
		queryTerm[4]="image";
		String[][]title = new String[5][50];
		int it=0;
		for(String s:queryTerm) {
			//AdvanceSearch search=new AdvanceSearch(s);
			SimpleSearcher search=new SimpleSearcher(s,boost);
			queryResult[it]=search.getScore();
			title[it]=search.getTitle();
			it+=1;	
			
		}
		
		
		// 加载ground turth
		float[][]scoreT=marking.getScore();
		String[]titleA=marking.getTitle();
		String[]titleT=new String[5];
		for(int i =0;i<10;i++) {
			titleT[0]+=titleA[i]+" ";
		}
		for(int i =10;i<20;i++) {
			titleT[1]+=titleA[i]+" ";
		}
		for(int i =20;i<30;i++) {
			titleT[2]+=titleA[i]+" ";
		}
		for(int i =30;i<40;i++) {
			titleT[3]+=titleA[i]+" ";
		}
		for(int i =40;i<50;i++) {
			titleT[4]+=titleA[i]+" ";
		}
		//每个查询的平均正确率
		precision = new double[5];
		
		for(int i=0; i<5; i++) {
			//每个查询所有正确率之和
			double subprecision = 0;
			//每个查询返回的相关文档的数量
			int true_amount = 0;
			//每个查询返回的所有文档的数量
			int all_amount = 0;
			for(int j=0; j<50;j++) {

				if(titleT[i].indexOf(title[i][j])!=-1){//Arrays.asList(titleT).contains(title[i][j]
			
					all_amount += 1;
					true_amount += 1;
					subprecision += ((double)true_amount)/(all_amount);
										
				}
				else
					all_amount += 1;
				
			}
			precision[i] = subprecision/true_amount;
			
		}
		
		//计算平均正确率均值
		precision_value = 0;
		
		float aux = 0;
		for(int i = 0; i < 5; i++) {
			aux += precision[i];
		}
		precision_value = aux/5;
	}
	public float getMap() {
		//需要得到每个查询的每个结果是否相关的二维数组，这里随便写了一个
		return precision_value;
	}
	public double getMAP(String queryString) {
		//需要得到每个查询的每个结果是否相关的二维数组，这里随便写了一个
		for(int i=0;i<5;i++) {
			if(queryTerm[i]==queryString) {
				return precision[i];
			}
			
		}
		return -1;
				//System.out.println("The mean average precision of this test is: " + precision_value);
				//return precision_value;
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		float[]a= {0.85f,0.05f,0.1f} ;//0.9845075948017123
		//float[]a= {0.26f,0.37f,0.37f} ;
		map m=new map(a);
		System.out.println(m.getMap());
		System.out.println(m.getMAP("human"));
		/*
		float[][][] queryResult = new float[5][3][50];
		String[]queryTerm=new String[5];
		queryTerm[0]="human";
		queryTerm[1]="water";
		queryTerm[2]="system";
		queryTerm[3]="expression";
		queryTerm[4]="image";
		String[]queryField=new String[3];
		queryField[0]="title";
		queryField[1]="abstract";
		queryField[2]="content";
		String[]title = new String[50];
		Map<String,String > map = new HashMap<String,String>();
		SimpleSearcher search = null;
		for(String s:queryTerm) {
			System.out.println(s);

			
			for(String t:queryField) {
				File f=new File("/Users/yuanmengjuren/Desktop/"+s+"-"+t+".txt");
				FileOutputStream fos1=new FileOutputStream(f);
				OutputStreamWriter dos1=new OutputStreamWriter(fos1);
				//dos1.write(t+"\n");
			search=new SimpleSearcher(s,t);
			
			float[]result=search.getScore();
			title=search.getTitle();
			for(int i=0;i<Math.min(50, result.length);i++) {
				map.put(title[i], ((Float)(result[i])).toString());	
			}//
			for(int i=0;i<Math.min(50, map.size());i++) {
				for(String key:map.keySet()) {
					dos1.write(key+" "+map.get(key)+"\n");
				}
			}
			
			}
			//System.out.println("\n");
			
			
			
		}*/
	}

}
