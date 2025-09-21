package io.github.cytaylorw.utils.selenium.options;

import java.util.List;

import org.openqa.selenium.WebDriver;
import io.github.cytaylorw.utils.selenium.action.LatencyProfile;
import io.github.cytaylorw.utils.selenium.by.ByStrategies;
import io.github.cytaylorw.utils.selenium.by.ByStrategy;

public class WebOptions {

    private final WebDriver driver;

    private ScreenshotOptions normalScreenshotOptions;
    private ScreenshotOptions errorScreenshotOptions;
    private WaitOptions waitOptions;
    private List<ByStrategy> strategies;
    private LatencyProfile latencyProfile;

    // 🔁 Configurable latency presets
    private LatencyProfile lowLatencyProfile = LatencyProfile.low();
    private LatencyProfile mediumLatencyProfile = LatencyProfile.medium();
    private LatencyProfile highLatencyProfile = LatencyProfile.high();

    public WebOptions(WebDriver driver) {
        this.driver = driver;
        this.strategies = ByStrategies.defaultChain();
    }

    public WebDriver getDriver() {
        return driver;
    }
    
    public void setStragegies(List<ByStrategy> strategies) {
    	this.strategies = strategies;
    }
    
    public List<ByStrategy> getStragegies() {
    	return strategies;
    }

    // 📸 Screenshot Options
    public ScreenshotOptions screenshot() {
        return normalScreenshotOptions;
    }

    public ScreenshotOptions errorScreenshot() {
        return errorScreenshotOptions;
    }

    public void setScreenshotOptions(ScreenshotOptions normal) {
        this.normalScreenshotOptions = normal;
        if (this.errorScreenshotOptions == null && normal != null) {
            this.errorScreenshotOptions = ScreenshotOptions.errorFrom(normal);
        }
    }

    public void setErrorScreenshotOptions(ScreenshotOptions error) {
        this.errorScreenshotOptions = error;
    }

    // ⏱️ Wait Options
    public WaitOptions waitOptions() {
        return waitOptions;
    }

    public void setWaitOptions(WaitOptions waitOptions) {
        this.waitOptions = waitOptions;
    }

    // 🐢 Active Latency Profile
    public LatencyProfile latency() {
        return latencyProfile;
    }

    public void setLatencyProfile(LatencyProfile latencyProfile) {
        this.latencyProfile = latencyProfile;
    }

    // 🧩 Configurable latency presets
    public LatencyProfile lowLatency() {
        return lowLatencyProfile;
    }

    public LatencyProfile mediumLatency() {
        return mediumLatencyProfile;
    }

    public LatencyProfile highLatency() {
        return highLatencyProfile;
    }

    public void setLowLatencyProfile(LatencyProfile profile) {
        this.lowLatencyProfile = profile;
    }

    public void setMediumLatencyProfile(LatencyProfile profile) {
        this.mediumLatencyProfile = profile;
    }

    public void setHighLatencyProfile(LatencyProfile profile) {
        this.highLatencyProfile = profile;
    }
}
