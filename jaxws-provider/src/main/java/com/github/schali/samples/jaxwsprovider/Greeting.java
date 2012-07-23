package com.github.schali.samples.jaxwsprovider;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.HandlerChain;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.github.schali.samples.jaxwsprovider.service.GreetingService;

@WebService
@HandlerChain(file = "handler-chain.xml")
public class Greeting extends SpringBeanAutowiringSupport{

	@Inject
	GreetingService service;
	
	@Resource
	WebServiceContext ctx;
	
	@WebResult(name="greeting")
	public String greetMe(@WebParam(name="name") String name) {
		return service.greetMe(name, ctx);
	}
	
	@WebResult(name="isInRole")
	public String isInRole(@WebParam(name="roleName") String roleName) {
		return service.isInRole(roleName, ctx);
	}

}
