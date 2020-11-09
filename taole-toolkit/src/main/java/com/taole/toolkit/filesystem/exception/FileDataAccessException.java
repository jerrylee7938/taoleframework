/**
 * <pre>
 * Copyright:		Copyright(C) 2005-2006, feloo.org
 * Filename:		FileDataAccessException.java
 * Module:			TODO
 * Class:			FileDataAccessException
 * Date:			2007-8-17
 * Author:			
 * Description:		TODO
 *
 *
 * ======================================================================
 * Change History Log
 * ----------------------------------------------------------------------
 * Mod. No.	| Date		| Name			| Reason			| Change Req.
 * ----------------------------------------------------------------------
 * 			| 2007-8-17	| 	| Created			|
 *
 * </pre>
 **/

package com.taole.toolkit.filesystem.exception;

/**
 * Class: FileDataAccessException Remark: TODO
 * 
 * @author: 
 */
public class FileDataAccessException extends RuntimeException {

	private static final long serialVersionUID = -9149623471190219758L;

	/**
	 * Constructor for FileDataAccessException.
	 * 
	 * @param msg
	 *            the detail message
	 */
	public FileDataAccessException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for FileDataAccessException.
	 * 
	 * @param msg
	 *            the detail message
	 * @param ex
	 *            root cause (usually from using a underlying data access API such as JDBC)
	 */
	public FileDataAccessException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
