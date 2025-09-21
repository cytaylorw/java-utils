package io.github.cytaylorw.utils.selenium.by;

import java.util.List;

import org.openqa.selenium.By;

public class ByStrategies {

    public static ByStrategy id() {
        return By::id;
    }

    public static ByStrategy name() {
        return By::name;
    }

    public static ByStrategy className() {
        return By::className;
    }

    public static ByStrategy css() {
        return By::cssSelector;
    }

    public static ByStrategy xpath() {
        return By::xpath;
    }
    
    public static ByStrategy dataAttr(String data) {
        return locator -> By.cssSelector("[data-" + data + "='" + locator + "']");
    }

    public static ByStrategy dataTestAttr() {
        return locator -> By.cssSelector("[data-test='" + locator + "']");
    }

    public static ByStrategy textContains() {
        return locator -> By.xpath("//*[contains(text(),'" + locator + "')]");
    }

    public static ByStrategy exactText() {
        return locator -> By.xpath("//*[text()='" + locator + "']");
    }

    public static ByStrategy classStartsWith() {
        return locator -> By.cssSelector("[class^='" + locator + "']");
    }
    
    public static ByStrategy autoDetect() {
        return locator -> {
            if (locator == null || locator.isBlank()) {
                throw new IllegalArgumentException("Locator string cannot be null or blank");
            }

            String raw = locator;
            String trimmed = raw.trim();

            // 1. XPath detection
            if (trimmed.startsWith("/") || trimmed.startsWith("(") || trimmed.startsWith(".") || trimmed.contains("//")) {
                return By.xpath(trimmed);
            }

            // 2. CSS detection — use raw, not trimmed
            if (raw.matches(".*[>~+\\[:.#].*") || raw.matches(".*:(hover|nth-child|first-child|last-child).*")) {
                return By.cssSelector(raw);
            }

            // 3. Text detection — if contains anything other than [a-zA-Z0-9-_]
            if (!trimmed.matches("^[a-zA-Z0-9_-]+$")) {
                return By.xpath("//*[normalize-space(text())='" + trimmed + "']");
            }

            // 4. Fallback to ID
            return By.id(trimmed);
        };
    }

    public static List<ByStrategy> defaultChain() {
        return List.of(autoDetect(), name(), className(), exactText(), id(), css(), xpath());
    }

}