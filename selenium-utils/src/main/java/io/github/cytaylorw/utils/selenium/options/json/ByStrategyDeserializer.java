package io.github.cytaylorw.utils.selenium.options.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import io.github.cytaylorw.utils.selenium.by.ByStrategies;
import io.github.cytaylorw.utils.selenium.by.ByStrategy;

import java.io.IOException;

public class ByStrategyDeserializer extends JsonDeserializer<ByStrategy> {
    @Override
    public ByStrategy deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String type = p.getText();
        return switch (type) {
        	case "auto" -> ByStrategies.autoDetect();
            case "id" -> ByStrategies.id();
            case "text" -> ByStrategies.exactText();
            case "textContains" -> ByStrategies.textContains();
            case "css" -> ByStrategies.css();
            case "xpath" -> ByStrategies.xpath();
            default -> throw new IllegalArgumentException("Unknown strategy: " + type);
        };
    }
}
