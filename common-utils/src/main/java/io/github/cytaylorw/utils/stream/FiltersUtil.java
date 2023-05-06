package io.github.cytaylorw.utils.stream;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Java stream filters.
 * 
 * @author Taylor Wong
 */
public class FiltersUtil {

  /**
   * Return a Predicate which filter out the duplicates.
   * 
   * @param <T> the type of the input elements
   * @param extractor an extractor function that returns the key value
   * @return a Predicate which filter out the duplicates
   */
  public static <T> Predicate<T> distinct(Function<? super T, ?> extractor) {
    Set<Object> seen = ConcurrentHashMap.newKeySet();
    return t -> seen.add(extractor.apply(t));
  }

  /**
   * Return a Predicate which filter out the null values.
   * 
   * @param <T> the type of the input elements
   * @param extractor an extractor function that returns the key value
   * @return a Predicate which filter out the null values
   */
  public static <T> Predicate<T> nonNull(Function<? super T, ?> extractor) {
    return t -> Objects.nonNull(extractor.apply(t));
  }
}
