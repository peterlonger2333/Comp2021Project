package hk.edu.polyu.comp.comp2021.g17.cvfs.model.file;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.DiskMemoryNotEnoughException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.FileAlreadyExistException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.FileNotExistException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.IllegalOperationException;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.InvalidArgumentException;


class DiskTest {
	
	Disk disk;
	
	@Test
	@Order(1)
	void constructorAndToStringTest() {
		try {
			disk = new Disk(1000);
			assertEquals("/",disk.toString(),"Disk now should only contain root directory");
			assertThrows(InvalidArgumentException.class, () -> new Disk(39),
					"Invalid Argument should be thrown when disk size less than 40 is attempted.");
		} catch (Exception e) {
			assertEquals(1,2,"This exception should not be triggered.");
		}
		
	}
	
	@Test
	@Order(2)
	void newDirTest() {
		try {
			disk.newDir("dir1");
		}catch(Exception e) {
			assertEquals(1,2,"This exception should not be triggered.");
		}
	}
	
	@Test
	@Order(3)
	void newDocTest() {
		try {
			disk.newDoc("doc1",DocumentType.txt, "Something is written.");
		}catch(Exception e) {
			assertEquals(1,2,"This exception should not be triggered.");
		}
	}
	
	@Test
	@Order(4)
	void changeDirTest() {
		try {
			assertThrows(IllegalOperationException.class, () -> disk.changeDir("Doc1"),
					"IllegalOperationException shall be thrown when changing to a Document is attempted");
			disk.changeDir("dir1");
			assertEquals("/dir1", disk.toString(), "Should change to dir1");
			disk.changeDir("..");
			assertEquals("/", disk.toString(), "Should change to root");
			disk.changeDir(".");
			assertEquals("/", disk.toString(), "Should change to root");
		}catch(Exception e) {
			assertEquals(1,2,"This exception should not be triggered.");
		}
		
	}
	
	@Test
	@Order(5)
	void deleteTest() {
		try {
			disk.delete("doc1");
			assertThrows(FileNotExistException.class, () -> disk.delete("doc2"),
					"FileNotExistException should be thrown");
			disk.newDoc("doc1", DocumentType.txt, "content goes here");
		}catch(Exception e) {
			assertEquals(1,2,"This exception should not be triggered.");
		}
	}
	
	@Test
	@Order(6)
	void renameTest() {
		try {
			disk.rename("doc1","newdoc1");
			disk.rename("newdoc1", "doc1");
			assertThrows(FileNotExistException.class, () -> disk.rename("newdoc1", "doc1"),
					"FileNotExistException should be thrown when renaming non-existant file is attempted");
			assertThrows(FileAlreadyExistException.class, () -> disk.rename("doc1", "doc1"),
					"FileAlreadyExistException should be thrown when renaming to a name that has already existed");
		}catch(Exception e) {
			assertEquals(1,2,"This exception should not be triggered.");
		}
	}
	
	
	@Test
	@Order(7)
	void listTest() {
		assertTimeout(ofMillis(2000),() -> disk.list());
		assertTimeout(ofMillis(2000),() -> disk.rList());
	}
	
	@Test
	@Order(8)
	void memoryExceptionTest() {
		try {
			Disk smallDisk = new Disk(41);
			assertThrows(DiskMemoryNotEnoughException.class, () -> smallDisk.newDir("dir1"));
			assertThrows(DiskMemoryNotEnoughException.class, () -> smallDisk.newDoc("doc1",DocumentType.txt,"safas"));
		} catch (Exception e) {
			assertEquals(1,2,"This exception should not be triggered.");
		}
	}
	
}
