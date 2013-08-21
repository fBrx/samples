package com.github.samples.jaxrsspring;


import javax.inject.Inject;
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
@Path("/")
public class GreetingResource {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingResource.class);

    /** Injection target for date formatting service */
    @Inject
    private DateService         dateSvc;

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
    @Path("/greeting")
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
    @Path("/greeting/{name}")
    @Produces("text/plain")
    public String greetMe(@PathParam("name") String name) {
        LOG.debug("personalized greeting. name: " + name);
        if (name == null)
            return greet();
        else
            return "whuuuuuuuzzzuuuuupp, " + name;
    }

    /**
     * Outputs the current date and time in default format.
     * 
     * @return the current date and time
     * @see DateService
     */
    @GET
    @Path("/date")
    @Produces("text/plain")
    public String getDefaultDate() {
        return dateSvc.getDate("dd.MM.yyyy HH:mm:ss");
    }

    /**
     * Outputs the current date and time in a custom format.
     * 
     * @param pattern
     *            the pattern used for formatting
     * @return formatted date and time
     * @see DateService
     */
    @GET
    @Path("/date/{pattern}")
    @Produces("text/plain")
    public String getDefaultDate(@PathParam("pattern") String pattern) {
        return dateSvc.getDate(pattern);
    }
}
