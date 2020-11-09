package com.taole.framework.util;

public class BooleanUtils {

	public static boolean isTrue(Object value) {
		if (value == null) {
			return false;
		}
		if (Boolean.class.isInstance(value)) {
			return ((Boolean) value).booleanValue();
		}
		if (String.class.isInstance(value)) {
			return Boolean.parseBoolean((String) value);
		}
		if (Integer.class.isInstance(value)) {
			return 1 == ((Integer) value).intValue();
		}
		if (Long.class.isInstance(value)) {
			return 1 == ((Long) value).longValue();
		}
		if (Character.class.isInstance(value)) {
			return '1' == ((Character) value).charValue();
		}
		return false;
	}
}
