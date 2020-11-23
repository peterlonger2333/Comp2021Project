package hk.edu.polyu.comp.comp2021.g17.cvfs.model.file;

import java.util.LinkedList;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.DiskMemoryNotEnoughException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.FileAlreadyExistException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.FileNotExistException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.IllegalOperationException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.InvalidArgumentException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.InvalidFileNameException;

public class Disk {
	private Directory root;
	private Directory cwd;
	private LinkedList<String> path;
	private final int maxSize;
	private int currentSize;
	
	public Disk(int size) throws InvalidArgumentException{
		//At least 40 bytes so it can have a root directory
		if (size <= 40) throw new InvalidArgumentException("Cannot create a disk less than 40 bytes");

		path = new LinkedList<>();
		maxSize = size;
		try {
			root = new Directory(".",null);
			currentSize += 40;
			cwd = root;
			path.add("/");
		} catch (InvalidFileNameException | FileAlreadyExistException e) {
			//This exception is never triggered
			e.printStackTrace();
		}
	}
	
	/**
	 * Change current working directory to the directory with name name
	 * @param name
	 * @throws IllegalOperationException
	 * @throws FileNotExistException
	 * @throws InvalidFileNameException
	 */
	public void changeDir(String name) throws IllegalOperationException, FileNotExistException, InvalidArgumentException {
		//TODO
		//Be sure to check the type of the file is a Directory
		//Throw an exception if it is a Document
		//Remember to modify the path
		if (name == ".."){
			if (cwd.parent == null) throw new IllegalOperationException("No parent directory");
			else{cwd = cwd.parent;}
		}
		cwd = cwd.findDir(name);
	}
	
	/**
	 * Return the path as a string
	 * @return
	 */
	public String toString() {
		//TODO
		//'toString' the path in a desired format
		return path.toString();
	}
	
	/**
	 * Create a new directory in current working directory
	 * @param name
	 * @throws DiskMemoryNotEnoughException
	 * @throws FileAlreadyExistException
	 * @throws InvalidFileNameException
	 */
	public void newDir(String name) throws DiskMemoryNotEnoughException, FileAlreadyExistException, InvalidFileNameException {
		//TODO Use the interface of Directory
		//remember to check the size of disk to make sure it doesn't overflow
		if (this.maxSize < this.root.getSize() + 40) throw new DiskMemoryNotEnoughException("Disk memory is not enough.");
		cwd.newDir(name);
	}
	
	/**
	 * Create a new Document in current working directory
	 * @param name
	 * @param type
	 * @param content
	 * @throws DiskMemoryNotEnoughException
	 * @throws FileAlreadyExistException
	 * @throws InvalidFileNameException
	 */
	public void newDoc(String name, DocumentType type, String content) throws DiskMemoryNotEnoughException, FileAlreadyExistException, InvalidFileNameException {
		//TODO Same as above
		if (this.maxSize < this.root.getSize() + 40 + content.length() * 2) throw new DiskMemoryNotEnoughException("Disk memory is not enough.");
		cwd.newDoc(name,content,type);
	}
	
	/**
	 * Delete the file with name name in current working directory
	 * @param name
	 * @throws FileNotExistException
	 * @throws IllegalOperationException
	 */
	public void delete(String name) throws FileNotExistException, IllegalOperationException {
		//TODO
		cwd.delete(name);
	}
	
	/**
	 * Rename the file with oldName to newName
	 * @param oldName
	 * @param newName
	 */
	public void rename(String oldName, String newName) throws FileNotExistException, FileAlreadyExistException, InvalidFileNameException {
		//TODO
		cwd.rename(oldName,newName);
	}
	
	/**
	 * List all files in current working directory, but do not follow directory
	 */
	public void list() throws FileNotExistException, InvalidFileNameException {
		//TODO
		cwd.list();
	}
	
	/**
	 * List all files in current working directory, follow the directory
	 */
	public void rList() throws FileNotExistException {
		//TODO
		cwd.rList();
	}
}
