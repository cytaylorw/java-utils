package io.github.cytaylorw.utils.selenium.screenshot;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import io.github.cytaylorw.utils.selenium.action.LatencyProfile;
import io.github.cytaylorw.utils.selenium.core.WebManager;
import io.github.cytaylorw.utils.selenium.options.ScreenshotOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class ScreenshotHelper extends AbstractScreenshotHelper {

    public ScreenshotHelper(WebManager manager, ScreenshotOptions options, LatencyProfile latency) {
    	super(manager, options, latency);
    }
    
    public ScreenshotHelper(WebManager manager, LatencyProfile latency) {
        super(manager, manager.getOptions().screenshot(), latency);
    }
    
    public ScreenshotHelper(WebManager manager) {
    	super(manager, manager.getOptions().screenshot(), manager.getOptions().screenshotLatency());
    }
    
    
    public ScreenshotHelper(WebManager manager, ScreenshotOptions options) {
    	super(manager, options, manager.getOptions().screenshotLatency());
    }


    @Override
    public Path capture(String label) {
    	sleepBeforeAction();
        String filename = formatter.apply(label);
        Path destination = baseFolder.resolve(filename);

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
			Files.copy(src.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("Failed to capturescreenshot: " + e.getMessage(), e);
		}

        return destination;
    }
    
    public Path capture(WebElement element, String label) {
    	sleepBeforeAction();
        String filename = formatter.apply(label);
        Path destination = baseFolder.resolve(filename);

        try {
            File src = element.getScreenshotAs(OutputType.FILE);
            Files.copy(src.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            return destination;
        } catch (IOException e) {
            throw new RuntimeException("Failed to capture element screenshot: " + e.getMessage(), e);
        }
    }
}
