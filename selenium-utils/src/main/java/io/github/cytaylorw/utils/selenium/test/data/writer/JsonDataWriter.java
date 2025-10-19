package io.github.cytaylorw.utils.selenium.test.data.writer;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.github.cytaylorw.utils.selenium.test.data.TestDataFileWriter;

public class JsonDataWriter implements TestDataFileWriter{
	
	private static final Logger logger = Logger.getLogger(JsonDataWriter.class.getName());
	
	private final String filePath;
	
	public JsonDataWriter(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void write(Map<String, Object> data) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
            logger.info("Saved to: " + filePath);
        } catch (IOException e) {
        	logger.severe("Failed to save JSON: " + e.getMessage());
        }
	}

}
