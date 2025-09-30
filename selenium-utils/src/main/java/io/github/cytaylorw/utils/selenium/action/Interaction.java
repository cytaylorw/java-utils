package io.github.cytaylorw.utils.selenium.action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.github.cytaylorw.utils.selenium.core.WebManager;

public abstract class Interaction {
	
	protected final WebManager manager;
    protected final WebDriver driver;
    protected WebElement element = null;
    protected final LatencyProfile latency;

    public Interaction(WebManager manager) {
        this(manager, null, manager.getOptions().nativeLatency());
    }
    
    public Interaction(WebManager manager, WebElement element) {
        this(manager, element, manager.getOptions().nativeLatency());
    }

    public Interaction(WebManager manager, WebElement element, LatencyProfile latency) {
    	this.manager = manager;
        this.driver = manager.getDriver();
        this.latency = latency;
        this.element = element;
    }

    protected void sleepBeforeAction() {
        latency.apply();
    }

    public abstract void click(WebElement element);
    public abstract void type(WebElement element, String text);
    public abstract void clear(WebElement element);
    public abstract void submit(WebElement element);


	public void click() {
		click(element);
	}

	public void type(String text) {
		type(element, text);
	}

	public void clear() {
		clear(element);
	}

	public void submit() {
		submit(element);
	}

    // 🧩 NEW: Select option by visible text
    public void selectByText(WebElement selectElement, String visibleText) {
        sleepBeforeAction();
        new Select(selectElement).selectByVisibleText(visibleText);
    }
    
    public void selectByText(String visibleText) {
        selectByText(element, visibleText);
    }

    // 🧩 Optional: Select by value
    public void selectByValue(WebElement selectElement, String value) {
        sleepBeforeAction();
        new Select(selectElement).selectByValue(value);
    }
    
    public void selectByValue(String value) {
        selectByValue(element, value);
    }

    // 🧩 Optional: Select by index
    public void selectByIndex(WebElement selectElement, int index) {
        sleepBeforeAction();
        new Select(selectElement).selectByIndex(index);
    }
    
    public void selectByIndex(int index) {
    	selectByIndex(element, index);
    }
}