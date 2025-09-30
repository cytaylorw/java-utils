package io.github.cytaylorw.utils.selenium.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.github.cytaylorw.utils.selenium.options.json.FilenameFormatterDeserializer;
import io.github.cytaylorw.utils.selenium.screenshot.FilenameFormatter;
import io.github.cytaylorw.utils.selenium.screenshot.FilenameFormatters;

public class ScreenshotOptions {

    private String folder;
    private boolean overwrite = true;
    private FilenameFormatter formatter = FilenameFormatters.plain();

    public ScreenshotOptions() {
    }
    
    public ScreenshotOptions(String folder) {
        this.folder = folder;
    }

    public static ScreenshotOptions of(String folder) {
        return new ScreenshotOptions(folder);
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
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

}