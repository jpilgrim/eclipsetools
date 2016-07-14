package de.jevopi.j2og.graphics.decorators;

public enum Arrow {
	/**
	 * <pre>
	 * -----
	 * </pre>
	 */
	None("0"),
	/**
	 * <pre>
	 * ---->
	 * </pre>
	 */
	StickArrow("StickArrow"),
	/**
	 * <pre>
	 * ---|>
	 * </pre>
	 */
	UMLInheritance("UMLInheritance"),
	/**
	* <pre>
	* ---<>
	* </pre>
	*/
	FilledDiamond("FilledDiamond");

	String value;

	private Arrow(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
