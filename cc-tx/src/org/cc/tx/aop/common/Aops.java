/**
 * Aops.java 4:27:31 PM Apr 29, 2012
 *
 * Copyright(c) 2000-2012 HC360.COM, All Rights Reserved.
 */
package org.cc.tx.aop.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javassist.CtMethod;
import javassist.NotFoundException;

import org.cc.core.common.Exceptions;
import org.cc.tx.aop.asm.AsmFactory;

/**
 * <p>
 * 工具类
 * </p>
 * 
 * @author dixingxing
 * @date Apr 29, 2012
 */
public class Aops {
//	private static AopFactory factory = new JavassistFactory();
	private static AopFactory factory = new AsmFactory();

	public static BytecodeLoader classLoader = new BytecodeLoader();

	/**
	 * 
	 * <p>
	 * 根据字节码加载class
	 * </p>
	 * 
	 * @author dixingxing
	 * @date Apr 29, 2012
	 */
	public static class BytecodeLoader extends ClassLoader {
		public Class<?> defineClass(String className, byte[] byteCodes) {
			return super.defineClass(className, byteCodes, 0, byteCodes.length);
		}
	}

	/**
	 * 
	 * <p>
	 * 获取{@link BytecodeLoader}
	 * </p>
	 * 
	 * @see BytecodeLoader
	 * @return
	 */
	public static BytecodeLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * 
	 * <p>
	 * object类本身的方法不做重写
	 * </p>
	 * <p>
	 * "main" 方法不做重写
	 * </p>
	 * 
	 * @param m
	 * @return
	 */
	public static boolean needOverride(Method m) {
		// object类本身的方法不做重写
		if (m.getDeclaringClass().getName().equals(Object.class.getName())) {
			return false;
		}
		// "main" 方法不做重写
		if (Modifier.isPublic(m.getModifiers())
				&& Modifier.isStatic(m.getModifiers())
				&& m.getReturnType().getName().equals("void")
				&& m.getName().equals("main")) {
			return false;
		}

		return true;
	}

	/**
	 * 
	 * <p>
	 * object类本身的方法不做重写
	 * </p>
	 * <p>
	 * "main" 方法不做重写
	 * </p>
	 * 
	 * @param m
	 * @return
	 */
	public static boolean needOverride(CtMethod m) {
		// object类本身的方法不做重写
		if (m.getDeclaringClass().getName().equals(Object.class.getName())) {
			return false;
		}
		// "main" 方法不做重写
		try {
			if (Modifier.isPublic(m.getModifiers())
					&& Modifier.isStatic(m.getModifiers())
					&& m.getReturnType().getName().equals("void")
					&& m.getName().equals("main")) {
				return false;
			}
		} catch (NotFoundException e) {
			Exceptions.uncheck(e);
		}

		return true;
	}

	/**
	 * 
	 * <p>
	 * 代理方法，增加事务控制
	 * </p>
	 * 
	 * @param <T>
	 * @param obj
	 * @return
	 */
	public static <T> T proxy(T obj) {
		return factory.proxy(obj);
	}

	/**
	 * 
	 * <p>
	 * 把java字节码写入class文件
	 * </p>
	 * 
	 * @param <T>
	 * @param name
	 * @param data
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static <T> void writeClazz(String name, byte[] data) {
		try {
			File file = new File("C:/TEMP/" + name + ".class");
			FileOutputStream fout = new FileOutputStream(file);

			fout.write(data);
			fout.close();
		} catch (Exception e) {
			Exceptions.uncheck(e);
		}
	}
}
