package io.github.cytaylorw.utils.selenium.action;

import org.openqa.selenium.WebElement;

import io.github.cytaylorw.utils.selenium.core.WebManager;

public class NativeInteraction extends Interaction {

    public NativeInteraction(WebManager manager) {
        super(manager, null, manager.getOptions().nativeLatency());
    }
    
    public NativeInteraction(WebManager manager, WebElement element) {
    	super(manager, element, manager.getOptions().nativeLatency());
    }

    public NativeInteraction(WebManager manager, WebElement element, LatencyProfile latency) {
        super(manager, element, latency);
    }

    @Override
    public void click(WebElement element) {
        sleepBeforeAction();
        element.click();
    }

    @Override
    public void type(WebElement element, String text) {
        sleepBeforeAction();
        element.clear();
        element.sendKeys(text);
    }

    @Override
    public void clear(WebElement element) {
        sleepBeforeAction();
        element.clear();
    }

    @Override
    public void submit(WebElement element) {
        sleepBeforeAction();
        element.submit();
    }
}