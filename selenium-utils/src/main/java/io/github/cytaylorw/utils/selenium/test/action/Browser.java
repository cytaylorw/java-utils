package io.github.cytaylorw.utils.selenium.test.action;

import java.util.function.Consumer;

import io.github.cytaylorw.utils.selenium.browser.BrowserInteraction;
import io.github.cytaylorw.utils.selenium.browser.TabOrderTracker;
import io.github.cytaylorw.utils.selenium.core.WebManager;
import io.github.cytaylorw.utils.selenium.test.TestRunner;

public class Browser {

	public static <R extends TestRunner<R>> Consumer<R> action(Consumer<BrowserInteraction> action){
		return r -> {
			WebManager wm = r.getManager();
			action.accept(wm.browser());
		};
	}
	
	public static <R extends TestRunner<R>> Consumer<R> tabs(Consumer<TabOrderTracker> action){
		return r -> {
			WebManager wm = r.getManager();
			action.accept(wm.browser().tabs());
		};
	}
	
	public static <R extends TestRunner<R>> Consumer<R> navigateTo(String url){
		return r -> {
			WebManager wm = r.getManager();
			wm.browser().navigateTo(url);
		};
	}
	
	public static <R extends TestRunner<R>> Consumer<R> init(String url){
		return r -> {
			WebManager wm = r.getManager();
			wm.browser().maximize();
			wm.browser().navigateTo(url);
		};
	}
}
