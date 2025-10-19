package io.github.cytaylorw.utils.selenium.test.action;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

import org.openqa.selenium.support.ui.ExpectedCondition;

import io.github.cytaylorw.utils.selenium.test.TestRunner;
import io.github.cytaylorw.utils.selenium.ui.SmartConditions;

public class Runner {


    @SafeVarargs
    public static <R extends TestRunner<R>> Consumer<R> compose(Consumer<R>... runners) {
        return Arrays.stream(runners).reduce(Consumer::andThen).orElse(r -> {});
    }
    
    @SuppressWarnings("unchecked")
    public static Consumer<? super TestRunner<?>> cast(Consumer<?>... runners) {
        return (Consumer<? super TestRunner<?>>)
        		Arrays.stream(runners)
        	      .reduce((a, b) -> x -> {
        	          ((Consumer<Object>) a).accept(x);
        	          ((Consumer<Object>) b).accept(x);
        	      })
                  .orElse(x -> {});
    }



    public static <T, R extends TestRunner<R>> Consumer<R> ifMet(String locator,Function<SmartConditions, ExpectedCondition<T>> conditionFunction, Consumer<R> runner) {
        return r -> {
            if(r.getManager().locator(locator).isConditionMet(conditionFunction)){
                runner.accept(r);
            }
        };
    }
    

    public static <T, R extends TestRunner<R>> Consumer<R> ifMet(ExpectedCondition<T> condition, Consumer<R> runner) {
        return r -> {
            if(r.getManager().isConditionMet(condition)){
                runner.accept(r);
            }
        };
    }

    public static <T, R extends TestRunner<R>> Consumer<R> ifMetOrElse(String locator,Function<SmartConditions, ExpectedCondition<T>> conditionFunction, Consumer<R> trueRunner, Consumer<R> elseRunner) {
        return r -> {
            if(r.getManager().locator(locator).isConditionMet(conditionFunction)){
                trueRunner.accept(r);
            }else{
                elseRunner.accept(r);
            }
        };
    }

    public static <T, R extends TestRunner<R>> Consumer<R> ifMetOrElse(ExpectedCondition<T> condition, Consumer<R> trueRunner, Consumer<R> elseRunner) {
        return r -> {
            if(r.getManager().isConditionMet(condition)){
                trueRunner.accept(r);
            }else{
                elseRunner.accept(r);
            }
        };
    }
}
