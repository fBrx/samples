package com.github.samples.jaxrsplain;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class GreetingResourceTest {

	GreetingResource gr = new GreetingResource();
	
	@Test
	public void test_greet(){
		Assert.assertEquals("howdy, stranger!", gr.greet());
	}
	
	@Test
	public void test_greetMe_null() {
		Assert.assertEquals(gr.greet(), gr.greetMe(null));
	}
	
	@Test
	public void test_greetMe_flo() {
		Assert.assertEquals("whuuuuuuuzzzuuuuupp, flo", gr.greetMe("flo"));
	}
}
