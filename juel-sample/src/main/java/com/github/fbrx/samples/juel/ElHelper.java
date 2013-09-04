package com.github.fbrx.samples.juel;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import de.odysseus.el.util.SimpleContext;

/**
 * Utility class for resolving EL expressions against objects.
 */
public class ElHelper {

	/**
	 * Resolves an EL expression against an arbitrary object. The expression must be supplied relative to the supplied
	 * target object and will be constructed in the following form: <code>${target.expression}</code>.
	 * 
	 * @param target
	 *            the target object against which the expression will be resolved
	 * @param expression
	 *            the expression to be resolved
	 * @param targetType
	 *            the expected type of the return value
	 * @return the value of type <code>targetType</code> of the evaluated expression
	 * @throws IllegalArgumentException
	 *             in case one of the input parameters is <code>null</code>
	 * @throws ELException
	 *             if the provided expression is invalid
	 */
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
