package io.github.cytaylorw.utils.selenium.test.data;

import java.util.Map;

@FunctionalInterface
public interface TestDataFileWriter {

	void write(Map<String, Object> data);
}
