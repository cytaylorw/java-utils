package io.github.cytaylorw.utils.selenium.options;

import java.util.List;

import io.github.cytaylorw.utils.selenium.action.LatencyProfile;
import io.github.cytaylorw.utils.selenium.by.ByStrategies;
import io.github.cytaylorw.utils.selenium.by.ByStrategy;

public class WebOptions {

	private ScreenshotOptions normalScreenshotOptions;
	private ScreenshotOptions errorScreenshotOptions;

	private WaitOptions waitOptions;

	private LatencyProfile latencyProfile = LatencyProfile.low();
	private LatencyProfile nativeLatencyProfile;
	private LatencyProfile actionLatencyProfile;
	private LatencyProfile jsLatencyProfile;
	private LatencyProfile screenshotLatencyProfile;
	private LatencyProfile robotLatencyProfile;

	private List<ByStrategy> strategies;

    public WebOptions() {
        this.strategies = ByStrategies.defaultChain();
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

    public void setWaitOptions(WaitOptions waitOptions) {
        this.waitOptions = waitOptions;
    }

    public void setLatencyProfile(LatencyProfile latencyProfile) {
        this.latencyProfile = latencyProfile;
    }

    public void setNativeLatencyProfile(LatencyProfile profile) {
        this.nativeLatencyProfile = profile;
    }

    public void setActionLatencyProfile(LatencyProfile profile) {
        this.actionLatencyProfile = profile;
    }

    public void setJsLatencyProfile(LatencyProfile profile) {
        this.jsLatencyProfile = profile;
    }

    public void setStrategies(List<ByStrategy> strategies) {
        this.strategies = strategies;
    }
    
    public List<ByStrategy> getStrategies() {
    	return strategies;
    }

    // 📸 Screenshot Options
    public ScreenshotOptions screenshot() {
        return normalScreenshotOptions;
    }

    public ScreenshotOptions errorScreenshot() {
        return errorScreenshotOptions;
    }

    // ⏱️ Wait Options
    public WaitOptions waitOptions() {
        return waitOptions;
    }


    // 🐢 Active Latency Profile
    public LatencyProfile latency() {
        return latencyProfile;
    }


    // 🧩 Configurable latency presets
    public LatencyProfile nativeLatency() {
        return nativeLatencyProfile == null ? latencyProfile : nativeLatencyProfile;
    }

    public LatencyProfile actionLatency() {
        return actionLatencyProfile == null ? latencyProfile : actionLatencyProfile;
    }

    public LatencyProfile jsLatency() {
        return jsLatencyProfile == null ? latencyProfile : jsLatencyProfile;
    }

    public LatencyProfile screenshotLatency() {
        return screenshotLatencyProfile == null ? latencyProfile : screenshotLatencyProfile;
    }
    

    public LatencyProfile robotLatency() {
        return robotLatencyProfile == null ? latencyProfile : robotLatencyProfile;
    }
    
    @Override
    public String toString() {
        return "WebOptions {" +
            "\n  Screenshot Folder: " + (screenshot() != null ? screenshot().getFolder() : "null") +
            "\n  Error Screenshot Folder: " + (errorScreenshot() != null ? errorScreenshot().getFolder() : "null") +
            "\n  Wait Timeout: " + (waitOptions() != null ? waitOptions().getTimeout() : "null") +
            "\n  Polling Interval: " + (waitOptions() != null ? waitOptions().getPollingInterval() : "null") +
            "\n  Ignore Stale: " + (waitOptions() != null && waitOptions().isIgnoreStale()) +
            "\n  Latency Profile: " + (latency() != null ? latency().toString() : "null") +
            "\n  Strategies: " + (strategies != null ? strategies.size() + " configured :  1st element: " + strategies.get(0) : "null") +
            "\n}";
    }
}
