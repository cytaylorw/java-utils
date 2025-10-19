package io.github.cytaylorw.utils.selenium.options;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import io.github.cytaylorw.utils.selenium.action.LatencyProfile;
import io.github.cytaylorw.utils.selenium.by.ByStrategies;
import io.github.cytaylorw.utils.selenium.by.ByStrategy;
import io.github.cytaylorw.utils.selenium.test.data.TestData;
import io.github.cytaylorw.utils.selenium.test.log.TestLogFormatter;

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
	
	private String dataFile;
	
	private String resultFolder;
	private String logFolder;

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
    
    public String getDataFile() {
    	return dataFile;
    }
    
    public void setDataFile(String dataFile) {
    	this.dataFile = dataFile;
    	TestData.readFromJson(dataFile);
    }
    
    public String getResultFolder() {
		return resultFolder;
	}

	public void setResultFolder(String resultFolder) {
		this.resultFolder = resultFolder;
		ensureFolderExists(resultFolder);
		TestData.put("resultFolder", resultFolder);
	}

	public String getLogFolder() {
		return logFolder;
		
	}

	public void setLogFolder(String logFolder) {
		this.logFolder = logFolder;
		ensureFolderExists(logFolder);
		configLogger();
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
            "\n  Data file: " + (getDataFile() != null ? getDataFile() : "null") +
            "\n}";
    }
	
    private void ensureFolderExists(String folder) {
        Path path = Paths.get(folder);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void configLogger() {
        try {
        	Formatter LogFormatter = new TestLogFormatter();

        	
            // Generate timestamped filename
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String timestamp = LocalDateTime.now().format(formatter);
            String logFileName = Optional.ofNullable(logFolder).orElse("logs") + File.separator + "app_" + timestamp + ".log";

            // Create file handler
            FileHandler fileHandler = new FileHandler(logFileName, true);
            fileHandler.setEncoding("UTF-8");
            fileHandler.setFormatter(LogFormatter);
            
            // Console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(LogFormatter);


            // Configure root logger
            Logger rootLogger = Logger.getLogger("");
            rootLogger.setLevel(Level.INFO);
            rootLogger.setUseParentHandlers(false);

            for (Handler handler : rootLogger.getHandlers()) {
                rootLogger.removeHandler(handler);
            }

            rootLogger.addHandler(fileHandler);
            rootLogger.addHandler(consoleHandler); 

        } catch (IOException e) {
            System.err.println("Failed to configure logging: " + e.getMessage());
        }
    }
}
