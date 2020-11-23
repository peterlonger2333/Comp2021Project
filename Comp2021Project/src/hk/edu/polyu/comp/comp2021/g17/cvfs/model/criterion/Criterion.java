package hk.edu.polyu.comp.comp2021.g17.cvfs.model.criterion;

import java.util.ArrayList;
import java.util.Arrays;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.InvalidArgumentException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.DocumentType;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.File;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.FileType;

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
	
	private boolean isLetter(char character) {
		int code = (int)character;
		if ((code >= 65 && code <=90) || (code >= 97 && code <= 122)) {
			return true;
		}
		return false;
	}
	
	private boolean isValidCriName(String name) {
		if (name.length() == 2) {
			if (isLetter(name.charAt(0)) && isLetter(name.charAt(1))) {return true;}
		}
		
		return false;
	}
	
	private boolean isValidCri(AttrName an, Op op, Object val) {
		ArrayList<Op> sizeOps = new ArrayList<Op>(Arrays.asList(new Op[] {Op.G, Op.GE, Op.L, Op.LE, Op.E, Op.NE}));
		ArrayList<Op> nameOps = new ArrayList<Op>(Arrays.asList(new Op[] {Op.contains, Op.not_contains}));
		ArrayList<Op> typeOps = new ArrayList<Op>(Arrays.asList(new Op[] {Op.equals, Op.not_equals}));
		ArrayList<Op> compOps = new ArrayList<Op>((Arrays.asList(new Op[] {Op.AND, Op.OR})));
 		
		switch (an) {
		
		case size:
			return sizeOps.contains(op) && val instanceof Integer;
		case name:
			return nameOps.contains(op) && val instanceof String;
		case type:
		case filetype:
			return typeOps.contains(op) && (val instanceof DocumentType || val instanceof FileType);
		case composite:
			return compOps.contains(op) && val instanceof String;
		}
		
		return false;
	}

	public abstract boolean assertCri(File file);
	
	private static Op string2Op(String sop) throws InvalidArgumentException {
		Op result = null;
		
		for (Op op : Op.values()) {
			if (op.name().compareTo(sop) == 0) {
				result = op;
				break;
			}
		}
		
		if (result == null) throw new InvalidArgumentException("Invalid Operator " + sop);
		
		return result;
	}
	
	private static DocumentType string2Type(String stype) throws InvalidArgumentException {
		DocumentType result = null;
		
		for (DocumentType t : DocumentType.values()) {
			if (t.name().compareTo(stype) == 0) {
				result = t;
				break;
			}
		}
		
		if (result == null) throw new InvalidArgumentException("Invalid Document Type " + stype);
		
		return result;
	}
	
	public static Criterion newSimpleCri(String name, String attrname, String op, String val) throws InvalidArgumentException{
		
		AttrName attr = null;
		
		for (AttrName an : AttrName.values()) {
			if (an.name().compareTo(attrname) == 0) {
				attr = an;
				break;
			}
		}
		
		if (attr == null) throw new InvalidArgumentException("No such attrbute name: " + attrname);
		
		switch (attr) {
		
		case name:
			if (op.compareTo("contains") != 0) 
				throw new InvalidArgumentException("Operator must be 'contains'");
			return new NameCri(name, val);
		
		case size:
			try {
				return new SizeCri(name, string2Op(op), Integer.parseInt(val));
			}catch(NumberFormatException nfe) {
				throw new InvalidArgumentException(val + " is not a number");
			}

		case type:
			return new TypeCri(name,string2Type(val));
			
		default:
			throw new InvalidArgumentException("Unknown arguments");
		}
	}
	
	public static Criterion newNegation(String name, Criterion cri) throws IllegalArgumentException {
		return new Criterion(name, cri.attrname, cri.op.negate(), cri.name){
			public boolean assertCri(File file) {
				return !cri.assertCri(file);
			}
		};
	}
	
	public static Criterion newBinaryCri(String name, Criterion c1, Criterion c2, String boString) throws IllegalArgumentException, InvalidArgumentException {
		
		Op bo = string2Op(boString);
		return new Criterion(name, AttrName.composite, bo, c1.name + " " + c2.name) {
			public boolean assertCri(File file) {
				switch (bo) {
					case AND:
						return c1.assertCri(file) && c2.assertCri(file);
					case OR:
						return c1.assertCri(file) || c2.assertCri(file);
					default:
						//never reached
						break;
				}
				//never reached
				return false;
			}
		};
	}
	
	public static Criterion genIsDocument() {
		return new Criterion("isDocument", AttrName.type, Op.equals, "Document") {
			public boolean assertCri(File file) {
				return file.getType() == FileType.Direntory;
			}
		};
	}
	
	@Override 
	public String toString() {
		return String.format("%15s | %10s | %10s | %20s",name, attrname, op.name(), val.toString());
	}
}
