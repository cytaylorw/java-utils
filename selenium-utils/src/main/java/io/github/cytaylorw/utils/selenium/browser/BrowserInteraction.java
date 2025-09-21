package io.github.cytaylorw.utils.selenium.browser;

import org.openqa.selenium.*;

import io.github.cytaylorw.utils.selenium.core.WebManager;

public class BrowserInteraction {

	private final WebManager manager;
    private final WebDriver driver;
    private final TabOrderTracker tabs;

    public BrowserInteraction(WebManager manager) {
    	this.manager = manager;
        this.driver = manager.getDriver();
        this.tabs = new TabOrderTracker(manager);
    }
    
    public WebManager manager() {
    	return manager;
    }
    
    public TabOrderTracker tabs() {
        return tabs;
    }

    // 🧭 Navigation
    public BrowserInteraction navigateTo(String url) {
        driver.get(url);
        return this;
    }

    public BrowserInteraction refresh() {
        driver.navigate().refresh();
        return this;
    }

    public BrowserInteraction back() {
        driver.navigate().back();
        return this;
    }

    public BrowserInteraction forward() {
        driver.navigate().forward();
        return this;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    // 🖥️ Window Size & Position
    public BrowserInteraction maximize() {
        driver.manage().window().maximize();
        return this;
    }

    public BrowserInteraction fullscreen() {
        driver.manage().window().fullscreen();
        return this;
    }

    public BrowserInteraction resize(int width, int height) {
        driver.manage().window().setSize(new Dimension(width, height));
        return this;
    }

    public BrowserInteraction move(int x, int y) {
        driver.manage().window().setPosition(new Point(x, y));
        return this;
    }

    // 🧹 Cookies
    public BrowserInteraction deleteCookies() {
        driver.manage().deleteAllCookies();
        return this;
    }

    public Cookie getCookie(String name) {
        return driver.manage().getCookieNamed(name);
    }

    // 🧠 JS Execution
    public Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }
}
