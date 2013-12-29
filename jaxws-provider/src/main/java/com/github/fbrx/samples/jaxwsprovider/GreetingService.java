package com.github.fbrx.samples.jaxwsprovider;

import java.util.Collection;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class GreetingService {

	private Logger LOG = Logger.getLogger(GreetingService.class.getName());
	
	private static final String DEFAULT_FORMAT = "<not initialized>";

	private String format = DEFAULT_FORMAT;
	private String formatAuthorized = DEFAULT_FORMAT;
	
	public String greetMe(String name) {
		if( SecurityContextHolder.getContext() == null || 
			SecurityContextHolder.getContext().getAuthentication() == null || 
			SecurityContextHolder.getContext().getAuthentication().getName() == null)
			
			return String.format(format, name);
		
		else
			return String.format(formatAuthorized, name, SecurityContextHolder.getContext().getAuthentication().getName());
	}
	
	@RolesAllowed("ADMIN")
	public String isInRole(String roleName) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for(GrantedAuthority a : authorities)
			if(a.getAuthority().equals(roleName))
				return "true";
		
		return "false";
	}

	public void setFormat(String format) {
		if(format == null || format.equals(""))
			format = DEFAULT_FORMAT;
		
		LOG.config("setting format to " + format);
		this.format = format;
	}

	public void setFormatAuthorized(String formatAuthorized) {
		if(format == null || format.equals(""))
			format = DEFAULT_FORMAT;
		
		LOG.config("setting formatAuthorized to " + formatAuthorized);
		this.formatAuthorized = formatAuthorized;
	}
	
}
