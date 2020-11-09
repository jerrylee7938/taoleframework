package com.taole.toolkit.filesystem.service;

import java.io.IOException;
import java.io.OutputStream;

public class ListenedOutputStream extends OutputStream {
	
	private OutputStream target;
	
	private UploadListener listener;
	
	public ListenedOutputStream(OutputStream target, UploadListener listener) {
		this.target = target;
		this.listener = listener;
		this.listener.start();
	}
	
	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		target.write(b, off, len);
		listener.readBytes(len - off);
	}
	
	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(byte[] b) throws IOException {
		target.write(b);
		listener.readBytes(b.length);
	}
	
	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		target.write(b);
		listener.readBytes(1);
	}

}
