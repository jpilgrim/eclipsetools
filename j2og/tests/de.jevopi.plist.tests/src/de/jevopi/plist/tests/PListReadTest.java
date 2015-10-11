package de.jevopi.plist.tests;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import de.jevopi.plist.PList;

public class PListReadTest {

	void assertPList(String fileName) {
		try {
			URL url = this.getClass().getClassLoader().getResource("de/jevopi/plist/tests/" + fileName);
			if (url == null) {
				Assert.fail("Did not found " + fileName);
			}
			PList list = PList.read(this.getClass().getClassLoader()
					.getResourceAsStream("de/jevopi/plist/tests/" + fileName));
			byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));
			String expected = new String(bytes, StandardCharsets.UTF_8);
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

}
