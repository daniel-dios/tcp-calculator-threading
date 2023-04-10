package server.operation.impl;

import server.operation.Operation;

public class Div implements Operation {
    private final byte a;
    private final byte b;

    public Div(final byte a, final byte b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public long solve() {
        if (b == 0) {
            return Long.MAX_VALUE;
        }
        return a / b;
    }

    @Override
    public String toReadableFormat() {
        return a + ":" + b;
    }
}
