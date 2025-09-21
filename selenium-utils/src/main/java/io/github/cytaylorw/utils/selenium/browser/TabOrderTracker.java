package io.github.cytaylorw.utils.selenium.browser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;

import io.github.cytaylorw.utils.selenium.core.WebManager;

public class TabOrderTracker {

	private final WebManager manager;
    private final WebDriver driver;
    private final List<String> orderedHandles = new ArrayList<>();
    private int currentIndex = 0;

    public TabOrderTracker(WebManager manager) {
    	this.manager = manager;
        this.driver = manager.getDriver();
        orderedHandles.add(driver.getWindowHandle());
    }
    
    
    public WebManager manager() {
    	return manager;
    }

    public void update() {
        Set<String> currentHandles = driver.getWindowHandles();
        for (String handle : currentHandles) {
            if (!orderedHandles.contains(handle)) {
                orderedHandles.add(handle);
            }
        }
        syncCurrentIndex();
    }

    public void switchTo(int index) {
        if (index >= 0 && index < orderedHandles.size()) {
            driver.switchTo().window(orderedHandles.get(index));
            currentIndex = index;
        }
    }

    public void switchToNewTab() {
        update(); // Refresh tab list
        if (orderedHandles.size() > 1) {
            int lastIndex = orderedHandles.size() - 1;
            switchTo(lastIndex);
        }
    }

    public void closeAndSwitchTo(int index) {
        driver.close();
        orderedHandles.remove(currentIndex);
        switchTo(index);
    }
    
    public void close() {
        if (orderedHandles.size() <= 1) {
            driver.close(); // Last tab — just close
            orderedHandles.clear();
            currentIndex = -1;
            return;
        }

        int previousIndex = (currentIndex > 0) ? currentIndex - 1 : 1;
        driver.close();
        orderedHandles.remove(currentIndex);

        // Adjust index after removal
        currentIndex = (previousIndex < orderedHandles.size()) ? previousIndex : orderedHandles.size() - 1;
        driver.switchTo().window(orderedHandles.get(currentIndex));
    }
    
    public void closeAll() {
        // Copy to avoid ConcurrentModificationException
        List<String> handlesToClose = new ArrayList<>(orderedHandles);

        for (String handle : handlesToClose) {
            driver.switchTo().window(handle);
            driver.close();
        }

        orderedHandles.clear();
        currentIndex = -1;
    }
    
    public void closeAllExceptFirst() {
        if (orderedHandles.size() <= 1) return;

        String firstHandle = orderedHandles.get(0);
        List<String> handlesToClose = new ArrayList<>(orderedHandles.subList(1, orderedHandles.size()));

        for (String handle : handlesToClose) {
            driver.switchTo().window(handle);
            driver.close();
        }

        orderedHandles.clear();
        orderedHandles.add(firstHandle);
        driver.switchTo().window(firstHandle);
        currentIndex = 0;
    }

    public int getCurrentTabIndex() {
        syncCurrentIndex();
        return currentIndex;
    }

    public String getCurrentHandle() {
        return orderedHandles.get(currentIndex);
    }

    public int getTabCount() {
        return orderedHandles.size();
    }

    private void syncCurrentIndex() {
        String currentHandle = driver.getWindowHandle();
        int index = orderedHandles.indexOf(currentHandle);
        if (index >= 0) {
            currentIndex = index;
        }
    }
}
