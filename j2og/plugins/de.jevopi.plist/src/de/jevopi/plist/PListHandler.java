package de.jevopi.plist;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Reading PLIST is basically only needed for tests. Thus, conformance with
 * OmniGraffle's output has highest priority.
 */
public class PListHandler extends DefaultHandler {

	private static final DateTimeFormatter DATETIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss xxxx");

	interface ValueSetter {
		void addString(String text);

		void addData(String text);

		void addDate(TemporalAccessor temporalAccessor);

		void addReal(double d);

		void addInteger(int i);

		void addBoolean(boolean value);

		void addCollection(PListObject plElement);
	}

	class DictSetter implements ValueSetter {
		PLDict dict;

		public DictSetter(PLDict element) {
			dict = element;
		}

		@Override
		public void addString(String text) {
			dict.put(key, text);
		}

		@Override
		public void addData(String text) {
			dict.put(key, text);
		}

		@Override
		public void addDate(TemporalAccessor temporalAccessor) {
			dict.put(key, temporalAccessor);
		}

		@Override
		public void addReal(double d) {
			dict.put(key, d);
		}

		@Override
		public void addInteger(int i) {
			dict.put(key, i);
		}

		@Override
		public void addBoolean(boolean value) {
			dict.put(key, value);
		}

		@Override
		public void addCollection(PListObject plistobject) {
			dict.put(key, plistobject);
		}

	}

	class ArraySetter implements ValueSetter {
		PLArray array;

		public ArraySetter(PLArray element) {
			array = element;
		}

		@Override
		public void addString(String text) {
			array.add(text);
		}

		@Override
		public void addData(String text) {
			array.add(text);
		}

		@Override
		public void addDate(TemporalAccessor temporalAccessor) {
			array.add(temporalAccessor);
		}

		@Override
		public void addReal(double d) {
			array.add(d);
		}

		@Override
		public void addInteger(int i) {
			array.add(i);
		}

		@Override
		public void addBoolean(boolean value) {
			array.add(value);
		}

		@Override
		public void addCollection(PListObject plistobject) {
			array.add(plistobject);
		}

	}

	class ListSetter implements ValueSetter {
		PList list;

		public ListSetter(PList list) {
			this.list = list;
		}

		@Override
		public void addString(String text) {
			list.setElement(new PLPrimitive(text));
		}

		@Override
		public void addData(String text) {
			list.setElement(new PLPrimitive(text));
		}

		@Override
		public void addDate(TemporalAccessor temporalAccessor) {
			list.setElement(new PLPrimitive(temporalAccessor));
		}

		@Override
		public void addReal(double d) {
			list.setElement(new PLPrimitive(d));
		}

		@Override
		public void addInteger(int i) {
			list.setElement(new PLPrimitive(i));
		}

		@Override
		public void addBoolean(boolean value) {
			list.setElement(new PLPrimitive(value));
		}

		@Override
		public void addCollection(PListObject plistobject) {
			list.setElement(plistobject);
		}

	}

	String key = null;
	String text = null;
	private Stack<ValueSetter> setters = new Stack<>();
	PList plist;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		ValueSetter setter = null;
		switch (qName) {
		case "plist":
			plist = new PList();
			plist.version = attributes.getValue("version");
			setter = new ListSetter(plist);
			break;
		case "array":
			PLArray array = new PLArray();
			setters.peek().addCollection(array);
			setter = new ArraySetter(array);
			break;
		case "dict":
			PLDict dict = new PLDict();
			setters.peek().addCollection(dict);
			setter = new DictSetter(dict);
			break;
		}
		if (setter != null) {
			setters.push(setter);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String s = String.copyValueOf(ch, start, length);
		if (text == null) {
			text = s;
		} else {
			text += s;
		}
		text = text.trim();
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
		case "key":
			key = text;
			break;
		case "string":
			setters.peek().addString(text);
			break;
		case "data":
			setters.peek().addData(text);
			break;
		case "date":
			setters.peek().addDate(parseTime());
			break;
		case "real":
			setters.peek().addReal(Double.parseDouble(text));
			break;
		case "integer":
			setters.peek().addInteger(Integer.parseInt(text));
			break;
		case "true":
			setters.peek().addBoolean(true);
			break;
		case "false":
			setters.peek().addBoolean(false);
			break;
		case "array":
		case "dict":
		case "plist":
			setters.pop();
			break;
		}
		text = null;
	}

	private TemporalAccessor parseTime() {
		String s = text + "1970-01-01 00:00:00 +0000".substring(text.length());
		return LocalDateTime.parse(s, DATETIME_PATTERN);
	}

	public PList getPList() {
		return plist;
	}
}
