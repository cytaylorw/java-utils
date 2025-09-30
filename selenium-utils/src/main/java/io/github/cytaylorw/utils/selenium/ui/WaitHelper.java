package io.github.cytaylorw.utils.selenium.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;

import io.github.cytaylorw.utils.selenium.core.WebManager;
import io.github.cytaylorw.utils.selenium.options.WaitOptions;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class WaitHelper {

	private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration DEFAULT_POLLING = Duration.ofMillis(500);
    private static final List<Class<? extends Throwable>> DEFAULT_IGNORED = List.of(
        NoSuchElementException.class,
        StaleElementReferenceException.class
    );
    
    private final WebManager manager;
    private final WebDriver driver;
    private final FluentWait<WebDriver> defaultWait;
    
    public WaitHelper(WebManager manager) {
    	this.manager = manager;
        this.driver = manager.getDriver();
    	this.defaultWait = create(manager.getOptions().waitOptions());
    }
    
    public WebManager manager() {
    	return manager;
    }
    
    public FluentWait<WebDriver> getDefault() {
    	return defaultWait;
    }
    
    // Basic FluentWait with defaults
    public FluentWait<WebDriver> create() {
        return create(DEFAULT_TIMEOUT, DEFAULT_POLLING, DEFAULT_IGNORED, null);
    }
    
    public FluentWait<WebDriver> create(WaitOptions waitOtions) {
    	if(waitOtions == null) return create();
        return create(waitOtions.getTimeout(), waitOtions.getPollingInterval()
        		, waitOtions.isIgnoreStale() ? DEFAULT_IGNORED : List.of(), null);
    }

    // Custom timeout and polling
    public FluentWait<WebDriver> create(Duration timeout, Duration polling) {
        return create(timeout, polling, DEFAULT_IGNORED, null);
    }

    // Full control: timeout, polling, ignored exceptions, message
    public FluentWait<WebDriver> create(
        Duration timeout,
        Duration polling,
        List<Class<? extends Throwable>> ignoredExceptions,
        String message
    ) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(timeout)
            .pollingEvery(polling)
            .ignoreAll(ignoredExceptions);

        if (message != null && !message.isBlank()) {
            wait.withMessage(message);
        }

        return wait;
    }

    // Optional: shortcut with custom message
    public FluentWait<WebDriver> withMessage(String message) {
        return create(DEFAULT_TIMEOUT, DEFAULT_POLLING, DEFAULT_IGNORED, message);
    }
    
    
    public <T> T waitUntil(FluentWait<WebDriver> wait, ExpectedCondition<T> condition) {
        try {
            return wait.until(condition);
        } catch (Exception e) {
        	manager.errorScreenshot().capture(e.getClass().getSimpleName());
            throw e;
        }
    }
    
    public <T> T waitUntil(ExpectedCondition<T> condition) {
            return waitUntil(defaultWait, condition);
    }

    
    public <T> Optional<T> tryUntil(ExpectedCondition< T> condition) {
        return tryUntil(defaultWait, condition);
    }

    public static <T> Optional<T> tryUntil(FluentWait<WebDriver> wait, ExpectedCondition<T> condition) {
        try {
            T result = wait.until(condition);
            return Optional.ofNullable(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    public <T> Optional<T> tryUntil(ExpectedCondition<T> condition, Consumer<T> expectedAction, Runnable timeoutAction
        ) {
    	return tryUntil(defaultWait, condition, expectedAction, timeoutAction);
    }

    // 🔧 Enhanced method: post-success hook + timeout hook
    public static <T> Optional<T> tryUntil(
        FluentWait<WebDriver> wait,
        ExpectedCondition<T> condition,
        Consumer<T> expectedAction,
        Runnable timeoutAction
    ) {
        try {
            T result = wait.until(condition);
            if (result != null && expectedAction != null) {
                expectedAction.accept(result);
            }
            return Optional.ofNullable(result);
        } catch (Exception e) {
            if (timeoutAction != null) {
                timeoutAction.run();
            }
            return Optional.empty();
        }
    }

}