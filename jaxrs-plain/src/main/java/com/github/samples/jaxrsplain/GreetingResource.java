package com.github.samples.jaxrsplain;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/greeting")
public class GreetingResource {

	@GET
	@Produces("text/plain")
	public String greet(){
		return "howdy, stranger!";
	}
	
	@GET
	@Path("/{name}")
	@Produces("text/plain")
	public String greetMe(@PathParam("name") String name){
		return "whuuuuuuuzzzuuuuupp, " + name;
	}
}
