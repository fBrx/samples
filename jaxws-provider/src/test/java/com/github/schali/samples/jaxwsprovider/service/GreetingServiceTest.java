package com.github.schali.samples.jaxwsprovider.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.Principal;

import javax.xml.ws.WebServiceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mockito;

import com.github.schali.samples.jaxwsprovider.GreetingService;

@RunWith(BlockJUnit4ClassRunner.class)
public class GreetingServiceTest {

	GreetingService svc;
	private static final String FORMAT = "howdy, %s";
	private static final String FORMAT_AUTHENTICATED = "howdy ho, %s (%s)";
	private static final String NAME = "Florian";
	private static final String PRINCIPAL_NAME = "mrflom";
	
	@Before
	public void before() {
		svc = new GreetingService();
		svc.setFormat(FORMAT);
		svc.setFormatAuthorized(FORMAT_AUTHENTICATED);
	}
	
	@Test
	public void testGreetMeNullContext() {
		String result = svc.greetMe(NAME);
		
		assertNotNull(result);
		assertEquals(String.format(FORMAT, NAME), result);
	}
	
	@Test
	public void testGreetMeNullPrincipal() {
		WebServiceContext ctx = Mockito.mock(WebServiceContext.class);
		Mockito.when(ctx.getUserPrincipal()).thenReturn(null);
		
		String result = svc.greetMe(NAME);
		
		assertNotNull(result);
		assertEquals(String.format(FORMAT, NAME), result);
	}
	
	@Test
	public void testGreetMeNullName() {
		WebServiceContext ctx = Mockito.mock(WebServiceContext.class);
		Principal p = Mockito.mock(Principal.class);
		Mockito.when(p.getName()).thenReturn(null);
		Mockito.when(ctx.getUserPrincipal()).thenReturn(p);
		
		String result = svc.greetMe(NAME);
		
		assertNotNull(result);
		assertEquals(String.format(FORMAT, NAME), result);
	}
	
//	@Test
//	public void testGreetMeNameSet() {
//		WebServiceContext ctx = Mockito.mock(WebServiceContext.class);
//		Principal p = Mockito.mock(Principal.class);
//		Mockito.when(p.getName()).thenReturn(PRINCIPAL_NAME);
//		Mockito.when(ctx.getUserPrincipal()).thenReturn(p);
//		
//		Authentication auth = Mockito.mock(Authentication.class);
//		Mockito.when(auth.getName()).thenReturn(PRINCIPAL_NAME);
//		SecurityContext secCtx = Mockito.mock(SecurityContext.class);
//		Mockito.when(secCtx.getAuthentication()).thenReturn(auth);
//		SecurityContextHolder holder = Mockito.mock(SecurityContextHolder.class);
//		Mockito.when(holder.getContext()).thenReturn(secCtx);
//		
//		String result = svc.greetMe(NAME, ctx);
//		
//		assertNotNull(result);
//		assertEquals(String.format(FORMAT_AUTHENTICATED, NAME, PRINCIPAL_NAME), result);
//	}
	
	@Test
	public void testNotInitalized() {
		svc.setFormat(null);
		svc.setFormatAuthorized(null);
		
		String result = svc.greetMe(NAME);
		
		assertNotNull(result);
		assertEquals("<not initialized>", result);
	}

}
