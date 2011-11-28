package com.github.samples.jaxrsplain;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * JAX-RS Resource class which can display general or personalized greeting messages.
 *
 * @author Florian Müller
 */
@Path("/greeting")
public class GreetingResource {

	/**
	 * Outputs a generic greeting message
	 * 
	 * @return a greeting message
	 */
	@GET
	@Produces("text/plain")
	public String greet(){
		return "howdy, stranger!";
	}
	
	
	/**
	 * Outputs a personalized greeting message
	 * 
	 * @param name name to use for personalization of the message
	 * @return personalized greeting message
	 */
	@GET
	@Path("/{name}")
	@Produces("text/plain")
	public String greetMe(@PathParam("name") String name){
		return "whuuuuuuuzzzuuuuupp, " + name;
	}
}
