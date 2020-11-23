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
	 * check if a character variable is either letter or digit
	 * @param temp
	 * @return true if character is letter or digit, false otherwise
	 */
	private boolean isLetterOrDigit(char character) {
		int code = (int)character;
		if ((code >= 48 && code <= 57) || (code >= 65 && code <=90) || (code >= 97 && code <= 122)) {
			return true;
		}
		return false;
	}
	
	/**
	 * check is the file name is legal
	 * @return
	 * i don't know if i am correct : name != null 
	 */
	private boolean isNameLegal(String name){
		//TODO
		if ( name != null && name.length() <= 10) {
			for (int i = 0; i < name.length(); i++) {
				if (!isLetterOrDigit(name.charAt(i)) ) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSize() {
		return size;
	}
	
	public FileType getType() {
		return type;
	}
	
	public Object getContent() {
		return this.content;
	}
	
}
