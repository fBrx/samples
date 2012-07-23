package com.github.schali.samples.jaxwsprovider.service;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.xml.ws.WebServiceContext;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class GreetingService {

	private static final String DEFAULT_FORMAT = "<not initialized>";

	private String format = DEFAULT_FORMAT;
	private String formatAuthorized = DEFAULT_FORMAT;
	
	public String greetMe(String name, WebServiceContext ctx) {
		SecurityContext secctx = SecurityContextHolder.getContext();
		
		if(ctx == null || 
		   ctx.getUserPrincipal() == null || 
		   ctx.getUserPrincipal().getName() == null || 
		   ctx.getUserPrincipal().getName().equals(""))
			
			return String.format(format, name);
		
		else
			return String.format(formatAuthorized, name, SecurityContextHolder.getContext().getAuthentication().getName());
	}
	
	@RolesAllowed("ADMIN")
	public String isInRole(String roleName, WebServiceContext ctx) {
		SecurityContext secCtx = SecurityContextHolder.getContext();
		System.out.println(secCtx);
		
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for(GrantedAuthority a : authorities)
			if(a.getAuthority().equals(roleName))
				return "true";
		
		return "false";
	}

	public void setFormat(String format) {
		if(format == null || format.equals(""))
			format = DEFAULT_FORMAT;
		
		System.out.println("setting format to " + format);
		this.format = format;
	}

	public void setFormatAuthorized(String formatAuthorized) {
		if(format == null || format.equals(""))
			format = DEFAULT_FORMAT;
		
		System.out.println("setting formatAuthorized to " + formatAuthorized);
		this.formatAuthorized = formatAuthorized;
	}
	
}
