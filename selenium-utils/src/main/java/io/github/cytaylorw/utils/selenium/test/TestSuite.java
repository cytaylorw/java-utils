package io.github.cytaylorw.utils.selenium.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.github.cytaylorw.utils.selenium.core.WebManager;
import io.github.cytaylorw.utils.selenium.test.exception.TestException;

public class TestSuite extends TestRunner<TestSuite> {

	private final WebManager manager;
	
	private List<TestCase> testcases;

	TestSuite(WebManager manager){
		this.manager = manager;
		this.testcases = new ArrayList<TestCase>();
	}
	
	public static TestSuite of(WebManager manager) {
		return new TestSuite(manager);
	}
	
	public WebManager getManager() {
		return this.manager;
	}
		
	
	public TestSuite testcase(Function<TestSuite, TestCase> stepFunction) {
		this.testcase(stepFunction.apply(this));
		return this;
	}
	
	public TestSuite testcase(TestCase testcase) {
		this.testcases.add(testcase);
		return this;
	}

	@Override
	protected void execute() {
		testcases.forEach(this::execute);
	}
	
	private void execute(TestCase testcase) {
		testcase.run();
		this.isPass = testcase.isPass();
		this.error = testcase.getError();
		
		if(!this.isPass) {
			throw new TestException(this);
		}	
	}
}
