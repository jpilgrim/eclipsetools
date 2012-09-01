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

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class NamedElement {

	String name;
	
	boolean context = false;
	
	
	/**
	 * 
	 */
	public NamedElement(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param i_context the context to set
	 */
	public void setContext(boolean i_context) {
		context = i_context;
	}
	
	/**
	 * @return the context
	 */
	public boolean isContext() {
		return context;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @since Oct 31, 2011
	 */
	@Override
	public boolean equals(Object i_obj) {
		if (i_obj == null || !(i_obj instanceof NamedElement)) return false;
		if (i_obj == this) return true;
		NamedElement ne = (NamedElement) i_obj;
		if (name==null) return ne.name==null;
		return name.equals(((NamedElement) i_obj).getName());
	}
	
	/** 
	 * {@inheritDoc}
	 * @see java.lang.Object#hashCode()
	 * @since Oct 31, 2011
	 */
	@Override
	public int hashCode() {
		if (name!=null) return name.hashCode();
		return 0;
	}

}
