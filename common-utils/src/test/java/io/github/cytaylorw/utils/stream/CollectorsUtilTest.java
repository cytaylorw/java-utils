package io.github.cytaylorw.utils.stream;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.junit.jupiter.api.Test;


/**
 * Test {@link CollectorsUtil}
 */
public class CollectorsUtilTest {

  /**
   * Test {@link CollectorsUtil#toLinkedMap(Function, Function)}
   */
  @Test
  public void toLinkedMap() {
    List<String> list = List.of("cat", "dog", "bird");
    Map<String, String> result =
        list.stream().collect(CollectorsUtil.toLinkedMap(s -> s.substring(0, 1), Function.identity()));
    assertThat(result).isInstanceOf(LinkedHashMap.class);
    int i = 0;
    for (var e : result.entrySet()) {
      assertThat(e.getKey()).isEqualTo(list.get(i).substring(0, 1));
      assertThat(e.getValue()).isEqualTo(list.get(i));
      i++;
    }
  }

}
