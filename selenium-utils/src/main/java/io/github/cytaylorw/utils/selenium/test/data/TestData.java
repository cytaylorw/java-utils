package io.github.cytaylorw.utils.selenium.test.data;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import io.github.cytaylorw.utils.selenium.test.TestRunner;
import io.github.cytaylorw.utils.selenium.test.data.reader.JsonDataReader;
import io.github.cytaylorw.utils.selenium.test.data.writer.JsonDataWriter;

public class TestData {
	private static final Logger logger = Logger.getLogger(TestData.class.getName());
	
	private static final Map<String, Object> data = new ConcurrentHashMap<>();

    static {
        data.put("results", Collections.synchronizedMap(new LinkedHashMap<String, TestRunner<?>>()));
    }

    public static void put(String key, Object value) {
        data.put(key, value);
    }
    
    @SuppressWarnings("unchecked")
    public static <R extends TestRunner<R>> void putResult(R runner) {
    	((Map<String, TestRunner<?>>)data.get("results")).put(runner.getNumber(), runner);
    }

    public static Object get(String key) {
        return data.get(key);
    }
    
    public static String getAsString(String key) {
        return Optional.ofNullable(data.get(key)).map(Object::toString).orElse("null");
    }

    @SuppressWarnings("unchecked")
	public static Map<String, TestRunner<?>> getResults() {
        return (Map<String, TestRunner<?>>)data.get("results");
    }

    public static Map<String, Object> getAll() {
        return data;
    }

    public static void clear() {
        data.clear();
    }
    
    public static void read(TestDataFileReader reader) {
    	data.putAll(reader.read());
    }
    
    public static void write(TestDataFileWriter writer) {
    	writer.write(data);
    }

    public static void readFromJson(String filePath) {
    	read(new JsonDataReader(filePath));
    }

    public static void writeToJson(String filePath) {
    	write(new JsonDataWriter(filePath));
    }
    
    public static void writeToJson() {
    	writeToJson(Optional.ofNullable(data.get("resultFolder")).map(Object::toString).orElse("") + File.separator + generateResultFileName());
    }
    
    private static String generateResultFileName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return "result_" + timestamp + ".json";
    }



}
