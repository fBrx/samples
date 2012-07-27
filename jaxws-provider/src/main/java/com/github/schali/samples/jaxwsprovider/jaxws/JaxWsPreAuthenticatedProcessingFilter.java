package com.github.schali.samples.jaxwsprovider.jaxws;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;

public class JaxWsPreAuthenticatedProcessingFilter {
	protected final Log LOG = LogFactory.getLog(getClass());
	
	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	private AuthenticationManager authenticationManager = null;
	private ApplicationEventPublisher eventPublisher = null;
    
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest,?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

	public void authenticateJaxWs(WebServiceContext wsContext, HttpServletRequest request, HttpServletResponse response) {
		if(wsContext != null) {
			Object principal = wsContext.getUserPrincipal();
	        Object credentials = "N/A";

	        if (principal == null) {
	            LOG.warn("No pre-authenticated principal found in request");
	            return;
	        }

	        LOG.info("preAuthenticatedPrincipal = " + principal + ", trying to authenticate");

	        try {
	            PreAuthenticatedAuthenticationToken authRequest = new PreAuthenticatedAuthenticationToken(principal, credentials);
	            authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	            Authentication authResult = authenticationManager.authenticate(authRequest);
	            successfulAuthentication(request, response, authResult);
	        } catch (AuthenticationException failed) {
	            unsuccessfulAuthentication(request, response, failed);
	        }
		
		}else
			LOG.warn("no context found");
	}
	
	/**
     * Puts the <code>Authentication</code> instance returned by the
     * authentication manager into the secure context.
     */
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Authentication success: " + authResult);
        }
        SecurityContextHolder.getContext().setAuthentication(authResult);
        // Fire event
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }
    }

    /**
     * Ensures the authentication object in the secure context is set to null when authentication fails.
     * <p>
     * Caches the failure exception as a request attribute
     */
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        SecurityContextHolder.clearContext();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Cleared security context due to exception", failed);
        }
        request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, failed);
    }
    
    public void setApplicationEventPublisher(ApplicationEventPublisher anApplicationEventPublisher) {
        this.eventPublisher = anApplicationEventPublisher;
    }
	
}
