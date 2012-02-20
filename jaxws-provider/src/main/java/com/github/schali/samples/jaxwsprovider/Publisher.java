package com.github.schali.samples.jaxwsprovider;

import javax.xml.ws.Endpoint;


/**
 *
 */
public class Publisher 
{
    public static void main( String[] args )
    {
    	Endpoint.publish("http://localhost:1337/greeting", new GreetingService());
    }
}
