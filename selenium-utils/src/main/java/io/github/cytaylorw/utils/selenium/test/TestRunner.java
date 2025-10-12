package io.github.cytaylorw.utils.selenium.test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import io.github.cytaylorw.utils.selenium.core.WebManager;
import io.github.cytaylorw.utils.selenium.ui.SmartConditions;

public abstract class TestRunner<T extends TestRunner<T>> {
	
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	protected static final String SEPERATOR = "-";
	
	protected TestSuite testsuite;

	protected TestCase testcase;
	
	protected TestStep step;

	protected String number;
	
	protected String name;
	
	protected String description;
	
	protected boolean isPass;
	
	protected Throwable error;
	
	protected Consumer<? super T> before;
	
	protected Consumer<? super T> after;

	private int screenshotCount = 1;
	
	@SuppressWarnings("unchecked")
	protected T getThis() {
		return (T) this;
	}
	
	public TestSuite getTestsuite() {
		return testsuite;
	}
	
	public void setTestsuite(TestSuite testsuite) {
		this.testsuite = testsuite;
	}
	
	public TestCase getTestcase() {
		return testcase;
	}
	
	public void setTestcase(TestCase testcase) {
		this.testcase = testcase;
	}
	
	public TestStep getStep() {
		return step;
	}
	
	public void setStep(TestStep step) {
		this.step = step;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isPass() {
		return this.isPass;
	}
	
	public Throwable getError() {
		return error;
	}
	
	public T before(Consumer<? super T> before) {
		this.before = before;
		return getThis();
	}
	
	public T after(Consumer<? super T> after) {
		this.after = after;
		return getThis();
	}
	
	public T info(String number, String name, String description) {
		this.setNumber(number);
		this.setName(name);
		this.setDescription(description);
		return getThis();
	}
	
	
	public T info(String number, String name) {
		this.setNumber(number);
		this.setName(name);
		return getThis();
	}
	
	
	public T info(String number) {
		this.setNumber(number);
		return getThis();
	}
	
	public void run() {
		logger.info(this.getNumber() + " Starting...");
		try {			
			if(this.before != null) {			
				this.before.accept(getThis());
			}
			this.execute();
			
			if(this.after != null) {			
				this.after.accept(getThis());
			}
		}catch(Throwable throwable) {
			this.error = throwable;
			this.isPass = false;
			this.errorScreenshot();
		}
		logger.info(this.getNumber() + " Finished.");
	}
	
	public void screenshot() {
		this.getManager().screenshot(this.getScreenshotLabel());
	}
	
    public void screenshot(Function<SmartConditions, ExpectedCondition<WebElement>> conditionFunction) {
    	this.getManager().screenshot(conditionFunction, this.getScreenshotLabel());
    }

	public void screenshot(String locater) {
		this.getManager().screenshot(locater, this.getScreenshotLabel());
	}

	public void errorScreenshot() {
		this.getManager().errorScreenshot().capture(this.getNumber());
	}
	
	protected String getScreenshotLabel() {
		return this.number + "_" + screenshotCount++;
	}
	
	protected String getChildSeperator() {
		return SEPERATOR;
	}
	
	abstract public WebManager getManager();
	
	abstract protected void execute();
}
