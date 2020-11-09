package com.taole.toolkit.util;

import java.sql.Blob;
import org.hibernate.Hibernate;

public class BlobConvert {
	public static Blob StringToBlob(String str) throws Exception {
		return Hibernate.createBlob(str.getBytes("utf-8"));
	}

	public static String BlobToString(Blob blob) throws Exception {
		byte[] content = blob.getBytes((long) 1, (int) blob.length());
		return new String(content,"utf-8");
	}
}
