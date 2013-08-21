package com.github.fbrx.samples.juel;

import java.util.Arrays;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.PropertyNotFoundException;
import javax.el.ValueExpression;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.fbrx.samples.juel.model.Contract;
import com.github.fbrx.samples.juel.model.Customer;

import de.odysseus.el.util.SimpleContext;

public class JuelTest {

	private Contract cont1;
	private Customer cust1;

	ELContext elCtx;

	@Before
	public void before() {
		this.cust1 = new Customer("flo", "müller", 28, null);
		this.cont1 = new Contract(this.cust1, "BSV", 100000, null, null);

		ExpressionFactory elFactory = ExpressionFactory.newInstance();
		Assert.assertNotNull(elFactory);

		this.elCtx = new SimpleContext();
		Assert.assertNotNull(this.elCtx);

		this.elCtx.getVariableMapper().setVariable("cust1", elFactory.createValueExpression(this.cust1, Customer.class));
	}

	@Test(expected = PropertyNotFoundException.class)
	public void testBeanNotFound() {
		ValueExpression ve = ExpressionFactory.newInstance().createValueExpression(this.elCtx, "${narf.lastname}", String.class);

		Assert.assertEquals("müller", ve.getValue(this.elCtx));
	}

	@Test
	public void testBooleanExpression() {
		ValueExpression ve = ExpressionFactory.newInstance().createValueExpression(this.elCtx, "${cust1.age > 30}", Boolean.class);

		Assert.assertFalse((Boolean) ve.getValue(this.elCtx));
	}

	@Test
	public void testPropertyLookup() {
		ValueExpression ve = ExpressionFactory.newInstance().createValueExpression(this.elCtx, "${cust1.lastname}", String.class);

		Assert.assertEquals("müller", ve.getValue(this.elCtx));

		this.cust1.setLastname("brüssel");
		Assert.assertEquals("brüssel", ve.getValue(this.elCtx));
	}

	@Test(expected = PropertyNotFoundException.class)
	public void testPropertyNotFound() {
		ValueExpression ve = ExpressionFactory.newInstance().createValueExpression(this.elCtx, "${cust1.narf}", String.class);

		Assert.assertEquals("müller", ve.getValue(this.elCtx));
	}

	@Test
	public void testTreeNavigation() {
		this.cust1.setContracts(Arrays.asList(this.cont1));

		ValueExpression ve = ExpressionFactory.newInstance().createValueExpression(this.elCtx, "${cust1.contracts[0].type}", String.class);

		Assert.assertEquals("BSV", ve.getValue(this.elCtx));
	}

}
