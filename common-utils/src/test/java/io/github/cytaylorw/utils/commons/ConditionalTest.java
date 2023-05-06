package io.github.cytaylorw.utils.commons;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;


/**
 * Test {@link Conditional}
 */
public class ConditionalTest {

  /**
   * Test {@link Conditional#of(Object)}
   */
  @Test
  public void of() {
    Conditional<Object> object = Conditional.of(null);
    assertThat(object).isInstanceOf(Conditional.class);
  }

  /**
   * Test {@link Conditional#isTrue()}
   */
  @Test
  public void isTrue() {
    Conditional<Object> object = Conditional.of(null);
    Predicate<Object> isNull = Objects::isNull;

    boolean result = object.evaluate(isNull).isTrue();
    assertThat(result).isTrue();

    result = object.evaluate(isNull.negate()).isTrue();
    assertThat(result).isFalse();
  }

  /**
   * Test {@link Conditional#isFalse()}
   */
  @Test
  public void isFalse() {
    Conditional<Object> object = Conditional.of(null);
    Predicate<Object> nonNull = Objects::nonNull;

    boolean result = object.evaluate(nonNull).isFalse();
    assertThat(result).isTrue();

    result = object.evaluate(nonNull.negate()).isFalse();
    assertThat(result).isFalse();
  }

  /**
   * Test {@link Conditional#ifTrue(java.util.function.Consumer)}
   */
  @Test
  public void ifTrue() {
    List<Object> actionCall = new ArrayList<>();
    Predicate<Object> isNull = Objects::isNull;

    String value = null;
    Conditional<String> text = Conditional.of(value);

    text.evaluate(isNull).ifTrue(v -> actionCall.add(v));
    assertThat(actionCall).hasSize(1);
  }

  /**
   * Test {@link Conditional#ifFalse(java.util.function.Consumer)}
   */
  @Test
  public void ifFalse() {
    List<Object> actionCall = new ArrayList<>();
    Predicate<Object> nonNull = Objects::nonNull;

    String value = null;
    Conditional<String> text = Conditional.of(value);

    text.evaluate(nonNull).ifFalse(v -> actionCall.add(v));
    assertThat(actionCall).hasSize(1);
  }

  /**
   * Test {@link Conditional#ifTrueOrElse(java.util.function.Consumer, java.util.function.Consumer)}
   */
  @Test
  public void ifTrueOrElse() {
    List<Object> actionCall = new ArrayList<>();
    List<Object> elseActionCall = new ArrayList<>();
    Predicate<Object> isNull = Objects::isNull;
    Predicate<Object> nonNull = Objects::nonNull;

    String value = null;
    Conditional<String> text = Conditional.of(value);

    text.evaluate(isNull).ifTrueOrElse(v -> actionCall.add(v), v -> elseActionCall.add(v));
    assertThat(actionCall).hasSize(1);
    assertThat(elseActionCall).hasSize(0);

    text.evaluate(nonNull).ifTrueOrElse(v -> actionCall.add(v), v -> elseActionCall.add(v));
    assertThat(actionCall).hasSize(1);
    assertThat(elseActionCall).hasSize(1);
  }

  /**
   * Test {@link Conditional#ifFalseOrElse(java.util.function.Consumer, java.util.function.Consumer)}
   */
  @Test
  public void ifFalseOrElse() {
    List<Object> actionCall = new ArrayList<>();
    List<Object> elseActionCall = new ArrayList<>();
    Predicate<Object> isNull = Objects::isNull;
    Predicate<Object> nonNull = Objects::nonNull;

    String value = null;
    Conditional<String> text = Conditional.of(value);

    text.evaluate(nonNull).ifFalseOrElse(v -> actionCall.add(v), v -> elseActionCall.add(v));
    assertThat(actionCall).hasSize(1);
    assertThat(elseActionCall).hasSize(0);

    text.evaluate(isNull).ifFalseOrElse(v -> actionCall.add(v), v -> elseActionCall.add(v));
    assertThat(actionCall).hasSize(1);
    assertThat(elseActionCall).hasSize(1);
  }

  /**
   * Test
   * {@link Conditional#ifTrueOrElseThrow(java.util.function.Consumer, java.util.function.Supplier)}
   * 
   * @throws InterruptedException
   */
  @Test
  public void ifTrueOrElseThrow() throws InterruptedException {
    List<Object> actionCall = new ArrayList<>();
    Predicate<Object> isNull = Objects::isNull;
    Predicate<Object> nonNull = Objects::nonNull;

    String value = null;
    Conditional<String> text = Conditional.of(value);

    text.evaluate(isNull).ifTrueOrElseThrow(v -> actionCall.add(v), () -> new InterruptedException());
    assertThat(actionCall).hasSize(1);

    Throwable thrown = catchThrowable(
        () -> text.evaluate(nonNull).ifTrueOrElseThrow(v -> actionCall.add(v), () -> new InterruptedException()));
    assertThat(thrown).isInstanceOf(InterruptedException.class);
    assertThat(actionCall).hasSize(1);
  }

  /**
   * Test
   * {@link Conditional#ifFalseOrElseThrow(java.util.function.Consumer, java.util.function.Supplier)}
   * 
   * @throws InterruptedException
   */
  @Test
  public void ifFalseOrElseThrow() throws InterruptedException {
    List<Object> actionCall = new ArrayList<>();
    Predicate<Object> isNull = Objects::isNull;
    Predicate<Object> nonNull = Objects::nonNull;

    String value = null;
    Conditional<String> text = Conditional.of(value);

    text.evaluate(nonNull).ifFalseOrElseThrow(v -> actionCall.add(v), () -> new InterruptedException());
    assertThat(actionCall).hasSize(1);

    Throwable thrown = catchThrowable(
        () -> text.evaluate(isNull).ifFalseOrElseThrow(v -> actionCall.add(v), () -> new InterruptedException()));
    assertThat(thrown).isInstanceOf(InterruptedException.class);
    assertThat(actionCall).hasSize(1);
  }

  /**
   * Test {@link Conditional#ifTrueThrow(java.util.function.Supplier)}
   * 
   * @throws InterruptedException
   */
  @Test
  public void ifTrueThrow() throws InterruptedException {
    Predicate<Integer> isPositive = n -> Integer.signum(n) == 1;

    int value = 100;
    Conditional<Integer> number = Conditional.of(value);

    Throwable thrown = catchThrowable(() -> number.evaluate(isPositive).ifTrueThrow(() -> new InterruptedException()));
    assertThat(thrown).isInstanceOf(InterruptedException.class);
  }

  /**
   * Test {@link Conditional#ifFalseThrow(java.util.function.Supplier)}
   * 
   * @throws InterruptedException
   */
  @Test
  public void ifFalseThrow() throws InterruptedException {
    Predicate<Integer> isPositive = n -> Integer.signum(n) == 1;

    int value = -100;
    Conditional<Integer> number = Conditional.of(value);

    Throwable thrown = catchThrowable(() -> number.evaluate(isPositive).ifFalseThrow(() -> new InterruptedException()));
    assertThat(thrown).isInstanceOf(InterruptedException.class);
  }
}
