package hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception;

@SuppressWarnings("serial")
public class FileNotExistException extends Exception{
	public FileNotExistException(String msg) {
		super(msg);
	}
}
