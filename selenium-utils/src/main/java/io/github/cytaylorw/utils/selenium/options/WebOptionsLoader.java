package io.github.cytaylorw.utils.selenium.options;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import io.github.cytaylorw.utils.selenium.action.LatencyProfile;
import io.github.cytaylorw.utils.selenium.by.ByStrategy;
import io.github.cytaylorw.utils.selenium.options.json.ByStrategyDeserializer;
import io.github.cytaylorw.utils.selenium.options.json.FilenameFormatterDeserializer;
import io.github.cytaylorw.utils.selenium.screenshot.FilenameFormatter;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class WebOptionsLoader {

    private static final String DEFAULT_FILENAME = "web-options.json";

    public static WebOptions loadFromJson(File jsonFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);


        SimpleModule module = new SimpleModule();
        module.addDeserializer(FilenameFormatter.class, new FilenameFormatterDeserializer());
        module.addDeserializer(ByStrategy.class, new ByStrategyDeserializer());
        mapper.registerModule(module);

        WebOptions options = mapper.readValue(jsonFile, WebOptions.class);
        System.out.println("ScreenshotOptions: " + options.screenshot());
        System.out.println("WaitOptions: " + options.waitOptions());


        if (options.errorScreenshot() == null && options.screenshot() != null) {
            options.setErrorScreenshotOptions(ScreenshotOptions.errorFrom(options.screenshot()));
        }

        return options;
    }

    public static WebOptions loadDefault() {
        File defaultFile = Paths.get(DEFAULT_FILENAME).toFile();
        System.out.println("Looking for config at: " + defaultFile.getAbsolutePath());
        System.out.println("Found: " + defaultFile.exists());
        if (defaultFile.exists()) {
            try {
                return loadFromJson(defaultFile);
            } catch (IOException e) {
                System.err.println("⚠️ Failed to load config: " + e.getMessage());
            }
        }
        return new WebOptions();
    }
}
