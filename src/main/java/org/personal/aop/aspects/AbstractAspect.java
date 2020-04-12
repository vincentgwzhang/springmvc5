package org.personal.aop.aspects;

import java.util.Arrays;
import java.util.List;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

public abstract class AbstractAspect
{

    protected String getParameterValue(Signature signature, Object[] values, String parameterName)
    {
        return getParameterValue(signature, values, parameterName, String.class);
    }

    protected <T> T getParameterValue(Signature signature, Object[] values, String parameterName, Class<T> clazz)
    {
        final MethodSignature methodSignature = (MethodSignature) signature;
        final List<String> parameterNames = Arrays.asList(methodSignature.getParameterNames());
        final int index = parameterNames.indexOf(parameterName);
        if (index == -1)
            throw new IllegalArgumentException(String.format("Operation \"%s\" doesn't have a parameter called \"%s\"!", signature.getName(), parameterName));
        return getCastedValueOrThrowException(values, index, methodSignature.getName(), parameterName, clazz);
    }

    @SuppressWarnings("unchecked")
    private <T> T getCastedValueOrThrowException(Object[] values, int index, String operation, String parameter, Class<T> clazz)
    {
        try
        {
            return (T) values[index];
        }
        catch (final ClassCastException e)
        {
            throw new ClassCastException(String.format("Operation \"%s\" has a parameter called \"%s\" but its type is %s, not %s!", operation, parameter, values[index].getClass().getSimpleName(), clazz.getSimpleName()));
        }
    }
}
