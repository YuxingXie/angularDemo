package com.dabast.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RuntimeUtil {
	/**
	 * 
	 * @param c 实现类的class
	 * @param szInterface 接口的class
	 * @return
	 */
	public static boolean isInterface(Class c, Class szInterface) {
		Class[] face = c.getInterfaces();
		for (int i = 0, j = face.length; i < j; i++) {
			if (face[i]==szInterface) {
				return true;
			} else {
				Class[] face1 = face[i].getInterfaces();
				for (int x = 0; x < face1.length; x++) {
					if (face1[x]==szInterface) {
						return true;
					} else if (isInterface(face1[x], szInterface)) {
						return true;
					}
				}
			}
		}
		if (null != c.getSuperclass()) {
			return isInterface(c.getSuperclass(), szInterface);
		}
		return false;
	}
	public static void main(String[] args) {
		System.out
				.println(isInterface(ArrayList.class, Collection.class));
		System.out
				.println(ArrayList.class.isAssignableFrom(List.class));
		System.out
		.println(Collection.class.isAssignableFrom(List.class));
	}
}
