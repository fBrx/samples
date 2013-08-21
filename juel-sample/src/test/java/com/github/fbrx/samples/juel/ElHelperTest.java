package com.github.fbrx.samples.juel;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.fbrx.samples.juel.ElHelper;
import com.github.fbrx.samples.juel.model.Contract;
import com.github.fbrx.samples.juel.model.Customer;

public class ElHelperTest {

	private Contract cont1;
	private Customer cust1;

	@Before
	public void before() {
		this.cust1 = new Customer("flo", "müller", 28, null);
		this.cont1 = new Contract(this.cust1, "BSV", 100000, null, null);
		this.cust1.setContracts(Arrays.asList(this.cont1));
	}

	@Test
	public void testResolveEl() {
		String s = ElHelper.resolveEl(this.cust1, "lastname", String.class);

		Assert.assertEquals("müller", s);
	}

	@Test
	public void testResolveElGraphNavigation() {
		Double d = ElHelper.resolveEl(this.cust1, "contracts[0].amount", Double.class);

		Assert.assertEquals((Double) 100000d, d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testResolveElNullExpression() {
		ElHelper.resolveEl(this.cust1, null, Object.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testResolveElNullTarget() {
		ElHelper.resolveEl(null, "aa", Object.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testResolveElNullTargetType() {
		ElHelper.resolveEl(this.cust1, "aa", null);
	}

}
