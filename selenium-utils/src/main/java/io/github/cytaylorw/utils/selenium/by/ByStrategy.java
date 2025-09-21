package io.github.cytaylorw.utils.selenium.by;

import org.openqa.selenium.By;

@FunctionalInterface
public interface ByStrategy {
    By apply(String locator);
}