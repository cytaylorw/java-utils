package io.github.cytaylorw.utils.selenium.test.action;

import java.util.function.Consumer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.github.cytaylorw.utils.selenium.action.Interaction;
import io.github.cytaylorw.utils.selenium.action.NativeInteraction;
import io.github.cytaylorw.utils.selenium.core.WebManager;
import io.github.cytaylorw.utils.selenium.test.TestRunner;
import io.github.cytaylorw.utils.selenium.ui.CustomConditions;
import io.github.cytaylorw.utils.selenium.ui.SmartConditions;

public class Element {
	
	public static <R extends TestRunner<R>> Consumer<R> action(String locator, Consumer<NativeInteraction> action){
		return r -> {
			WebManager wm = r.getManager();
			action.accept(wm.locator(locator).action(SmartConditions::elementToBeClickable));
		};
	}
	
	public static <A extends Interaction, R extends TestRunner<R>> Consumer<R> action(String locator, Class<A> clazz, Consumer<A> action){
		return r -> {
			WebManager wm = r.getManager();
			action.accept(wm.locator(locator).action(SmartConditions::elementToBeClickable, clazz));
		};
	}
		
	public static <A extends Interaction, R extends TestRunner<R>> Consumer<R> action(By locator, Class<A> clazz, Consumer<A> action){
		return r -> {
			WebManager wm = r.getManager();
			action.accept(wm.action(ExpectedConditions.elementToBeClickable(locator), clazz));
		};
	}
	
	
	public static <A extends Interaction, R extends TestRunner<R>> Consumer<R> action(ExpectedCondition<WebElement> condition, Class<A> clazz, Consumer<A> action){
		return r -> {
			WebManager wm = r.getManager();
			action.accept(wm.action(condition, clazz));
		};
	}

	public static <R extends TestRunner<R>> Consumer<R> click(String locator){
		return r -> {
			WebManager wm = r.getManager();
			wm.locator(locator).action(SmartConditions::elementToBeClickable).click();
		};
	}
	
	public static <R extends TestRunner<R>> Consumer<R> click(By locator){
		return r -> {
			WebManager wm = r.getManager();
			wm.action(ExpectedConditions.elementToBeClickable(locator), NativeInteraction.class).click();
		};
	}
	
	public static <R extends TestRunner<R>> Consumer<R> type(String locator, String text){
		return r -> {
			WebManager wm = r.getManager();
			wm.locator(locator).action(SmartConditions::inputReady).type(text);;
		};
	}
	
	public static <R extends TestRunner<R>> Consumer<R> type(By locator, String text){
		return r -> {
			WebManager wm = r.getManager();
			wm.action(CustomConditions.inputReady(locator), NativeInteraction.class).type(text);
		};
	}
}
