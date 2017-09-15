package org.nobibi.startrace.framework.utils;

import java.util.UUID;

public class UUIDGen {
	
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
