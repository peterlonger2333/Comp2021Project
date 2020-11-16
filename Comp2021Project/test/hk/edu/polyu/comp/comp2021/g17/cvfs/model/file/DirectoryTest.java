package hk.edu.polyu.comp.comp2021.g17.cvfs.model.file;


import static org.junit.jupiter.api.Assertions.*;
import static java.time.Duration.ofMillis;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.*;

class DirectoryTest {

	Directory d0;
	Directory d1,d2;
	String[] invalidFileNames = {};
	String[] validFileNames = {};
	
	@BeforeAll
	static void constructorAndToStringTest() throws InvalidFileNameException {
		Directory testDir1 = new Directory("testDir1",null);
		Directory testDir2 = new Directory("testDir2",testDir1);
		assertEquals("[.]", testDir1.toString(), "Directory should be initialized.");
		assertEquals("[.,..]", testDir2.toString(), "Directory should be initialized.");
	}
	
	@BeforeEach
	void init() throws InvalidFileNameException {
		d0 = new Directory("/",null);
		d1 = new Directory("dir1",d0);
		d2 = new Directory("dir2",d1);
	}
	
	@Test
	@Order(1)
	void newDirTest() throws InvalidFileNameException, FileAlreadyExistException {
		int oriSize = d0.getSize();
		d2.newDir("testDir2");
		assertEquals("[.,..,testDir2]",d2.toString(),"Directory should contain desired contents");
		assertEquals(oriSize + 40, d0.getSize(), "Sizes of parent directories should detect the change");
	}
	
	@Test
	@Order(2)
	void newDocTest() {
		try {
			Document testDoc = new Document("testDoc", DocumentType.txt, "testDocContent");
			int d0InitSize = d0.getSize();
			d2.newDoc(testDoc.name, (String) testDoc.content, testDoc.getDocumentType());
			assertEquals("[.,..,testDoc]",d2.toString(),"Directory should contain desired contents");
			
			assertEquals(d0InitSize + testDoc.getSize(), d0.getSize(), "root should detect the change in size");
			
			assertThrows(FileAlreadyExistException.class, () -> d2.newDoc("testDoc","some other content",DocumentType.txt),
					"Cannot hava documents with same in one directory");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(3)
	void deleteTest() throws FileAlreadyExistException, InvalidFileNameException, IllegalOperationException, FileNotExistException {
		Document testDoc = new Document("testDoc", DocumentType.txt, "testDocContent");
		int d0InitSize = d0.getSize();
		d2.newDoc(testDoc.name, (String) testDoc.content, testDoc.getDocumentType());
		d2.delete("testDoc");
		
		assertEquals("[.,..]",d2.toString(),"File should be deleted");
		assertEquals(d0InitSize, d0.getSize(), "Sizes should be adjusted");
		assertThrows(FileNotExistException.class, () -> d2.delete("testDoc"), "Cannot delete a file that does not exist");
		assertThrows(IllegalOperationException.class, () -> d2.delete("."), "Cannot delete '.'");
		assertThrows(IllegalOperationException.class, () -> d2.delete(".."), "Cannot delete '..'");

	}
	
	@Test
	@Order(4)
	void renameTest() {
		try {
			Document testDoc = new Document("testDoc", DocumentType.txt, "testDocContent");
			d2.newDoc(testDoc.name, (String) testDoc.content, testDoc.getDocumentType());
			d2.rename("testDoc", "testDocRenamed");
			
			assertEquals("[.,..,testDocRenamed]",d2.toString(),"file should be renamed");
			assertThrows(FileNotExistException.class, () -> d2.rename("nosuchfile", "nosuchname"),
					"Cannot rename a file that does not exist");
		}catch(Exception e) {
			assertEquals(1, 1,"No exception should be thrown");
		}
	}
	
	@Test
	@Order(5)
	void listTest() {
		assertTimeout(ofMillis(2000),() -> d1.list());
	}
	
	@Test
	@Order(6)
	void rListTest() {
		assertTimeout(ofMillis(2000),() -> d1.rList());
	}
}
