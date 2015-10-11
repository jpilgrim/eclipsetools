package de.jevopi.plist;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class PList extends PLElement {
	PListObject element;
	String version = "1.0";

	public static class DummyEntityResolver implements EntityResolver {
		@Override
		public InputSource resolveEntity(String publicID, String systemID) throws SAXException {

			return new InputSource(new StringReader(""));
		}
	}

	@Override
	protected void serialize(Writer w, int indent) {
		println(w, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		println(w,
				"<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
		println(w, "<plist version=\"" + version + "\">");
		if (element != null) {
			element.serialize(w, 0);
			println(w);
		}
		println(w, "</plist>");
	}

	public void setElement(PListObject element) {
		this.element = element;
	}

	public void write(Writer w) {
		serialize(w, 0);
	}

	public static PList read(InputStream input) throws ParserConfigurationException, SAXException, IOException {
		XMLReader xmlreader = XMLReaderFactory.createXMLReader();
		PListHandler handler = new PListHandler();
		xmlreader.setContentHandler(handler);
		xmlreader.setEntityResolver(new DummyEntityResolver());
		InputSource inputSource = new InputSource(input);
		xmlreader.parse(inputSource);
		return handler.getPList();
	}
}
