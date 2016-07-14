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
import java.util.Iterator;
import java.util.List;

import de.jevopi.j2og.config.Config;
import static de.jevopi.j2og.config.Config.*;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class Operation extends Member {

	List<TypedElement> formalParameters;

	/**
	 * @param i_name
	 */
	public Operation(String i_name) {
		super(i_name);
		formalParameters = new ArrayList<TypedElement>();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see de.jevopi.j2og.model.NamedElement#equals(java.lang.Object)
	 * @since Oct 31, 2011
	 */
	@Override
	public boolean equals(Object i_obj) {
		if (i_obj == this) {
			return true;
		}
		if (!super.equals(i_obj)) {
			return false;
		}

		if (i_obj instanceof Operation) {
			Operation o = (Operation) i_obj;
			return formalParameters.equals(o.formalParameters);
		}
		return false;

	}

	public boolean sameSignature(Operation o2) {
		if (o2.sizeFormalParameters() != sizeFormalParameters()) {
			return false;
		}
		Iterator<TypedElement> iterFP1 = formalParameters().iterator();
		Iterator<TypedElement> iterFP2 = o2.formalParameters().iterator();
		while (iterFP1.hasNext()) {
			if (iterFP1.next().type != iterFP2.next().type) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param i_e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 * @since Aug 18, 2011
	 */
	public boolean addFormalParameter(TypedElement i_e) {
		return formalParameters.add(i_e);
	}

	/**
	 * @param i_index
	 * @return
	 * @see java.util.List#get(int)
	 * @since Aug 18, 2011
	 */
	public TypedElement getFormalParameter(int i_index) {
		return formalParameters.get(i_index);
	}

	public int sizeFormalParameters() {
		return formalParameters.size();
	}

	/**
	 * @return
	 * @see java.util.List#iterator()
	 * @since Aug 18, 2011
	 */
	public Iterable<TypedElement> formalParameters() {
		return formalParameters;
	}

	public String toUML(Config config) {
		StringBuffer out = new StringBuffer();
		out.append(getScope().umlSymbol());
		out.append(name);
		out.append("(");

		if (config.is(SHOW_PARAMETERNAMES) || config.is(SHOW_PARAMETERTYPES)) {

			boolean bFirst = true;
			for (TypedElement fp : formalParameters()) {
				if (!bFirst) {
					out.append(", ");
				} else {
					bFirst = false;
				}

				if (config.is(SHOW_PARAMETERNAMES)) {
					out.append(fp.name);
				}

				if (config.is(SHOW_PARAMETERNAMES) && config.is(SHOW_PARAMETERTYPES)) {
					out.append(": ");
				}

				if (config.is(SHOW_PARAMETERTYPES)) {
					out.append(fp.type.displayName);

					String card = fp.getBoundString();
					if (!card.isEmpty()) {
						out.append("[").append(card).append("]");
					}
				}

			}
		} else {
			if (!formalParameters.isEmpty()) {
				out.append("..");
			}
		}
		out.append(")");
		return out.toString();
	}
}
