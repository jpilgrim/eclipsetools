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
package de.jevopi.j2og.config;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class Config {

	public boolean showPrivate;
	public boolean showPackage;
	public boolean showProtected;
	public boolean showPublic;

	public boolean showGetterSetter;
	public boolean showAttributTypes;
	public boolean showParameterTypes;
	public boolean showParameterNames;

	public boolean showAttributes;
	public boolean showOperations;
	public boolean showOverridings;

	public boolean showStaticAttributes;
	public boolean showStaticOperations;

	public boolean convertAttributesToAssociations;
	public boolean forceAllAssociations;
	public boolean showDependencies;

	public boolean recursive;
	public boolean omitCommonPackagePrefix;

	public boolean showContext;

}
