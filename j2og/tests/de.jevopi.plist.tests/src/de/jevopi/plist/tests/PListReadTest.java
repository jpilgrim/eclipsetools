package de.jevopi.plist.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Assert;
import org.junit.Test;

import de.jevopi.plist.PList;

public class PListReadTest {

	void assertPList(String fileName) {
		try {
			String bundleFileName = "res" + File.separator + fileName;
			FileInputStream fis = new FileInputStream(bundleFileName);
			PList list = PList.read(fis);

			String expected = getFileContent(bundleFileName);
			String actual = list.toString();
			Assert.assertEquals(expected, actual);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error reading expected file " + fileName + ": " + e.getMessage());
		}
	}

	@Test
	public void testReadArray() {
		assertPList("array.plist");
	}

	@Test
	public void testReadDict() {
		assertPList("dict.plist");
	}

	@Test
	public void testReadEmpty() {
		assertPList("empty.plist");
	}

	@Test
	public void testReadMini() {
		assertPList("mini.plist");
	}

	@Test
	public void testReadGraffle() {
		assertPList("classifier.graffle");
	}

	public static String getFileContent(String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(fileName);
		StringBuilder sb = new StringBuilder();
		Reader r = new InputStreamReader(fis, "UTF-8");
		int ch = r.read();
		while (ch >= 0) {
			sb.append((char) ch);
			ch = r.read();
		}
		r.close();
		return sb.toString();
	}

}
