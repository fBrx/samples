package com.github.schali.samples.jaxwsprovider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.Principal;

import javax.xml.ws.WebServiceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(BlockJUnit4ClassRunner.class)
public class GreetingServiceTest {

	GreetingService svc;
	
	@Before
	public void before() {
		svc = new GreetingService();
	}
	
	@Test
	public void testGreetMeNullContext() {
		String result = svc.greetMe("flo");
		
		assertNotNull(result);
		assertEquals("Howdy, flo!", result);
	}
	
	@Test
	public void testGreetMeNullPrincipal() {
		WebServiceContext ctx = Mockito.mock(WebServiceContext.class);
		Mockito.when(ctx.getUserPrincipal()).thenReturn(null);
		ReflectionTestUtils.setField(svc, "ctx", ctx);
		
		String result = svc.greetMe("flo");
		
		assertNotNull(result);
		assertEquals("Howdy, flo!", result);
	}
	
	@Test
	public void testGreetMeNullName() {
		WebServiceContext ctx = Mockito.mock(WebServiceContext.class);
		Principal p = Mockito.mock(Principal.class);
		Mockito.when(p.getName()).thenReturn(null);
		Mockito.when(ctx.getUserPrincipal()).thenReturn(p);
		ReflectionTestUtils.setField(svc, "ctx", ctx);
		
		String result = svc.greetMe("flo");
		
		assertNotNull(result);
		assertEquals("Howdy, flo!", result);
	}
	
	@Test
	public void testGreetMeNameSet() {
		WebServiceContext ctx = Mockito.mock(WebServiceContext.class);
		Principal p = Mockito.mock(Principal.class);
		Mockito.when(p.getName()).thenReturn("FLO");
		Mockito.when(ctx.getUserPrincipal()).thenReturn(p);
		ReflectionTestUtils.setField(svc, "ctx", ctx);
		
		String result = svc.greetMe("flo");
		
		assertNotNull(result);
		assertEquals("Howdy, flo! You are authenticated as FLO.", result);
	}

}
