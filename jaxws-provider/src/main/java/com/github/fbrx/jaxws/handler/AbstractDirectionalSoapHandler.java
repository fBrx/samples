package com.github.fbrx.jaxws.handler;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public abstract class AbstractDirectionalSoapHandler<C extends SOAPMessageContext> implements SOAPHandler<C> {

	public boolean handleMessage(SOAPMessageContext context) {
		Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		HttpServletRequest request = (HttpServletRequest) context.get(MessageContext.SERVLET_REQUEST);
		HttpServletResponse response = (HttpServletResponse) context.get(MessageContext.SERVLET_RESPONSE);
		
		if(isOutbound)
			return handleOutboundMessage(context, request, response);
		else
			return handleInboundMessage(context, request, response);
	}

	protected abstract boolean handleInboundMessage(SOAPMessageContext context, HttpServletRequest request, HttpServletResponse response);

	protected abstract boolean handleOutboundMessage(SOAPMessageContext context, HttpServletRequest request, HttpServletResponse response);

	public void close(MessageContext context) {
		//noop
	}
	
	public Set<QName> getHeaders() {
		return null;
	}
}
