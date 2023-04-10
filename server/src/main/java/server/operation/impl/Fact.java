package server.operation.impl;

import server.operation.Operation;

import static java.lang.Long.MAX_VALUE;

public class Fact implements Operation {
    private final byte a;

    public Fact(final byte a) {
        this.a = a;
    }

    @Override
    public long solve() {
        long start = 1;
        for (int i = 1; i <= a; i++) {
            start *= i;
            if (start > MAX_VALUE / i) {
                return MAX_VALUE;
            }
        }
        return start;
    }

    @Override
    public String toReadableFormat() {
        return a + "!";
    }
}
