package io.github.cytaylorw.utils.selenium.action;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;

import io.github.cytaylorw.utils.selenium.core.WebManager;

public class ActionInteraction extends NativeInteraction {

    public ActionInteraction(WebManager manager) {
        super(manager, null, manager.getOptions().actionLatency());
    }
    
    public ActionInteraction(WebManager manager, WebElement element) {
    	super(manager, element, manager.getOptions().actionLatency());
    }

    public ActionInteraction(WebManager manager, WebElement element, LatencyProfile latency) {
        super(manager, element, latency);
    }

    @Override
    public void click(WebElement element) {
        sleepBeforeAction();
        new Actions(driver).moveToElement(element).click().perform();
    }

    @Override
    public void type(WebElement element, String text) {
        sleepBeforeAction();
        new Actions(driver).moveToElement(element).click().sendKeys(text).perform();
    }

    // 🧩 NEW: Select option by visible text using keyboard navigation
    public void selectByText(WebElement selectElement, String visibleText) {
        sleepBeforeAction();
        new Actions(driver)
            .moveToElement(selectElement)
            .click()
            .sendKeys(visibleText)
            .sendKeys(org.openqa.selenium.Keys.ENTER)
            .perform();
    }

    // 🧩 Optional: Select by index via arrow keys
    public void selectByIndex(WebElement selectElement, int index) {
        sleepBeforeAction();
        Actions actions = new Actions(driver)
            .moveToElement(selectElement)
            .click();

        for (int i = 0; i < index; i++) {
            actions.sendKeys(org.openqa.selenium.Keys.ARROW_DOWN);
        }

        actions.sendKeys(org.openqa.selenium.Keys.ENTER).perform();
    }

    public void scrollDown(int times) {
        sleepBeforeAction();
        new Actions(driver).sendKeys(Keys.ARROW_DOWN).perform();

    }

    public void scrollUp(int times) {
        sleepBeforeAction();
        new Actions(driver).sendKeys(Keys.ARROW_UP).perform();
    }

    public void scrollTo(WebElement element) {
        sleepBeforeAction();
        new Actions(driver)
            .moveToElement(element)
            .perform(); // This triggers native scroll in many cases
    }
    
    public void scrollTo() {
    	scrollTo(element);
    }

    public void rightClick(WebElement element) {
        sleepBeforeAction();
        new Actions(driver).moveToElement(element).contextClick().perform();
    }

    public void rightClick() {
    	rightClick(element);
    }
    
    public void pageDown() {
        sleepBeforeAction();
        new Actions(driver).sendKeys(Keys.PAGE_DOWN).perform();
    }

    public void pageUp() {
        sleepBeforeAction();
        new Actions(driver).sendKeys(Keys.PAGE_UP).perform();
    }
    
    public void hover(WebElement element) {
        sleepBeforeAction();
        new Actions(driver).moveToElement(element).perform();

    }
    
    public void hover() {
    	hover(element);

    }
}
