package io.github.cytaylorw.utils.selenium.test.exception;

import io.github.cytaylorw.utils.selenium.test.TestRunner;

public class TestException extends RuntimeException {

	private static final long serialVersionUID = -6501316863442734663L;
	
	public TestException(TestRunner<?> runner) {
		super(runner.getClass().getSimpleName()+" failed.", runner.getError());

	}

}
