package io.github.cytaylorw.utils.selenium.screenshot;

import java.nio.file.Path;

import org.openqa.selenium.WebDriver;

import io.github.cytaylorw.utils.selenium.action.LatencyProfile;
import io.github.cytaylorw.utils.selenium.core.WebManager;
import io.github.cytaylorw.utils.selenium.options.ScreenshotOptions;

public abstract class AbstractScreenshotHelper {

	protected final WebManager manager;
	protected final ScreenshotOptions options;
    protected final WebDriver driver;
    protected final Path baseFolder;
    protected final FilenameFormatter formatter;
    protected final LatencyProfile latency;

    public AbstractScreenshotHelper(WebManager manager, ScreenshotOptions options, LatencyProfile latency) {
    	this.manager = manager;
    	this.options = options;
        this.driver = manager.getDriver();
        this.baseFolder = Path.of(options.getFolder());
        this.formatter = options.getFormatter();
        this.latency = latency;
    }
    
    public AbstractScreenshotHelper(WebManager manager, LatencyProfile latency) {
        this(manager, manager.getOptions().screenshot(), latency);
    }
    
    public AbstractScreenshotHelper(WebManager manager) {
        this(manager, manager.getOptions().screenshot(), manager.getOptions().nativeLatency());
    }
    
    
    public AbstractScreenshotHelper(WebManager manager, ScreenshotOptions options) {
        this(manager, options, manager.getOptions().nativeLatency());
    }

    protected void sleepBeforeAction() {
        latency.apply();
    }

    public abstract Path capture(String label);
}
