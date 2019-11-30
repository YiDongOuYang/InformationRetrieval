package com.javacodegeeks.enterprise.lucene.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.core.LetterTokenizer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;

public class StopAnalyzer2 extends Analyzer {

	public TokenStream tokenStream(String fieldName, TokenStream reader) {
  	    return new PorterStemFilter(new StopFilter(new LowerCaseFilter(reader), EnglishAnalyzer.ENGLISH_STOP_WORDS_SET));
  	  }

  	  @Override
  	  protected TokenStreamComponents createComponents(final String fieldName) {
  	    Tokenizer tokenizer = new LetterTokenizer();
  	    return new TokenStreamComponents(tokenizer, new PorterStemFilter(new StopFilter(new LowerCaseFilter(tokenizer), EnglishAnalyzer.ENGLISH_STOP_WORDS_SET)));
  	  }

}
