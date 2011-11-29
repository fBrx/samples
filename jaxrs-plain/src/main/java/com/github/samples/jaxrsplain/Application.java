package com.github.samples.jaxrsplain;

import java.util.HashSet;
import java.util.Set;

/**
 * JAX-RS Application class to register all root resources. 
 * 
 * @author Florian Müller
 * @see javax.ws.rs.core.Application
 */
public class Application extends javax.ws.rs.core.Application {

	/**
	 * {@inheritDoc}
	 * 
	 * This implementation registers the root resource {@link GreetingResource}
	 * 
	 * @see GreetingResource
	 */
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(GreetingResource.class);
		return s;
	}
}
