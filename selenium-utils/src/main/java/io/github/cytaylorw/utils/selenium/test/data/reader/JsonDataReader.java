package io.github.cytaylorw.utils.selenium.test.data.reader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cytaylorw.utils.selenium.test.data.TestDataFileReader;

public class JsonDataReader implements TestDataFileReader {
	
	private final String filePath;
	
	public JsonDataReader(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public Map<String, Object> read() {
        ObjectMapper mapper = new ObjectMapper();
        try {
        	Map<String, Object> config =  mapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {});
        	return config;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + e.getMessage(), e);
        }
		
	}

}
