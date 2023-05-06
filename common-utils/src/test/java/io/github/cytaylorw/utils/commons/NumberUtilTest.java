package io.github.cytaylorw.utils.commons;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;


/**
 * Test {@link NumberUtil}
 */
public class NumberUtilTest {

  /**
   * Test {@link NumberUtil#format(long, String)}
   */
  @Test
  public void formatLong() {
    String formatted = NumberUtil.format(1_000, "###,###,###.##");
    assertThat(formatted).isEqualTo("1,000");
  }

  /**
   * Test {@link NumberUtil#format(double, String)}
   */
  @Test
  public void formatDouble() {
    String formatted = NumberUtil.format(1_000.1, "###,###,###.##");
    assertThat(formatted).isEqualTo("1,000.1");
  }

  /**
   * Test {@link NumberUtil#format(Object, String)}
   */
  @Test
  public void formatObject() {
    String formatted = NumberUtil.format(Integer.valueOf(1_000), "###,###,###.##");
    assertThat(formatted).isEqualTo("1,000");
  }

}
