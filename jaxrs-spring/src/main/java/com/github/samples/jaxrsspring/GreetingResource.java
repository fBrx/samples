package com.github.samples.jaxrsspring;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * JAX-RS Resource class which can display general or personalized greeting messages.
 * 
 * @author Florian Müller
 */
@Component
@Path("/greeting")
public class GreetingResource {

	private static final Logger LOG = LoggerFactory.getLogger(GreetingResource.class);
	
	/**
	 * No Arg constructor
	 */
	public GreetingResource() {
		LOG.debug("created resource: " + this);
	}
	
	/**
	 * Outputs a generic greeting message
	 * 
	 * @return a greeting message
	 */
	@GET
	@Produces("text/plain")
	public String greet() {
		LOG.debug("generic greeting");
		return "howdy, stranger!";
	}

	/**
	 * Outputs a personalized greeting message. If no name is supplied, output a generic message.
	 * 
	 * @param name
	 *            name to use for personalization of the message
	 * @return personalized greeting message
	 */
	@GET
	@Path("/{name}")
	@Produces("text/plain")
	public String greetMe(@PathParam("name") String name) {
		LOG.debug("personalized greeting. name: " + name);
		if (name == null)
			return greet();
		else
			return "whuuuuuuuzzzuuuuupp, " + name;
	}
}
