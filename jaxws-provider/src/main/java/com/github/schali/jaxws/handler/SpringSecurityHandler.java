package com.github.schali.jaxws.handler;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.github.schali.jaxws.spring.security.JaxWsPreAuthenticatedProcessingFilter;

public class SpringSecurityHandler extends SpringBeanAutowiringSupport implements SOAPHandler<SOAPMessageContext> {

	Logger LOG = Logger.getLogger(getClass().getName());
	
	@Autowired
	JaxWsPreAuthenticatedProcessingFilter authFilter;
	
	@Resource
	WebServiceContext wsContext;
	
	public void close(MessageContext msgContext) { }

	public boolean handleFault(SOAPMessageContext msgContext) { return false; }

	public boolean handleMessage(SOAPMessageContext msgContext) {
		boolean outbound = (Boolean) msgContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY); 

		if(!outbound) {
			HttpServletRequest request = (HttpServletRequest) msgContext.get(MessageContext.SERVLET_REQUEST);
			HttpServletResponse response = (HttpServletResponse) msgContext.get(MessageContext.SERVLET_RESPONSE);
			try {
				authFilter.authenticateJaxWs(wsContext, request, response);
			}catch (Throwable ex) {
				SecurityContextHolder.clearContext();
				LOG.severe(ex.getMessage());
				LOG.log(Level.SEVERE, ex.getMessage(), ex);
			}
			
		}else {
			SecurityContextHolder.clearContext();
		}
		
		return true;
	}

	public Set<QName> getHeaders() { return null; }

}
