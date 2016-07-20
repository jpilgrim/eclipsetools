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
package de.jevopi.j2og.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class Model {

	Map<String, Type> allTypes;
	public Set<String> basePackageNames = new HashSet<>();
	public Set<Type> modelTypes = new HashSet<>();

	/**
	 * In some cases set by rewriter.
	 */
	public List<Attribute> assocs = new ArrayList<Attribute>();

	/**
	 *
	 */
	public Model() {
		allTypes = new HashMap<String, Type>();
	}

	public Type get(String fqn) {
		return allTypes.get(fqn);
	}

	public Type add(Type type) {
		modelTypes.add(type);
		return allTypes.put(type.getFqn(), type);
	}

	public void addTypes(Collection<Type> types) {
		for (Type type : types) {
			add(type);
		}
	}

	/**
	 * @return
	 * @since Aug 19, 2011
	 */
	public Collection<Type> allTypes() {
		return allTypes.values();
	}

	public boolean isBasePackage(String packageName) {
		return basePackageNames.contains(packageName);
	}

}
