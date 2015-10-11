package de.jevopi.j2og.tests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import de.jevopi.j2og.graphics.GraphDocument;
import de.jevopi.j2og.graphics.Graphic;
import de.jevopi.j2og.model.Attribute;
import de.jevopi.j2og.model.Class;
import de.jevopi.j2og.model.Operation;
import de.jevopi.j2og.model.Type;
import de.jevopi.j2og.umlgraphics.ClassifierShapes;

public class ClassifierTest {

	@Test
	public void testClassifier() throws IOException {
		GraphDocument graphDocument = new GraphDocument();

		Type ta = new Class("SomeClass", "");
		ta.addAttribute(new Attribute("attributeA"));
		ta.addAttribute(new Attribute("attributeB"));
		ta.addOperation(new Operation("operationA"));
		ta.addOperation(new Operation("operationB"));

		Graphic ga = ClassifierShapes.createClassifier(ta, 10, 10, true, true);
		graphDocument.graphicsList.add(ga);

		String actual = graphDocument.dumpToString();
		String expected = new String(Files.readAllBytes(Paths.get("res/classifier.graffle")), StandardCharsets.UTF_8);

		Assert.assertEquals(expected, actual);
	}

}