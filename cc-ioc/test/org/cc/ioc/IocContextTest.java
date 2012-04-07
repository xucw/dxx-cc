/**
 * IocContextTest.java 4:58:20 PM Apr 7, 2012
 *
 * Copyright(c) 2000-2012 HC360.COM, All Rights Reserved.
 */
package org.cc.ioc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * 
 * @author dixingxing	
 * @date Apr 7, 2012
 */
public class IocContextTest {
	private static final Logger LOG = Logger.getLogger(IocContextTest.class);

	/**
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		IocContext.init();
	}
	
	@After
	public void tearDown() {
		IocContext.clear();
	}

	/**
	 * Test method for {@link org.cc.ioc.IocContext#init()}.
	 */
	@Test
	public void testInit() {
		IocContext ic = IocContext.get(IocContext.class);
		// 没有标记 ioc 也没有属性需要ioc 
		assertNull(ic);
		
		IocContext ic2 = IocContext.get(IocContext.class);
		assertTrue(ic == ic2);
		
		assertNotNull(IocContext.get(ProductDetail.class));
		assertNotNull(IocContext.get(Product.class));
		
		Order order = IocContext.get(Order.class);
		assertNotNull(order);
		
		User user = IocContext.get(User.class);
		User userImpl = IocContext.get(UserMan.class);
		assertNotNull(user);
		assertNotNull(userImpl);
		assertTrue(user == userImpl);
		
		assertTrue(order.getUser() == user);
		
		for(Class<?> key : IocContext.iMap.keySet()) {
			List<Class<?>> impls = IocContext.iMap.get(key);
			for(Class<?> impl : impls) {
				LOG.debug(key.getName() + ":" + impl.getName());
			}
		}
	}

	/**
	 * Test method for {@link org.cc.ioc.IocContext#get(java.lang.Class)}.
	 */
	@Test
	public void testGet() {
		Order o = IocContext.get(Order.class);
		assertNotNull(o);
		assertNotNull(o.getProduct());
	}
	
	/**
	 * Test method for {@link org.cc.ioc.IocContext#clear()}.
	 */
	@Test
	public void testClear() {
		IocContext.clear();
		assertNull(IocContext.get(IocContext.class));
	}
	
}
