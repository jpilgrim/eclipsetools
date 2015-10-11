package de.jevopi.j2og.graphics;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import de.jevopi.j2og.graphics.geometry.Point;
import de.jevopi.j2og.graphics.properties.LayoutInfo;
import de.jevopi.plist.PLArray;
import de.jevopi.plist.PLDict;
import de.jevopi.plist.PList;

public class GraphDocument extends Element {
	public final String[] applicationVersion = { "de.jevopi.j2og", "1.0.0.0" };
	public final int activeLayerIndex = 0;
	public final boolean autoAdjust = true;
	// backgroundGraphic
	public final int baseZoom = 0;
	public final Point canvasOrigin = new Point(0, 0);
	public final int columnAlign = 1;
	public final double columnSpacing = 36;
	public final String creationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	public final String creator = System.getProperty("user");
	public final String displayScale = "1.000 cm = 1.000 cm";
	public final int graphDocumentVersion = 8;
	public final ArrayList<Graphic> graphicsList = new ArrayList<>();
	public final PLDict gridInfo = new PLDict();
	public final String guidesLocked = NO;
	public final String guidesVisible = YES;
	public final int hPages = 1;
	public final int imageCounter = 1;
	public final boolean keepToScale = false;
	public final Layer[] layers = { new Layer() };
	public final LayoutInfo layoutInfo = new LayoutInfo();
	public final String linksVisible = NO;
	public final String magnetsVisible = NO;
	public final PLArray masterSheets = new PLArray();
	public final String modificationDate = creationDate;
	public final String modifier = creator;
	public final String notesVisible = NO;
	public final int orientation = 2;
	public final String originVisible = NO;
	public final String pageBreaks = YES;
	public final WindowInfo windowInfo = new WindowInfo();

	// printInfo

	public PList getPList() {
		PList plist = new PList();
		plist.setElement(this.toPLElement());
		return plist;
	}

	public void write(File f) throws IOException {
		OutputStream out = new FileOutputStream(f);
		toStream(out);
	}

	public String dumpToString() {
		OutputStream out = new ByteArrayOutputStream();
		try {
			toStream(out);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return out.toString();
	}

	public void toStream(OutputStream out) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
		try {
			getPList().write(writer);
		} finally {
			writer.close();
		}
	}

}
