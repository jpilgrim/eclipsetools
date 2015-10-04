package de.jevopi.j2og;

public class ModelException extends RuntimeException {

	private static final long serialVersionUID = -1440200752061955071L;

	public ModelException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModelException(String message) {
		super(message);
	}

}
