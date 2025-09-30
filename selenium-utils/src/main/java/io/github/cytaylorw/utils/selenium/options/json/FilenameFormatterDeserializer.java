package io.github.cytaylorw.utils.selenium.options.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import io.github.cytaylorw.utils.selenium.screenshot.FilenameFormatter;
import io.github.cytaylorw.utils.selenium.screenshot.FilenameFormatters;

import java.io.IOException;

public class FilenameFormatterDeserializer extends JsonDeserializer<FilenameFormatter> {

    @Override
    public FilenameFormatter deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (node.isTextual()) {
            return switch (node.asText()) {
                case "plain" -> FilenameFormatters.plain();
                case "timestamped" -> FilenameFormatters.timestamped();
                case "error" -> FilenameFormatters.errorFormatter();
                default -> throw new IllegalArgumentException("Unknown formatter type: " + node.asText());
            };
        }

        String type = node.get("type").asText();
        String value = node.has("value") ? node.get("value").asText() : "";

        return switch (type) {
            case "prefix" -> FilenameFormatters.withPrefix(value);
            case "suffix" -> FilenameFormatters.withSuffix(value);
            default -> throw new IllegalArgumentException("Unknown formatter type: " + type);
        };
    }
}
