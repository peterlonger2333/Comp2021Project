package hk.edu.polyu.comp.comp2021.g17.cvfs.model.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.*;

class Dirent implements Iterable<File>{
	//A Dirent is a dictionary keys -- String mapped object -- File 16 of initial capacity 16*0.75 to double the capacity
	HashMap<String,File> dirent = new LinkedHashMap<>();

	/**
	 * put a new File into Directory entry
	 * @param name
	 * @param f
	 * @throws FileAlreadyExistException
	 */
	void put(String name, File f) throws FileAlreadyExistException {
		if (name.equals(f.getName())){
			File temp = dirent.get(name);
			//We do not permit directory and document to share the same name
			if (temp != null) throw new FileAlreadyExistException("File already exists.");
			dirent.put(name, f);
		}
	}
	
	/**
	 * get file by name
	 * @param name
	 * @return a file with name name
	 * @throws FileNotExistException
	 */
	File get(String name) throws FileNotExistException {
		File temp = dirent.get(name);
		if (temp == null) throw new FileNotExistException("File does not exist");
		return dirent.get(name);
	}
	
	/**
	 * remove a file(directory or document)
	 * @param name
	 * @return true or false
	 * @throws FileNotExistException
	 */
	int remove(String name) throws FileNotExistException {
		File temp = this.get(name);
		int size = temp.size;
		dirent.remove(name);
		return size;
	}

	int getSize(){ return dirent.size(); }

	public Iterator<File> iterator() { return dirent.values().iterator(); }
	public Iterator<String> keyIterator(){ return dirent.keySet().iterator(); }
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
	public Directory(String name, Directory parent) throws InvalidFileNameException, FileAlreadyExistException {
		// size = 40 + total size of its contained file?
		super(FileType.Direntory, name, 40, null);
		this.parent = parent;
		Dirent dir = new Dirent();
		try {
			dir.put(".",this);
			if(parent != null)  dir.put("..",parent);
		} catch (FileAlreadyExistException e) {
			//This exception is never triggered
			e.printStackTrace();
		}
		super.content = dir; //redundant ?
		//this.content = dir;
		if(this.parent != null){
			this.parent.content.put(name,this);
			changeSize(this.parent, this,'+');
		}
	}

	/**
	 * change the size of dic and all its parent by adding/subtracting the size of file, depending on sign
	 * @param dic
	 * @param file
	 * @param sign
	 */
	private void changeSize(Directory dic, File file, char sign){
		Directory cur = dic;
		while(cur != null){
			cur.size = (sign == '+') ? (cur.size += file.getSize()): (cur.size -= file.getSize()); // only + and else
			cur = cur.parent;
		}
	}

	/**
	 * check if a character variable is either letter or digit
	 * @param character
	 * @return true if character is letter or digit, false otherwise
	 */
	private boolean isLetterOrDigit(char character) {
		int code = character;
		if ((code >= 48 && code <= 57) || (code >= 65 && code <=90) || (code >= 97 && code <= 122)) {
			return true;
		}
		return false;
	}

	/**
	 * check is the file name is legal
	 * @param name
	 * @return true if name is legal, false otherwise
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

	/**
	 * add a new document with name name, type type, to the directory entry
	 * @param name
	 * @param content
	 * @param type
	 * @throws FileAlreadyExistException
	 * @throws InvalidFileNameException
	 */
	public void newDoc(String name, String content, DocumentType type) throws FileAlreadyExistException, InvalidFileNameException {
		//TODO create a document and add it to directory entry
		//remember to increase the sizes of the directory and its parent and so on... up to root, whose parent is null
		Document newdoc =new Document(name,type,content);
		this.content.put(name,newdoc);
		changeSize(this, newdoc,'+');
	}
	
	public void newDoc(String name, String content, String type) throws FileAlreadyExistException, InvalidFileNameException {
		DocumentType rightOne = null;
		for (DocumentType t:DocumentType.values()) {
			if (t.name().compareTo(type) == 0) {
				rightOne = t;
				break;
			}
		}
		newDoc(name, content, rightOne);
	}
	
	/**
	 * add a new directory to the entry
	 * @param name
	 * @throws FileAlreadyExistException
	 * @throws InvalidFileNameException
	 */
	public void newDir(String name) throws FileAlreadyExistException, InvalidFileNameException {
		//TODO add a new Directory to the entry
		//remember to initialize the directory (add "." and "..")
		//and configure the sizes (of itself, its parent, ... up to root
		Directory newdir = new Directory(name,this);

	}
	
	/**
	 * delete a file(directory or document) from the entry
	 * @param name
	 * @throws FileNotExistException
	 * @throws IllegalOperationException
	 */
	public void delete(String name) throws FileNotExistException, IllegalOperationException {
		//TODO delete a file from the entry with name name
		//remember to correct the sizes
		//also note that it can't delete "." and "..", an exception shall be thrown if such cases occur
		if(name.equals(".") || name.equals("..")) throw new IllegalOperationException("Deleting the directory is not allowed.");
		File removed = this.content.get(name); // get the removed object, check if it exists or left for information
		changeSize(this,removed,'-');
		this.content.remove(name);

	}
	/**
	 * rename the file from oldName to newName
	 * exceptions are thrown when 1)oldName does not exist 2)newName already exists
	 * @param oldName
	 * @param newName
	 * @throws FileNotExistException
	 * @throws FileAlreadyExistException
	 * @throws InvalidFileNameException
	 */
	public void rename(String oldName, String newName) throws FileNotExistException, FileAlreadyExistException, InvalidFileNameException {
		//TODO rename the file(dir or doc)
		File toChangeName = content.get(oldName);
		if(!isNameLegal(newName)) throw new InvalidFileNameException("Invalid name.");
		toChangeName.name = newName; // not sure if this will work
		content.remove(oldName);
		content.put(newName,toChangeName);
	}
	
