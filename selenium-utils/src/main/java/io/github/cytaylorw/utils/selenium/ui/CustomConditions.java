package io.github.cytaylorw.utils.selenium.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomConditions {

    public static ExpectedCondition<WebElement> textContains(By locator, String substring) {
        return driver -> {
            try {
                WebElement el = driver.findElement(locator);
                return el.getText().contains(substring) ? el : null;
            } catch (Exception e) {
                return null;
            }
        };
    }

    public static ExpectedCondition<Boolean> disabled(By locator) {
        return driver -> {
            WebElement el = driver.findElement(locator);
            return !el.isEnabled() || el.getAttribute("disabled") != null;
        };
    }
    
    public static ExpectedCondition<Boolean> notPresent(By locator) {
        return driver -> {
            return driver.findElements(locator).isEmpty();
        };
    }
    
    public static ExpectedCondition<WebElement> inputReady(By locator) {
        return driver -> {
            WebElement el = driver.findElement(locator);
            boolean visible = el.isDisplayed();
            boolean enabled = el.isEnabled();
            String readonly = el.getAttribute("readonly");
            boolean isReadonly = readonly != null;

            return visible && enabled && !isReadonly ? el : null;

        };
    }
}
