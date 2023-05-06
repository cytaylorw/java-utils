package io.github.cytaylorw.utils.commons;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;


/**
 * Test {@link BeanUtil}
 */
public class BeanUtilTest {

  /**
   * Test {@link BeanUtil#anyNull(Object, String...)}
   */
  @Test
  public void anyNull() {
    Person p1 = this.newPerson();
    boolean result = BeanUtil.anyNull(p1, "name", "height");
    assertThat(result).isTrue();
    result = BeanUtil.anyNull(p1, "name", "age");
    assertThat(result).isTrue();
    result = BeanUtil.anyNull(p1, "name");
    assertThat(result).isFalse();
  }

  /**
   * Test {@link BeanUtil#getGetterName(String)}
   */
  @Test
  public void getGetterName() {
    String getter = BeanUtil.getGetterName("name");
    assertThat(getter).isEqualTo("getName");
  }

  /**
   * Test {@link BeanUtil#getValue(Object, String)}
   */
  @Test
  public void getValue() {
    Person p1 = this.newPerson();
    Object value = BeanUtil.getValue(p1, "name");
    assertThat(value).isEqualTo("Taylor");
  }

  /**
   * Test {@link BeanUtil#getValue(Object, String, java.util.function.Function)}
   */
  @Test
  public void getValueException() {
    Person p1 = this.newPerson();
    assertThatExceptionOfType(ExecutionException.class).isThrownBy(() -> {
      BeanUtil.getValue(p1, "test", ExecutionException::new);
    });
  }


  /**
   * Mock data
   * 
   * @return
   */
  private Person newPerson() {
    Person p1 = new Person();
    p1.setName("Taylor");
    return p1;
  }

  /**
   * Test data object
   */
  public class Person {

    /**
     * name
     */
    private String name;

    /**
     * age
     */
    private Integer age;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Integer getAge() {
      return age;
    }

    public void setAge(Integer age) {
      this.age = age;
    }
  }
}
