package com.github.fbrx.jaxws.handler;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.github.fbrx.util.XMLUtils;

public class LoggingInHandler extends AbstractDirectionalSoapHandler<SOAPMessageContext> {

	private Logger LOG = Logger.getLogger(LoggingInHandler.class.getName());
	
	public boolean handleFault(SOAPMessageContext context) {
		//noop on faults, since they are always outbound
		return true;
	}

	protected boolean handleInboundMessage(SOAPMessageContext context, HttpServletRequest request, HttpServletResponse response) {
		SOAPMessage sm = context.getMessage();
		SOAPPart sp = sm.getSOAPPart();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLUtils.serializeXml(sp, baos);
		try {
			LOG.info(baos.toString("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			LOG.info(baos.toString());
		}
		
		return true;
	}

	protected boolean handleOutboundMessage(SOAPMessageContext context, HttpServletRequest request, HttpServletResponse response) {
		//noop on outbound messages
		return true;
	}
	
}
