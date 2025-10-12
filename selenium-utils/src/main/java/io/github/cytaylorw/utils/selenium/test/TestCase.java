package io.github.cytaylorw.utils.selenium.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


import io.github.cytaylorw.utils.selenium.core.WebManager;
import io.github.cytaylorw.utils.selenium.test.exception.TestException;

public class TestCase extends TestRunner<TestCase> {

	private final TestSuite testsuite;
	
	private List<TestStep> steps;

	TestCase(TestSuite testsuite){
		this.testsuite = testsuite;
		this.steps = new ArrayList<TestStep>();
	}
	
	public TestSuite getTestsuite() {
		return this.testsuite;
	}
	
	public WebManager getManager() {
		return this.getTestsuite().getManager();
	}
	
	public static TestCase of(TestSuite testsuit) {
		return new TestCase(testsuit);
	}
		
	public TestCase step(Function<TestCase, TestStep> stepFunction) {
		this.step(stepFunction.apply(this));
		return this;
	}
	
	public TestCase step(TestStep step) {
		this.steps.add(step);
		return this;
	}
	
	@Override
	public void setNumber(String suffix) {
		this.number = testsuite.getNumber() + this.getChildSeperator() + suffix;
	}
	
	@Override
	protected void execute() {
		steps.forEach(this::execute);
	}
	
	private void execute(TestStep step) {
		step.run();
		this.isPass = step.isPass();
		this.error = step.getError();
		
		if(!this.isPass) {
			throw new TestException(this);
		}
		
	}
}