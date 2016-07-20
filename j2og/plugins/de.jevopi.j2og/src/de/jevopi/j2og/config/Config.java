/*******************************************************************************
 * Copyright (c) 2012 Jens von Pilgrim
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *    Jens von Pilgrim - initial API and implementation
 ******************************************************************************/
package de.jevopi.j2og.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class Config {

	public final static String SHOW_PRIVATE = "SHOW_PRIVATE";
	public final static String SHOW_PACKAGE = "SHOW_PACKAGE";
	public final static String SHOW_PROTECTED = "SHOW_PROTECTED";
	public final static String SHOW_PUBLIC = "SHOW_PUBLIC";

	public final static String SHOW_GETTERSETTER = "SHOW_GETTERSETTER";
	public final static String SHOW_ATTRIBUTTYPES = "SHOW_ATTRIBUTTYPES";
	public final static String SHOW_PARAMETERTYPES = "SHOW_PARAMETERTYPES";
	public final static String SHOW_PARAMETERNAMES = "SHOW_PARAMETERNAMES";

	public final static String SHOW_ATTRIBUTES = "SHOW_ATTRIBUTES";
	public final static String SHOW_OPERATIONS = "SHOW_OPERATIONS";
	public final static String SHOW_OVERRIDINGS = "SHOW_OVERRIDINGS";

	public final static String ENUMS_AS_ATTRIBUTES = "ENUMS_AS_ATTRIBUTES";


	public final static String SHOW_STATICATTRIBUTES = "SHOW_STATICATTRIBUTES";
	public final static String SHOW_STATICOPERATIONS = "SHOW_STATICOPERATIONS";

	public final static String CONVERTATTRIBUTESTOASSOCIATIONS = "CONVERTATTRIBUTESTOASSOCIATIONS";
	public final static String FORCEALLASSOCIATIONS = "FORCEALLASSOCIATIONS";
	public final static String SHOW_DEPENDENCIES = "SHOW_DEPENDENCIES";

	public static final String RECURSIVE = "RECURSIVE";

	public static final String OMIT_COMMON_PACKAGEPREFIX = "OMIT_COMMON_PACKAGEPREFIX";
	public static final String SHOW_PACKAGE_NAME = "SHOW_PACKAGE_NAME";
	public static final String SHOW_PACKAGE_NAME_CONTEXT = "SHOW_PACKAGE_NAME_CONTEXT";

	public static final String SHOW_CONTEXT = "SHOW_CONTEXT";
	public static final String CONTEXT_GRAY = "CONTEXT_GRAY";

	public static final String SHOW_CONFIRMATION = "SHOW_CONFIRMATION";

	protected final Map<String, Boolean> selections;

	public Config() {
		selections = new HashMap<String, Boolean>();
	}


	public void set(String key, boolean value) {
		selections.put(key, value);
	}

	public boolean is(String key) {
		Boolean value = selections.get(key);
		if (value!=null) return value.booleanValue();
		return false;
	}

}
