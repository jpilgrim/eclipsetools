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

	public final static ConfigEntry SHOW_PRIVATE = new ConfigEntry("SHOW_PRIVATE", false);
	public final static ConfigEntry SHOW_PACKAGE = new ConfigEntry("SHOW_PACKAGE", false);
	public final static ConfigEntry SHOW_PROTECTED = new ConfigEntry("SHOW_PROTECTED", false);
	public final static ConfigEntry SHOW_PUBLIC = new ConfigEntry("SHOW_PUBLIC", true);

	public final static ConfigEntry SHOW_GETTERSETTER = new ConfigEntry("SHOW_GETTERSETTER", true);
	public final static ConfigEntry SHOW_ATTRIBUTTYPES = new ConfigEntry("SHOW_ATTRIBUTTYPES", true);
	public final static ConfigEntry SHOW_PARAMETERTYPES = new ConfigEntry("SHOW_PARAMETERTYPES", true);
	public final static ConfigEntry SHOW_PARAMETERNAMES = new ConfigEntry("SHOW_PARAMETERNAMES", true);

	public final static ConfigEntry SHOW_ATTRIBUTES = new ConfigEntry("SHOW_ATTRIBUTES", true);
	public final static ConfigEntry SHOW_OPERATIONS = new ConfigEntry("SHOW_OPERATIONS", true);
	public final static ConfigEntry SHOW_OVERRIDINGS = new ConfigEntry("SHOW_OVERRIDINGS", true);

	public final static ConfigEntry ENUMS_AS_ATTRIBUTES = new ConfigEntry("ENUMS_AS_ATTRIBUTES", false);


	public final static ConfigEntry SHOW_STATICATTRIBUTES = new ConfigEntry("SHOW_STATICATTRIBUTES", true);
	public final static ConfigEntry SHOW_STATICOPERATIONS = new ConfigEntry("SHOW_STATICOPERATIONS", true);

	public final static ConfigEntry CONVERTATTRIBUTESTOASSOCIATIONS = new ConfigEntry("CONVERTATTRIBUTESTOASSOCIATIONS", true);
	public final static ConfigEntry FORCEALLASSOCIATIONS = new ConfigEntry("FORCEALLASSOCIATIONS", true);
	public final static ConfigEntry SHOW_DEPENDENCIES = new ConfigEntry("SHOW_DEPENDENCIES", true);

	public static final ConfigEntry RECURSIVE = new ConfigEntry("RECURSIVE", true);

	public static final ConfigEntry OMIT_COMMON_PACKAGEPREFIX = new ConfigEntry("OMIT_COMMON_PACKAGEPREFIX", true);
	public static final ConfigEntry SHOW_PACKAGE_NAME = new ConfigEntry("SHOW_PACKAGE_NAME", true);
	public static final ConfigEntry SHOW_PACKAGE_NAME_CONTEXT = new ConfigEntry("SHOW_PACKAGE_NAME_CONTEXT", true);

	public static final ConfigEntry SHOW_CONTEXT = new ConfigEntry("SHOW_CONTEXT", true);
	public static final ConfigEntry CONTEXT_GRAY = new ConfigEntry("CONTEXT_GRAY", true);

	public static final ConfigEntry SHOW_CONFIRMATION = new ConfigEntry("SHOW_CONFIRMATION", true);

	protected final Map<String, Boolean> selections;

	public Config() {
		selections = new HashMap<String, Boolean>();
	}


	public void set(ConfigEntry key, boolean value) {
		selections.put(key.name, value);
	}

	public boolean is(ConfigEntry key) {
		Boolean value = selections.get(key.name);
		if (value!=null) return value.booleanValue();
		return key.defaultValue;
	}

}
