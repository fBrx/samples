package com.github.schali.samples.jaxwsprovider.jaxws;

import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class SpringSecurityHandler extends SpringBeanAutowiringSupport implements SOAPHandler<SOAPMessageContext> {

	@Inject
	JaxWsPreAuthenticatedProcessingFilter authFilter;
	
	@Resource
	WebServiceContext ctx;
	
	@Override
	public void close(MessageContext msgContext) { }

	@Override
	public boolean handleFault(SOAPMessageContext msgContext) { return false; }

	@Override
	public boolean handleMessage(SOAPMessageContext msgContext) {
		boolean outbound = (Boolean) msgContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY); 

		if(!outbound) {
			HttpServletRequest request = (HttpServletRequest) msgContext.get(MessageContext.SERVLET_REQUEST);
			HttpServletResponse response = (HttpServletResponse) msgContext.get(MessageContext.SERVLET_RESPONSE);
			authFilter.authenticateJaxWs(ctx, request, response);
			
		}else {
			SecurityContextHolder.clearContext();
		}
		
		return true;
	}

	@Override
	public Set<QName> getHeaders() { return null; }

}
