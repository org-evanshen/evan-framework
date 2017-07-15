package org.evanframework.utils;

import java.io.IOException;

public class PdfConvertException extends IOException {
	private static final long serialVersionUID = -4132765686889655059L;

	public PdfConvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public PdfConvertException(String message) {
		super(message);
	}
}