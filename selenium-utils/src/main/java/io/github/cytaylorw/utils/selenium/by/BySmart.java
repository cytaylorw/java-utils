package io.github.cytaylorw.utils.selenium.by;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class BySmart extends By {
    private static final Logger logger = Logger.getLogger(BySmart.class.getName());

    private String locatorString;
    private final List<ByStrategy> strategies;

    public BySmart() {
        this.strategies = ByStrategies.defaultChain();
    }

    public BySmart(List<ByStrategy> strategies) {
        this.strategies = strategies;
    }
    
    public static BySmart create() {
        return new BySmart();
    }

    public static BySmart create(List<ByStrategy> strategies) {
        return new BySmart(strategies);
    }

    public void setLocatorString(String locatorString) {
        this.locatorString = locatorString;
    }

    public String getLocatorString() {
        return locatorString;
    }
    
    public BySmart with(String locatorString) {
        this.locatorString = locatorString;
        return this;
    }

    @Override
    public WebElement findElement(SearchContext context) {
        if (locatorString == null || locatorString.isEmpty()) {
            throw new IllegalStateException("BySmart: locatorString must be set before use.");
        }

        logger.info("BySmart: Searching for element using strategies for: " + locatorString);

        for (ByStrategy strategy : strategies) {
            By by = strategy.apply(locatorString);
            try {
                logger.fine("Trying: " + by);
                WebElement el = context.findElement(by);
                if (Objects.nonNull(el) && el.isDisplayed()) {
                    logger.info("Found element using: " + by);
                    return el;
                }
            } catch (Exception e) {
                logger.fine("Strategy failed: " + by + " — " + e.getClass().getSimpleName());
            }
        }

        throw new RuntimeException("BySmart: No matching element found for: " + locatorString);
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        if (locatorString == null || locatorString.isEmpty()) {
            throw new IllegalStateException("BySmart: locatorString must be set before use.");
        }

        for (ByStrategy strategy : strategies) {
            By by = strategy.apply(locatorString);
            try {
                List<WebElement> elements = context.findElements(by);
                if (!elements.isEmpty()) {
                    logger.info("Found elements using: " + by);
                    return elements;
                }
            } catch (Exception e) {
                logger.fine("Strategy failed: " + by + " — " + e.getClass().getSimpleName());
            }
        }

        return List.of();
    }

    @Override
    public String toString() {
        return "BySmart(" + locatorString + ")";
    }
}
