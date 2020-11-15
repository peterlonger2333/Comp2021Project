package hk.edu.polyu.comp.comp2021.g17.cvfs.model.file;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hk.edu.polyu.comp.comp2021.g17.cvfs.model.file.File;
import hk.edu.polyu.comp.comp2021.g17.cvfs.model.exception.*;

class DirectoryTest {

	Directory root;
	String[] invalidFileNames = {};
	String[] validFileNames = {};
	
	DirectoryTest() {
		try {
			root = new Directory("/",null);
		} catch (InvalidFileNameException e) {
			return;
		}
	}
	
	@Test
	void invalidFileNameExceptionTest_throws() {
		
	}
	
	@Test
	void invalidFileNameExceptionTest_nothrows() {
		
	}
}
