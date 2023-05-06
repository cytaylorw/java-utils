package io.github.cytaylorw.utils.stream;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Additional Collectors for Java Stream API.
 * 
 * @author Taylor Wong
 */
public class CollectorsUtil {

  /**
   * Returns a Collector that accumulates elements into a LinkedHashMap whose keys and values are the
   * result of applying the provided mapping functions to the input elements.
   * 
   * 
   * 
   * @param <T> the type of the input elements
   * @param <K> the output type of the key mapping function
   * @param <U> the output type of the value mapping function
   * @param keyMapper a mapping function to produce keys
   * @param valueMapper a mapping function to produce values
   * @return a Collector which collects elements into a LinkedHashMap whose keys are the result of
   *         applying a key mapping function to the input elements, and whose values are the result of
   *         applying a value mapping function to all input elements equal to the key
   */
  public static <T, K, U> Collector<T, ?, Map<K, U>> toLinkedMap(Function<? super T, ? extends K> keyMapper,
      Function<? super T, ? extends U> valueMapper) {
    return Collectors.toMap(keyMapper, valueMapper, (u, v) -> u, LinkedHashMap::new);
  }
}
