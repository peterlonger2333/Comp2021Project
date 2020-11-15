package hk.edu.polyu.comp.comp2021.g17.cvfs.model.file;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.InvalidFileNameException;

public class Document extends File{
	DocumentType type;
	
	public Document(String name, DocumentType type, String content) throws InvalidFileNameException {
		super(FileType.Document,name, 40 + 2 * content.length(),content);
		this.type = type;
	}
	
	/**
	 * get the type of this document file
	 * @return DocumentType of the document
	 */
	public DocumentType getDocumentType() {
		return type;
	}
}
