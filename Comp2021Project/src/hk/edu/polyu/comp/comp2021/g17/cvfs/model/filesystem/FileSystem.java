package hk.edu.polyu.comp.comp2021.g17.cvfs.model.filesystem;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.DiskMemoryNotEnoughException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.InvalidArgumentException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.UsageException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.Disk;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.DocumentType;


public class FileSystem {
	ArrayList<Disk> disks;
	Disk currentDisk;
	ArrayList<Criterion> criteria;
	ArrayList<String> commandHistory;
	
	public static void main(String[] args) {
		new FileSystem();
	}
	
	public FileSystem() {
		//TODO
		//Calling constructor is when the system is entered. Interaction begins here
		//Arguments are passed as String
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String commandName;
		String args;
		String commandPattern = "(newDisk|newDir|newDoc|delete|rename|changeDir|list|rList|newSimpleCri|newNegation|newBinaryCri|printAllCriteria"
				+ "|search|rSearch|store|load|undo|redo)";
		
		while(true) {
			try {
				if (!sc.hasNext(commandPattern)) throw new IllegalArgumentException("Command not found");
				
				else {
					commandName = sc.next();
					args = sc.nextLine();
					
					try {
						Method m = Class.forName("fileSystem.FileSystem").getMethod(commandName,String.class);
						m.invoke(args); //this swallows all exceptions!!!
					} catch(NoSuchMethodException e) {
						//this exception should never be triggered
						e.printStackTrace();
					} catch(InvocationTargetException ie) {
						throw ie.getCause();
					}
				}
				
			}catch(Throwable e) {
				System.err.println(e.getMessage());
			}

		}
	}
	
	/**
	 * create a new disk and use it as current working disk
	 * @param args
	 * @throws UsageException
	 * @throws InvalidArgumentException
	 */
	public  void newDisk(String args) throws UsageException, InvalidArgumentException {
		//parameter parsing ignores the insignificant parts
		Scanner sc = new Scanner(args);
		try {
			int size = sc.nextInt();
			currentDisk = new Disk(size);
			disks.add(currentDisk);
		}catch(NoSuchElementException e) {
			throw new UsageException("Usage: newDisk <diskSize>");
		}finally {
			sc.close();
		}
	}
	
	public  void newDir(String args) throws UsageException, DiskMemoryNotEnoughException {
		Scanner sc = new Scanner(args);
		try {
			String name = sc.next();
			currentDisk.newDir(name);
		}catch(NoSuchElementException e) {
			 throw new UsageException("Usage: newDir <dirName>");
		}finally {
			sc.close();
		}
	}
	
	@SuppressWarnings("resource")
	public  void newDoc(String args) throws UsageException, DiskMemoryNotEnoughException {
		Scanner sc= new Scanner(args);
		try {
			String name = sc.next();
			String type = sc.next("(html|txt|css|java)");
			String content = sc.next("\".*\"");
			DocumentType dType;
			if (type.compareTo("html") == 0) dType = DocumentType.html;
			else if (type.compareTo("txt") == 0) dType = DocumentType.txt;
			else if (type.compareTo("css") == 0) dType = DocumentType.css;
			else dType = DocumentType.java;
			currentDisk.newDoc(name, dType, content);
		}catch(NoSuchElementException e) {
			throw new UsageException("Usage: newDoc <name, type, content>");
		}
		
	}
	
	public  void delete(String args) throws UsageException {
		Scanner sc = new Scanner(args);
		try {
			String name = sc.next();
			currentDisk.delete(name);
		}catch(NoSuchElementException e) {
			 throw new UsageException("Usage: delete <docName>");
		}finally {
			sc.close();
		}
	}
	
	public  void rename(String args) throws UsageException {
		Scanner sc = new Scanner(args);
		try {
			String oldName = sc.next();
			String newName = sc.next();
			currentDisk.rename(oldName, newName);
		}catch(NoSuchElementException e) {
			 throw new UsageException("Usage: rename <oldName, newName>");
		}finally {
			sc.close();
		}
	}
	
	public  void changeDir(String args) throws InvalidArgumentException, UsageException {
		Scanner sc = new Scanner(args);
		try {
			String name = sc.next();
			currentDisk.changeDir(name);
		}catch(NoSuchElementException e) {
			 throw new UsageException("Usage: changeDir <dirName>");
		}finally {
			sc.close();
		}
	}
	
	public  void list(String args) throws UsageException {
		Scanner sc = new Scanner(args);
		try {
			if (sc.hasNext()) throw new UsageException("Usage: list");
		}finally {
			sc.close();
		}
	}
	
	public  void rList(String args) throws UsageException {
		Scanner sc = new Scanner(args);
		try {
			if (sc.hasNext()) throw new UsageException("Usage: rList");
		}finally {
			sc.close();
		}
	}
	
	public  void newSimpleCri(String args) {
		//TODO
	}
	
	public  void newNegation(String args) {
		//TODO
	}
	
	public  void newBinaryCri(String args) {
		//TODO
	}
	
	public  void printAllCriteria(String args) {
		//TODO
	}
	
	public  void search(String args) {
		//TODO
	}
	
	public  void rSearch(String args) {
		//TODO
	}
	
	public  void store(String args) {
		//TODO
	}
	
	public  void load(String args) {
		//TODO
	}
	
	public  void undo(String args) {
		//TODO
	}
	
	public  void redo(String args) {
		//TODO
	}
}
