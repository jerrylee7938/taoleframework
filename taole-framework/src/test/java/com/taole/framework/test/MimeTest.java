package com.taole.framework.test;

import java.util.Collection;

import org.junit.Test;

import eu.medsea.mimeutil.MimeUtil;

public class MimeTest {

	static {
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.WindowsRegistryMimeDetector");
	}

	
	@Test
	public void test() {
		Collection<?> mimes = MimeUtil.getMimeTypes("c:\test.jpg");
		for (Object o: mimes) {
			System.out.println(o);
		}
	}
}
