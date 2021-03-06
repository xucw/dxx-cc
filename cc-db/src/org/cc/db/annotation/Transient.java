/**
 * Colum.java 3:02:56 PM Feb 6, 2012
 *
 * Copyright(c) 2000-2012 HC360.COM, All Rights Reserved.
 */
package org.cc.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 指定属性和数据库无关(瞬时)
 * 
 * @see SqlBuilder#buildInsert(Object)
 * @see SqlBuilder#buildUpdate(Object, String)
 * @author dixingxing
 * @date Feb 6, 2012
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transient {
	boolean value() default true;
}
