package hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception;

@SuppressWarnings("serial")
public class FileAlreadyExistException extends Exception{
	public FileAlreadyExistException(String msg) {
		super(msg);
	}
}