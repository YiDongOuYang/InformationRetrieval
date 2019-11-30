package com.javacodegeeks.enterprise.lucene.index;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.core.LetterTokenizer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.AttributeFactory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
import org.apache.lucene.util.packed.PackedInts.Reader;
import org.dom4j.DocumentException;



public class SimpleIndexer {
    private static final String indexDirectory = "/Users/yuanmengjuren/Desktop/da/ir/pro/index";
    private static final String dirToBeIndexed = "/Users/yuanmengjuren/Desktop/da/ir/pro/oriPDFs-output";
    public static void main(String[] args) throws Exception {
    	Path indexDir = Paths.get(indexDirectory);
        File dataDir = new File(dirToBeIndexed);
        Analyzer analyzer = new StopAnalyzer2();//StandardAnalyzer
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir),config);
        File[] files = dataDir.listFiles();
        
        for(File f:files){//读取文件并构建索引
			if(f.getName().endsWith(".xml")){//找文件时我用的txt，注意
				//System.out.println("Indexing: "+urlf+" "+f.getName()+"<<<");
				Document doc = new Document();//以文档为单位
				MyFilter myFilter = new MyFilter(f);
				Field path_field = new TextField("path",myFilter.getPDF(),Field.Store.YES);
				Field text_field = new TextField("content",myFilter.getText(),Field.Store.YES);//文档由多个域构成
				Field title_field = new TextField("title",myFilter.getTitle(),Field.Store.YES);
				Field abstract_field = new TextField("abstract",myFilter.getAbstract(),Field.Store.YES);
				Field time_field = new TextField("time",myFilter.getPublishTime(),Field.Store.YES);
				Field pdf_field = new TextField("pdf",myFilter.getPDF(),Field.Store.YES);
				doc.add(new SortedDocValuesField ("title", new BytesRef(myFilter.getTitle()) ));
				doc.add(text_field);//域加载到文档中
				doc.add(title_field);
				doc.add(path_field);
				doc.add(abstract_field);
				doc.add(time_field);
				doc.add(pdf_field);
				indexWriter.addDocument(doc);	//文档加入到索引写入器中
				
			}
		}
        indexWriter.close();
        System.out.print(files.length);
        
         
    }


	

    


    
}