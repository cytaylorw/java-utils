package io.github.cytaylorw.utils.selenium.test.action;

import java.util.function.Consumer;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.github.cytaylorw.utils.selenium.core.WebManager;
import io.github.cytaylorw.utils.selenium.test.TestRunner;
import io.github.cytaylorw.utils.selenium.ui.CustomConditions;
import io.github.cytaylorw.utils.selenium.ui.SmartConditions;

public class Waits {
	
    public static <T, R extends TestRunner<R>> Consumer<R>  waitFor(ExpectedCondition<T> condition) {
		return r -> {
			WebManager wm = r.getManager();
			wm.waitUntil(condition, null, null);
		};
    }
	
    public static <T, R extends TestRunner<R>> Consumer<R>  waitFor(ExpectedCondition<T> condition, Long timeoutInSec) {
		return r -> {
			WebManager wm = r.getManager();
			wm.waitUntil(condition, timeoutInSec, null);
		};
    }

    public static <T, R extends TestRunner<R>> Consumer<R>  waitFor(String locator, Function<SmartConditions, ExpectedCondition<T>> conditionFunction) {
		return r -> {
			WebManager wm = r.getManager();
			wm.locator(locator).waitUntil(conditionFunction);
		};
    }
    
    public static <R extends TestRunner<R>> Consumer<R>  waitForDisabled(String locator) {
		return r -> {
			WebManager wm = r.getManager();
			wm.locator(locator).waitUntil(SmartConditions::disabled);
		};
    }
    
    public static <R extends TestRunner<R>> Consumer<R>  waitForDisabled(String locator, Long timeoutInSec) {
		return r -> {
			WebManager wm = r.getManager();
			wm.locator(locator).waitUntil(SmartConditions::disabled, timeoutInSec);
		};
    }

    public static <R extends TestRunner<R>> Consumer<R> waitForDisabled(By locator, Long timeoutInSec, Long pollingInMs) {
		return r -> {
			WebManager wm = r.getManager();
			wm.waitUntil(CustomConditions.disabled(locator), timeoutInSec, pollingInMs);
		};
    }
    
    public static <R extends TestRunner<R>> Consumer<R>  waitForNotPresent(String locator) {
		return r -> {
			WebManager wm = r.getManager();
			wm.locator(locator).waitUntil(SmartConditions::notPresent);
		};
    }
    

    public static <R extends TestRunner<R>> Consumer<R>  waitForNotPresent(String locator, Long timeoutInSec) {
		return r -> {
			WebManager wm = r.getManager();
			wm.locator(locator).waitUntil(SmartConditions::notPresent, timeoutInSec);
		};
    }

    public static <R extends TestRunner<R>> Consumer<R> waitForNotPresentd(By locator, Long timeoutInSec, Long pollingInMs) {
		return r -> {
			WebManager wm = r.getManager();
			wm.waitUntil(CustomConditions.notPresent(locator), timeoutInSec, pollingInMs);
		};
    }

    public static <R extends TestRunner<R>> Consumer<R>  waitForHidden(String locator) {
		return r -> {
			WebManager wm = r.getManager();
			wm.locator(locator).waitUntil(SmartConditions::invisibilityOfElementLocated);
		};
    }
    

    public static <R extends TestRunner<R>> Consumer<R>  waitForHidden(String locator, Long timeoutInSec) {
		return r -> {
			WebManager wm = r.getManager();
			wm.locator(locator).waitUntil(SmartConditions::invisibilityOfElementLocated, timeoutInSec);
		};
    }

    public static <R extends TestRunner<R>> Consumer<R> waitForHidden(By locator, Long timeoutInSec, Long pollingInMs) {
		return r -> {
			WebManager wm = r.getManager();
			wm.waitUntil(ExpectedConditions.invisibilityOfElementLocated(locator), timeoutInSec, pollingInMs);
		};
    }
    

    public static <R extends TestRunner<R>> Consumer<R> waitForVisible(String locator) {
		return r -> {
			WebManager wm = r.getManager();
			wm.locator(locator).waitUntil(SmartConditions::visibilityOfElementLocated);
		};
    }
    

    public static <R extends TestRunner<R>> Consumer<R> waitForVisible(String locator, Long timeoutInSec) {
		return r -> {
			WebManager wm = r.getManager();
			wm.locator(locator).waitUntil(SmartConditions::visibilityOfElementLocated, timeoutInSec);
		};
    }

    public static <R extends TestRunner<R>> Consumer<R> waitForVisible(By locator, Long timeoutInSec, Long pollingInMs) {
		return r -> {
			WebManager wm = r.getManager();
			wm.waitUntil(ExpectedConditions.visibilityOfElementLocated(locator), timeoutInSec, pollingInMs);
		};
    }
}
