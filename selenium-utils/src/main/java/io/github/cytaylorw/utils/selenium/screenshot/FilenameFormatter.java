package io.github.cytaylorw.utils.selenium.screenshot;

import java.util.function.Function;

@FunctionalInterface
public interface FilenameFormatter extends Function<String, String> {
    String apply(String label);
}
