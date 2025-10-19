package io.github.cytaylorw.utils.selenium.test.data;

import java.util.Map;

@FunctionalInterface
public interface TestDataFileReader {

	Map<String, Object> read();
}
