package de.jevopi.j2og.tests;

import static de.jevopi.j2og.config.Config.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import de.jevopi.j2og.config.Config;
import de.jevopi.j2og.graphics.GraphDocument;
import de.jevopi.j2og.graphics.Graphic;
import de.jevopi.j2og.model.Attribute;
import de.jevopi.j2og.model.Class;
import de.jevopi.j2og.model.Operation;
import de.jevopi.j2og.model.Type;
import de.jevopi.j2og.umlgraphics.ClassifierShapes;
import de.jevopi.plist.PLDict;
import de.jevopi.plist.PList;
import de.jevopi.plist.PListObject;

public class ClassifierTest {

	@Test
	public void testClassifier() throws Exception {
		GraphDocument graphDocument = new GraphDocument();

		Type ta = new Class("SomeClass", "");
		ta.addAttribute(new Attribute("attributeA"));
		ta.addAttribute(new Attribute("attributeB"));
		ta.addOperation(new Operation("operationA"));
		ta.addOperation(new Operation("operationB"));

		Config config = new Config();
		config.set(SHOW_ATTRIBUTES, true);
		config.set(SHOW_OPERATIONS, true);
		ClassifierShapes cshapes = new ClassifierShapes(config);

		Graphic ga = cshapes.createClassifier(ta, 27, 27);
		graphDocument.graphicsList.add(ga);

		// String actual = graphDocument.dumpToString();
		// String expected = new
		// String(Files.readAllBytes(Paths.get("res/classifier.graffle")),
		// StandardCharsets.UTF_8);
		File f = new File("classifierJ2OG.graffle");

		OutputStream out = new FileOutputStream(f);
		OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
		try {
			graphDocument.getPList().write(writer);
		} finally {
			writer.close();
		}

		PList actualList = graphDocument.getPList();
		PList expectList = PList
				.read(new ByteArrayInputStream(Files.readAllBytes(Paths.get("res/classifier.graffle"))));

		actualList.removeAllKeys("Bounds", "Magnets", "Points", "Text", "TextPlacement");
		expectList.removeAllKeys("Bounds", "Magnets", "Points", "Text", "TextPlacement");

		PListObject actualGraphics = ((PLDict) actualList.getElement()).get("GraphicsList");
		PListObject expectGraphics = ((PLDict) expectList.getElement()).get("GraphicsList");

		Assert.assertEquals(expectGraphics.toString(), actualGraphics.toString());
		// Assert.assertEquals(expectList.toString(), actualList.toString());

	}

}