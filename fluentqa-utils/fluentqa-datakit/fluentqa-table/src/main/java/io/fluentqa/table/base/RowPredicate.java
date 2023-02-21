package io.fluentqa.table.base;

@FunctionalInterface
public interface RowPredicate<R, T> {

    Valid test(R r, T t);

}