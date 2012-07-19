package com.github.schali.samples.jaxwsprovider;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

@WebService
public class GreetingService {

	@Resource
	WebServiceContext ctx;
	
	@WebResult(name="greeting")
	public String greetMe(@WebParam(name="name") String name) {
		if(ctx == null || 
		   ctx.getUserPrincipal() == null || 
		   ctx.getUserPrincipal().getName() == null || 
		   ctx.getUserPrincipal().getName().equals(""))
			
			return "Howdy, " + name + "!";
		
		else
			return "Howdy, " + name + "! You are authenticated as " + ctx.getUserPrincipal().getName() + ".";
	}
	
}
