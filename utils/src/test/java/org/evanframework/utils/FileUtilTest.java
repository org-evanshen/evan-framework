package org.evanframework.utils;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class FileUtilTest {

	@Test
	public void testWriteFile() throws IOException {
		FileUtils.writeStringToFile(new File("e:/js/1.txt"), "c\nb","UTF-8",false);
	}

	@Test
	public void testReadfile() throws IOException {
		String s = FileUtils.readFile("d:/124/0.dat");
		System.out.println(s);
		//String s2 = FileUtils.readFileToString(new File("‪d:/124/1.dat"));
		//System.out.println(s2);
	}

	@Test
	public void testGetDirByNameprefix() {
		fail("Not yet implemented");
	}

	@Test
	public void testRenameFile() {
		FileUtils.renameFile("e:/js/1.txt", "2.txt");
	}

	@Test
	public void testRenameDir() {

	}

	@Test
	public void testCopyFile() throws IOException {
		FileUtils.copyFile("e:/js/2.txt", "e:/a.txt");
	}

	@Test
	public void testDeletefile() {
		FileUtils.deleteFile("e:/js/3.txt");
	}

	@Test
	public void testDeletedir() {
		//FileUtil.deleteDir("e:/js/");	
	}
}
