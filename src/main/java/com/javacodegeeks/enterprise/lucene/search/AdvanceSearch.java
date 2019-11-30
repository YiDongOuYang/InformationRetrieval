package com.javacodegeeks.enterprise.lucene.search;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.javacodegeeks.enterprise.lucene.index.StopAnalyzer2;

public class AdvanceSearch {
	private static final String indexDirectory = "/Users/yuanmengjuren/Desktop/大三上/信息组织检索/项目一/index";///Users/yuanmengjuren/Desktop/大三上/信息组织检索/项目一/index
    private static String queryString;
    private static final int maxHits = 50;
    
    private static IndexSearcher indexSearcher;
    private static Analyzer analyzer;
    private static Query multiFieldQuery;
    private static ScoreDoc[] hits;
    
    public AdvanceSearch(String queryString,String field) {//String wholeString,String completeString,String aString,String nString,String[] position
    	try {
    		AdvanceSearch.queryString=queryString;
    	Path indexDir = Paths.get(indexDirectory);
    	
		
			Directory directory = FSDirectory.open(indexDir);
		
    	DirectoryReader directoryReader = DirectoryReader.open(directory);
    	indexSearcher = new IndexSearcher(directoryReader);
    	analyzer = new StopAnalyzer2();//StandardAnalyzer
        QueryParser queryParser = new QueryParser(field, analyzer);
        
        Query query = queryParser.parse("kool kat");
        
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
			
    		result[i]=document.get("path");
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
    
    public static void main(String[] args) throws Exception {
    	/*SimpleSearcher search=new SimpleSearcher("novel","novel","novel","no","4","1900","2010");
    	System.out.println(search.getTitle());*/
    	
    	
        //Query query = QueryParser.parse("kool kat", "contents",analyzer);
        //Term term = new Term("content","mobi");
        //PrefixQuery query = new PrefixQuery(term);
        
        //PhraseQuery query1 = new PhraseQuery("title","vestimentiferan");
        
        
        //TermRangeQuery query2 = TermRangeQuery.newStringRange("title","198805","198810",true,true);
        
//        BooleanQuery.Builder builder = new BooleanQuery.Builder();
//        builder.add(query1, Occur.SHOULD);
//        builder.add(query2, Occur.SHOULD);
//        BooleanQuery booleanQuery = builder.build();
//        Result result = analyze(booleanQuery);
        
        
        
        
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
