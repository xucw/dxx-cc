package org.cc.demo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.view.VelocityLayoutServlet;

/**
 * 
 * 自定义velocity异常处理
 * 
 * @author dixingxing	
 * @date Mar 30, 2012
 */
@SuppressWarnings("serial")
public final class VelocityServlet extends VelocityLayoutServlet {

	private static final Log LOG = LogFactory.getLog(VelocityServlet.class);

	@Override
	protected void error(HttpServletRequest req, HttpServletResponse res,
			Throwable excp) {

		Throwable t = excp;
		if (excp instanceof MethodInvocationException) {
			t = ((MethodInvocationException) excp).getWrappedThrowable();
		}

		try {
			if (t instanceof ResourceNotFoundException) {
				LOG.error(t.getMessage() + "(" + req.getRequestURL().toString() + ")");
				if (!res.isCommitted()) {
					res.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				StringBuilder log = new StringBuilder(
						"ERROR：Unknown Velocity Error，url=");
				log.append(req.getRequestURL());
				if (req.getQueryString() != null) {
					log.append('?');
					log.append(req.getQueryString());
				}
				log.append('(');
				log.append(new Date());
				log.append(')');
				LOG.error(log.toString(), t);
				log = null;
				req.setAttribute("javax.servlet.jsp.jspException", t);
				res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} catch (IOException e) {
			LOG.error("Exception occured in VelocityServlet.error", e);
		} catch (IllegalStateException e) {
			LOG.error("==============<<IllegalStateException>>==============",
					e.getCause());
		}
		return;
	}

	@Override
	protected void doRequest(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		long start = System.currentTimeMillis();
		super.doRequest(req, res);

		if (req.getAttribute("close_comment") == null) {
			Date current = Calendar.getInstance(req.getLocale()).getTime();
			PrintWriter pw = res.getWriter();
			pw.print("\r\n<!-- Generated by cc (");
			pw.print(current);
			pw.print(") ");
			pw.print(current.getTime() - start);
			pw.print("ms -->");
		}
	}

}