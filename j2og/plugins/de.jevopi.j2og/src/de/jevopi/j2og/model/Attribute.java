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

import de.jevopi.j2og.config.Config;



/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class Attribute extends Member {

	/**
	 * @param i_name
	 */
	public Attribute(String i_name) {
		super(i_name);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 * @since Aug 19, 2011
	 */
	@Override
	public String toString() {
		String s = getName() + ": " + getType();
		String b = getBoundString();
		if (b != null && !b.isEmpty()) {
			s += "[" + b + "]";
		}
		return s;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.jevopi.j2og.model.TypedElement#toUML()
	 * @since Aug 19, 2011
	 */

	public String toUML(Config config) {
		StringBuilder out = new StringBuilder();
		out.append(getScope().umlSymbol());
		out.append(getName());

		if (config.showAttributTypes) {
			out.append(": ");
			out.append(getType().getName());

			String card = getBoundString();
			if (!card.isEmpty()) {
				out.append("[").append(card).append("]");
			}
		}
		
		return out.toString();

	}
}
