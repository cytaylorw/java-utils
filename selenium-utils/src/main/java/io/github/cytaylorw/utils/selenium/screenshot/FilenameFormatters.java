package io.github.cytaylorw.utils.selenium.screenshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class FilenameFormatters {

    private FilenameFormatters() {
        // Utility class — prevent instantiation
    }

    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    /** Plain formatter: label.png */
    public static FilenameFormatter plain() {
        return label -> label + ".png";
    }

    /** Timestamped formatter: label-20250921-2050.png */
    public static FilenameFormatter timestamped() {
        return label -> {
            String ts = LocalDateTime.now().format(TIMESTAMP_FORMAT);
            return label + "-" + ts + ".png";
        };
    }

    /** Custom suffix formatter: label_suffix.png */
    public static FilenameFormatter withSuffix(String suffix) {
        return label -> label + "_" + suffix + ".png";
    }

    /** Custom prefix formatter: prefix_label.png */
    public static FilenameFormatter withPrefix(String prefix) {
        return label -> prefix + "_" + label + ".png";
    }

    /** Error formatter: error_label-20250921-2050.png */
    public static FilenameFormatter errorFormatter() {
        return label -> {
            String ts = LocalDateTime.now().format(TIMESTAMP_FORMAT);
            return "error_" + label + "-" + ts + ".png";
        };
    }
}