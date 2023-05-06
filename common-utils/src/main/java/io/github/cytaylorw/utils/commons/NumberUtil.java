package io.github.cytaylorw.utils.commons;

import java.text.DecimalFormat;

/**
 * Number Utilities
 * 
 * @author Taylor Wong
 */
public class NumberUtil {

  /**
   * Formats a long to produce a string using the given DecimalFormat pattern.
   * 
   * @param number the long number to format
   * @param pattern a non-localized pattern string.
   * @return Formatted string.
   */
  public static String format(long number, String pattern) {
    DecimalFormat df = new DecimalFormat(pattern);
    return df.format(number);
  }

  /**
   * Formats a double to produce a string using the given DecimalFormat pattern.
   * 
   * @param number the long number to format
   * @param pattern a non-localized pattern string.
   * @return Formatted string.
   */
  public static String format(double number, String pattern) {
    DecimalFormat df = new DecimalFormat(pattern);
    return df.format(number);
  }

  /**
   * Formats an object to produce a string using the given DecimalFormat pattern.
   * 
   * @param number The object to format
   * @param pattern a non-localized pattern string.
   * @return Formatted string.
   */
  public static String format(Object number, String pattern) {
    DecimalFormat df = new DecimalFormat(pattern);
    return df.format(number);
  }

}
