/**
 * A.java 10:29:00 AM Feb 9, 2012
 *
 * Copyright(c) 2000-2012 HC360.COM, All Rights Reserved.
 */
package org.cc.core.data;

/**
 * 
 * 
 * @author dixingxing
 * @date Feb 9, 2012
 */
@SuppressWarnings("serial")
public class Child extends Father implements People {
	public static final int UUID = 0;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
