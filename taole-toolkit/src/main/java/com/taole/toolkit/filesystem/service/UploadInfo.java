package com.taole.toolkit.filesystem.service;

import java.io.Serializable;

public class UploadInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4698718700104143928L;

	private long totalSize = 0;

	private long readBytes = 0;

	private String status = "done";

	private String fileId;

	private String fileName;
	
	private String fileType;

	/**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
	 * @return Returns the fileId.
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId
	 *            The fileId to set.
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public UploadInfo() {
	}

	public long getReadBytes() {
		return readBytes;
	}

	public void setReadBytes(long readBytes) {
		this.readBytes = readBytes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public boolean isInProgress() {
		return "progress".equals(status) || "start".equals(status);
	}

}
