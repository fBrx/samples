package com.github.schali.samples.jaxwsprovider;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public class GreetingService {

	@WebResult(name="greeting")
	public String greetMe(@WebParam(name="name") String name) {
		
		return "Howdy, " + name + "!";
	}
	
}
