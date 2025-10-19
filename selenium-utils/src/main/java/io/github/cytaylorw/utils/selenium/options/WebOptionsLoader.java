package io.github.cytaylorw.utils.selenium.options;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import io.github.cytaylorw.utils.selenium.by.ByStrategy;
import io.github.cytaylorw.utils.selenium.options.json.ByStrategyDeserializer;
import io.github.cytaylorw.utils.selenium.options.json.FilenameFormatterDeserializer;
import io.github.cytaylorw.utils.selenium.screenshot.FilenameFormatter;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class WebOptionsLoader {
	
	private static final Logger logger = Logger.getLogger(WebOptionsLoader.class.getName());

    private static final String DEFAULT_FILENAME = "web-options.json";

    public static WebOptions loadFromJson(File jsonFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);


        SimpleModule module = new SimpleModule();
        module.addDeserializer(FilenameFormatter.class, new FilenameFormatterDeserializer());
        module.addDeserializer(ByStrategy.class, new ByStrategyDeserializer());
        mapper.registerModule(module);

        WebOptions options = mapper.readValue(jsonFile, WebOptions.class);
        logger.info("ScreenshotOptions: " + options.screenshot());
        logger.info("WaitOptions: " + options.waitOptions());


        if (options.errorScreenshot() == null && options.screenshot() != null) {
            options.setErrorScreenshotOptions(ScreenshotOptions.errorFrom(options.screenshot()));
        }

        logger.info("WebOptions loaded: " + options.waitOptions());
        return options;
    }

    public static WebOptions loadDefault() {
        File defaultFile = Paths.get(DEFAULT_FILENAME).toFile();
        logger.info("Looking for config at: " + defaultFile.getAbsolutePath());
        logger.info("Found: " + defaultFile.exists());
        if (defaultFile.exists()) {
            try {
                return loadFromJson(defaultFile);
            } catch (IOException e) {
            	logger.severe("⚠️ Failed to load config: " + e.getMessage());
            }
        }
        return new WebOptions();
    }
}
