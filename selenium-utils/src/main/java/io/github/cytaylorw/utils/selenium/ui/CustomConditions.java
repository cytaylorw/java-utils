package io.github.cytaylorw.utils.selenium.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomConditions {

    public static ExpectedCondition<Boolean> textContains(By locator, String substring) {
        return driver -> {
            try {
                WebElement el = driver.findElement(locator);
                return el.getText().contains(substring);
            } catch (Exception e) {
                return false;
            }
        };
    }

    public static ExpectedCondition<WebElement> visibleAndEnabled(By locator) {
        return driver -> {
            WebElement el = driver.findElement(locator);
            return (el.isDisplayed() && el.isEnabled()) ? el : null;
        };
    }
}
