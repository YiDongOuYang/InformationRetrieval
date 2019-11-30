package com.javacodegeeks.enterprise.lucene.search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FieldDoc;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;

import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import com.javacodegeeks.enterprise.lucene.index.StopAnalyzer2;

import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
//import org.apache.lucene.queryparser.analyzing.AnalyzingQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;

import org.apache.lucene.search.BooleanClause.Occur;

public class SimpleSearcher {
    private static final String indexDirectory = "/Users/yuanmengjuren/Desktop/da/ir/pro/index";///Users/yuanmengjuren/Desktop/index
    private static String queryString="novel";
    private static final int maxHits = 50;
    
    private static IndexSearcher indexSearcher;
    private static Analyzer analyzer;
    private static Query multiFieldQuery;
    private static ScoreDoc[] hits;
//    private static  directory;
	
    
    public SimpleSearcher(String wholeString,String completeString,String aString,String nString,String field,String startyear,String cutoffyear) {
    	this();
    
        	try {
        		if(field!="1") {
        			String[] fields=new String[1];
        			if(field=="2") {
        				fields[0]= "title";
        			}
        			else if(field=="3"){
        				fields[0]="abstract";
        			}
                    else if(field=="4"){
                    	fields[0]="cotent";
        			}
        			
        			Occur[] clauses = {Occur.SHOULD};
            		//String[] fields = {"title","abstract","content"};
            		//multiFieldQuery= MultiFieldQueryParser.parse(aString, fields, clauses, analyzer);
            		String[] newArray =completeString.split(" "); //{"novel","dna"};//WholeString.split(" ");
            		for(String k:newArray) {
            		System.out.println("!!!"+k);}
            		Term[]ta=new Term[newArray.length];//!!
            		int iit=0;
            		for (String s:newArray) {
            			
            				ta[iit]=new Term(fields[0],s);
            				iit+=1;
            			
            		}
        			
        		}
        		else {
        		
        		String[] fields = {"title","abstract","content"};
        		//array[Integer.valueOf(field)];//String[]fields=
        		Occur[] clauses = {Occur.SHOULD, Occur.SHOULD, Occur.SHOULD};
        		//String[] fields = {"title","abstract","content"};
        		multiFieldQuery= MultiFieldQueryParser.parse(aString, fields, clauses, analyzer);
        		}
        		
        		/*String[] fields ={"title","abstract","content"};
        		//array[Integer.valueOf(field)];//String[]fields=
        		Occur[] clauses = {Occur.SHOULD, Occur.SHOULD, Occur.SHOULD};
        		//String[] fields = {"title","abstract","content"};
        		multiFieldQuery= MultiFieldQueryParser.parse(aString, fields, clauses, analyzer);*/
        		
        		String[] newArray =completeString.split(" "); //{"novel","dna"};//wholeString.split(" ");
        		for(String k:newArray) {
        		System.out.println("!!!"+k);}
        		Term[]ta=new Term[newArray.length];//!!
        		int iit=0;
        		for (String s:newArray) {
        			
        				ta[iit]=new Term("content",s);
        				iit+=1;
        			
        		}
        		
        		String[] newW = wholeString.split(" ");
        		for(String k:newW) {
            		System.out.println("!!!"+k);}
        		
        		String tw="";//!!
        		for (String s:newW) {
        				tw+="+"+s+" ";
        		}
        		String[] newN = nString.split(" ");
        		for(String k:newN) {
            		System.out.println("!!!"+k);}
        		for (String s:newN) {
    				tw+="-"+s+" ";
    		}
        		
        		
        		
        		
                
                TermRangeQuery query2 = TermRangeQuery.newStringRange("time",startyear,cutoffyear,true,true);
        	
        	MultiPhraseQuery.Builder builder1 = new MultiPhraseQuery.Builder();
        	/*Term t1=new Term("content", "gene");
       	 Term t2=new Term("content", "novel");
       	builder1.add(new Term[] {t1,t2});*/
       	builder1.add(ta);
       	 MultiPhraseQuery query3 = builder1.build();
       	 
       	QueryParser queryParser = new QueryParser("content", analyzer);
    	 //Query query4 = queryParser.parse("+kool +kat -asdf");
       	Query query4 = queryParser.parse(tw);
       	 
        	BooleanQuery.Builder builder = new BooleanQuery.Builder();
            builder.add(multiFieldQuery, Occur.SHOULD);
            builder.add(query2, Occur.SHOULD);
            builder.add(query3, Occur.SHOULD);
            builder.add(query4, Occur.SHOULD);
            BooleanQuery booleanQuery = builder.build();
            //Result result = analyzer(booleanQuery);
            
    			
    	        
            TopDocs topDocs =indexSearcher.search(booleanQuery, maxHits);
            
            hits = topDocs.scoreDocs;
           
            
            for (int i = 0; i < hits.length; i++) {
                int docId = hits[i].doc;
                Document d = indexSearcher.doc(docId);
                
                System.out.println(d.get("title") + " Score :"+hits[i].score);
                System.out.println(d.get("pdf"));
              }
        		
    		
        	}catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			System.out.println(e);
    		}catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			System.out.println(e);
    		}
            
        }
    
    
    public SimpleSearcher(String queryString,String startyear,String cutoffyear) {
    	this();
    	try {
    	
    	TermRangeQuery query2 = TermRangeQuery.newStringRange("title","1900","2010",true,true);
        
        
        TopDocs topDocs =indexSearcher.search(query2, maxHits);
        
        hits = topDocs.scoreDocs;
       
        
        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = indexSearcher.doc(docId);
            
            System.out.println(d.get("title") + " Score :"+hits[i].score);
            
          }
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    
    public SimpleSearcher() {
    	Path indexDir = Paths.get(indexDirectory);
    	
		try {
			
			Directory directory = FSDirectory.open(indexDir);
			DirectoryReader directoryReader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(directoryReader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	analyzer = new StopAnalyzer2();//StandardAnalyzer
        //QueryParser queryParser = new QueryParser("content", analyzer);
        
    }
    
public SimpleSearcher(String queryString,float[]boostNum) {
	this();
		Map<String,Float>boost =new HashMap<String, Float>();
	    boost.put("title",boostNum[0]);
	    boost.put("abstract",boostNum[1]);
	    boost.put("content",boostNum[1]);
	    String[] fields = {"title", "abstract","content"};
	    MultiFieldQueryParser mulFieldQueryParser = new MultiFieldQueryParser(fields, analyzer, boost);
	    try {
			multiFieldQuery = mulFieldQueryParser.parse(queryString);
			TopDocs topDocs =indexSearcher.search(multiFieldQuery, maxHits);
	        hits = topDocs.scoreDocs;
	        for (int i = 0; i < hits.length; i++) {
	            int docId = hits[i].doc;
	            Document d = indexSearcher.doc(docId);
	            
	            System.out.println(d.get("title") + " Score :"+hits[i].score);
	            
	          }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
    }
    public SimpleSearcher(String queryString) {//,String fieldss
    	this();
    	try {
    	//SimpleSearcher.queryString=queryString;
        Occur[] clauses = {Occur.SHOULD, Occur.SHOULD,Occur.SHOULD};
        String[] fields = {"title", "abstract","content"};
        multiFieldQuery = MultiFieldQueryParser.parse(queryString, fields, clauses, analyzer);
        
        
        
        TopDocs topDocs =indexSearcher.search(multiFieldQuery, maxHits);
        
        hits = topDocs.scoreDocs;
       
        
        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = indexSearcher.doc(docId);
            
            System.out.println(d.get("title") + " Score :"+hits[i].score);
            
          }
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    public SimpleSearcher(String queryString,String fieldss) {//
    	this();
    	try {
    	//SimpleSearcher.queryString=queryString;
        QueryParser queryParser = new QueryParser(fieldss, analyzer);
        Query query =queryParser.parse(queryString);
        
        
        TopDocs topDocs =indexSearcher.search(query, maxHits);
        
        hits = topDocs.scoreDocs;
       
        
        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = indexSearcher.doc(docId);
            
            System.out.println(d.get("title") + " Score :"+hits[i].score);
            
          }
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    public String[] getPath()  {
    	String[] result=new String[getNumber()];
    	try {
    	int i=0;
    	for (ScoreDoc scoreDoc : hits) {
    		 
			
				Document document = indexSearcher.doc(scoreDoc.doc);
			
    		result[i]=document.get("pdf");
    		i+=1;
    	}
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    public String[] getTitle()  {
    	String[] result=new String[getNumber()];
    	int i=0;
    	try {
    	for (ScoreDoc scoreDoc : hits) {
    		Document document;
			
				document = indexSearcher.doc(scoreDoc.doc);
			
    		result[i]=document.get("title");
    		i+=1;
    	}
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    public String[] getPublishTime()  {
    	String[] result=new String[getNumber()];
    	int i=0;
    	try {
    	for (ScoreDoc scoreDoc : hits) {
    		Document document;
			
				document = indexSearcher.doc(scoreDoc.doc);
			
    		result[i]=document.get("time");
    		i+=1;
    	}
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    public String[] getAbstract()  {
    	String[] result=new String[getNumber()];
    	int i=0;
    	try {
    	for (ScoreDoc scoreDoc : hits) {
    		Document document;
			
				document = indexSearcher.doc(scoreDoc.doc);
			
    		result[i]=document.get("abstract");
    		i+=1;
    	}
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    public String[] getHighlight()  { 
    	QueryScorer scorer = new QueryScorer(multiFieldQuery, "content");
        SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<span>", "</span>");
        Highlighter highlighter = new Highlighter(htmlFormatter, scorer);
		highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
    	String[] result=new String[getNumber()];
    	int i=0;
    	try {
    	for (ScoreDoc scoreDoc : hits) {
    		
			
				Document document = indexSearcher.doc(scoreDoc.doc);
			
    		try {
				result[i]=highlighter.getBestFragment(analyzer, "content", document.get("content"));
			} catch (InvalidTokenOffsetsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		i+=1;
    	}
    	} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return result;
    }
    public int getNumber() {
    	return hits.length;
    }
    public float[]getScore(){
    	float[] score=new float[getNumber()];
    	int i=0;
    	for (ScoreDoc scoreDoc : hits) {
    		score[i]=scoreDoc.score;
    		i+=1;
    	}
    	return score;
    	
    }
    
    public static void main(String[] args)  {
    	//float[] boost= {1.2f,0.8f};
    	//SimpleSearcher search=new SimpleSearcher("novel",boost);//,boost
    	SimpleSearcher search=new SimpleSearcher("Direct Linkage","Phosphoinositide","Signaling clinical","no","1","1900","2010");
    	/*
Path indexDir = Paths.get(indexDirectory);
    	
		try {
			
			Directory directory = FSDirectory.open(indexDir);
			DirectoryReader directoryReader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(directoryReader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	analyzer = new StopAnalyzer2();//StandardAnalyzer
    	 MultiPhraseQuery.Builder builder = new MultiPhraseQuery.Builder();
    	 Term t1=new Term("content", "gene");
    	 Term t2=new Term("content", "novel");
    	 builder.add(new Term[] {t1,t2});
    	 
    	 //builder.add(new Term("content", ""));
    	 MultiPhraseQuery pq = builder.build();//对字符串做好处理 包括前缀query 代码写好注释
    	
    	QueryParser queryParser = new QueryParser("content", analyzer);
    	 Query query1 = queryParser.parse("+kool +kat -asdf");
    	 TermRangeQuery query2 = TermRangeQuery.newStringRange("title","1900","2010",true,true);
         
    	BooleanQuery.Builder builder1 = new BooleanQuery.Builder();
        builder1.add(query1, Occur.SHOULD);
        builder1.add(pq, Occur.SHOULD);
        builder1.add(query2, Occur.SHOULD);
        BooleanQuery booleanQuery = builder1.build();
    	
    	
        
    	 TopDocs topDocs =indexSearcher.search(booleanQuery, maxHits);
         
         hits = topDocs.scoreDocs;
        
         
         for (int i = 0; i < hits.length; i++) {
             int docId = hits[i].doc;
             Document d = indexSearcher.doc(docId);
             
             System.out.println(d.get("title") + " Score :"+hits[i].score);
             
           }*/
    	//SimpleSearcher a=new SimpleSearcher("novel","","","","","","");
    	//SimpleSearcher search=new SimpleSearcher("novel dna","novel dna","novel dna","no no","4","1900","2010");
    	
    	 
//    	 Term term = new Term("content","mobi");
//         PrefixQuery query = new PrefixQuery(term);
    	
    	
        //Query query = QueryParser.parse("+kool +kat -asdf", "contents",analyzer);
        
        
        //PhraseQuery query1 = new PhraseQuery("title","vestimentiferan");
        
        
        //
    
        
        
        
        
//        FuzzyQuery
        
  /*QueryBuilder builder = new QueryBuilder(analyzer);
        Query query = builder.createPhraseQuery("text", queryStr);*/
        /*
        TopFieldDocs docs = indexSearcher.search(query1, maxHits, new Sort(new SortField("title", SortField.Type.STRING)));
        //SortField[] sortFields=indexSearcher.search(multiFieldQuery, maxHits).merge(sort, start, topN, shardHits, setShardIndex)MergeSortQueue.sort
        for (int i = 0; i < 1; i++) {
            FieldDoc d = (FieldDoc) docs.scoreDocs[i];
            float expected = (float) Math.sqrt(d.score);
            System.out.print(d.doc);
            //float actual = ((Double)d.fields[0]).floatValue();
            System.out.println(expected);
            System.out.println(bytesToDouble(d.fields[0]));
            */
        
       
        
    
        
        
        
    }
}
