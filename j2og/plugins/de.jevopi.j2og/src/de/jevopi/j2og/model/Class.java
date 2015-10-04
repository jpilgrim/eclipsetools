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
public class Class extends Type {

	boolean m_abstract;

	Class superClass;

	/**
	 * @param i_name
	 * @param i_packageName
	 */
	public Class(String i_name, String i_packageName) {
		super(i_name, i_packageName);
	}

	/**
	 * @return the abstract
	 */
	public boolean isAbstract() {
		return m_abstract;
	}

	/**
	 * @param i_abstract
	 *            the abstract to set
	 */
	public void setAbstract(boolean i_abstract) {
		m_abstract = i_abstract;
	}

	/**
	 * @return the superClass
	 */
	public Class getSuper() {
		return superClass;
	}

	/**
	 * @param i_superClass
	 *            the superClass to set
	 */
	public void setSuper(Class i_superClass) {
		superClass = i_superClass;
	}

	/**
	 * @param i_name
	 * @return
	 * @since Aug 19, 2011
	 */
	public Attribute findAttributeByName(String i_name) {
		for (Attribute attribute : attributes) {
			if (attribute.name.equalsIgnoreCase(i_name)) {
				return attribute;
			}
		}
		return null;
	}

}
