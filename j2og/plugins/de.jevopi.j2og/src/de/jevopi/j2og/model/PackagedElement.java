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
public class PackagedElement extends NamedElement {

	String packageName;
	
	

	/**
	 * @param i_name
	 */
	public PackagedElement(String name, String packageName) {
		super(name);
		this.packageName = packageName;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see de.jevopi.j2og.model.NamedElement#equals(java.lang.Object)
	 * @since Oct 31, 2011
	 */
	@Override
	public boolean equals(Object i_obj) {
		if (!super.equals(i_obj)) return false;
		if (i_obj instanceof PackagedElement) {
			PackagedElement pe = (PackagedElement) i_obj;
			if (packageName == null) return pe.packageName == null;

			return packageName.equals(pe.packageName);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.jevopi.j2og.model.NamedElement#hashCode()
	 * @since Oct 31, 2011
	 */
	@Override
	public int hashCode() {
		int h=0;
		if (name!=null) h = name.hashCode();
		if (packageName!=null) h+= 31*packageName.hashCode();
		return h;
	}

	/**
	 * @return the fqn
	 */
	public String getFqn() {
		if (packageName != null && packageName.length() > 0)
			return packageName + "." + name;
		else
			return name;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 * @since Aug 18, 2011
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " " + getFqn();
	}

}
