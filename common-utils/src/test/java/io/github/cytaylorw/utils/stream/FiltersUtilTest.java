package io.github.cytaylorw.utils.stream;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;


/**
 * Test {@link FiltersUtil}
 */
public class FiltersUtilTest {

  /**
   * Test {@link FiltersUtil#distinct(Function)}
   */
  @Test
  public void distinct() {
    List<String> list = List.of("cat", "dog", "bird", "dog");
    assertThat(list.size()).isEqualTo(4);

    List<String> result = list.stream().filter(FiltersUtil.distinct(Function.identity())).collect(Collectors.toList());
    assertThat(result.size()).isEqualTo(3);
  }

  /**
   * Test {@link FiltersUtil#nonNull(Function)}
   */
  @Test
  public void nonNull() {
    List<String> list = List.of("cat", "dog", "bird");
    list = new ArrayList<>(list);
    list.add(null);
    assertThat(list.size()).isEqualTo(4);

    List<String> result = list.stream().filter(FiltersUtil.nonNull(Function.identity())).collect(Collectors.toList());
    assertThat(result.size()).isEqualTo(3);
  }

}
