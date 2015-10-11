package de.jevopi.plist.tests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import de.jevopi.plist.PLArray;
import de.jevopi.plist.PLDict;
import de.jevopi.plist.PLElement;
import de.jevopi.plist.PList;

public class PListTests {

	void assertPList(String fileName, PLElement element) {
		try {
			URL url = this.getClass().getClassLoader().getResource("de/jevopi/plist/tests/" + fileName);
			if (url == null) {
				Assert.fail("Did not found " + fileName);
			}
			byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));
			String expected = new String(bytes, StandardCharsets.UTF_8);
			String actual = element.toString();
			Assert.assertEquals(expected, actual);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			Assert.fail("Error reading expected file " + fileName + ": " + e.getMessage());
		}
	}

	@Test
	public void empty() {
		assertPList("empty.plist", new PList());
	}

	@Test
	public void dict() {
		PList plist = new PList();
		PLDict dict = new PLDict();
		plist.setElement(dict);
		dict.put("someBoolean1", true);
		dict.put("someBoolean2", false);
		dict.put("someInteger", 1);
		dict.put("someReal", 3.14);
		dict.put("someString", "Hello");
		dict.put("someDate", LocalDateTime.of(1970, 5, 17, 0, 0, 0));
		assertPList("dict.plist", plist);
	}

	@Test
	public void array() {
		PList plist = new PList();
		PLArray array = new PLArray();
		plist.setElement(array);
		array.add(true);
		array.add(false);
		array.add(1);
		array.add(3.14);
		array.add("Hello");
		array.add(LocalDateTime.of(1970, 5, 17, 0, 0, 0));
		assertPList("array.plist", plist);
	}

	@Test
	public void mini() {
		PList plist = new PList();
		PLDict dict = new PLDict();
		plist.setElement(dict);
		dict.put("someInteger", 1);
		PLArray array = new PLArray();
		array.add(3.14);
		array.add("World");
		PLDict dict2 = new PLDict();
		dict2.put("someString", "Sub");
		array.add(dict2);
		dict.put("arrayKey", array);
		dict.put("someString", "Hello");
		assertPList("mini.plist", plist);
	}

}
