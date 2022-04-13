package io.github.cytaylorw.utils.commons;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Taylor
 *
 * @param <T> the type of value
 */
public class Conditional<T> {

	/**
	 * Value to be evaluated.
	 */
	private final T value;
	
	/**
	 * Condition from the predicate test result.
	 */
	private boolean condition;
	
	/**
	 * Constructs an instance with the described value.
	 * 
	 * @param value value to be evaluated.
	 */
	private Conditional(T value) {
		this.value = value;		
	}
	
    /**
     * Returns a {@code Conditional} with the given value.
     * 
     * @param <T> the type of the value
     * @param value value to be checked.
     * @return a {@code Conditional} with the given value.
     */
    public static <T> Conditional<T> of(T value) {
        return new Conditional<>(value);
    }
    
    /**
     * Evaluate the value with the given predicate and return 
     * the {@code Conditional} instance.
     * 
     * @param predicate the predicate to apply to a value, if present
     * @return the {@code Conditional} instance.
     * @throws NullPointerException if the predicate is {@code null}
     */
    public Conditional<T> evaluate(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        this.condition = predicate.test(value);
        return this;
    }
    
    /**
     * If the condition is true, returns {@code true}, otherwise
     * {@code false}.
     * 
     * @return {@code true} if the condition is true, otherwise {@code false}
     */
    public boolean isTrue() {
        return condition;
    }
    
    /**
     * If the condition is false, returns {@code true}, otherwise
     * {@code false}.
     * 
     * @return {@code true} if the condition is false, otherwise {@code false}
     */
    public boolean isFalse() {
    	return !condition;
    }
    
    /**
     * If the condition is true, performs the given action with the value,
     * otherwise does nothing.
     * 
     * @param action the action to be performed, if the condition is true
     * @throws NullPointerException if the condition is true and the given 
     * 		   action is {@code null}
     */
    public void ifTrue(Consumer<? super T> action) {
        if (condition) {
            action.accept(value);
        }
    }
    
    /**
     * If the condition is false, performs the given action with the value,
     * otherwise does nothing.
     * 
     * @param action the action to be performed, if the condition is false
     * @throws NullPointerException if the condition is false and the given 
     * 		   action is {@code null}
     */
    public void ifFalse(Consumer<? super T> action) {
        if (!condition) {
            action.accept(value);
        }
    }
    
    /**
     * If the condition is true, performs the given action with the value,
     * otherwise performs the else action.
     * 
     * @param action the action to be performed, if the condition is true
     * @param elseAction the else action to be performed, if the condition is false
     * @throws NullPointerException if the condition is true and the given 
     * 		   action is {@code null} or the condition is false and the given 
     * 		   else action is {@code null}
     */
    public void ifTrueOrElse(Consumer<? super T> action, Consumer<? super T> elseAction) {
        if (condition) {
            action.accept(value);
        } else {
        	elseAction.accept(value);
        }
    }
    
    /**
     * If the condition is false, performs the given action with the value,
     * otherwise performs the else action.
     * 
     * @param action the action to be performed, if the condition is false
     * @param elseAction the else action to be performed, if the condition is true
     * @throws NullPointerException if the condition is false and the given 
     * 		   action is {@code null} or the condition is true and the given 
     * 		   else action is {@code null}
     */
    public void ifFalseOrElse(Consumer<? super T> action, Consumer<? super T> elseAction) {
        if (!condition) {
            action.accept(value);
        } else {
        	elseAction.accept(value);
        }
    }
    
    /**
     * If the condition is true, performs the given action with the value,
     * otherwise throws an exception produced by the exception supplying function.
     * 
     * @param <X> Type of the exception to be thrown
     * @param action the action to be performed, if the condition is true
     * @param exceptionSupplier the supplying function that produces an
     *        exception to be thrown
     * @throws X if the condition is false
     * @throws NullPointerException if the condition is true and the given 
     * 		   action is {@code null}
     */
    public <X extends Throwable> void ifTrueOrElseThrow(Consumer<? super T> action, Supplier<? extends X> exceptionSupplier) throws X {
        if (condition) {
            action.accept(value);
        } else {
        	throw exceptionSupplier.get();
        }
    }
    
    /**
     * If the condition is false, performs the given action with the value,
     * otherwise throws an exception produced by the exception supplying function.
     * 
     * @param <X> Type of the exception to be thrown
     * @param action the action to be performed, if the condition is false
     * @param exceptionSupplier the supplying function that produces an
     *        exception to be thrown
     * @throws X if the condition is true
     * @throws NullPointerException if the condition is false and the given 
     * 		   action is {@code null}
     */
    public <X extends Throwable> void ifFalseOrElseThrow(Consumer<? super T> action, Supplier<? extends X> exceptionSupplier) throws X {
        if (!condition) {
            action.accept(value);
        } else {
        	throw exceptionSupplier.get();
        }
    }
    
    /**
     * If the condition is true, throws an exception produced by
     * the exception supplying function.
     * 
     * @param <X> Type of the exception to be thrown
     * @param exceptionSupplier the supplying function that produces an
     *        exception to be thrown
     * @throws X if the condition is true
     */
    public <X extends Throwable> void ifTrueThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (condition) {
            throw exceptionSupplier.get();
        }
    }
    
    /**
     * If the condition is false, throws an exception produced by
     * the exception supplying function.
     * 
     * @param <X> Type of the exception to be thrown
     * @param exceptionSupplier the supplying function that produces an
     *        exception to be thrown
     * @throws X if the condition is false
     */
    public <X extends Throwable> void ifFalseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (!condition) {
            throw exceptionSupplier.get();
        }
    }
}
