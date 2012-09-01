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

package de.jevopi.j2og;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class AppleScriptLauncher {

	public static void launch(String appleScriptCode) {
		String[] cmd = {
			"osascript", "-e", appleScriptCode
		};
		Process process;
		try {
			process = Runtime.getRuntime().exec(cmd);

			BufferedReader bufferedReader =
				new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			String s;
			while ((s = bufferedReader.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}
