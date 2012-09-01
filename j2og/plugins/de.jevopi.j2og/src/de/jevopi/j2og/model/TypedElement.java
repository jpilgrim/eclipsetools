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
public class TypedElement extends NamedElement {
	
	Type type;
	int min = 0;
	int max = 1;
	
	
	
	/**
	 * @param i_name
	 */
	public TypedElement(String i_name) {
		super(i_name);
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param i_type the type to set
	 */
	public void setType(Type i_type) {
		type = i_type;
	}
	
	public void setBounds(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public String getBoundString() {
		if (min==0 && max==1) {
			return "";
		}
		
		if (min<0 && max<0) {
			return "n";
		}
		
		if (min==max) {
			return String.valueOf(min);
		}
		
		if (min==0 && max<0) return "*";
		
		String s = min + "..";
		s += max<0 ? "*" : String.valueOf(max);
		
		return s;
		
	}
	
	public String toUML() {
		StringBuffer out = new StringBuffer();
		
		out.append(getName());
		out.append(": ");
		out.append(getType().getName());

		String card = getBoundString();
		if (!card.isEmpty()) {
			out.append("[").append(card).append("]");
		}
		return out.toString();
	}
	
}
