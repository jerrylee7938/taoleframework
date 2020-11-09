package com.taole.toolkit.filesystem.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.fileupload.disk.DiskFileItem;

public class ListenedDiskFileItem extends DiskFileItem {

	private static final long serialVersionUID = -6330779058372999385L;

	private ListenedOutputStream los = null;

	private UploadListener listener;

	/**
	 * @param fieldName
	 * @param contentType
	 * @param isFormField
	 * @param fileName
	 * @param sizeThreshold
	 * @param repository
	 */
	public ListenedDiskFileItem(String fieldName, String contentType,
			boolean isFormField, String fileName, int sizeThreshold,
			File repository, UploadListener listener) {
		super(fieldName, contentType, isFormField, fileName, sizeThreshold,
				repository);
		this.listener = listener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.fileupload.disk.DiskFileItem#getOutputStream()
	 */
	@Override
	public OutputStream getOutputStream() throws IOException {
		if (los == null) {
			los = new ListenedOutputStream(super.getOutputStream(), listener);
		}
		return los;
	}

}
