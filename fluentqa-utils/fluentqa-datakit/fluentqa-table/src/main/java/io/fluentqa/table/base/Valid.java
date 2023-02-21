package io.fluentqa.table.base;

public class Valid {

    /**
     * Current row verification success identifier
     */
    private boolean success;

    /**
     * Prompt message when verification fails
     */
    private String msg;

    private Valid() {
    }

    public boolean isSuccess() {
        return success;
    }

    public String msg() {
        return msg;
    }

    public static Valid ok() {
        Valid valid = new Valid();
        valid.success = true;
        return valid;
    }

    public static Valid error(String msg) {
        Valid valid = new Valid();
        valid.success = false;
        valid.msg = msg;
        return valid;
    }

}
