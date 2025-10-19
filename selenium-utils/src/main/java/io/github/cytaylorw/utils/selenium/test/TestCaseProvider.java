package io.github.cytaylorw.utils.selenium.test;

@FunctionalInterface
public interface TestCaseProvider {

	TestCase toTestCase(TestSuite testsuite);
}
