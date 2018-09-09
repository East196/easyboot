package com.github.east196.easyboot.core;

public class SearchTerm {
	
	public enum Relation {
		EQ,

		LIKE,

		GT,

		LT,

		GTE,

		LTE;
	}

	private Relation relation;
	private String value;

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
