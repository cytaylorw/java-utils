package io.github.cytaylorw.utils.selenium.core;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

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
import io.github.cytaylorw.utils.selenium.ui.ElementCondition;
import io.github.cytaylorw.utils.selenium.ui.SmartConditions;
import io.github.cytaylorw.utils.selenium.ui.WaitHelper;

public class WebManager {

	private final WebOptions options;
    private final WebDriver driver;
    private final BrowserInteraction browser;
    private final WaitHelper wait;
    private final BySmart by;
    private final SmartConditions conditions;

    public WebManager(WebOptions options) {
    	this.options = options;
        this.driver = options.getDriver();
        this.browser = new BrowserInteraction(this);
        this.wait = new WaitHelper(this);
        this.by = BySmart.create(options.getStragegies());
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
    
    public <T> T waitUntil(ExpectedCondition<T> condition, Long timeoutInSec) {
    	return waitUntil(condition, timeoutInSec, null);
    }
    
    public <T> T waitUntil(ExpectedCondition<T> condition) {
    	return this.wait.waitUntil(condition);
    }
    
    public <A extends Interaction> A action(ElementCondition condition, Class<A> clazz) {
    	WebElement element = waitUntil(condition);
    	try {
			return clazz.getDeclaredConstructor().newInstance(this, element);
		} catch (Exception e) {
			throw new RuntimeException("System Failed to: " + e.getMessage(), e);
		}
    }
        
    public NativeInteraction action(ElementCondition condition) {
    	return action(condition, NativeInteraction.class);
    }
    
    public <T> Optional<T> tryUntil(ExpectedCondition<T> condition) {
    	return this.wait.tryUntil(condition);
    }
    
    public <T> Optional<T> tryUntil(FluentWait<WebDriver> wait, ExpectedCondition<T> condition) {
    	return WaitHelper.tryUntil(wait, condition);
    }
    
    public <S extends AbstractScreenshotHelper> S screenshot(Class<S> clazz) {
    	try {
			return clazz.getDeclaredConstructor().newInstance(this);
		} catch (Exception e) {
			throw new RuntimeException("System Failed to: " + e.getMessage(), e);
		}
    }
    
    public void screenshot(ElementCondition condition, String label) {
    	WebElement element = this.wait.getDefault().until(condition);
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

