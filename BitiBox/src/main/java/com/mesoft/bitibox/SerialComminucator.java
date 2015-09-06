package com.mesoft.bitibox;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerialComminucator {

	final static Logger logger = LoggerFactory
			.getLogger(SerialComminucator.class);

	Util util = new Util();

	void connect(String portName) throws Exception {

		logger.info("SerialComminucator starting with {} portname", portName);

		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			logger.error("Error: Port is currently in use");
		} else {
			int timeout = 2000;
			CommPort commPort = portIdentifier.open(this.getClass().getName(),
					timeout);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

				InputStream in = serialPort.getInputStream();
				OutputStream out = serialPort.getOutputStream();

				(new Thread(new SerialReader(in))).start();
				(new Thread(new SerialWriter(out))).start();

			} else {
				logger.error("Error: Only serial ports are handled by this code.");
			}
		}
	}

	public static class SerialReader implements Runnable {

		InputStream in;

		public SerialReader(InputStream in) {
			this.in = in;
		}

		public void run() {
			byte[] buffer = new byte[1024];
			int len = -1;
			try {
				while ((len = this.in.read(buffer)) > -1) {
					System.out.print(new String(buffer, 0, len));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static class SerialWriter implements Runnable {

		OutputStream out;

		public SerialWriter(OutputStream out) {
			this.out = out;
		}

		public void run() {
			try {

				while (true) {
					Util util = new Util();
					Thread.sleep(Integer.valueOf(util.getConfig("updatePeriod")));
					String count = FacebookAdapter.getValue();
					
					byte[] aa = String.valueOf(Integer.valueOf(count))
							.getBytes();

					out.write(aa, 0, aa.length);

					Thread.sleep(Integer.valueOf(util.getConfig("updatePeriod")));

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void init(String portName) {
		try {
			(new SerialComminucator()).connect(portName);
		} catch (Exception e) {
			logger.error("error in init ", e);
		}
	}
}