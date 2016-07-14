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

import static de.jevopi.j2og.config.Config.*;

import de.jevopi.j2og.config.Config;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class Attribute extends Member {

	private boolean containment = false;
	private boolean derived = false;

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
		String s = name + ": " + type;
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
		if (derived) {
			out.append("/");
		}
		out.append(name);

		if (config.is(SHOW_ATTRIBUTTYPES)) {
			out.append(": ");
			out.append(type.name);

			String card = getBoundString();
			if (!card.isEmpty()) {
				out.append("[").append(card).append("]");
			}
		}

		return out.toString();

	}

	public void setContainment(boolean containment) {
		this.containment = containment;
	}

	public boolean isContainment() {
		return containment;
	}

	public void setDerived(boolean derived) {
		this.derived = derived;
	}

	public boolean isDerived() {
		return derived;
	}
}
