package io.github.cytaylorw.utils.selenium.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

@FunctionalInterface
public interface ElementCondition extends ExpectedCondition<WebElement> {

}
