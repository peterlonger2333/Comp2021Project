package hk.edu.polyu.comp.comp2021.g17.cvfs.model.criterion;

public enum Op{
	contains,
	equals,
	G,//greater
	GE,//greater or equal
	L,LE,E,NE,
	not_contains,
	AND, OR,
	not_equals;
	public Op negate() {
		//TODO
		//return the negate version of op
		return null;
	}
}
