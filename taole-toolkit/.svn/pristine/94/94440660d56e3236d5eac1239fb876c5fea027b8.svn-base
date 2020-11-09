package com.taole.toolkit.filesystem.service;

import javax.servlet.http.HttpServletRequest;

import com.taole.toolkit.filesystem.domain.File;

public class UploadListener {

	private HttpServletRequest request;

	private int totalBytes = 0;

	private int readBytes = 0;

	private String sn;

	public UploadListener(HttpServletRequest request) {
		this.request = request;
		this.totalBytes = request.getContentLength();
		this.sn = request.getParameter("sn");
	}

	public void done() {
		this.readBytes = this.totalBytes;
		updateUploadInfo("done");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.feloo.commons.fileupload.OutputStreamListener#error()
	 */
	public void error() {
		updateUploadInfo("error");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.feloo.commons.fileupload.OutputStreamListener#readBytes(int)
	 */
	public void readBytes(int readBytes) {
		this.readBytes += readBytes;
		updateUploadInfo("progress");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.feloo.commons.fileupload.OutputStreamListener#start()
	 */
	public void start() {
		this.readBytes = 0;
		updateUploadInfo("start");
	}

	private void updateUploadInfo(String status) {
		UploadInfo info = getUploadInfo();
		info.setReadBytes(this.readBytes);
		info.setStatus(status);
	}

	private UploadInfo getUploadInfo() {
		String key = getSessionKey();
		UploadInfo info = (UploadInfo) request.getSession().getAttribute(key);
		if (info == null) {
			info = new UploadInfo();
			request.getSession().setAttribute(key, info);
			info.setTotalSize(totalBytes);
		}
		return info;
	}

	public void setFileInfo(File file) {
		UploadInfo info = getUploadInfo();
		info.setFileId(file.getId());
		info.setFileName(file.getName());
		info.setFileType(file.getMimeType());
	}
	
	
	public void clean() {
		request.getSession().setAttribute(getSessionKey(), null);
	}

	private String getSessionKey() {
		return SESSON_KEY + this.sn;
	}

	public static final String SESSON_KEY = "$upload_info_";

	public static UploadInfo getUploadInfo(HttpServletRequest request) {
		return (UploadInfo) request.getSession().getAttribute(
				SESSON_KEY + request.getParameter("sn"));

	}
}
