package com.github.fbrx.samples.juel;

import java.util.Arrays;

import javax.el.ELException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.fbrx.samples.juel.model.Contract;
import com.github.fbrx.samples.juel.model.Customer;

/**
 * Unit Tests for the {@link ElHelper} utility class.
 */
public class ElHelperTest {

	private Contract cont1;
	private Customer cust1;

	/**
	 * Setup of test objects. Will be executed before each test.
	 */
	@Before
	public void before() {
		this.cust1 = new Customer("flo", "müller", 28, null);
		this.cont1 = new Contract(this.cust1, "BSV", 100000, null, null);
		this.cust1.setContracts(Arrays.asList(this.cont1));
	}

	/**
	 * lookup of a simple property
	 */
	@Test
	public void testResolveEl() {
		String s = ElHelper.resolveEl(this.cust1, "lastname", String.class);

		Assert.assertEquals("müller", s);
	}

	/**
	 * navigation through an object graph
	 */
	@Test
	public void testResolveElGraphNavigation() {
		Double d = ElHelper.resolveEl(this.cust1, "contracts[0].amount", Double.class);

		Assert.assertEquals((Double) 100000d, d);
	}

	/**
	 * test of invalid expression
	 */
	@Test(expected = ELException.class)
	public void testResolveElInvalidExpression() {
		ElHelper.resolveEl(this.cust1, "${me.lastname}", Object.class);
	}

	/**
	 * test of null expression
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testResolveElNullExpression() {
		ElHelper.resolveEl(this.cust1, null, Object.class);
	}

	/**
	 * test of null target object
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testResolveElNullTarget() {
		ElHelper.resolveEl(null, "aa", Object.class);
	}

	/**
	 * test of null target type
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testResolveElNullTargetType() {
		ElHelper.resolveEl(this.cust1, "aa", null);
	}

}
