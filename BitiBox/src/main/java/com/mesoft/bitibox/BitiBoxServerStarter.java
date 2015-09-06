package com.mesoft.bitibox;

import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitiBoxServerStarter {

	final static Logger logger = LoggerFactory
			.getLogger(BitiBoxServerStarter.class);

	public static String getSerialPortName() {
		List<String> vp = new ArrayList<String>();
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier) portList
					.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				logger.debug("Port Name:" + portId.getName());
				return portId.getName();

			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		// We will create our server running at http://localhost:8070

		Util util = new Util();

		String port = util.getConfig("serverPort");

		SerialComminucator ser = new SerialComminucator();

		String portName = getSerialPortName();
		if (portName == null) {
			logger.error("Port name not found . Display not found");
			return;
		}

		ser.init(portName);

		Server server = new Server(Integer.valueOf(port));

		logger.info("BitiBox Starting Port Number :{}", port);

		Context root = new Context(server, "/", Context.SESSIONS);
		root.addServlet(new ServletHolder(new ControlServlet()), "/cs");

		server.start();
		server.join();

	}
}