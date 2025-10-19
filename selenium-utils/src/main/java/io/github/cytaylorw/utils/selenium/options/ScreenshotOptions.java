package io.github.cytaylorw.utils.selenium.options;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.github.cytaylorw.utils.selenium.options.json.FilenameFormatterDeserializer;
import io.github.cytaylorw.utils.selenium.screenshot.FilenameFormatter;
import io.github.cytaylorw.utils.selenium.screenshot.FilenameFormatters;

public class ScreenshotOptions {
	
	private static final Logger logger = Logger.getLogger(ScreenshotOptions.class.getName());

    private String folder;
    private boolean overwrite = true;
    private FilenameFormatter formatter = FilenameFormatters.plain();

    public ScreenshotOptions() {
    }
    
    public ScreenshotOptions(String folder) {
        this.setFolder(folder);
    }

    public static ScreenshotOptions of(String folder) {
        return new ScreenshotOptions(folder);
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
        ensureFolderExists();
        
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public FilenameFormatter getFormatter() {
        return formatter;
    }

    @JsonDeserialize(contentUsing = FilenameFormatterDeserializer.class)
    @JsonProperty("formatter")
    public void setFormatter(FilenameFormatter formatter) {
        this.formatter = formatter;
    }

    // 🏭 Factory for error screenshot config
    public static ScreenshotOptions errorFrom(ScreenshotOptions base) {
        ScreenshotOptions error = new ScreenshotOptions(base.getFolder());
        error.setOverwrite(false);
        error.setFormatter(FilenameFormatters.errorFormatter());
        return error;
    }

    private void ensureFolderExists() {
        Path path = Paths.get(folder);
        try {
            Files.createDirectories(path);
            logger.info("Folder path: " + path.toAbsolutePath());
        } catch (IOException e) {
            logger.severe("Failed to create folder: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


}