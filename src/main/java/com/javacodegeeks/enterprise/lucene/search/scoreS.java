package com.javacodegeeks.enterprise.lucene.search;

import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.search.CollectionStatistics;
import org.apache.lucene.search.TermStatistics;
import org.apache.lucene.search.similarities.Similarity;

public class scoreS extends Similarity{

	@Override
	public long computeNorm(FieldInvertState state) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SimScorer scorer(float boost, CollectionStatistics collectionStats, TermStatistics... termStats) {
		// TODO Auto-generated method stub
		return null;
	}

}
