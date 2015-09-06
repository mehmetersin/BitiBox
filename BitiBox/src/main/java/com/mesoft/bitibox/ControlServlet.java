package com.mesoft.bitibox;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlServlet extends HttpServlet {

	final static Logger logger = LoggerFactory.getLogger(ControlServlet.class);

	public Util util = new Util();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String key = req.getParameter("key");
		String value = req.getParameter("value");
		
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");

		logger.debug("Request Parameters . Key {} ,Value {}", key,
				value);
		
		util.setConfig(key, value);

		String remoteId = req.getRemoteAddr();
		String remoteHost = req.getRemoteHost();

		try {
			out.print("Iamalive");

		} catch (Exception e) {
			logger.error("Error while processing", e);
			out.print("ERROR");
		}

	}
}