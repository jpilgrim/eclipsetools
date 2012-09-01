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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class Model {

	
	Map<String, Type> allTypes;

	/**
	 * 
	 */
	public Model() {
		allTypes = new HashMap<String, Type>();
	}

	/**
	 * @param i_arg0
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 * @since Aug 19, 2011
	 */
	public Type get(String fqn) {
		return allTypes.get(fqn);
	}

	/**
	 * @param i_arg0
	 * @param i_arg1
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 * @since Aug 19, 2011
	 */
	public Type add(Type type) {
		return allTypes.put(type.getFqn(), type);
	}

	/**
	 * @return
	 * @since Aug 19, 2011
	 */
	public Collection<Type> allTypes() {
		return allTypes.values();
		
		
		
	}
	
	
	
	
}
