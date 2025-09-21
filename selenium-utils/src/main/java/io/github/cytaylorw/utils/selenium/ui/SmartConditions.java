package io.github.cytaylorw.utils.selenium.ui;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.github.cytaylorw.utils.selenium.by.BySmart;

import java.util.List;

public class SmartConditions {

    private final BySmart bySmart;

    public SmartConditions(BySmart bySmart) {
        this.bySmart = bySmart;
    }

    // 🔁 Dynamic locator injection
    public SmartConditions with(String locatorString) {
        bySmart.with(locatorString);
        return this;
    }

    // ✅ Instance methods using current locator
    public ExpectedCondition<WebElement> elementToBeClickable() {
        return ExpectedConditions.elementToBeClickable(bySmart);
    }

    public ExpectedCondition<WebElement> visibilityOfElementLocated() {
        return ExpectedConditions.visibilityOfElementLocated(bySmart);
    }

    public ExpectedCondition<WebElement> presenceOfElementLocated() {
        return ExpectedConditions.presenceOfElementLocated(bySmart);
    }

    public ExpectedCondition<Boolean> invisibilityOfElementLocated() {
        return ExpectedConditions.invisibilityOfElementLocated(bySmart);
    }

    public ExpectedCondition<List<WebElement>> visibilityOfAllElementsLocatedBy() {
        return ExpectedConditions.visibilityOfAllElementsLocatedBy(bySmart);
    }

    public ExpectedCondition<List<WebElement>> presenceOfAllElementsLocatedBy() {
        return ExpectedConditions.presenceOfAllElementsLocatedBy(bySmart);
    }

    public ExpectedCondition<Boolean> elementToBeSelected() {
        return ExpectedConditions.elementToBeSelected(bySmart);
    }

    public ExpectedCondition<Boolean> elementSelectionStateToBe(boolean selected) {
        return ExpectedConditions.elementSelectionStateToBe(bySmart, selected);
    }

    public ExpectedCondition<Boolean> attributeContains(String attr, String value) {
        return ExpectedConditions.attributeContains(bySmart, attr, value);
    }

    public ExpectedCondition<Boolean> attributeToBe(String attr, String value) {
        return ExpectedConditions.attributeToBe(bySmart, attr, value);
    }

    public ExpectedCondition<Boolean> textToBePresentInElementLocated(String text) {
        return ExpectedConditions.textToBePresentInElementLocated(bySmart, text);
    }

    public ExpectedCondition<List<WebElement>> numberOfElementsToBeMoreThan(int count) {
        return ExpectedConditions.numberOfElementsToBeMoreThan(bySmart, count);
    }

    public ExpectedCondition<List<WebElement>> numberOfElementsToBeLessThan(int count) {
        return ExpectedConditions.numberOfElementsToBeLessThan(bySmart, count);
    }

    public ExpectedCondition<List<WebElement>> numberOfElementsToBe(int count) {
        return ExpectedConditions.numberOfElementsToBe(bySmart, count);
    }

    public BySmart getBySmart() {
        return bySmart;
    }
}
