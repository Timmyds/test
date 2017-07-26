package com.fxb.world.util;

import java.util.UUID;

public class UUIDUtil {

	/**
	 * 获取32位uuid
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
