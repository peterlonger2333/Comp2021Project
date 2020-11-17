package hk.edu.polyu.comp.comp2021.g17.cvfs.model.criterion;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.File;

public abstract class Criterion{
	String name;
	AttrName attrname;;
	Op op;
	Object val;
	
	protected Criterion(String name, AttrName attrname, Op op, Object val) throws IllegalArgumentException {
		if (!isValidCriName(name) || !isValidCri(attrname, op, val)) throw new IllegalArgumentException("Invalid argument combination is passed on creating criterion");
		this.name = name;
		this.attrname = attrname;
		this.op = op;
		this.val = val;
	}
	
	/**
	 * check if a character variable is letter 
	 * @param temp
	 * @return true if character is letter, false otherwise
	 */
	private boolean isLetter(char character) {
		int code = (int)character;
		if ((code >= 65 && code <=90) || (code >= 97 && code <= 122)) {
			return true;
		}
		return false;
	}
	
	/**
	 * check if name is valid
	 * @param name
	 * @return true if name is valid, false otherwise
	 */
	private boolean isValidCriName(String name) {
		//TODO 
		if (name.length() == 2) {
			if (isLetter(name.charAt(0)) && isLetter(name.charAt(1))) {return true;}
			}
		return false;
	}
	
	/**
	 * check if AttrName,Op,Val constitute a valid criterion
	 * @param an
	 * @param op
	 * @param val
	 * @return true if it does, false otherwise
	 */
	private boolean isValidCri(AttrName an, Op op, Object val) {
		//TODO
		return true;
	}
	
	/**
	 * Assert whether the file satisfies the criterion
	 * @param file
	 * @return
	 */
	
	public abstract boolean assertCri(File file);
	
	public static Criterion newSimpleCri(String name, String attrname, String op, String val){
		//TODO
		//An interface to create a criterion
		//first convert the string to the correct type,then call the corresponding constructor(TypeCri, NameCri, SizeCri)
		return null;
	}
	
	public static Criterion newNegation(String name, Criterion cri) throws IllegalArgumentException {
		return new Criterion(name, cri.attrname, cri.op.negate(), cri.name){
			public boolean assertCri(File file) {
				return !cri.assertCri(file);
			}
		};
	}
	
	public static Criterion newBinaryCri(String name, Criterion c1, Criterion c2, Op bo) throws IllegalArgumentException {
		return new Criterion(name, AttrName.composite, bo, c1.name + " " + c2.name) {
			public boolean assertCri(File file) {
			//TODO
			//return the right composition of c1 and c2
				return c1.assertCri(file) && c2.assertCri(file);
			}
		};
	}
	
	public void printCri() {
		//TODO
		//print the information about this criterion
	}
}
