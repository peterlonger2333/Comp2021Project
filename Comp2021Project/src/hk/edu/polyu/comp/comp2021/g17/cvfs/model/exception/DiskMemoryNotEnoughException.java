package hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception;

@SuppressWarnings("serial")
public class DiskMemoryNotEnoughException extends Exception{
	public DiskMemoryNotEnoughException(String msg) {
		super(msg);
	}
}