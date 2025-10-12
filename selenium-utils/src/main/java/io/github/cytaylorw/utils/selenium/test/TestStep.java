package io.github.cytaylorw.utils.selenium.test;

import java.util.function.Consumer;

import io.github.cytaylorw.utils.selenium.core.WebManager;

public class TestStep extends TestRunner<TestStep> {
	
	private Consumer<TestStep> action = s -> {};

	TestStep(TestCase testcase){
		this.testsuite = testcase.getTestsuite();
		this.testcase = testcase;
		this.step = this;
	}
	
	public TestCase getTestCase() {
		return this.testcase;
	}
	
	public WebManager getManager() {
		return this.testcase.getTestsuite().getManager();
	}
	
	public static TestStep of(TestCase testcase) {
		return new TestStep(testcase);
	}
	
	public TestStep action(Consumer<TestStep> action) {
		this.action = this.action.andThen(action);
		return this;
	}
	
	@Override
	public void setNumber(String suffix) {
		this.number = testcase.getNumber() + this.getChildSeperator() + suffix;
	}
	
	@Override
	protected String getChildSeperator() {
		return ".";
	}
	
	@Override
	protected void execute() {
		System.out.println(this.getClass() + " Starting...");
		action.accept(this);
		this.isPass = true;
	}
}
