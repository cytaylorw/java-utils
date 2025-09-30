package io.github.cytaylorw.utils.selenium.action;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import io.github.cytaylorw.utils.selenium.core.WebManager;

public class JsInteraction extends Interaction {

    public JsInteraction(WebManager manager) {
        super(manager, null, manager.getOptions().jsLatency());
    }
    
    public JsInteraction(WebManager manager, WebElement element) {
    	super(manager, element, manager.getOptions().jsLatency());
    }

    public JsInteraction(WebManager manager, WebElement element, LatencyProfile latency) {
        super(manager, element, latency);
    }

    @Override
    public void click(WebElement element) {
        sleepBeforeAction();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    @Override
    public void type(WebElement element, String text) {
        sleepBeforeAction();
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, text);
    }

    @Override
    public void clear(WebElement element) {
        sleepBeforeAction();
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
    }

    @Override
    public void submit(WebElement element) {
        sleepBeforeAction();
        ((JavascriptExecutor) driver).executeScript("arguments[0].submit();", element);
    }
    
    @Override
    public void selectByValue(WebElement selectElement, String value) {
        sleepBeforeAction();
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('change'));",
            selectElement, value
        );
    }
    
    public void scrollTo(WebElement element) {
        sleepBeforeAction();
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element
        );
    }
    
    public void scrollTo() {
    	scrollTo(element);
    }
    
    public void pageDown() {
        sleepBeforeAction();
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, window.innerHeight);");
    }

    public void pageUp() {
        sleepBeforeAction();
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -window.innerHeight);");
    }

}
