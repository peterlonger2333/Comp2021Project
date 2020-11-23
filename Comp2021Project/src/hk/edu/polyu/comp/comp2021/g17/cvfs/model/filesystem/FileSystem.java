package hk.edu.polyu.comp.comp2021.g17.cvfs.model.filesystem;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.DiskMemoryNotEnoughException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.FileAlreadyExistException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.FileNotExistException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.IllegalOperationException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.InvalidArgumentException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.UsageException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.Disk;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.DocumentType;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.File;


public class FileSystem {
	ArrayList<Disk> disks;
	Disk currentDisk;
	HashMap<String,Criterion> criteria;
	ArrayList<String> commandHistory;
	int commandPointer = -1; //points to last command
	
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
						
						//Operation succeed, store the command in history
						if (commandName.compareTo("undo") != 0 && commandName.compareTo("redo") != 0) {
							commandHistory.add(commandName + " " + args);
							commandPointer++;
						}
						
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
	
	public  void delete(String args) throws UsageException, FileNotExistException {
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
	
	public  void rename(String args) throws UsageException, FileNotExistException, FileAlreadyExistException {
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
	
	public  void changeDir(String args) throws  UsageException, IllegalOperationException {
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
	
	public  void newSimpleCri(String args) throws UsageException, InvalidArgumentException {
		Scanner sc = new Scanner(args);
		
		try {
			String[] argStrings = new String[4];
			for (int i=0; i<4; i++) {
				argStrings[i] = sc.next();
			}
			
			criteria.put(argStrings[0], Criterion.newSimpleCri(argStrings[0],argStrings[1],argStrings[2],argStrings[3]));
		}catch(NoSuchElementException nsee) {
			throw new UsageException("Usage: newSimpleCri <name, attrname, oprator, oprand>");
		}finally {
			sc.close();
		}
		
	}
	
	public  void newNegation(String args) throws UsageException, InvalidArgumentException {
		Scanner sc = new Scanner(args);
		
		try {
			String negName = sc.next();
			if (criteria.get(negName) != null) throw new InvalidArgumentException(negName + " already exists");
			String toNegName = sc.next();
			Criterion toNegCri = criteria.get(toNegName);
			if (toNegCri == null) throw new InvalidArgumentException("No criterion: " + toNegName);
			criteria.put(negName, Criterion.newNegation(negName, toNegCri));
		}catch(NoSuchElementException nsee) {
			throw new UsageException("Usage: newNegation <name, criterion>");
		}finally {
			sc.close();
		}
	}
	
	public  void newBinaryCri(String args) throws UsageException, InvalidArgumentException {
		Scanner sc = new Scanner(args);
		
		try {
			String[] argStrings = new String[4];
			for (int i=0; i<4; i++) {
				argStrings[i] = sc.next();
			}
			
			Criterion cri1 = criteria.get(argStrings[1]);
			Criterion cri2 = criteria.get(argStrings[2]);
			
			if (cri1 == null) throw new InvalidArgumentException(argStrings[1] + " does not exist");
			if (cri2 == null) throw new InvalidArgumentException(argStrings[2] + " does not exist");
			
			criteria.put(argStrings[0],Criterion.newBinaryCri(argStrings[0], cri1, cri2, argStrings[3]));
		}catch(NoSuchElementException nsee) {
			throw new UsageException("Usage: newBinaryCri <name, cri1, cri2, binaryOperator>");
		}finally {
			sc.close();
		}
	}
	
	public  void printAllCriteria(String args) throws UsageException {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(args);

		if (sc.hasNext()) throw new UsageException("Usage: printAllCriteria <>");
		
		for (Criterion c : criteria.values()) {
			System.out.println(c.toString());
		}
	}
	
	public  void search(String args) throws UsageException, InvalidArgumentException {
		Scanner sc = new Scanner(args);
		
		try {
			String criName = sc.next();
			Criterion cri = criteria.get(criName);
			if (cri == null) throw new InvalidArgumentException("Criterion '" + criName + "' does not exist");
			
			ArrayList<File> files = currentDisk.getFiles();
			
			for (File f : files) {
				if(cri.assertCri(f)) System.out.println(f.toString());
			}
			
		}catch(NoSuchElementException nsee) {
			throw new UsageException("Usage: search <criName>");
		}finally {
			sc.close();
		}
	}
	
	public  void rSearch(String args) throws InvalidArgumentException, UsageException {
		Scanner sc = new Scanner(args);
		
		try {
			String criName = sc.next();
			Criterion cri = criteria.get(criName);
			if (cri == null) throw new InvalidArgumentException("Criterion '" + criName + "' does not exist");
			
			ArrayList<File> files = currentDisk.rGetFiles();
			
			for (File f : files) {
				if(cri.assertCri(f)) System.out.println(f.toString());
			}
			
		}catch(NoSuchElementException nsee) {
			throw new UsageException("Usage: rSearch <criName>");
		}finally {
			sc.close();
		}
	}
	
	public  void store(String args) {
		//TODO
	}
	
	public  void load(String args) {
		//TODO
	}
	
}
