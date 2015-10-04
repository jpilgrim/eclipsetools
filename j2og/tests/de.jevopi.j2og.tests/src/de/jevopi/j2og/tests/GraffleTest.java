package de.jevopi.j2og.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import de.jevopi.j2og.graphics.GraphDocument;
import de.jevopi.j2og.graphics.Graphic;
import de.jevopi.j2og.model.Class;
import de.jevopi.j2og.model.Interface;
import de.jevopi.j2og.model.Type;
import de.jevopi.j2og.umlgraphics.ClassifierShapes;
import de.jevopi.j2og.umlgraphics.Lines;

public class GraffleTest {

	public static void main(String[] args) throws IOException {
		GraphDocument graphDocument = new GraphDocument();

		Type ta = new Class("SomeAImplC", "packA");
		Type tb = new Class("SomeBDepC", "packB");
		Type tc = new Interface("InterfaceC", "packC");
		Type td = new Class("SomeDSubA", "packB");

		Graphic ga = ClassifierShapes.createClassifierSimple(ta, 100, 100);
		Graphic gb = ClassifierShapes.createClassifierSimple(tb, 300, 100);
		Graphic gc = ClassifierShapes.createClassifierSimple(tc, 300, 200);
		Graphic gd = ClassifierShapes.createClassifierSimple(td, 100, 200);

		Collection<Graphic> assoc = Lines.createAssociation(ga, gb);
		Collection<Graphic> impl = Lines.createImplementation(ga, gc);
		Collection<Graphic> gen = Lines.createGeneralization(gd, ga);
		Collection<Graphic> dep = Lines.createDependency(gb, gc);

		graphDocument.graphicsList.add(ga);
		graphDocument.graphicsList.add(gb);
		graphDocument.graphicsList.add(gc);
		graphDocument.graphicsList.add(gd);
		graphDocument.graphicsList.addAll(assoc);
		graphDocument.graphicsList.addAll(impl);
		graphDocument.graphicsList.addAll(gen);
		graphDocument.graphicsList.addAll(dep);

		File f = new File("lines.graffle");

		OutputStream out = new FileOutputStream(f);
		OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
		try {
			graphDocument.getPList().write(writer);
		} finally {
			writer.close();
		}

		System.out.println("Done");
	}

}
