# InformationRetrieval
based on lucene8.3  
  override analyzer to add StopFilter and PorterStemFilter  
  MultiFieldQuery for normal search  
  Advance Search:  
    "with all of the words": QueryParser +  
    "with the exact phrase": PhraseQuery  
    "with at least one of the words":TermQuery  
    "without the words": QueryParser -  
    "where my words occur": set Field  
    "Return articles dated between": TermRangeQuery  
    combine above: BooleanQuery
