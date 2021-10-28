package io.github.cytaylorw.utils.commons;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class Checker<T> {

	private final T value;
	
	private boolean testResult;
	
	private Checker(T value) {
		this.value = value;		
	}
	
    public static <T> Checker<T> of(T value) {
        return new Checker<>(value);
    }
    
    public Checker<T> verify(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        this.testResult = predicate.test(value);
        return this;
    }
    
    public boolean isTrue() {
        return testResult;
    }
    
    public boolean isFalse() {
    	return !testResult;
    }
}
