package hk.edu.polyu.comp.comp2021.g17.cvfs.model.criterion;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.DocumentType;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.File;

class TypeCri extends Criterion{
	public TypeCri(String criName, DocumentType type) throws IllegalArgumentException {
		super(criName, AttrName.type, Op.equals, (Object)type);
	}

	@Override
	public
	boolean assertCri(File file) {
		// TODO Auto-generated method stub
		// cast file to a document then read its type
		return false;
	}
	
}
