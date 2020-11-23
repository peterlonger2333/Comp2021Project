package hk.edu.polyu.comp.comp2021.g17.cvfs.model.criterion;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.Document;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.DocumentType;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.File;

class TypeCri extends Criterion{
	DocumentType comparand;
	
	public TypeCri(String criName, DocumentType type) throws IllegalArgumentException {
		super(criName, AttrName.type, Op.equals, (Object)type);
		this.comparand = type;
	}

	@Override
	public
	boolean assertCri(File file) {
		Document doc = (Document) file;
		
		return doc.getDocumentType() == comparand;
	}
	
}
