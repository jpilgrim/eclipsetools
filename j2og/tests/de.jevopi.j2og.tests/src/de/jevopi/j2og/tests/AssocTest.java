package de.jevopi.j2og.tests;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import de.jevopi.j2og.config.Config;
import de.jevopi.j2og.graphics.GraphDocument;
import de.jevopi.j2og.graphics.Graphic;
import de.jevopi.j2og.model.Attribute;
import de.jevopi.j2og.model.Class;
import de.jevopi.j2og.model.Type;
import de.jevopi.j2og.umlgraphics.ClassifierShapes;
import de.jevopi.j2og.umlgraphics.Lines;
import de.jevopi.plist.PLDict;
import de.jevopi.plist.PList;
import de.jevopi.plist.PListObject;

public class AssocTest {

	@Test
	public void testAssoc() throws Exception {
		GraphDocument graphDocument = new GraphDocument();

		Type ta = new Class("SomeClassA", "");
		Type tb = new Class("SomeClassB", "");
		Attribute attr = new Attribute("attributeA2B");
		attr.type = tb;

		Config config = new Config();

		config.convertAttributesToAssociations = true;
		config.showAttributes = true;
		config.showOperations = true;
		ClassifierShapes cshapes = new ClassifierShapes(config);

		Graphic ga = cshapes.createClassifier(ta, 100, 100);
		Graphic gb = cshapes.createClassifier(tb, 200, 200);
		graphDocument.graphicsList.add(ga);
		graphDocument.graphicsList.add(gb);
		Collection<Graphic> assoc = Lines.createAssociation(attr, ga, gb);
		graphDocument.graphicsList.addAll(assoc);

		File f = new File("assocJ2OG.graffle");

		OutputStream out = new FileOutputStream(f);
		OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
		try {
			graphDocument.getPList().write(writer);
		} finally {
			writer.close();
		}

		PList actualList = graphDocument.getPList();
		PList expectList = PList.read(new ByteArrayInputStream(Files.readAllBytes(Paths.get("res/assoc.graffle"))));

		actualList.removeAllKeys("Bounds", "Magnets", "Points", "Text");
		expectList.removeAllKeys("Bounds", "Magnets", "Points", "Text");

		PListObject actualGraphics = ((PLDict) actualList.getElement()).get("GraphicsList");
		PListObject expectGraphics = ((PLDict) expectList.getElement()).get("GraphicsList");

		Assert.assertEquals(expectGraphics.toString(), actualGraphics.toString());
		// Assert.assertEquals(expectList.toString(), actualList.toString());

	}

}