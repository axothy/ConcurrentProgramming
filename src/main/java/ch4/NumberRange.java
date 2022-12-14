package ch4;

import java.util.concurrent.atomic.AtomicInteger;

public class NumberRange {
    //INVARIANT: lower <= upper

    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(0);

    public void setLower(int i) {
        if (i > upper.get()) {
            throw new IllegalArgumentException("cant set lower = " + i + " > upper");
        }
        lower.set(i);
    }

    public void setUpper(int i) {
        if (i > lower.get()) {
            throw new IllegalArgumentException("cant set upper = " + i + " < lower");
        }
        upper.set(i);
    }

    public boolean isRange(int i) {
        return (i >= lower.get() && i <= upper.get());
    }
}

