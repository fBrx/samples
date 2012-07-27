package com.github.schali.samples.jaxwsprovider;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.github.schali.samples.jaxwsprovider.service.GreetingService;

@WebService
@HandlerChain(file = "handler-chain.xml")
public class Greeting extends SpringBeanAutowiringSupport{

	Logger LOG = Logger.getLogger(getClass().getName());
	
	//without injecting wsContext in actual service it will not be correctly injected in handler (at least in was 7)
	@Resource
	WebServiceContext wsContext;
	
	//was 7 does not like @inject
	@Autowired
	GreetingService service;
	
	@WebResult(name="greeting")
	public String greetMe(@WebParam(name="name") String name){
		return service.greetMe(name);
	}
	
	@WebResult(name="isInRole")
	public String isInRole(@WebParam(name="roleName") String roleName){
		return service.isInRole(roleName);
	}

}
