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
public class Member extends TypedElement {

	

	public static enum Scope {
		
		PRIVATE("-"), PACKAGE("~"), PROTECTED("#"), PUBLIC("+");
		
		private String umlSymbol;
		
		Scope(String symbol) {
			umlSymbol = symbol;
		}
		
		public String umlSymbol() {
			return umlSymbol;
		}
	}
	
	Scope scope = Scope.PUBLIC;
	
	boolean m_static;
	
	Type owner;
	
	/**
	 * @param i_name
	 */
	public Member(String i_name) {
		super(i_name);
	}
	
	/**
	 * @return the scope
	 */
	public Scope getScope() {
		return scope;
	}

	/**
	 * @param i_scope the scope to set
	 */
	public void setScope(Scope i_scope) {
		scope = i_scope;
	}

	/**
	 * @return the static
	 */
	public boolean isStatic() {
		return m_static;
	}

	/**
	 * @param i_static the static to set
	 */
	public void setStatic(boolean i_static) {
		m_static = i_static;
	}

	/**
	 * @return the owner
	 */
	public Type getOwner() {
		return owner;
	}

	/**
	 * @param i_owner the owner to set
	 */
	public void setOwner(Type i_owner) {
		owner = i_owner;
	}

	
	
	
	
	
}