	/**
	 * An interface for changeDir
	 * @param name
	 * @return Directory with name
	 * @throws FileNotExistException
	 */
	public Directory findDir(String name) throws FileNotExistException, InvalidArgumentException {
		//TODO find from the dirent the directory with name name and return it
		//a cast is needed since we want a Directory not just a File
		if (this.content.get(name).getClass() != this.getClass()) throw new InvalidArgumentException("Not a directory.");
		return (Directory) this.content.get(name);
	}

	/**
	 * An interface for changeDir
	 * @param name
	 * @return Document with name
	 * @throws FileNotExistException
	 */
	public Document findDoc(String name) throws FileNotExistException, InvalidArgumentException {
		//TODO find from the dirent the directory with name name and return it
		//a cast is needed since we want a Directory not just a File
		if (this.content.get(name).getClass() == this.getClass()) throw new InvalidArgumentException("Not a document.");
		return (Document) this.content.get(name);
	}

	
	/**
	 * list all files directly in this directory
	 * @throws FileNotExistException
	 */
	public void list() throws FileNotExistException, InvalidFileNameException, IllegalOperationException {
		//TODO list all entry names in dirent, but do not get into a directory
		//note that dirent is iterable
		//it is ok to print the .txt .css in this file
		Iterator<String> it = this.content.keyIterator();
		it.next();
		it.next();
		Document temp = new Document("temp",DocumentType.txt,"");
		int number = 0, size = 0;
		//it.next();
		System.out.println("\nIn directory " + this.getName() + ": ");
		while (it.hasNext()){
			String itFile = it.next();
			if(content.get(itFile).getClass() == this.getClass()){
				System.out.println("  Directory: " + itFile + " | Size: " + this.content.get(itFile).size );
			}
			else if(content.get(itFile).getClass() == temp.getClass()){
				System.out.println("  Document: " + itFile + " | Type: " + content.get(itFile).type +
						" | Size: " + this.content.get(itFile).size );
			}
			number++;
			size += this.content.get(itFile).size;
		}
		System.out.println("\nTotal number of files: " + number);
		System.out.println("Total sizes of files: " + size);
	}

	/**
	 * recursively list all documents and directories
	 * @param dir
	 * @param blank
	 * @throws FileNotExistException
	 */
	public int dList(Directory dir,String blank) throws FileNotExistException, IllegalOperationException {
		int count = 0;
		Iterator<String> it = dir.content.keyIterator();
		blank += "  ";
		it.next();
		it.next();
		while(it.hasNext()){
			String itFile = it.next();
			if (dir.content.get(itFile).getClass() == dir.getClass()){
				Directory other = (Directory) dir.content.get(itFile);
				System.out.println(blank + "Directory: " + other.getName() + " | Size: " + other.content.getSize() );
				count += dList(other,blank);
			}
			else{
				System.out.println(blank + "File: " + dir.content.get(itFile).getName() + " | Type: " + dir.content.get(itFile).type +
						" | Size: " + dir.content.get(itFile).getSize() );
			}
			count++;
		}
		return count;
	}
	
	/**
	 * recursively list all files
	 */
	public void rList() throws FileNotExistException, IllegalOperationException {
		//TODO list all entry names in dirent, follow the all directories except "." and ".."
		int number = 0;
		String blank = "";
		System.out.println("\nIn directory " + this.getName() + ": ");
		number += dList(this,blank);
		System.out.println("\nTotal number of files: " + number);
		System.out.println("Total sizes of files: " + this.getSize());
	}
	// print the parent
	public String toString() {
		//TODO output should be in this format: [.,..,dir1,doc1, ...]
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int i = 0;
		Iterator<String> it = this.content.keyIterator();
		//it.next();
		while (it.hasNext()){
			String itFile = it.next();
			sb.append(itFile);
			if (it.hasNext())
				sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public ArrayList<File> getFiles(){
		ArrayList<File> result = new ArrayList<File>();
		Iterator<String> ite = content.keyIterator();

		while (ite.hasNext()) {
			String name = ite.next();
			if (name.compareTo(".") == 0 ||name.compareTo("..") == 0) continue;
			try {
				result.add(content.get(name));
			} catch (FileNotExistException e) {
				// TODO Auto-generated catch block
				// never reached
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public ArrayList<File> rGetFiles(){
		ArrayList<File> result = new ArrayList<File>();
		Iterator<String> ite = content.keyIterator();

		while (ite.hasNext()) {
			try {
				String fileName = ite.next();
				File file = content.get(fileName);
				
				if (file instanceof Document) result.add(file);
				
				else {
					if (fileName.compareTo("..") == 0) {
						continue;
					}else if (fileName.compareTo(".") == 0) {
						result.add(file);
					}else {
						Directory dir = (Directory) file;
						result.addAll(dir.rGetFiles());
					}
				}
			} catch (FileNotExistException e) {
				// Never triggered
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public Directory getDirectory(String name) throws FileNotExistException {
		return (Directory) content.get(name);
	}
}
