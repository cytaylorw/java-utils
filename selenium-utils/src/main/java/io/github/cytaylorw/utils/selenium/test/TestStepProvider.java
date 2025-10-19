package io.github.cytaylorw.utils.selenium.test;

@FunctionalInterface
public interface TestStepProvider {

	TestStep toTestCase(TestCase testcase);
}
