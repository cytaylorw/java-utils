package io.github.cytaylorw.utils.selenium.core;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

import io.github.cytaylorw.utils.selenium.action.Interaction;
import io.github.cytaylorw.utils.selenium.action.NativeInteraction;
import io.github.cytaylorw.utils.selenium.browser.BrowserInteraction;
import io.github.cytaylorw.utils.selenium.by.BySmart;
import io.github.cytaylorw.utils.selenium.options.WebOptions;
import io.github.cytaylorw.utils.selenium.screenshot.AbstractScreenshotHelper;
import io.github.cytaylorw.utils.selenium.screenshot.RobotScreenshotHelper;
import io.github.cytaylorw.utils.selenium.screenshot.ScreenshotHelper;
import io.github.cytaylorw.utils.selenium.ui.SmartConditions;
import io.github.cytaylorw.utils.selenium.ui.WaitHelper;

public class WebManager {

	private final WebOptions options;
    private final WebDriver driver;
    private final BrowserInteraction browser;
    private final WaitHelper wait;
    private final BySmart by;
    private final SmartConditions conditions;

    public WebManager(WebDriver driver, WebOptions options) {
    	this.options = options;
        this.driver = driver;
        this.browser = new BrowserInteraction(this);
        this.wait = new WaitHelper(this);
        this.by = BySmart.create(options.getStrategies());
        this.conditions = new SmartConditions(by);
    }
    

    public WebOptions getOptions() {
        return options;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public BrowserInteraction browser() {
        return browser;
    }
    
    public BySmart by() {
        return by;
    }
    
    public SmartConditions conditions() {
    	return conditions;
    }
    
    public WebManager locator(String locator) {
    	conditions.with(locator);
    	return this;
    }
    
    public WaitHelper getWait() {
    	return wait;
    }    
    
    public <T> T waitUntil(ExpectedCondition<T> condition, Long timeoutInSec, Long pollingInMs) {
    	FluentWait<WebDriver> wait = this.wait.getDefault();
    	if(Objects.nonNull(timeoutInSec)) {
    		wait.withTimeout(Duration.ofSeconds(timeoutInSec));
    	}
    	if(Objects.nonNull(pollingInMs)) {
    		wait.pollingEvery(Duration.ofMillis(pollingInMs));
    	}
    	return this.wait.waitUntil(wait, condition);
    }
    
    
    public <T> T waitUntil(Function<SmartConditions, ExpectedCondition<T>> conditionFunction, Long timeoutInSec, Long pollingInMs) {
    	return waitUntil(conditionFunction.apply(conditions), timeoutInSec, null);
    }
    
    public <T> T waitUntil(Function<SmartConditions, ExpectedCondition<T>> conditionFunction, Long timeoutInSec) {
    	return waitUntil(conditionFunction, timeoutInSec, null);
    }
    
    public <T> T waitUntil(Function<SmartConditions, ExpectedCondition<T>> conditionFunction) {
    	return waitUntil(conditionFunction, null, null);
    }
    
    public <A extends Interaction> A action(ExpectedCondition<WebElement> condition, Class<A> clazz) {
    	WebElement element = waitUntil(condition, null, null);
    	try {
			return clazz.getDeclaredConstructor(WebManager.class, WebElement.class).newInstance(this, element);
		} catch (Exception e) {
			throw new RuntimeException("System Failed to: " + e.getMessage(), e);
		}
    }
    
    public <A extends Interaction> A action(Function<SmartConditions, ExpectedCondition<WebElement>> conditionFunction, Class<A> clazz) {
    	return action(conditionFunction.apply(conditions), clazz);
    }
        
    public NativeInteraction action(Function<SmartConditions, ExpectedCondition<WebElement>> conditionFunction) {
    	return action(conditionFunction, NativeInteraction.class);
    }
    
    public <T> Optional<T> tryUntil(ExpectedCondition<T> condition) {
    	return this.wait.tryUntil(condition);
    }
    
    public <T> Optional<T> tryUntil(ExpectedCondition<T> condition, FluentWait<WebDriver> wait) {
    	return WaitHelper.tryUntil(wait, condition);
    }
    
    public <S extends AbstractScreenshotHelper> S screenshot(Class<S> clazz) {
    	try {
			return clazz.getDeclaredConstructor(WebManager.class).newInstance(this);
		} catch (Exception e) {
			throw new RuntimeException("System Failed to: " + e.getMessage(), e);
		}
    }
        
    public void screenshot(String label) {
    	new ScreenshotHelper(this).capture(label);
    }
    
    public void screenshot(Function<SmartConditions, ExpectedCondition<WebElement>> conditionFunction, String label) {
    	WebElement element = waitUntil(conditionFunction.apply(conditions), null, null);
    	new ScreenshotHelper(this).capture(element, label);
    }
    
    
    public void screenshot(String locator, String label) {
    	WebElement element = this.locator(locator).waitUntil(SmartConditions::visibilityOfElementLocated, null, null);
    	new ScreenshotHelper(this).capture(element, label);
    }
        
    public RobotScreenshotHelper errorScreenshot() {
    	return new RobotScreenshotHelper(this, options.errorScreenshot());
    }
    
    
    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }
}

