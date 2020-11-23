package hk.edu.polyu.comp.comp2021.g17.cvfs.model.file;

import java.util.ArrayList;
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
		
		maxSize = size;
		try {
			root = new Directory(".",null);
			currentSize += 40;
			cwd = root;
			path.add("/");
		} catch (InvalidFileNameException e) {
			//This exception is never triggered
			e.printStackTrace();
		} catch (FileAlreadyExistException fe) {
			//This exception is never triggered
			fe.printStackTrace();
		}
	}
	
	/**
	 * Change current working directory to the directory with name name
	 * @param name
	 * @throws IllegalOperationException
	 */
	public void changeDir(String name) throws IllegalOperationException{
		//TODO
		//Be sure to check the type of the file is a Directory
		//Throw an exception if it is a Document
		//Remember to modify the path
	}
	
	/**
	 * Return the path as a string
	 * @return
	 */
	public String toString() {
		//TODO
		//'toString' the path in a desired format
		return null;
	}
	
	/**
	 * Create a new directory in current working directory
	 * @param name
	 * @throws DiskMemoryNotEnoughException
	 */
	public void newDir(String name) throws DiskMemoryNotEnoughException{
		//TODO Use the interface of Directory
		//remember to check the size of disk to make sure it doesn't overflow
	}
	
	/**
	 * Create a new Document in current working directory
	 * @param name
	 * @param type
	 * @param content
	 */
	public void newDoc(String name, DocumentType type, String content) throws DiskMemoryNotEnoughException{
		//TODO Same as above
	}
	
	/**
	 * Delete the file with name name in current working directory
	 * @param name
	 */
	public void delete(String name) throws FileNotExistException {
		//TODO
	}
	
	/**
	 * Rename the file with oldName to newName
	 * @param oldName
	 * @param newName
	 */
	public void rename(String oldName, String newName) throws FileNotExistException, FileAlreadyExistException{
		//TODO
	}
	
	/**
	 * List all files in current working directory, but do not follow directory
	 */
	public void list() {
		//TODO
	}
	
	/**
	 * List all files in current working directory, follow the directory
	 */
	public void rList() {
		//TODO
	}
	
	public ArrayList<File> getFiles(){
		return null;
	}
	
	public ArrayList<File> rGetFiles(){
		return null;
	}
	
	public Directory getRoot() {
		return root;
	}
	
	public Directory getcwd() {
		return cwd;
	}
}
