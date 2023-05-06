package io.github.cytaylorw.utils.commons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

/**
 * Bean reflection utilities
 * 
 * @author Taylor Wong
 */
public class BeanUtil {

  /**
   * Return true if any bean properties are null else false. Invalid property name will be evaluated
   * to null.
   * 
   * @param bean the bean object
   * @param beanProps the bean property names
   * @return true if any bean properties are null else false
   */
  public static boolean anyNull(Object bean, String... beanProps) {
    boolean result = true;
    if (Objects.nonNull(bean)) {
      result = Arrays.stream(beanProps).map(p -> BeanUtil.getValue(bean, p)).anyMatch(Objects::isNull);
    }
    return result;
  }


  /**
   * Return the formatted getter name from the name of the property.
   * 
   * @param name the name of the property
   * @return the getter name
   */
  public static String getGetterName(String name) {
    return "get".concat(name.substring(0, 1).toUpperCase(Locale.ENGLISH)).concat(name.substring(1));
  }

  /**
   * Return the bean property value. If the exceptionSupplier is provided, throw given exception when
   * an exception is thrown, else return null.
   * 
   * @param <X> Type of the exception to be thrown
   * @param bean the bean object
   * @param beanProp the bean property name
   * @param exceptionFunction the function that takes the caught exception and return a new exception
   *        to be thrown
   * @return the bean property value. If the exceptionSupplier is provided, throw given exception when
   *         an exception is thrown, else return null.
   * @throws X if exception occurs
   */
  public static <X extends Throwable> Object getValue(Object bean, String beanProp,
      Function<Throwable, ? extends X> exceptionFunction) throws X {
    Object result = null;
    try {
      Method getter = bean.getClass().getDeclaredMethod(BeanUtil.getGetterName(beanProp));
      result = getter.invoke(bean);
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
      if (Objects.nonNull(exceptionFunction)) {
        throw exceptionFunction.apply(e);
      }
    }
    return result;
  }

  /**
   * Return the bean property value. null if an exception is thrown.
   * 
   * @param bean the bean object
   * @param beanProp the bean property name
   * @return the bean property value. null if an exception is thrown
   */
  public static Object getValue(Object bean, String beanProp) {
    return BeanUtil.getValue(bean, beanProp, null);
  }
}
