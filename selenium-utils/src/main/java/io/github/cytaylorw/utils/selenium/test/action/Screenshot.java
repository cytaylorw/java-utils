package io.github.cytaylorw.utils.selenium.test.action;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import io.github.cytaylorw.utils.selenium.test.TestRunner;
import io.github.cytaylorw.utils.selenium.ui.SmartConditions;

public class Screenshot {

	public static <R extends TestRunner<R>> Consumer<R> captureStep(){
		return r -> {
			Optional.ofNullable(r.getStep()).ifPresent(s -> s.screenshot());
		};
	}
	
	public static <R extends TestRunner<R>> Consumer<R> captureStep(Function<SmartConditions, ExpectedCondition<WebElement>> conditionFunction){
		return r -> {
			Optional.ofNullable(r.getStep()).ifPresent(s -> s.screenshot(conditionFunction));
		};
	}
	
	
	public static <R extends TestRunner<R>> Consumer<R> captureStep(String locater){
		return r -> {
			Optional.ofNullable(r.getStep()).ifPresent(s -> s.screenshot(locater));
		};
	}
	
	public static <R extends TestRunner<R>> Consumer<R> capture(){
		return r -> {
			Optional.ofNullable(r.getTestcase()).ifPresent(s -> s.screenshot());
		};
	}
	
	public static <R extends TestRunner<R>> Consumer<R> capture(Function<SmartConditions, ExpectedCondition<WebElement>> conditionFunction){
		return r -> {
			Optional.ofNullable(r.getTestcase()).ifPresent(s -> s.screenshot(conditionFunction));
		};
	}
	
	
	public static <R extends TestRunner<R>> Consumer<R> capture(String locater){
		return r -> {
			Optional.ofNullable(r.getTestcase()).ifPresent(s -> s.screenshot(locater));
		};
	}
}
