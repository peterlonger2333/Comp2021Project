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
	Directory d1, d2;
	String[] invalidFileNames = {};
	String[] validFileNames = {};

	@BeforeAll
	static void constructorAndToStringTest() throws InvalidFileNameException, FileAlreadyExistException {
		Directory testDir1 = new Directory("testDir1", null);
		assertEquals("[.]", testDir1.toString(), "Directory should be initialized.");
		Directory testDir2 = new Directory("testDir2", testDir1);
		assertEquals("[.,..]", testDir2.toString(), "Directory should be initialized.");
		assertEquals("[.,testDir2]", testDir1.toString(), "Parent directory should detect changes (in dirent)");
	}


	@BeforeEach
	void init() throws InvalidFileNameException, FileAlreadyExistException {
		d0 = new Directory("root", null);
		d1 = new Directory("dir1", d0);
		d2 = new Directory("dir2", d1);
		/*
		System.out.println(d0);
		System.out.println(d1);
		System.out.println(d2);
		*/
	}

	@Test
	void newDirTest() {
		try {
			int oriSize = d0.getSize();
			d2.newDir("testDir2");
			assertEquals("[.,..,testDir2]", d2.toString(), "Directory should contain desired contents");
			assertEquals(oriSize + 40, d0.getSize(), "Sizes of parent directories should detect the change");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			assertEquals(1, 2, "No exception shall be thrown");
		}

	}

	@Test
	void newDocTest() {
		try {
			Document testDoc = new Document("testDoc", DocumentType.txt, "testDocContent");
			int d0InitSize = d0.getSize();
			d2.newDoc(testDoc.name, (String) testDoc.content, testDoc.getDocumentType());
			assertEquals("[.,..,testDoc]", d2.toString(), "Directory should contain desired contents");

			assertEquals(d0InitSize + testDoc.getSize(), d0.getSize(), "root should detect the change in size");

			assertThrows(FileAlreadyExistException.class, () -> d2.newDoc("testDoc", "some other content", DocumentType.txt),
					"Cannot hava documents with same in one directory");
		} catch (Exception e) {
			assertEquals(1, 2, "No exception shall be thrown");
		}
	}

	@Test
	void deleteTest() {
		try {
			Document testDoc = new Document("testDoc", DocumentType.txt, "testDocContent");
			int d0InitSize = d0.getSize();
			d2.newDoc(testDoc.name, (String) testDoc.content, testDoc.getDocumentType());
			d2.delete("testDoc");

			assertEquals("[.,..]", d2.toString(), "File should be deleted");
			assertEquals(d0InitSize, d0.getSize(), "Sizes should be adjusted");
			assertThrows(FileNotExistException.class, () -> d2.delete("testDoc"), "Cannot delete a file that does not exist");
			assertThrows(IllegalOperationException.class, () -> d2.delete("."), "Cannot delete '.'");
			assertThrows(IllegalOperationException.class, () -> d2.delete(".."), "Cannot delete '..'");
		} catch (Exception e) {
			assertEquals(1, 2, "No exception shall be thrown");
		}
	}

	@Test
	void renameTest() {
		try {
			Document testDoc = new Document("testDoc", DocumentType.txt, "testDocContent");
			d2.newDoc(testDoc.name, (String) testDoc.content, testDoc.getDocumentType());
			d2.rename("testDoc", "testDocRenamed");

			assertEquals("[.,..,testDocRenamed]", d2.toString(), "file should be renamed");
			assertThrows(FileNotExistException.class, () -> d2.rename("nosuchfile", "nosuchname"),
					"Cannot rename a file that does not exist");
		} catch (Exception e) {
			assertEquals(1, 2, "No exception shall be thrown");
		}
	}

	@Test
	//it should print contents it have in its level only.
	void listTest() {
		//assertTimeout(ofMillis(2000),() -> d1.list());
		try {
			d2.newDoc("pig", "i hate you", DocumentType.txt);
			d2.newDir("rabbit");
			d2.findDir("rabbit").newDoc("pigHome", "this is my home", DocumentType.txt);
			d2.list();
			d2.findDir("rabbit").list();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	// it should print all the contents it have (directory / document type)
	void rListTest() {
		//assertTimeout(ofMillis(2000),() -> d1.rList());
		try {
			d2.newDoc("pig", "i hate you", DocumentType.txt);
			d2.newDir("rabbit");
			d2.findDir("rabbit").newDoc("pigHome", "this is my home", DocumentType.txt);
			d2.rList();
		} catch (Exception e) {

		}
	}
}
