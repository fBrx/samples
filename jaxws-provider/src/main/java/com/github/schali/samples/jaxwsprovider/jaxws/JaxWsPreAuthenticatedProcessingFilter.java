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
	protected final Log logger = LogFactory.getLog(getClass());
	
	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	private AuthenticationManager authenticationManager = null;
	private ApplicationEventPublisher eventPublisher = null;
    
	/**
     * @param authenticationManager
     *            The AuthenticationManager to use
     */
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    /**
     * @param authenticationDetailsSource
     *            The AuthenticationDetailsSource to use
     */
    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest,?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

	public void authenticateJaxWs(WebServiceContext wsContext, HttpServletRequest request, HttpServletResponse response) {
		if(wsContext != null) {
			System.out.println("authenticating with " + wsContext.getUserPrincipal());
			
			Authentication authResult;
	        Object principal = wsContext.getUserPrincipal().getName();
	        Object credentials = "N/A";

	        if (principal == null) {
	            if (logger.isDebugEnabled()) {
	                logger.debug("No pre-authenticated principal found in request");
	            }

	            return;
	        }

	        if (logger.isDebugEnabled()) {
	            logger.debug("preAuthenticatedPrincipal = " + principal + ", trying to authenticate");
	        }

	        try {
	            PreAuthenticatedAuthenticationToken authRequest = new PreAuthenticatedAuthenticationToken(principal, credentials);
	            authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	            authResult = authenticationManager.authenticate(authRequest);
	            successfulAuthentication(request, response, authResult);
	        } catch (AuthenticationException failed) {
	            unsuccessfulAuthentication(request, response, failed);
	        }
		
		}else
			System.out.println("no context found");
	}
	
	/**
     * Puts the <code>Authentication</code> instance returned by the
     * authentication manager into the secure context.
     */
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success: " + authResult);
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

        if (logger.isDebugEnabled()) {
            logger.debug("Cleared security context due to exception", failed);
        }
        request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, failed);
    }
    
    /**
     * @param anApplicationEventPublisher
     *            The ApplicationEventPublisher to use
     */
    public void setApplicationEventPublisher(ApplicationEventPublisher anApplicationEventPublisher) {
        this.eventPublisher = anApplicationEventPublisher;
    }
	
}
