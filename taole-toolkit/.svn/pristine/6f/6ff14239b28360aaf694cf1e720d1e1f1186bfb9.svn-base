package com.taole.toolkit.filesystem.service;

import java.io.File;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

public class ListenedDiskFileItemFactory extends DiskFileItemFactory {

	private UploadListener listener = null;

	public ListenedDiskFileItemFactory(UploadListener listener) {
		super();
		this.listener = listener;
	}

	public ListenedDiskFileItemFactory(int sizeThreshold, File repository,
			UploadListener listener) {
		super(sizeThreshold, repository);
		this.listener = listener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.fileupload.disk.DiskFileItemFactory#createItem(java.lang.String,
	 *      java.lang.String, boolean, java.lang.String)
	 */
	@Override
	public FileItem createItem(String fieldName, String contentType,
			boolean isFormField, String fileName) {
		return new ListenedDiskFileItem(fieldName, contentType, isFormField,
				fileName, getSizeThreshold(), getRepository(), listener);
	}

}
