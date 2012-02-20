package com.github.schali.samples.jaxwsprovider;

import javax.xml.ws.Endpoint;


/**
 *
 */
public class Publisher 
{
    public static void main( String[] args )
    {
    	String endpoint = "http://localhost:1337/greeting";
    	System.out.println("publishing greeting service to " + endpoint);
    	
    	Endpoint.publish(endpoint, new GreetingService());
    }
}
