package io.fluentqa.table.base;

@FunctionalInterface
public interface StyleConsumer<T, U> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     */
    U accept(T t, U u);

}