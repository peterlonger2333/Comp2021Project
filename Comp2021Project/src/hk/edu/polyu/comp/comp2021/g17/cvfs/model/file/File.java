package hk.edu.polyu.comp.comp2021.g17.cvfs.model.file;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.InvalidFileNameException;

public abstract class File {

	String name;
	int size;
	Object content;
	FileType type;
	
	protected File(FileType type,String name, int size, Object content) throws InvalidFileNameException {
		if (!isNameLegal(name)) 
			throw new InvalidFileNameException(name + " is not a valid name\nRequirement: only digits and letters, length not exceeds 10");
		this.name = name;
		this.size = size;
		this.content = content;
		this.type = type;
	}
	
	/**
	 * check is the file name is legal
	 * @return
	 */
	private boolean isNameLegal(String name){
		//TODO
		return true;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return size;
	}
	
}
