package com.github.fbrx.samples.juel;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import de.odysseus.el.util.SimpleContext;

public class ElHelper {

	@SuppressWarnings("unchecked")
	public static <T> T resolveEl(Object target, String expression, Class<T> targetType) {
		if (target == null || expression == null || targetType == null)
			throw new IllegalArgumentException("target, expression and targetType must not be null");

		ExpressionFactory ef = ExpressionFactory.newInstance();
		ELContext ec = new SimpleContext();

		ec.getVariableMapper().setVariable("me", ef.createValueExpression(target, target.getClass()));

		ValueExpression ve = ef.createValueExpression(ec, "${me. " + expression + "}", Object.class);

		return (T) ve.getValue(ec);
	}

}
