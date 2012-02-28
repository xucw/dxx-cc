/**
 * MainServlet.java 5:05:10 PM Feb 15, 2012
 *
 * Copyright(c) 2000-2012 HC360.COM, All Rights Reserved.
 */
package org.cc.demo.web;


import org.apache.log4j.Logger;
import org.cc.core.web.DispatcherServlet;
import org.cc.core.web.WebMethod;
import org.cc.demo.jms.PerformanceLogProducer;
import org.cc.demo.log.Log;
import org.cc.demo.log.LogContext;

/**
 * 
 * 
 * @author dixingxing
 * @date Feb 15, 2012
 */
@SuppressWarnings("serial")
public class MainServlet extends DispatcherServlet {
	private final static Logger logger = Logger.getLogger(MainServlet.class);

	@Override
	protected void afterProcess(WebMethod webMethod, long start) {
		long cost = costTime(start);
		Log log = LogContext.getLog(webMethod);
		if (log != null) {
			log.setCost(cost);
			PerformanceLogProducer.log(log);
		}
		logger.debug("����" + webMethod.method.getName() + "��ʱ" + cost + "����");
	}

	@Override
	protected void exceptionOccured(WebMethod webMethod, long start, Throwable e) {
		long cost = costTime(start);
		Log log = LogContext.getLog(webMethod);
		if (log != null) {
			log.setCost(cost);
			log.setError(e);
			PerformanceLogProducer.log(log);
		}
		logger.debug("�����쳣��ʱ" + cost + "����", e);
	}

	private long costTime(long start) {
		return (System.currentTimeMillis() - start);
	}

}