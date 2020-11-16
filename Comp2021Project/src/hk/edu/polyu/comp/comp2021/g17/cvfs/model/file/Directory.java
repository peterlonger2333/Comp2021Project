package hk.edu.polyu.comp.comp2021.g17.cvfs.model.file;

import java.util.HashMap;
import java.util.Iterator;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.FileAlreadyExistException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.FileNotExistException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.IllegalOperationException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.InvalidFileNameException;

class Dirent implements Iterable<File>{
	//A Dirent is a dictionary
	HashMap<String,File> dirent = new HashMap<String,File>();
	
	/**
	 * put a new File into Directory entry
	 * @param name
	 * @param f
	 * @throws FileAlreadyExistException
	 */
	void put(String name, File f) throws FileAlreadyExistException{
		File temp = dirent.get(name);
		//We do not permit directory and document to share the same name
		if (temp != null) throw new FileAlreadyExistException("File " + name + " already exists");
		dirent.put(name, f);
	}
	
	/**
	 * get file by name
	 * @param name
	 * @return a file with name name
	 * @throws FileNotExistException 
	 */
	File get(String name) throws FileNotExistException {
		File temp = dirent.get(name);
		if (temp == null) throw new FileNotExistException("\"" + name + "\" does not exist");
		return dirent.get(name);
	}
	
	/**
	 * remove a file(directory or document)
	 * @param name
	 * @return size of the removed file
	 * @throws FileNotExistException
	 */
	int remove(String name) throws FileNotExistException  {
		File temp = this.get(name);
		int size = temp.size;
		dirent.remove(name);
		return size;
	}

	public Iterator<File> iterator() {
		return dirent.values().iterator();
	}
}

public class Directory extends File{
	
	Directory parent; //Keep a pointer to its parent for easy tracing-up
	Dirent content; //Get a content field for itself so we don't need to cast every time we use the dirent
	/**
	 * Constructor, pass in name and its parent
	 * @param name
	 * @param parent
	 * @throws InvalidFileNameException
	 */
	public Directory(String name, Directory parent) throws InvalidFileNameException {
		super(FileType.Direntory, name, 40, null);
		this.parent = parent;
		Dirent dir = new Dirent();
		try {
			dir.put(".",this);
			dir.put("..",parent);
			this.size += 80; 
			//TODO don't know if this is correct 
		} catch (FileAlreadyExistException e) {
			//This exception is never triggered
			e.printStackTrace();
		}
		super.content = dir;
		this.content = dir;
	}
	
	/**
	 * add a new document with name name, type type, to the directory entry
	 * @param name
	 * @param content
	 * @param type
	 */
	public void newDoc(String name, String content, DocumentType type) throws FileAlreadyExistException {
		//TODO create a document and add it to directory entry
		//remember to increase the sizes of the directory and its parent and so on... up to root, whose parent is null
	}
	
	/**
	 * add a new directory to the entry
	 * @param name
	 */
	public void newDir(String name) throws FileAlreadyExistException{
		//TODO add a new Directory to the entry
		//adjust size
	}
	
	/**
	 * delete a file(directory or document) from the entry
	 * @param name
	 * @throws IllegalOperationException
	 */
	public void delete(String name) throws FileNotExistException, IllegalOperationException{
		//TODO delete a file from the entry with name name
		//remember to correct the sizes
		//also note that it can't delete "." and "..", an exception shall be thrown if such cases occur
	}
	/**
	 * rename the file from oldName to newName
	 * exceptions are thrown when 1)oldName does not exist 2)newName already exists
	 * @param oldName
	 * @param newName
	 */
	public void rename(String oldName, String newName) throws FileNotExistException{
		//TODO rename the file(dir or doc)
	}
	
	/**
	 * An interface for changeDir
	 * @param name
	 * @return Directory with name name
	 */
	public Directory findDir(String name) {
		//TODO find from the dirent the directory with name name and return it
		//a cast is needed since we want a Directory not just a File
		return null;
	}
	
	/**
	 * list all files in this directory
	 */
	public void list() {
		//TODO list all entry names in dirent, but do not get into a directory
		//note that dirent is iterable
	}
	
	/**
	 * recursively list all files
	 */
	public void rList() {
		//TODO list all entry names in dirent, follow the all directories except "." and ".."
	}
	
	public String toString() {
		//TODO output should be in this format: [.,..,dir1,doc1, ...]
		return null;
	}
}
