package io.fluentqa.table.base;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
public class Result<T> {

    private AtomicInteger atomicSuccess = new AtomicInteger(0);
    private AtomicInteger atomicError   = new AtomicInteger(0);

    private Map<Integer, String> errorMap = new TreeMap<>();

    private List<T> rows;

    private List<T> successRows;
    private List<T> errorRows;

    public Result(List<T> rows) {
        this.rows = rows;
        this.successRows = new ArrayList<>(rows.size());
        this.errorRows = new ArrayList<>();
    }

    public Result<T> valid(RowPredicate<Integer, T> rowPredicate) {
        for (int i = 0, size = rows.size(); i < size; i++) {
            Valid valid = rowPredicate.test(i + 1, rows.get(i));
            if (null == valid) {
                continue;
            }
            if (valid.isSuccess()) {
                successRows.add(rows.get(i));
                atomicSuccess.incrementAndGet();
            } else {
                atomicError.incrementAndGet();
                errorRows.add(rows.get(i));
                errorMap.put(i + 1, valid.msg());
            }
        }
        return this;
    }

    public boolean isValid() {
        return Integer.valueOf(rows.size()).equals(atomicSuccess.get());
    }

    public int count() {
        return rows.size();
    }

    public int successCount() {
        return atomicSuccess.get();
    }

    public int errorCount() {
        return atomicError.get();
    }

    public List<T> rows() {
        return rows;
    }

    public List<T> successRows() {
        return successRows;
    }

    public List<T> errorRows() {
        return errorRows;
    }

    public List<String> errorMessages() {
        return new ArrayList<>(errorMap.values());
    }

    public Map<Integer, String> errorMap() {
        return errorMap;
    }

}